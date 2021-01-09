/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** An alpha-numeric token beginning with an alphabetic character that represents a name of something, that identifies something. */
trait IdentToken extends ExprToken

/** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. This is a trait not a
 *  final class, because some IdentiferUpperTokens are valid raw Hexadecimal and trigdual tokens and some are not. */
trait IdentUpperToken extends ExprToken

object IdentUpperToken
{ def unapply(inp: AnyRef): Option[(TextPosn, String)] = inp match
  { case ilt: IdentLowerToken => Some((ilt.startPosn, ilt.srcStr))
    case _ => None
  }
}

/** A valid identifier beginning with a lowercase letter or an underscore character. */
case class IdentLowerToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def subTypeStr: String = "IdentifierLowerOnly"
}

/* A valid IdentiferUpperToken that is also a valid HexaDecimalToken. */
case class IdentifierMaybeHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpperToken with MaybeHexaToken
{ override def subTypeStr: String = "IdentifierMaybeHexa"
  @inline override def digitsStr: String = srcStr
  override def getInt: Int = asHexaInt
}

/** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. This is not a valid
 *  integer token of any kind. */
case class IdentUpperOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentUpperToken
{
  override def subTypeStr: String = "IdentifierUpperOnly"
}

/** A valid IdentifierLowerToken that is also a valid TrigdualToken. */
/*case class IdentLowerTrigToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken
{  override def subTypeStr: String = "IdentLowerTrig"
}*/

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