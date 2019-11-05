package ostrat
package pParse

object DecimalNumber
{
  def apply(rem: CharsOff, tp: TextPosn, firstDigit: Char)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def intLoop(rem: CharsOff, str: String, longAcc: Long): EMon3[CharsOff, TextPosn, Token] = rem match
    {
      case CharsOff0() => Good3(rem, tp.addStr(str), IntDeciToken(tp, longAcc))
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && tail.ifHead(_.isDigit) => bad3(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L => bad3(tp, "Integer too big for 64 bit value")
      case CharsOff1Tail(d, tail) if d.isDigit && str.length == 18 && longAcc > 922337203685477580L && d > '7' =>
        bad3(tp, "Integer too big for 64 bit value")
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
