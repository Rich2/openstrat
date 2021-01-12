/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Token for base 32 or duatricesimal natural number. All DecimalTokens are also legal base32 tokens, as well as all raw hexadecimal tokens. Base 32
 * , '0' .. '9', followed by 'a' .. 'v' with letter 'o' replaced with 'w'. Some Alpha numeric terms are valid TrigDualInts. */
trait NatBase32Token extends NatToken
{ override def subTypeStr: String = "TrigDual"

  /** USes the digitStr to calculate the natural number from the digitStr. */
  def asbase32: Int = ???
}

/** A raw natural integer token for base32 that is not a valid hexadecimal or decimal number */
case class NatBase32RawToken(startPosn: TextPosn, srcStr: String) extends NatBase32Token
{
  override def digitsStr: String = srcStr
}

case class Nat0tToken(startPosn: TextPosn, digitsStr: String) extends NatBase32Token
{
  override def srcStr: String = "0t" + digitsStr
}