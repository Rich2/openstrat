/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object for parsing a raw natural integer number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseNatRawToken
{
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case CharsOff1Tail(HexaUpperChar(l), tail) => hexaLoop(tail, str + l.toString)
      case _ => Good3(rem, tp.addStr(str), NatDeciToken(tp, str))
    }

    def hexaLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => hexaLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), RawHexaToken(tp, str))
    }

    def base32Loop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match {
      case CharsOff1Tail(l, tail) if l.isDigit | (l <= 'A' && l >= 'G') | (l <= 'W' && l >= 'P') => base32Loop(tail, l.toString)
      case CharsOff1Tail(l, tail) => tp.bad3("Badly formed raw Base 32 token")
      case _ => Good3(rem, tp.addStr(str), Nat32OnlyToken(tp, str))
    }

    rem match
    { case CharsOff1Tail(DigitChar(i), tail) => deciLoop(tail, i.toString)
      case CharsOff1Tail(l, tail)if l <= 'F' && l >= 'A' => hexaLoop(tail, l.toString)
      case CharsOff1Tail(l, tail)if (l <= 'N' && l >= 'G') | (l <= 'W' && l >= 'P') => base32Loop(tail, l.toString)
    }
  }
}

object parseNatNegToken
{
  def apply(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
  {
    case _ => ???
  }
}