/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** not sure about comment tokens */
object TokensFind
{
  type TokensMon = EMon[List[Token]]
  /** Max numbers for long and hexidecimal formats needs to be implemented */
  def apply(srcStr: String, fileName: String): TokensMon = mainLoop(srcStr.toList, new FilePosn(fileName, 1, 1), Nil)
  def fromString(srcStr: String): TokensMon = apply(srcStr, "FromString")
     
  private def mainLoop(rem: List[Char], tp: TextPosn, tokenAcc: List[Token]): TokensMon = rem match
  { 
    case Nil => tokenAcc.gRet      
    
    case '/' :: '*' :: rem =>
    {
      def loop(rem: Seq[Char], tp: TextPosn): TokensMon = rem match
      { case Nil => tokenAcc.gRet
        case '*' :: '/' :: rem => mainLoop(rem, tp, tokenAcc)
        case _ :: rem => loop(rem, tp.nextChar) 
      }      
      loop(rem, tp.addLinePosn(2))
    }
    
    case '0' :: 'x' :: tail  =>
    {
      def hexIntLoop(rem: List[Char], strAcc: String, intAcc: Int): TokensMon = rem match
      {
        case Nil => (HexIntToken(tp, strAcc, intAcc) :: tokenAcc).gRet
        case h :: tail => h match
        {
          case d if d.isHexDigit && (strAcc.length == 9) && tail.ifHead(_.isDigit) => hexLongLoop(rem, strAcc, intAcc.toLong)                  
          case d if d.isDigit => hexIntLoop(tail, strAcc - d.toString, (intAcc * 16) + d - '0')
          case al if (al <= 'F') && (al >= 'A') => hexIntLoop(tail, strAcc - al.toString, (intAcc * 16) + al - 'A' + 10)
          case al if (al <= 'f') && (al >= 'a') => hexIntLoop(tail, strAcc - al.toString, (intAcc * 16) + al - 'a' + 10)
          case _ => {deb("ml") ;mainLoop(rem, tp.addStr(strAcc), IntToken(tp, strAcc, intAcc) :: tokenAcc) }
        }
      }            
      def hexLongLoop(rem: List[Char], strAcc: String, longAcc: Long): TokensMon = rem match
      {
        case Nil => (LongIntToken(tp, strAcc, longAcc) :: tokenAcc).gRet
        case d :: tail if d.isHexDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")                  
        case d :: tail if d.isDigit => hexLongLoop(tail, strAcc - d.toString, (longAcc * 16) + d - '0')
        case al :: tail if (al <= 'F') && (al >= 'A') => hexLongLoop(tail, strAcc - al.toString, (longAcc * 16) + al - 'A' + 10)
        case al :: tail if (al <= 'f') && (al >= 'a') => hexLongLoop(tail, strAcc - al.toString, (longAcc * 16) + al - 'a' + 10)
        case _ :: tail => mainLoop(rem, tp.addStr(strAcc), LongIntToken(tp, strAcc, longAcc) :: tokenAcc)        
      }            
      hexIntLoop(tail, "0x", 0)
    }
    
    case h :: tail => h match
    {
      case '\n' => mainLoop(tail, tp.newLine, tokenAcc)
      case ';' => mainLoop(tail, tp.nextChar,  SemicolonToken(tp) :: tokenAcc)
      case ',' => mainLoop(tail, tp.nextChar,  CommaToken(tp) :: tokenAcc)
      case '(' => mainLoop(tail, tp.nextChar,  ParenthOpen(tp) :: tokenAcc)
      case ')' => mainLoop(tail, tp.nextChar,  ParenthClose(tp) :: tokenAcc)
      case '[' => mainLoop(tail, tp.nextChar,  SquareOpen(tp) :: tokenAcc)
      case ']' => mainLoop(tail, tp.nextChar,  SquareClose(tp) :: tokenAcc)      
      case '.' => mainLoop(tail, tp.nextChar,  DotToken(tp) :: tokenAcc)
      
      case c if c.isDigit =>
      {
        def intLoop(rem: List[Char], str: String, intAcc: Int): TokensMon = rem match
        {
          case Nil =>  (IntToken(tp, str, intAcc) :: tokenAcc).gRet
          case d :: t if d.isDigit && str.length == 9 && t.ifHead(_.isDigit) => longLoop(rem, str, intAcc.toLong)
          case d :: tail if d.isDigit && str.length == 9 && intAcc > 214748364 => longLoop(rem, str, intAcc.toLong)
          case d :: tail if d.isDigit && str.length == 9 && intAcc == 214748364 && d > '7' => longLoop(rem, str, intAcc.toLong)
          case d :: tail if d.isDigit => intLoop(tail, str - d.toString, (intAcc * 10) + d - '0')
          case '.' :: tail => decimalLoop(tail, str - h.toString, intAcc, 10)
          case _ :: tail => mainLoop(rem, tp.addStr(str),  IntToken(tp, str, intAcc) :: tokenAcc)
       }
                 
       def longLoop(rem: List[Char], str: String, longAcc: Long): TokensMon = rem match
       {
         case Nil => (LongIntToken(tp, str, longAcc) :: tokenAcc).gRet
         case d :: tail if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
         case d :: tail if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad1(tp, "Integer too big for 64 bit value")
         case d :: tail if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' => bad1(tp, "Integer too big for 64 bit value")
         case d :: tail if d.isDigit => longLoop(tail, str - d.toString, (longAcc * 10) + d - '0')
         case '.' :: tail => decimalLoop(tail, str - h.toString, longAcc, 10)
         case _ :: tail => mainLoop(rem, tp.addStr(str),  LongIntToken(tp, str, longAcc) :: tokenAcc)
       }      
                     
       def decimalLoop(rem: List[Char], str: String, floatAcc: Double, divisor: Double): TokensMon = rem match
       {
         case Nil => (FloatToken(tp, str, floatAcc) :: tokenAcc).gRet
         case d :: tail if d.isDigit => decimalLoop(tail, str - d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
         case c :: tail => mainLoop(rem, tp.addStr(str),  FloatToken(tp, str, floatAcc) :: tokenAcc)
       }
       intLoop(tail, c.toString, c - '0')
     }
      
     case a if a.isLetter =>
     { val (alphaStr, finalTail) = rem.span(a => a.isLetterOrDigit | a == '.')      
       mainLoop(finalTail, tp.addChars(alphaStr),  AlphaToken(tp, Symbol(alphaStr.mkString)) :: tokenAcc)            
     }
     
     case '\'' => ???
     
     case '\"' =>
     {
       def loop(rem: List[Char], strAcc: List[Char]): TokensMon = rem match
       {
         case Nil => bad1(tp, "Unclosed String")                  
         case '\"' :: tail2 => mainLoop(tail2, tp.addLinePosn(strAcc.length + 2),  StringToken(tp, strAcc.mkString) :: tokenAcc)
         case '\\' :: tail2 => tail2 match
         {
           case Nil =>  bad1(tp, "Unclosed String ending with unclosed escape Sequence")
           case c2 :: tail3 => c2 match
           { 
             case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc :+ c2)
             case c2 => bad1(tp, "Unrecognised escape Sequence \\" - c2.toString)
           }
         }               
         case h :: tail2 => loop(tail2, strAcc :+ h)
       }
       loop(tail, Nil)
     }
     
     case c if isOperator(c) =>
     {      
       val (opChars, finalTail) = rem.span(isOperator)
       val opStr = opChars.mkString
       
       val ot =  opChars.last match
       { case '+' | '-' => ife(h.isWhitespace, PlusInToken(tp, opStr), PlusPreToken(tp, opStr))
         case '=' => AsignToken(tp)
         case _ => OtherOperatorToken(tp, opStr)         
       }
       mainLoop(finalTail, tp.addChars(opChars),  ot :: tokenAcc)            
     }
     
     case h if h.isWhitespace => mainLoop(tail, tp.nextChar, tokenAcc)
     case c => throw new Exception("Unimplemented character in main loop: " + c.toString)
   }
  }
   
  private def isOperator(char: Char): Boolean = char match
  {
    case '+' | '-' | '*' | '/' | '=' => true
    case _ => false
  } 
}
