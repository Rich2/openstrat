/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** not sure about comment tokens */
case class TokensFind(srcStr: String)
{
  val array: Array[Char] = srcStr.toCharArray
  implicit val charArr: Chars = new Chars(array)
  type ETokenList = EMon[Refs[Token]]
  /** Max numbers for long and hexadecimal formats needs to be implemented */
  def apply(fileName: String): ETokenList = mainLoop(charArr.charsOffsetter, new TextPosn(fileName, 1, 1), Buff[Token]())
  def fromString: ETokenList = apply("String")

  private def mainLoop(remOff: CharsOff, tp: TextPosn, acc: Buff[Token]): ETokenList = remOff match
  {
    case CharsOff0() => acc.goodRefs//Good(acc.toList)//goodReverse
    case CharsOff1(';', newOff) => mainLoop(newOff, tp.nextChar, acc.append(SemicolonToken(tp)))
    case CharsOff1(',', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(CommaToken(tp)))
    case CharsOff1('(', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(ParenthOpen(tp)))
    case CharsOff1(')', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(ParenthClose(tp)))
    case CharsOff1('[', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(SquareOpen(tp)))
    case CharsOff1(']', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(SquareClose(tp)))
    case CharsOff1('{', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(CurlyOpen(tp)))
    case CharsOff1('}', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(CurlyClose(tp)))
    case CharsOff1('.', newOff) => mainLoop(newOff, tp.nextChar,  acc.append(DotToken(tp)))
    case CharsOff1('\n', newOff) => mainLoop(newOff, tp.newLine, acc)
    case CharsOff1(h, newOff) if h.isWhitespace => mainLoop(newOff, tp.nextChar, acc)
    //This looks wrong
   // case CharsOff3('\'', '\\', '\\', newOff) => mainLoop(newOff, tp.right(4), acc.append(CharToken(tp, '\\')))
  //  case CharsOff3('\'', '\\', '\"', newOff) => mainLoop(newOff, tp.right(4), acc.append(CharToken(tp, '\"')))
  //  case CharsOff3('\'', '\\', '\'', newOff) => mainLoop(newOff, tp.right(4), acc.append(CharToken(tp, '\'')))
    case CharsOff3('\'', c1, '\'', newOff) => mainLoop(newOff, tp.right(3), acc.append(CharToken(tp, c1)))
    case CharsOff1('\'', _)=> bad1(tp, "Unclosed Character literal.")

    case CharsOff1(a, newOff) if a.isLetter =>
    { val (alphaStr, finalTail) = remOff.span(a => a.isLetterOrDigit | a == '.')
      mainLoop(finalTail, tp.addChars(alphaStr.array),  acc.append(AlphaToken(tp, alphaStr.mkString)))
    }

    case CharsOff2('/', '*', remOff) =>
    {
      def loop(rem: CharsOff, tp: TextPosn): ETokenList = rem match
      { case CharsOff0() => acc.goodRefs //Good(acc.toList)
        case CharsOff2('*', '/', rem) => mainLoop(rem, tp, acc)
        case CharsOff1(_, rem) => loop(rem, tp.nextChar)
      }      
      loop(remOff, tp.addLinePosn(2))
    }
    
    case _ =>
    {
      val possToken: EMon[(CharsOff, TextPosn, Token)] = remOff match
      {
        case CharsOff2('0', 'x', tail)  => zeroX(tail, tp)
        case CharsOff1(d, tail) if d.isDigit => digitStart(tail, tp, d)
        //Not sure if that should be tail instead of remOff
        case CharsOffHead(c) if isOperator(c) => operatorStart(remOff, tp)
        case CharsOff1('\"', tail) => quoteStart(tail, tp)
        case CharsOffHead(c) => bad1(tp, "Unimplemented character in main loop: " + c.toString)
      }
      possToken.flatMap{pair =>
        val (rem, tp, token) = pair
        mainLoop(rem, tp, acc.append(token))
      }
    }
  }
  
  private[this] def quoteStart(rem: CharsOff, tp: TextPosn): EMon[(CharsOff, TextPosn, Token)] =
  {
    def loop(rem: CharsOff, strAcc: StringBuilder): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() => bad1(tp, "Unclosed String")
      case CharsOff1('\"', tail2) => Good3(tail2, tp.addLinePosn(strAcc.length + 2),  StringToken(tp, strAcc.mkString))
      
      case CharsOff1('\\', tail2) => tail2 match
      {
        case CharsOff0() =>  bad1(tp, "Unclosed String ending with unclosed escape Sequence")
        
        case CharsOff1(c2, tail3) => c2 match
        { 
          case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc.append(c2))
          case c2 => bad1(tp, "Unrecognised escape Sequence \\" + c2.toString)
        }
      }               
      case CharsOff1(h, tail2) => loop(tail2, strAcc.append(h))
    }
    loop(rem, new StringBuilder())
  }
  
  /** Not sure if this is fully fixed. */
  private[this] def operatorStart(remOff: CharsOff, tp: TextPosn): EMon[(CharsOff, TextPosn, Token)] =
  {
    val (opChars, finalTail) = remOff.span(isOperator)
    val opStr = opChars.mkString
   
    val ot =  opChars.last match
    {
      //below makes no sense
      case '+' | '-' => finalTail match
      { //case CharsOff0() =>
        case CharsOffHead(h) if !h.isWhitespace => PrefixToken(tp, opStr)
        case _ => PlusInToken(tp, opStr)
      }
      case '=' => AsignToken(tp)
      case _ => OtherOperatorToken(tp, opStr)         
    }
    Good3(finalTail, tp.addChars(opChars.toList),  ot)
  }    
  
  private[this] def digitStart(rem: CharsOff, tp: TextPosn, firstDigit: Char): EMon[(CharsOff, TextPosn, Token)] =
  {
    def intLoop(rem: CharsOff, str: String, intAcc: Int): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() =>  Good3(rem, tp.addStr(str), IntDecToken(tp, intAcc))
      case CharsOff1(d, t) if d.isDigit && str.length == 9 && t.ifHead(_.isDigit) => longLoop(rem, str, intAcc.toLong)
      case CharsOff1(d, tail) if d.isDigit && str.length == 9 && intAcc > 214748364 => longLoop(rem, str, intAcc.toLong)
      case CharsOff1(d, tail) if d.isDigit && str.length == 9 && intAcc == 214748364 && d > '7' => longLoop(rem, str, intAcc.toLong)
      case CharsOff1(d, tail) if d.isDigit => intLoop(tail, str + d.toString, (intAcc * 10) + d - '0')
      case CharsOff1('.', tail) => decimalLoop(tail, str + firstDigit.toString, intAcc, 10)
      case CharsOff1(_, tail) => Good3(rem, tp.addStr(str),  IntDecToken(tp, intAcc))
    }
             
    def longLoop(rem: CharsOff, str: String, longAcc: Long): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str),LongIntToken(tp, str, longAcc))
      case CharsOff1(d, tail) if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1(d, tail) if d.isDigit => longLoop(tail, str + d.toString, (longAcc * 10) + d - '0')
      case CharsOff1('.', tail) => decimalLoop(tail, str + firstDigit.toString, longAcc, 10)
      case CharsOff1(_, tail) => Good3(rem, tp.addStr(str), LongIntToken(tp, str, longAcc))
    }      
                 
    def decimalLoop(rem: CharsOff, str: String, floatAcc: Double, divisor: Double): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), FloatToken(tp, str, floatAcc))
      case CharsOff1(d, tail) if d.isDigit => decimalLoop(tail, str + d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
      case CharsOff1(c, tail) => Good3(rem, tp.addStr(str),  FloatToken(tp, str, floatAcc))
    }
    intLoop(rem, firstDigit.toString, firstDigit - '0')
  }  
  
  private[this] def zeroX(rem: CharsOff, tp: TextPosn): EMon[(CharsOff, TextPosn, Token)] =
  {
    def hexIntLoop(rem: CharsOff, strAcc: String, intAcc: Int): EMon[(CharsOff, TextPosn, Token)] = rem match
    { case CharsOff0() => Good3(rem, tp.addStr(strAcc), IntHexToken(tp, strAcc, intAcc))
      case CharsOff1(h, tail) => h match
      {
        case d if d.isHexDigit & (strAcc.length == 9) & tail.ifHead(_.isDigit) => hexLongLoop(rem, strAcc, intAcc.toLong)
        case d if d.isDigit => hexIntLoop(tail, strAcc + d.toString, (intAcc * 16) + d - '0')
        case al if (al <= 'F') && (al >= 'A') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'A' + 10)
        case al if (al <= 'f') && (al >= 'a') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'a' + 10)
        case _ => Good3(rem, tp.addStr(strAcc), IntHexToken(tp, strAcc, intAcc))
      }
    }

    def hexLongLoop(rem: CharsOff, strAcc: String, longAcc: Long): EMon[(CharsOff, TextPosn, Token)] = rem match
    { case CharsOff0() => Good3(rem, tp.addStr(strAcc), LongIntToken(tp, strAcc, longAcc))
      case CharsOff1(d, tail) if d.isHexDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1(d, tail) if d.isDigit => hexLongLoop(tail, strAcc + d.toString, (longAcc * 16) + d - '0')
      case CharsOff1(al, tail) if (al <= 'F') && (al >= 'A') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'A' + 10)
      case CharsOff1(al, tail) if (al <= 'f') && (al >= 'a') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'a' + 10)
      case CharsOff1(_, tail) => Good3(rem, tp.addStr(strAcc), LongIntToken(tp, strAcc, longAcc))
    }
    hexIntLoop(rem, "0x", 0)
  }
   
  private[this] def isOperator(char: Char): Boolean = char match
  {
    case '+' | '-' | '*' | '/' | '=' => true
    case _ => false
  } 
}