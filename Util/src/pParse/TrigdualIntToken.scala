/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Token for TrigdualInt type. All DecimalTokens are also legal TrigdualInt Tokens. Base 32, '0' .. '9', followed by 'a' .. 'v' with letter 'o'
 *  replaced with 'w'. Some Alpha numeric terms are valid TrigDualInts. */
case class TrigdualIntToken(startPosn: TextPosn, srcStr: String) extends IntToken
{ override def subTypeStr: String = "TrigDual"
  override def getInt: Int = ???
}