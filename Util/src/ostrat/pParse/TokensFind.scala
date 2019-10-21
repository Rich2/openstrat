/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** not sure about comment tokens */
object TokensFind
{
  type TokensMon = EMon[List[Token]]
  /** Max numbers for long and hexadecimal formats needs to be implemented */
  def apply(srcStr: String, fileName: String): TokensMon = mainLoop(srcStr.toList, new FilePosn(fileName, 1, 1), Nil)
  def fromString(srcStr: String): TokensMon = apply(srcStr, "FromString")
     
  private def mainLoop(rem: List[Char], tp: TextPosn, tokenAcc: List[Token]): TokensMon = rem match
  {
    case Nil => tokenAcc.goodReverse
    case ';' :: tail => mainLoop(tail, tp.nextChar,  SemicolonToken(tp) :: tokenAcc)
    case ',' :: tail => mainLoop(tail, tp.nextChar,  CommaToken(tp) :: tokenAcc)
    case '(' :: tail => mainLoop(tail, tp.nextChar,  ParenthOpen(tp) :: tokenAcc)
    case ')' :: tail => mainLoop(tail, tp.nextChar,  ParenthClose(tp) :: tokenAcc)
    case '[' :: tail => mainLoop(tail, tp.nextChar,  SquareOpen(tp) :: tokenAcc)
    case ']' :: tail => mainLoop(tail, tp.nextChar,  SquareClose(tp) :: tokenAcc)      
    case '.' :: tail => mainLoop(tail, tp.nextChar,  DotToken(tp) :: tokenAcc)
    case '\n' :: tail => mainLoop(tail, tp.newLine, tokenAcc)
    case h :: tail if h.isWhitespace => mainLoop(tail, tp.nextChar, tokenAcc)
    case '\'' :: '\\' :: '\\' :: tail2 => mainLoop(tail2, tp.right(4), CharToken(tp, '\\') :: tokenAcc)
    case '\'' ::  '\\' :: '\"' :: tail2 => mainLoop(tail2, tp.right(4), CharToken(tp, '\"') :: tokenAcc)
    case '\'' ::  '\\' :: '\'' :: tail2 => mainLoop(tail2, tp.right(4), CharToken(tp, '\'') :: tokenAcc)
    case '\'' ::  c1 :: '\'' :: tail2 => mainLoop(tail2, tp.right(3), CharToken(tp, c1) :: tokenAcc)       
    case '\'' ::  _ => bad1(tp, "Unclosed Character literal.")   
    
    case a :: tail if a.isLetter =>
    { 
      val (alphaStr, finalTail) = rem.span(a => a.isLetterOrDigit | a == '.')      
      mainLoop(finalTail, tp.addChars(alphaStr),  AlphaToken(tp, alphaStr.mkString) :: tokenAcc)            
    }
    
    case '/' :: '*' :: rem =>
    {
      def loop(rem: Seq[Char], tp: TextPosn): TokensMon = rem match
      { case Nil => tokenAcc.goodReverse
        case '*' :: '/' :: rem => mainLoop(rem, tp, tokenAcc)
        case _ :: rem => loop(rem, tp.nextChar) 
      }      
      loop(rem, tp.addLinePosn(2))
    }
    
    case _ =>
    {
      val possToken: EMon[(List[Char], TextPosn, Token)] = rem match
      {
        case '0' :: 'x' :: tail  => zeroX(tail, tp)
        case d :: tail if d.isDigit => digitStart(tail, tp, d)
        case c :: tail if isOperator(c) => operatorStart(rem, tp)
        case '\"' :: tail => quoteStart(tail, tp)
        case c :: tail => bad1(tp, "Unimplemented character in main loop: " + c.toString)        
      }
      possToken.flatMap{pair =>
        val (rem, tp, token) = pair
        mainLoop(rem, tp, token :: tokenAcc)
      }
    }  
  }
  
