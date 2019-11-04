package ostrat
package pParse

/** Function object for parsing expected Hexadecimal number. */
object Hexadecimal
{
  /** Function for parsing expected Hexadecimal number. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon[(CharsOff, TextPosn, IntLikeHexaToken)] =
  {
    def hexIntLoop(rem: CharsOff, strAcc: String, intAcc: Int): EMon[(CharsOff, TextPosn, IntLikeHexaToken)] = rem match
    { case CharsOff0() => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc, intAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => hexIntLoop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOff1Plus(LetterChar(_)) => bad1(tp, "Badly formed hexadecimal")
      case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc, intAcc))
    }

    def hexLongLoop(rem: CharsOff, strAcc: String, longAcc: Long): EMon[(CharsOff, TextPosn, IntLikeHexaToken)] = rem match
    { case CharsOff0() => Good3(rem, tp.addStr(strAcc), LongHexaToken(tp, strAcc, longAcc))
      case CharsOff1Tail(d, tail) if d.isHexaDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit => hexLongLoop(tail, strAcc + d.toString, (longAcc * 16) + d - '0')
      case CharsOff1Tail(al, tail) if (al <= 'F') && (al >= 'A') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'A' + 10)
      case CharsOff1Tail(al, tail) if (al <= 'f') && (al >= 'a') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'a' + 10)
      case CharsOff1Tail(_, tail) => Good3(rem, tp.addStr(strAcc), LongHexaToken(tp, strAcc, longAcc))
    }

    rem match
    { case CharsOff2Tail('0', 'x', tail) if tail.forN(16, _.isHexaDigit) => ??? //Needs big integer
      case CharsOff2Tail('0', 'x', tail) if tail.forN(8, _.isHexaDigit) => hexLongLoop(tail, "Ox", 0)
      case CharsOff3Tail('0', 'x', HexaDigitChar(_, i), tail) if i >= 8 & tail.forN(6, _.isHexaDigit) => hexLongLoop(tail, "Ox", 0)
      case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => hexIntLoop (tail, "0x" + c, 0)
      case CharsOff3Plus('0', 'x', WhitespaceChar(_)) => bad1(tp, "Empty hexademicmal token.")
      case CharsOff3Plus('0', 'x', c) => bad1(tp, "Badly formed hexademicmal token.")
      case CharsOff2('O', 'x') => bad1(tp, "Unclosed hexadecimal token")
    }
  }
}
