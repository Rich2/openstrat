/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Token for TrigdualInt type. All DecimalTokens are also legal TrigdualInt Tokens, as well as all raw hexadecimal tokens. Base 32, '0' .. '9',
 *  followed by 'a' .. 'v' with letter 'o' replaced with 'w'. Some Alpha numeric terms are valid TrigDualInts. */
trait NatTrigdualToken extends NatToken
{ override def subTypeStr: String = "TrigDual"

  /** USes the digitStr to calculate the natural number from the digitStr. */
  def asTrigDual: Int = ???
}

case class NatTrigDualRawToken(startPosn: TextPosn, srcStr: String) extends NatTrigdualToken
{
  override def digitsStr: String = srcStr
}