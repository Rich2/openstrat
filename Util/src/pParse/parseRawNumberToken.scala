/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Function object for parsing a raw number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseRawNumberToken
{
  def apply(rem: CharsOff, tp: TextPosn, str:String, isNeg: Boolean)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =  rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, str + d.toString, isNeg)
    case CharsOff2Tail('.', d, tail) if d.isDigit => parseDeciFrac(tail, tp, str, d.toString, isNeg)
    case CharsOff1Tail(HexaUpperChar(l), tail) => parseHexaToken(tail, tp, str + l.toString)
    case CharsOff1Tail(l, tail) if (l <= 'G' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed number token.")
    case _ if isNeg => Good3(rem, tp.addStr(str).right1, NegDeciToken(tp, str))
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
  def apply(rem: CharsOff, tp: TextPosn, str: String)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, RawNat32Token]= rem match
  { case CharsOff1Tail(l, tail) if l.isDigit | (l <= 'A' && l >= 'G') | (l <= 'W' && l >= 'P') => parseBase32(tail, tp, l.toString)
    case CharsOffHead(LetterOrUnderscoreChar(l)) => tp.bad3("Badly formed raw Base 32 token.")
    case _ => Good3(rem, tp.addStr(str), RawNat32Token(tp, str))
  }
}

object parseDeciFrac
{
  def apply (rem: CharsOff, tp: TextPosn, seq1: String, seq2: String, isNeg: Boolean)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] = rem match
  { case CharsOff1Tail(d, tail) if d.isDigit => apply(tail, tp, seq1, seq2 + d.toString, isNeg)
    case _ => Good3(rem, tp.addStr(seq1).addStr(seq2).right1, DeciFracPosToken(tp, seq1, seq2, ""))
  }
}