package ostrat
package pParse

/** Function object for parsing expected Hexadecimal number. */
object parseNumberToken
{
  /** Function for parsing expected Hexadecimal number. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def hexaLoop(rem: CharsOff, strAcc: String, intAcc: Long): EMon3[CharsOff, TextPosn, IntHexaToken] = rem match
    { case CharsOff0() => Good3(rem, tp.right(strAcc.length + 2), IntHexaToken(tp, strAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => hexaLoop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOff1Plus(LetterChar(_)) => tp.bad("Badly formed hexadecimal")
      case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc))
    }

    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), IntDeciToken(tp, str))
    }

    rem match
    { case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => hexaLoop (tail, c.toString, i)
      case CharsOff3Plus('0', 'x', WhitespaceChar(_)) => tp.bad("Empty hexademicmal token.")
      case CharsOff3Plus('0', 'x', c) => tp.bad("Badly formed hexademicmal token.")
      case CharsOff2('O', 'x') => tp.bad("Unclosed hexadecimal token")
      case CharsOff1Tail(DigitChar(i), tail) => deciLoop(tail, i.toString)
    }
  }
}
