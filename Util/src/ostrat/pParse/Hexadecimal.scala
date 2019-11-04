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
      case CharsOff2Plus(HexaDigitChar(_, _), HexaDigitChar(_, _)) if strAcc.length >= 9 /* -2 for 0x */ => hexLongLoop(rem, strAcc, intAcc.toLong)
      case CharsOff1Plus(HexaDigitChar(_, i)) if i > 9 => hexLongLoop(rem, strAcc, intAcc.toLong)
      case CharsOff1(HexaDigitChar(c, i)) => Good((rem.drop1, tp.right(strAcc.length - 1), IntHexaToken(tp, strAcc + c, intAcc * 16 + i)))
      case CharsOff1Tail(h, tail) => h match
      {

        case d if d.isDigit => hexIntLoop(tail, strAcc + d.toString, (intAcc * 16) + d - '0')
        case al if (al <= 'F') && (al >= 'A') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'A' + 10)
        case al if (al <= 'f') && (al >= 'a') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'a' + 10)
        case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc, intAcc))
      }
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
    { case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => hexIntLoop (tail, "0x" + c, 0)
      case CharsOff3Plus('0', 'x', WhitespaceChar(_)) => bad1(tp, "Empty hexademicmal token.")
      case CharsOff3Plus('0', 'x', c) => bad1(tp, "Badly formed hexademicmal token.")
      case CharsOff2('O', 'x') => bad1(tp, "Unclosed hexadecimal token")
    }
  }
}
