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
      case CharsOff1(h, tail) => h match
      {
        case d if d.isHexDigit & (strAcc.length == 9) & tail.ifHead(_.isDigit) => hexLongLoop(rem, strAcc, intAcc.toLong)
        case d if d.isDigit => hexIntLoop(tail, strAcc + d.toString, (intAcc * 16) + d - '0')
        case al if (al <= 'F') && (al >= 'A') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'A' + 10)
        case al if (al <= 'f') && (al >= 'a') => hexIntLoop(tail, strAcc + al.toString, (intAcc * 16) + al - 'a' + 10)
        case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc, intAcc))
      }
    }

    def hexLongLoop(rem: CharsOff, strAcc: String, longAcc: Long): EMon[(CharsOff, TextPosn, IntLikeHexaToken)] = rem match
    { case CharsOff0() => Good3(rem, tp.addStr(strAcc), LongHexaToken(tp, strAcc, longAcc))
      case CharsOff1(d, tail) if d.isHexDigit && strAcc.length == 18 && tail.ifHead(_.isDigit) => bad1(tp, "Integer too big for 64 bit value")
      case CharsOff1(d, tail) if d.isDigit => hexLongLoop(tail, strAcc + d.toString, (longAcc * 16) + d - '0')
      case CharsOff1(al, tail) if (al <= 'F') && (al >= 'A') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'A' + 10)
      case CharsOff1(al, tail) if (al <= 'f') && (al >= 'a') => hexLongLoop(tail, strAcc + al.toString, (longAcc * 16) + al - 'a' + 10)
      case CharsOff1(_, tail) => Good3(rem, tp.addStr(strAcc), LongHexaToken(tp, strAcc, longAcc))
    }
    { deb("start of hex"); hexIntLoop(rem, "0x", 0) }
  }

}