  private[this] def quoteStart(rem: List[Char], tp: TextPosn): EMon[(List[Char], TextPosn, Token)] =  
  {
    def loop(rem: List[Char], strAcc: List[Char]): EMon[(List[Char], TextPosn, Token)] = rem match
    {
      case Nil => bad1(tp, "Unclosed String")                  
      case '\"' :: tail2 => Good3(tail2, tp.addLinePosn(strAcc.length + 2),  StringToken(tp, strAcc.mkString))
      
      case '\\' :: tail2 => tail2 match
      {
        case Nil =>  bad1(tp, "Unclosed String ending with unclosed escape Sequence")
        
        case c2 :: tail3 => c2 match
        { 
          case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc :+ c2)
          case c2 => bad1(tp, "Unrecognised escape Sequence \\" + c2.toString)
        }
      }               
      case h :: tail2 => loop(tail2, strAcc :+ h)
    }
    loop(rem, Nil)
  }
  
  /** Needs fixing */
  private[this] def operatorStart(rem: List[Char], tp: TextPosn): EMon[(List[Char], TextPosn, Token)] =  
  {      
    val (opChars, finalTail) = rem.span(isOperator)
    val opStr = opChars.mkString
   
    val ot =  opChars.last match
    {
      //below makes no sense
      case '+' | '-' => finalTail match
      {
        case h :: _ if !h. isWhitespace => PlusPreToken(tp, opStr)          
        case _ => PlusInToken(tp, opStr)
      }
      case '=' => AsignToken(tp)
      case _ => OtherOperatorToken(tp, opStr)         
    }
    Good3(finalTail, tp.addChars(opChars),  ot)            
  }    
  
  private[this] def digitStart(rem: List[Char], tp: TextPosn, firstDigit: Char): EMon[(List[Char], TextPosn, Token)] =    
  {
    def intLoop(rem: List[Char], str: String, intAcc: Int): EMon[(List[Char], TextPosn, Token)] = rem match
    {
      case Nil =>  Good3(rem, tp.addStr(str), IntDecToken(tp, str, intAcc))
      case d :: t if d.isDigit && str.length == 9 && t.ifHead(_.isDigit) => longLoop(rem, str, intAcc.toLong)
      case d :: tail if d.isDigit && str.length == 9 && intAcc > 214748364 => longLoop(rem, str, intAcc.toLong)
      case d :: tail if d.isDigit && str.length == 9 && intAcc == 214748364 && d > '7' => longLoop(rem, str, intAcc.toLong)
      case d :: tail if d.isDigit => intLoop(tail, str + d.toString, (intAcc * 10) + d - '0')
      case '.' :: tail => decimalLoop(tail, str + firstDigit.toString, intAcc, 10)
      case _ :: tail => Good3(rem, tp.addStr(str),  IntDecToken(tp, str, intAcc))
    }
             
    def longLoop(rem: List[Char], str: String, longAcc: Long): EMon[(List[Char], TextPosn, Token)] = rem match
    {
      case Nil => Good3(rem, tp.addStr(str),LongIntToken(tp, str, longAcc))
      case d :: tail if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case d :: tail if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad1(tp, "Integer too big for 64 bit value")
      case d :: tail if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' => bad1(tp, "Integer too big for 64 bit value")
      case d :: tail if d.isDigit => longLoop(tail, str + d.toString, (longAcc * 10) + d - '0')
      case '.' :: tail => decimalLoop(tail, str + firstDigit.toString, longAcc, 10)
      case _ :: tail => Good3(rem, tp.addStr(str), LongIntToken(tp, str, longAcc))
    }      
                 
    def decimalLoop(rem: List[Char], str: String, floatAcc: Double, divisor: Double): EMon[(List[Char], TextPosn, Token)] = rem match
    {
      case Nil => Good3(rem, tp.addStr(str), FloatToken(tp, str, floatAcc))
      case d :: tail if d.isDigit => decimalLoop(tail, str + d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
      case c :: tail => Good3(rem, tp.addStr(str),  FloatToken(tp, str, floatAcc))
    }
    intLoop(rem, firstDigit.toString, firstDigit - '0')
  }  
  
  private[this] def zeroX(rem: List[Char], tp: TextPosn): EMon[(List[Char], TextPosn, Token)] = 
  {
      def hexIntLoop(rem: List[Char], strAcc: String, intAcc: Int): EMon[(List[Char], TextPosn, Token)] = rem match
      {
        case Nil => Good3(rem, tp.addStr(strAcc), IntHexToken(tp, strAcc, intAcc))
        case h :: tail => h match
        {
          case d if d.isHexDigit && (strAcc.length == 9) && tail.ifHead(_.isDigit) => hexLongLoop(rem, strAcc, intAcc.toLong)                  
          case d if d.isDigit => hexIntLoop(tail, strAcc + d.toString, (intAcc * 16) + d - '0')
          case al if (al <= 'F') && (al >= 'A') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'A' + 10)
          case al if (al <= 'f') && (al >= 'a') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'a' + 10)
          case _ => Good3(rem, tp.addStr(strAcc), IntDecToken(tp, strAcc, intAcc))
        }
      }            
      def hexLongLoop(rem: List[Char], strAcc: String, longAcc: Long): EMon[(List[Char], TextPosn, Token)] = rem match
      {
        case Nil => Good3(rem, tp.addStr(strAcc), LongIntToken(tp, strAcc, longAcc))
        case d :: tail if d.isHexDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")                  
        case d :: tail if d.isDigit => hexLongLoop(tail, strAcc + d.toString, (longAcc * 16) + d - '0')
        case al :: tail if (al <= 'F') && (al >= 'A') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'A' + 10)
        case al :: tail if (al <= 'f') && (al >= 'a') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'a' + 10)
        case _ :: tail => Good3(rem, tp.addStr(strAcc), LongIntToken(tp, strAcc, longAcc))       
      }            
      hexIntLoop(rem, "0x", 0)
    }  
   
  private[this] def isOperator(char: Char): Boolean = char match
  {
    case '+' | '-' | '*' | '/' | '=' => true
    case _ => false
  } 
}