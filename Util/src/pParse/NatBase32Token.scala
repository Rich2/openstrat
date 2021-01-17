/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Token for base 32 or duatricesimal natural number. All DecimalTokens are also legal base32 tokens, as well as all raw hexadecimal tokens. Base 32
 * , '0' .. '9', followed by 'a' .. 'v' with letter 'o' replaced with 'w'. Some Alpha numeric terms are valid TrigDualInts. */
trait NatBase32Token extends NatToken
{
  /** Uses the digitStr to calculate the natural number from the digitStr. */
  def asbase32: Int = ???
}

/** A raw base 32 natural number token includes valid standard decimal natural number and raw hexadecimal tokens. */
trait NatRawToken extends NatBase32Token

object NatBase32Token
{
  /** A raw natural integer token for base32 that is not a valid hexadecimal or decimal number */
  def apply(startPosnIn: TextPosn, srcStrIn: String): NatRawToken = new NatRawToken
  {
    val startPosn: TextPosn = startPosnIn
    val srcStr: String = srcStrIn
    override def subTypeStr: String = "Base32Raw"
    override def digitsStr: String = srcStr
  }
}

/** An unambiguous base32 natural number token, starts with the 0z characters. */
case class Nat0tToken(startPosn: TextPosn, digitsStr: String) extends NatBase32Token
{ override def subTypeStr: String = "NatOt"
  override def srcStr: String = "0t" + digitsStr
}