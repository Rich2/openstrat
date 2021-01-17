/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** An alphanumeric token beginning with an alphabetic character that normally represents a name of something, that identifies something. */
trait IdentifierToken extends ExprToken

/** An alphanumeric token beginning with an alphabetic character */
trait IdentifierUpToken extends IdentifierToken

object IdentifierUpToken
{
  def apply(startPosn: TextPosn, srcStr: String): IdentifierUpToken = IdentifierUpTokenImp(startPosn, srcStr)

  def unapply(inp: Any): Option[(TextPosn, String)] = inp match
  {
    case iup: IdentifierUpToken => Some(iup.startPosn, iup.srcStr)
    case _ => None
  }

  /** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. */
  case class IdentifierUpTokenImp(startPosn: TextPosn, srcStr: String) extends IdentifierUpToken {
    override def subTypeStr: String = "IdentifierUpper"
  }
}

trait IdentifierBase32Token extends IdentifierUpToken

object IdentifierBase32Token
{
  case class IdentifierBase32TokenImp(startPosn: TextPosn, srcStr: String) extends IdentifierUpToken {
    override def subTypeStr: String = "IdentifierBase32"
  }
}

case class IdentifierHexaToken(startPosn: TextPosn, srcStr: String) extends IdentifierBase32Token{
  override def subTypeStr: String = "IdentifierHexa"
}


/** A valid identifier beginning with a lowercase letter or an underscore character. */
case class IdentifierLwToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{ override def subTypeStr: String = "IdentifierLower"
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