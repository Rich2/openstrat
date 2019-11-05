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
  def apply(fileName: String): ETokenList =
    mainLoop(charArr.charsOffsetter, new TextPosn(fileName, 1, 1), Buff[Token]())

  def fromString: ETokenList = apply("String")

  private def mainLoop(remOff: CharsOff, tp: TextPosn, acc: Buff[Token]): ETokenList = remOff match
  {
    case CharsOff0() => acc.goodRefs
    case CharsOff1Tail(';', tail) => mainLoop(tail, tp.nextChar, acc.append(SemicolonToken(tp)))
    case CharsOff1Tail(',', tail) => mainLoop(tail, tp.nextChar,  acc.append(CommaToken(tp)))
    case CharsOff1Tail('(', tail) => mainLoop(tail, tp.nextChar,  acc.append(ParenthOpen(tp)))
    case CharsOff1Tail(')', tail) => mainLoop(tail, tp.nextChar,  acc.append(ParenthClose(tp)))
    case CharsOff1Tail('[', tail) => mainLoop(tail, tp.nextChar,  acc.append(SquareOpen(tp)))
    case CharsOff1Tail(']', tail) => mainLoop(tail, tp.nextChar,  acc.append(SquareClose(tp)))
    case CharsOff1Tail('{', tail) => mainLoop(tail, tp.nextChar,  acc.append(CurlyOpen(tp)))
    case CharsOff1Tail('}', tail) => mainLoop(tail, tp.nextChar,  acc.append(CurlyClose(tp)))
    case CharsOff1Tail('.', tail) => mainLoop(tail, tp.nextChar,  acc.append(DotToken(tp)))
    case CharsOff1Tail('\n', tail) => mainLoop(tail, tp.newLine, acc)
    case CharsOff1Tail(h, tail) if h.isWhitespace => mainLoop(tail, tp.nextChar, acc)
    case CharsOff2Tail('/', '/', tail) => { val len = tail.notPredicateLength(_ == '\n'); mainLoop(tail.drop(len), tp.right(len), acc) }
    case CharsOff3Tail('\'', c1, '\'', tail) => mainLoop(tail, tp.right(3), acc.append(CharToken(tp, c1)))
    case CharsOff1Tail('\'', _)=> bad1(tp, "Unclosed Character literal.")

    case CharsOff1Tail(a, tail) if a.isLetter =>
    { val (alphaStr, finalTail) = remOff.span(a => a.isLetterOrDigit | a == '.')
      mainLoop(finalTail, tp.addChars(alphaStr.array), acc.append(AlphaToken(tp, alphaStr.mkString)))
    }

    case CharsOff2Tail('/', '*', remOff) =>
    {
      def loop(rem: CharsOff, tp: TextPosn): ETokenList = rem match
      { case CharsOff0() => acc.goodRefs
        case CharsOff2Tail('*', '/', rem) => mainLoop(rem, tp, acc)
        case CharsOff1Tail(_, rem) => loop(rem, tp.nextChar)
      }      
      loop(remOff, tp.addLinePosn(2))
    }
    case CharsOff2Plus('0', 'x')  => Hexadecimal(remOff, tp).flatMap{ case (co, tp, ht) => mainLoop(co, tp, acc.append(ht)) }

    case _ =>
    {
      val possToken: EMon[(CharsOff, TextPosn, Token)] = remOff match
      {

        case CharsOff1Tail(d, tail) if d.isDigit => digitStart(tail, tp, d)
        //Not sure if that should be tail instead of remOff
        case CharsOff1Plus(c) if isOperator(c) => operatorStart(remOff, tp)
        case CharsOff1Tail('\"', tail) => quoteStart(tail, tp)
        case CharsOff1Plus(c) => bad1(tp, "Unimplemented character in main loop: " + c.toString)
      }

      possToken.flatMap{ pair =>
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
      case CharsOff1Tail('\"', tail2) => Good3(tail2, tp.addLinePosn(strAcc.length + 2),  StringToken(tp, strAcc.mkString))
      
      case CharsOff1Tail('\\', tail2) => tail2 match
      {
        case CharsOff0() =>  bad1(tp, "Unclosed String ending with unclosed escape Sequence")
        
        case CharsOff1Tail(c2, tail3) => c2 match
        { 
          case '\"' | '\n' | '\b' | '\t' | '\f' | '\r' | '\'' | '\\' => loop(tail3, strAcc.append(c2))
          case c2 => bad1(tp, "Unrecognised escape Sequence \\" + c2.toString)
        }
      }               
      case CharsOff1Tail(h, tail2) => loop(tail2, strAcc.append(h))
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
        case CharsOff1Plus(h) if !h.isWhitespace => PrefixToken(tp, opStr)
        case _ => PlusInToken(tp, opStr)
      }
      case '=' => AsignToken(tp)
      case _ => OtherOperatorToken(tp, opStr)         
    }
    Good3(finalTail, tp.addChars(opChars.toList),  ot)
  }    
  
  private[this] def digitStart(rem: CharsOff, tp: TextPosn, firstDigit: Char): EMon[(CharsOff, TextPosn, Token)] =
  {
    /*def intLoop(rem: CharsOff, str: String, intAcc: Int): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() =>  Good3(rem, tp.addStr(str), IntDeciToken(tp, intAcc))
      case CharsOff1Tail(d, t) if d.isDigit && str.length == 9 && t.ifHead(_.isDigit) => longLoop(rem, str, intAcc.toLong)
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 9 && intAcc > 214748364 => longLoop(rem, str, intAcc.toLong)
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 9 && intAcc == 214748364 && d > '7' => longLoop(rem, str, intAcc.toLong)
      case CharsOff1Tail(d, tail) if d.isDigit => intLoop(tail, str + d.toString, (intAcc * 10) + d - '0')
      case CharsOff1Tail('.', tail) => decimalLoop(tail, str + firstDigit.toString, intAcc, 10)
      case CharsOff1Tail(_, tail) => Good3(rem, tp.addStr(str),  IntDeciToken(tp, intAcc))
    }*/
             
    def intLoop(rem: CharsOff, str: String, longAcc: Long): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), IntDeciToken(tp, longAcc))
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit => intLoop(tail, str + d.toString, (longAcc * 10) + d - '0')
      case CharsOff1Tail('.', tail) => decimalLoop(tail, str + firstDigit.toString, longAcc, 10)
      case CharsOff1Tail(_, tail) => Good3(rem, tp.addStr(str), IntDeciToken(tp, longAcc))
    }      
                 
    def decimalLoop(rem: CharsOff, str: String, floatAcc: Double, divisor: Double): EMon[(CharsOff, TextPosn, Token)] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), FloatToken(tp, str, floatAcc))
      case CharsOff1Tail(d, tail) if d.isDigit => decimalLoop(tail, str + d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
      case CharsOff1Tail(c, tail) => Good3(rem, tp.addStr(str),  FloatToken(tp, str, floatAcc))
    }

    intLoop(rem, firstDigit.toString, firstDigit - '0')
  }  
  

  private[this] def isOperator(char: Char): Boolean = char match
  {
    case '+' | '-' | '*' | '/' | '=' => true
    case _ => false
  } 
}