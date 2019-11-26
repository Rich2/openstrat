package ostrat
package pParse

/** Token for Trigdual type. All DecimalTokens are also legal Trigdual Tokens. Base 32, '0' .. '9', followed by 'a' .. 'v' with letter 'o' replaced
 * with 'w'. */
case class TrigdualToken(startPosn: TextPosn, srcStr: String) extends IntToken
{ override def subTypeStr: String = "TrigDual"
  override def getInt: Int = ???
}
