package ostrat
package pParse

/** Function object for parsing expected Hexadecimal number. */
object parseHexadecimal
{
  /** Function for parsing expected Hexadecimal number. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, IntHexaToken] =
  {
    def hexIntLoop(rem: CharsOff, strAcc: String, intAcc: Long): EMon3[CharsOff, TextPosn, IntHexaToken] = rem match
    { case CharsOff0() => Good3(rem, tp.right(strAcc.length + 2), IntHexaToken(tp, strAcc, intAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => hexIntLoop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOff1Plus(LetterChar(_)) => tp.bad("Badly formed hexadecimal")
      case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc, intAcc))
    }

    rem match
    { case CharsOff2Tail('0', 'x', tail) if tail.forN(16, _.isHexaDigit) => ??? //Needs big integer
      case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => hexIntLoop (tail, c.toString, i)
      case CharsOff3Plus('0', 'x', WhitespaceChar(_)) => tp.bad("Empty hexademicmal token.")
      case CharsOff3Plus('0', 'x', c) => tp.bad("Badly formed hexademicmal token.")
      case CharsOff2('O', 'x') => tp.bad("Unclosed hexadecimal token")
    }
  }
}

object parseDeciNumber
{
  def apply(rem: CharsOff, tp: TextPosn, firstDigit: Char)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def intLoop(rem: CharsOff, str: String, longAcc: Long): EMon3[CharsOff, TextPosn, Token] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), IntDeciToken(tp, longAcc))
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => tp.bad("Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => tp.bad("Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' =>
        tp.bad("Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit => intLoop(tail, str + d.toString, (longAcc * 10) + d - '0')
      case CharsOff1Tail('.', tail) => decimalLoop(tail, str + firstDigit.toString, longAcc, 10)
      case CharsOff1Tail(_, tail) => Good3(rem, tp.addStr(str), IntDeciToken(tp, longAcc))
    }

    def decimalLoop(rem: CharsOff, str: String, floatAcc: Double, divisor: Double): EMon3[CharsOff, TextPosn, Token] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), FloatToken(tp, str, floatAcc))
      case CharsOff1Tail(d, tail) if d.isDigit => decimalLoop(tail, str + d.toString, floatAcc + (d - '0') / divisor, divisor * 10)
      case CharsOff1Tail(c, tail) => Good3(rem, tp.addStr(str),  FloatToken(tp, str, floatAcc))
    }

    intLoop(rem, firstDigit.toString, firstDigit - '0')
  }
}

object parseStringToken
{ /** Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first.*/
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, StringToken] =
  {
    val strAcc: StringBuilder = new StringBuilder()

    def loop(rem: CharsOff): EMon3[CharsOff, TextPosn, StringToken] = rem match
    { case CharsOff0() => tp.bad("Unclosed String")
      case CharsOff1Tail('\"', tail2) => Good3(tail2, tp.right(strAcc.length + 2),  StringToken(tp, strAcc.mkString))
      case CharsOff1('\\') =>  tp.bad("Unclosed String ending with unclosed escape Sequence")
      case CharsOff2Tail('\\', c2, tail) if Array('\"', '\n', '\b', '\t', '\f', '\r', '\'', '\\').contains(c2) => { strAcc.append(c2); loop(tail) }
      case CharsOff2Plus('\\', c2) => tp.bad("Unrecognised escape Sequence \\" + c2.toString)
      case CharsOff1Tail(h, tail2) => { strAcc.append(h); loop(tail2) }
    }
    rem match
    {
      case CharsOff1Tail('\"', tail) => loop(tail)
      case _ => tp.bad("These characters do not begin with a String opening delimitter.")
    }

  }
}

object parseOperatorToken
{
  /** Not sure if this is fully fixed. Parses an operator. Operators can have multiple charachters in RSON. */
  def apply(remOff: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
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
}

object parseIdentifier
{
  def apply(remOff: CharsOff, tpStart: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    val acc: StringBuffer = new StringBuffer()
    def loop(remOff: CharsOff, tp: TextPosn): EMon3[CharsOff, TextPosn, Token] = remOff match
    {
      case CharsOff0() => Good3(remOff, tp, IdentifierToken(tpStart, acc.toString))
      case CharsOff1Tail(LetterOrDigitChar(c), tail) => { acc.append(c); loop(tail, tp.right1) }
      case CharsOff2Plus('_', LetterOrDigitChar(_))  => { acc.append('_'); loop(remOff.drop1, tp.right1) }
      case CharsOff2Plus('_', '_') => tp.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Plus(_) => Good3(remOff, tp, IdentifierToken(tpStart, acc.toString))
    }

    remOff match
    {
      case CharsOff0() => tpStart.bad("Can not return Token form empty Char Array.")
      case CharsOff2Plus(LetterOrUnderscoreChar(c1), LetterOrDigitChar(_)) => { acc.append(c1); loop(remOff.drop1, tpStart.right1) }
      case CharsOff2Plus(LetterOrUnderscoreChar(c1), '_') => { acc.append(c1); loop(remOff.drop1, tpStart.right1) }
      case CharsOff2Plus('_', '_') => tpStart.right1.bad("Consecutive underscores in Identifier not allowed.")
      case CharsOff1Tail('_', tail)  => Good3(tail, tpStart.right1, UnderscoreToken(tpStart))
      case CharsOff1Tail(LetterChar(l), tail) => { acc.append(l); loop(tail, tpStart.right1) }
      case CharsOff1Plus(c) => tpStart.bad(c.toString + " is not a valid start to an identifer.")

    }
  }
}
