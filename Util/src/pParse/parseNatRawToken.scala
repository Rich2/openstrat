/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Function object for parsing a raw natural integer number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseNatRawToken
{
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)

      case _ => Good3(rem, tp.addStr(str), NatDeciToken(tp, str))
    }

    def hexaLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => hexaLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), NatRawHexaToken(tp, str))
    }

    def base32Loop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = ???

    rem match
    { case CharsOff1Tail(DigitChar(i, _), tail) => deciLoop(tail, i.toString)
    case CharsOff1Tail(HexaDigitChar(i, _), tail) => hexaLoop(tail, i.toString)
    }
  }
}