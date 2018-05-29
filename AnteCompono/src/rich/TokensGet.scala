/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

object TokensGet
{
   /** Max numbers for long and hexidecimal formats needs to be implemented */
   def apply(srcStr: String, fileName: String): TokensMon = mainLoop(srcStr.toSeq, FilePosn(1, 1, fileName), Seq())
   /** Max numbers for long and hexidecimal formats needs to be implemented */
   def fromString(srcStr: String): TokensMon = mainLoop(srcStr.toSeq, FilePosn.fromString(1), Seq())
     
   def mainLoop(rem: Seq[Char], fp: FilePosn, tokenAcc: Tokens): TokensMon = rem match
   {
      case Seq() => Good(tokenAcc)
      case Seq('0', 'x', tail @ _*) =>
         {
            def hexIntLoop(rem: Seq[Char], strAcc: String, intAcc: Int): TokensMon = rem match
            {
               case Seq() => Good(tokenAcc :+ IntToken(fp, strAcc, intAcc))
               case Seq(h, tail @ _*) => h match
               {
                  case d if d.isHexDigit && (strAcc.length == 9) && tail.ifHead(_.isDigit) => hexLongLoop(rem, strAcc, intAcc.toLong)                  
                  case d if d.isDigit => hexIntLoop(tail, strAcc - d.toString, (intAcc * 16) + d - '0')
                  case al if (al <= 'E') && (al >= 'A') => hexIntLoop(tail, strAcc - al.toString, (intAcc * 16) + al - 'A' + 10)
                  case al if (al <= 'e') && (al >= 'a') => hexIntLoop(tail, strAcc - al.toString, (intAcc * 16) + al - 'a' + 10)
                  case _ => mainLoop(rem, fp.addStr(strAcc), tokenAcc :+ IntToken(fp, strAcc, intAcc))
               }
            }
            def hexLongLoop(rem: Seq[Char], strAcc: String, longAcc: Long): TokensMon = rem match
            {
               case Seq() => Good(tokenAcc :+ LongIntToken(fp, strAcc, longAcc))
               case Seq(h, tail @ _*) => h match
               {
                  case d if d.isHexDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(fp, "Integer too big for 64 bit value")                  
                  case d if d.isDigit => hexLongLoop(tail, strAcc - d.toString, (longAcc * 16) + d - '0')
                  case al if (al <= 'E') && (al >= 'A') => hexLongLoop(tail, strAcc - al.toString, (longAcc * 16) + al - 'A' + 10)
                  case al if (al <= 'e') && (al >= 'a') => hexLongLoop(tail, strAcc - al.toString, (longAcc * 16) + al - 'a' + 10)
                  case _ => mainLoop(rem, fp.addStr(strAcc), tokenAcc :+ LongIntToken(fp, strAcc, longAcc))
               }
            }
            
            hexIntLoop(tail, "0x", 0)
         }
      case Seq(h, tail @ _*) => h match
      {
         case '\n' => mainLoop(tail, FilePosn(fp.lineNum + 1, 1, fp.fileName), tokenAcc)
         case ';' => mainLoop(tail, fp.nextChar, tokenAcc :+ SemicolonToken(fp))
         case ',' => mainLoop(tail, fp.nextChar, tokenAcc :+ CommaToken(fp))
         case '(' => mainLoop(tail, fp.nextChar, tokenAcc :+ ParenthOpen(fp))
         case ')' => mainLoop(tail, fp.nextChar, tokenAcc :+ ParenthClose(fp))
         case '[' => mainLoop(tail, fp.nextChar, tokenAcc :+ SquareOpen(fp))
         case ']' => mainLoop(tail, fp.nextChar, tokenAcc :+ SquareClose(fp))      
         case '.' => mainLoop(tail, fp.nextChar, tokenAcc :+ DotToken(fp))
      
         case c if c.isDigit =>
            {
               def intLoop(rem: Seq[Char], str: String, intAcc: Int): TokensMon = rem.fHead(
                     Good(tokenAcc :+ IntToken(fp, str, intAcc)),
                     (h, tail) => h match
                     {
                        case d if d.isDigit && str.length == 9 && tail.ifHead(_.isDigit) => longLoop(rem, str, intAcc.toLong)
                        case d if d.isDigit && str.length == 9 && intAcc > 214748364 => longLoop(rem, str, intAcc.toLong)
                        case d if d.isDigit && str.length == 9 && intAcc == 214748364 && d > '7' => longLoop(rem, str, intAcc.toLong)
                        case d if d.isDigit => intLoop(tail, str - d.toString, (intAcc * 10) + d - '0')
                        case '.' => decimalLoop(tail, str - h.toString, intAcc, 10)
                        case _ => mainLoop(rem, fp.addStr(str), tokenAcc :+ IntToken(fp, str, intAcc))
                     })
               def longLoop(rem: Seq[Char], str: String, longAcc: Long): TokensMon = rem.fHead(
                     Good(tokenAcc :+ LongIntToken(fp, str, longAcc)),
                     (h, tail) => h match
                     {
                        case d if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad1(fp, "Integer too big for 64 bit value")
                        case d if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad1(fp, "Integer too big for 64 bit value")
                        case d if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' => bad1(fp, "Integer too big for 64 bit value")
                        case d if d.isDigit => longLoop(tail, str - d.toString, (longAcc * 10) + d - '0')
                        case '.' => decimalLoop(tail, str - h.toString, longAcc, 10)
                        case _ => mainLoop(rem, fp.addStr(str), tokenAcc :+ LongIntToken(fp, str, longAcc))
                     })      
                     
               def decimalLoop(rem: Seq[Char], str: String, floatAcc: Double, divisor: Double): TokensMon = rem.fHead(
                     Good(tokenAcc :+ FloatToken(fp, str, floatAcc)),
                     (h, tail) => h match
                     {
                        case d if d.isDigit => decimalLoop(tail, str - d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
                        case c => mainLoop(rem, fp.addStr(str), tokenAcc :+ FloatToken(fp, str, floatAcc))
                     })
                           
               intLoop(tail, c.toString, c - '0')
            }
         case a if a.isLetter =>
            {            
               val (alphaStr, finalTail) = rem.span(a => a.isLetterOrDigit | a == '.')      
               mainLoop(finalTail, fp.addChars(alphaStr), tokenAcc :+ AlphaToken(fp, Symbol(alphaStr.mkString)))            
            }
         case '\"' =>
            {
               def loop(rem: Seq[Char], strAcc: Seq[Char]): TokensMon = rem match
               {
                  case Seq() => bad1(fp, "Unclosed String")                  
                  case Seq('\"', tail2 @ _*) => mainLoop(tail2, fp.addChars(strAcc.length + 2), tokenAcc :+ StringToken(fp, strAcc.mkString))
                  case Seq('\\', tail2 @ _*) => tail2.fHead(
                        bad1(fp, "Unclosed String ending with unclosed escape Sequence"),
                        (c2, tail3) => c2 match
                        {
                           case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc :+ c2)
                           case c2 => bad1(fp, "Unrecognised escape Sequence \\" - c2.toString)
                        })               
                  case Seq(h, tail2 @ _*) => loop(tail2, strAcc :+ h)
               }
               loop(tail, Seq())
            }
         case c if isOperator(c) =>
            {            
               val (opChars, finalTail) = rem.span(isOperator)
               val opStr = opChars.mkString
               val ot = finalTail match
               {
                  case Seq() => OtherOperatorToken(fp, opStr)
                  case Seq(h, tail @ _*) => opChars.last match
                  {
                     case '+' | '-' => ife(h.isWhitespace, PlusInToken(fp, opStr), PlusPreToken(fp, opStr))
                     case _ => OtherOperatorToken(fp, opStr)
                  }
               }
               mainLoop(finalTail, fp.addChars(opChars), tokenAcc :+ ot)            
            }
         case h if h.isWhitespace => mainLoop(tail, fp.nextChar, tokenAcc)
         case c => throw new Exception("Unknown character: " + c.toString)
      }
   }
   
   def isOperator(char: Char): Boolean = char match
   {
      case '+' | '-' | '*' | '/' => true
      case _ => false
   }
   
}