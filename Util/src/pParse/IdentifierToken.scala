/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** An alphanumeric token beginning with an alphabetic character that normally represents a name of something, that identifies something. */
trait IdentifierToken extends ExprToken

/** An alphanumeric token beginning with an alphabetic character. */
trait IdentifierUpToken extends IdentifierToken

object IdentifierUpToken
{
  def apply(startPosn: TextPosn, srcStr: String): IdentifierUpToken = IdentifierUpTokenImp(startPosn, srcStr)

  def unapply(inp: Any): Option[(TextPosn, String)] = inp match
  {
    case iup: IdentifierUpToken => Some((iup.startPosn, iup.srcStr))
    case _ => None
  }

  /** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. */
  case class IdentifierUpTokenImp(startPosn: TextPosn, srcStr: String) extends IdentifierUpToken {
    override def subTypeStr: String = "IdentifierUpper"
  }
}

/** An alphanumeric token beginning with an alphabetic character that most commonly represents a name of something, but is also a valid raw Base32
 *  Token. */
trait IdentifierBase32Token extends IdentifierUpToken with NatBase32Token

object IdentifierBase32Token
{
  case class IdentifierBase32TokenImp(startPosn: TextPosn, srcStr: String) extends IdentifierUpToken {
    override def subTypeStr: String = "IdentifierBase32"
  }
}

case class IdentifierHexaToken(startPosn: TextPosn, srcStr: String) extends IdentifierBase32Token with NatHexaToken
{ override def subTypeStr: String = "IdentifierHexa"
  override def digitsStr: String = srcStr
}

/** A valid identifier beginning with a lowercase letter or an underscore character. */
case class IdentifierLwToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{ override def subTypeStr: String = "IdentifierLower"
}