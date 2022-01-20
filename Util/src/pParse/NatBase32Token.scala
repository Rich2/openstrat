/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Token for base 32 natural number. All DecimalTokens are also legal base32 tokens, as well as all raw hexadecimal tokens. Base32 , '0' .. '9',
 *  followed by 'A' .. 'W' with letter 'O' unused. Some Alpha numeric terms are valid TrigDualInts. */
trait NatBase32Token extends NatToken
{ /** Uses the digitStr to calculate the natural number from the digitStr. */
  def asbase32: Int = ???
}

/** A raw natural Base32 integer token starting with a digit that is not a valid hexadecimal and hence is not a decimal number either. */
case class RawNat32OnlyToken(startPosn: TextPosn, srcStr: String) extends NatRawToken
{ override def exprTypeStr: String = "Base32Raw"
  override def digitsStr: String = srcStr
}

/** An unambiguous base32 natural number token, starts with the 0z characters. */
case class Nat0tToken(startPosn: TextPosn, digitsStr: String) extends NatBase32Token
{ override def exprTypeStr: String = "NatOt"
  override def srcStr: String = "0t" + digitsStr
}