package ostrat
package pParse

trait IdentifierToken extends ExprToken

/** An Identifier Token beginning with an uppercase letter. This is a trait not a final class, because some IdentiferUpperTokens are valid raw
 * Hexadecimal Tokens and some are not. */
trait IdentifierUpperToken extends ExprToken

object IdentifierUpperToken
{
  def unapply(inp: AnyRef): Option[(TextPosn, String)] = ???
}

/** An Identifier Token beginning with a lowercase letter or an underscore character. This is a trait not a final class, because some
 *  IdentifierLowerTokens are valid raw Trigdual Tokens and some are not. */
trait IdentifierLowerToken extends ExprToken

object IdentifierLowerToken
{
  def unapply(inp: AnyRef): Option[(TextPosn, String)] = inp match
  { case ilt: IdentifierLowerToken => Some((ilt.startPosn, ilt.srcStr))
    case _ => None
  }
}

/** A valid Identifier begining with a lowercase letter or an underscore character. */
case class IdentifierLowerOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentifierLowerToken
{ override def subTypeStr: String = "IdentifierLowerOnly"
}

/* A valid IdentiferUpperToken that is also a valid HexaDecimalToken. */
case class IdentifierMaybeHexaToken(startPosn: TextPosn, srcStr: String) extends IdentifierUpperToken with MaybeHexaToken
{ override def subTypeStr: String = "IdentifierMaybeHexa"
  @inline override def digitsStr: String = srcStr
  override def getInt: Int = asHexaInt
}

case class IdentifierUpperOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentifierUpperToken
{
  override def subTypeStr: String = "IdentifierUpperOnly"
}

/** A valid IdentifierLowerToken that is also a valid TrigdualToken. */
case class IdentifierMaybeTrigToken(startPosn: TextPosn, srcStr: String) extends IdentifierLowerToken
{  override def subTypeStr: String = "IdentLowerTrig"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def subTypeStr: String = "HashAlpha"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def subTypeStr: String = "Underscore"
}