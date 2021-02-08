/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** An alphanumeric token beginning with an alphabetic character that normally represents a name of something, that identifies something. */
trait IdentifierToken extends ExprToken

/** An identifier token beginning with an underscore character. */
case class IdentUnderToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{
  override def subTypeStr: String = "IndentUnder"
}

/** An alphanumeric identifier token beginning with an upper case alphabetic character. */
trait IdentUpToken extends IdentifierToken

object IdentUpToken
{
  def apply(startPosn: TextPosn, srcStr: String): IdentUpToken = IdentifierUpTokenImp(startPosn, srcStr)

  /** Extractor method for [[identUp]] type. */
  def unapply(inp: Any): Option[(TextPosn, String)] = inp match
  { case iup: IdentUpToken => Some((iup.startPosn, iup.srcStr))
    case _ => None
  }

  /** An alpha-numeric token beginning with an uppercase letter that represents a name of something, that identifies something. */
  case class IdentifierUpTokenImp(startPosn: TextPosn, srcStr: String) extends IdentUpToken {
    override def subTypeStr: String = "IdentifierUpper"
  }
}

/** An alphanumeric token beginning with an alphabetic character that most commonly represents a name of something, but is also a valid raw Base32
 *  Token. */
trait IdentUpBase32Token extends IdentUpToken with NatBase32Token

//trait IdentUpBase32Token extends

case class IdentUpBase32NoHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpToken
{ override def subTypeStr: String = "IdentifierBase32"
}

/** An identifier Token that is also a valid raw hexadecimal raw Base32 token. */
trait IdentHexaToken extends NatHexaToken

/** An identifier that is also a valid raw hexadecimal token. */
case class IdentUpHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpBase32Token with IdentHexaToken
{ override def subTypeStr: String = "IdentifierHexa"
  override def digitsStr: String = srcStr
}

/** A valid identifier beginning with a lowercase letter or an underscore character. */
trait IdentifierLwToken extends IdentifierToken
{ override def subTypeStr: String = "IdentifierLower"
}

object IdentifierLwToken
{
  def unapply(input: Any): Option[(TextPosn, String)] = input match {
    case il: IdentifierLwToken => Some((il.startPosn, il.srcStr))
    case _ => None
  }
}

/** An identifier beginning with a lowercase that is not a valid raw Base32 or hexadecimal token. */
case class IdentLowHexaToken(startPosn: TextPosn, srcStr: String) extends IdentifierLwToken
{ override def subTypeStr: String = "IdentifierLower"
}

/** An identifier beginning with a lowercase that is not a valid raw Base32 or hexadecimal token. */
case class IdentLowOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentifierLwToken
{ override def subTypeStr: String = "IdentifierLower"
}