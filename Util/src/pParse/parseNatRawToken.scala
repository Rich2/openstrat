/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object for parsing a raw natural integer number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseNatRawToken
{
  def apply(rem: CharsOff, tp: TextPosn, str:String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def hexaLoop(rem: CharsOff, tp: TextPosn, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit | (d <= 'F' && d >= 'A') => hexaLoop(tail, tp, str + d.toString)
      case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => base32Loop(tail, tp, l.toString)
      case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed raw hexadecimal token.")
      case _ => Good3(rem, tp.addStr(str), RawHexaToken(tp, str))
    }

    def base32Loop(rem: CharsOff, tp: TextPosn, str: String): EMon3[CharsOff, TextPosn, RawNat32OnlyToken] = rem match
    { case CharsOff1Tail(l, tail) if l.isDigit | (l <= 'A' && l >= 'G') | (l <= 'W' && l >= 'P') => base32Loop(tail, tp, l.toString)
      case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed raw Base 32 token.")
      case _ => Good3(rem, tp.addStr(str), RawNat32OnlyToken(tp, str))
    }

    def deciFracLoop(rem: CharsOff, tp: TextPosn, seq1: String, seq2: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciFracLoop(tail, tp, seq1, seq2 + d.toString)
      case _ => Good3(rem, tp.addStr(seq1).addStr(seq2).right1, DeciFracToken(tp, seq1, seq2, ""))
    }

    rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString)
      case CharsOff2Tail('.', d, tail) if d.isDigit => deciFracLoop(tail, tp, str, d.toString)
      case CharsOff1Tail(HexaUpperChar(l), tail) => hexaLoop(tail, tp, str + l.toString)
      case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => base32Loop(tail, tp, l.toString)
      case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed number token.")
      case _ => Good3(rem, tp.addStr(str), NatDeciToken(tp, str))
    }
  }
}

/** Function object for parsing [[IntNegToken]]. */
object parseNatNegToken
{
  def apply(rem: CharsOff, tp: TextPosn, str: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, IntNegToken] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed negative number token.")
    case _ => Good3(rem, tp.addStr(str), IntNegToken(tp, str))
  }
}