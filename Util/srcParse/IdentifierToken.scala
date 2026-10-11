/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** An identifier. */
trait Identifier extends TokenBase

object Identifier
{
  given unshowEv: Unshow[Identifier] = new Unshow[Identifier]
  { override def typeStr: String = "Identifier"
    
    override def fromExpr(expr: Expr): ParseExcEither[Identifier] = expr match
    { case idToken: IdentifierToken => Right(idToken)
      case expr => LeftParseExc("Not an identifier.")
    }    
  }
}

/** An identifier beginning with an uppercase letter. */
trait IdentUpper extends Identifier

object IdentUpper
{
  given unshowEv: Unshow[IdentUpper] = new Unshow[IdentUpper]
  { override def typeStr: String = "Identifier"

    override def fromExpr(expr: Expr): ParseExcEither[IdentUpper] = expr match
    { case idToken: IdentUpperToken => Right(idToken)
      case expr => LeftParseExc("Not an identifier.")
    }
  }
}

/** An identifier beginning with a lowercase letter. */
trait IdentLower extends Identifier

object IdentLower
{
  given unshowEv: Unshow[IdentLower] = new Unshow[IdentLower]
  { override def typeStr: String = "IdentLower"

    override def fromExpr(expr: Expr): ParseExcEither[IdentLower] = expr match
    { case idToken: IdentLowerToken => Right(idToken)
      case expr => LeftParseExc("Not an identifier.")
    }
  }
}

/** An alphanumeric token beginning with an alphabetic character that normally represents a name of something, that identifies something. */
trait IdentifierToken extends Identifier, OpExprMemToken

/** Extractor object for [[IdentifierToken]]. */
object IdentifierToken
{ /** Unapply extractor method for [[IdentifierToken]]. */
  def unapply(inp: Any): Option[String] = inp match
  { case idt: IdentifierToken => Some(idt.srcStr)
    case _ => None
  }
}

/** An identifier token beginning with an underscore character. */
case class IdentUnderToken(startPosn: TextPosn, srcStr: String) extends IdentifierToken
{ override def exprName: String = "IndentUnder"
}

/** An alphanumeric identifier token beginning with an upper case alphabetic character. */
trait IdentUpperToken extends IdentUpper, IdentifierToken

/** Extractor function object for [[IdentUpperToken]] type. */
object IdentUpperToken
{ /** Extractor method for [[IdentUpperToken]] type. */
  def unapply(inp: Any): Option[(TextPosn, String)] = inp match
  { case iup: IdentUpperToken => Some((iup.startPosn, iup.srcStr))
    case _ => None
  }
}

/** An alphanumeric token beginning with an alphabetic character that most commonly represents a name of something, but is also a valid raw Base32
 *  Token. */
trait IdentUpperBase32Token extends IdentUpperToken with ValidRawBase32NatToken
{ override def digitsStr: String = srcStr
}

case class IdentUpperBase32OnlyToken(startPosn: TextPosn, srcStr: String) extends IdentUpperBase32Token
{ override def exprName: String = "IdentifierUpperBase32"
}

/** An identifier beigning with an upper case letter that is also a valid raw hexadecimal token. */
case class IdentUpperHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpperBase32Token with ValidRawHexaNatToken
{ override def exprName: String = "IdentifierUpperHexa"
}

/** A valid identifier beginning with a lowercase letter or an underscore character. */
trait IdentLowerToken extends IdentLower, IdentifierToken

/** Extractor function object for [[IdentLowerToken]] type. */
object IdentLowerToken
{ /** Extractor method for [[IdentLowerToken]] type. */
  def unapply(input: Any): Option[(TextPosn, String)] = input match {
    case il: IdentLowerToken => Some((il.startPosn, il.srcStr))
    case _ => None
  }
}

/** An identifier beginning with a lowercase that is a valid raw hexadecimal and raw Base32 token. */
case class IdentLowerHexaToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken
{ override def exprName: String = "IdentifierLower"
}

/** An identifier beginning with a lowercase that is not a valid raw Base32 or hexadecimal token. */
case class IdentLowerBase32OnlyToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken with ValidRawBase32IntToken
{ override def exprName: String = "IdentifierLower"
  override def digitsStr: String = srcStr
}

/** An identifier beginning with a lowercase that is not a valid raw Base32 or hexadecimal token. */
case class IdentLowerOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken
{ override def exprName: String = "IdentLowerOnly"
}

/** An identifier beginning with a upper case that is not a valid raw Base32 or hexadecimal token. */
case class IdentUpperOnlyToken(startPosn: TextPosn, srcStr: String) extends IdentUpperToken
{ override def exprName: String = "IdentUpperOnly"
}