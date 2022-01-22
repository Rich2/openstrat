/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object for parsing a raw natural integer number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseNatRawToken
{
  def apply(rem: CharsOff, tp: TextPosn, str:String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =  rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString)
    case CharsOff2Tail('.', d, tail) if d.isDigit => parseDeciFrac(tail, tp, str, d.toString)
    case CharsOff1Tail(HexaUpperChar(l), tail) => parseHexaToken(tail, tp, str + l.toString)
    case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed number token.")
    case _ => Good3(rem, tp.addStr(str), NatDeciToken(tp, str))
  }
}

object parseHexaToken
{
  def apply(rem: CharsOff, tp: TextPosn, str: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit | (d <= 'F' && d >= 'A') => parseHexaToken(tail, tp, str + d.toString)
    case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed raw hexadecimal token.")
    case _ => Good3(rem, tp.addStr(str), RawHexaToken(tp, str))
  }
}

object parseBase32
{
  def apply(rem: CharsOff, tp: TextPosn, str: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, RawNat32OnlyToken]= rem match
  { case CharsOff1Tail(l, tail) if l.isDigit | (l <= 'A' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed raw Base 32 token.")
    case _ => Good3(rem, tp.addStr(str), RawNat32OnlyToken(tp, str))
  }
}

object parseDeciFrac
{
  def apply (rem: CharsOff, tp: TextPosn, seq1: String, seq2: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp.right1, seq1, seq2 + d.toString)
    case _ => Good3(rem, tp.addStr(seq1).addStr(seq2).right1, DeciFracToken(tp, seq1, seq2, ""))
  }
}

/** Function object for parsing [[NegDeciToken]]. */
object parseNatNegToken
{
  def apply(rem: CharsOff, tp: TextPosn, str: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, NegDeciToken] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed negative number token.")
    case _ => Good3(rem, tp.addStr(str), NegDeciToken(tp, str))
  }
}