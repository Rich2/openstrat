/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** An alphanumeric token beginning with an alphabetic character that normally represents a name of something, that identifies something. */
trait IdentifierToken extends OpExprMemToken

/** Extractor object for [[IdentifierToken]]. */
object IdentifierToken
{ /** Unaply extractor method for [[IdentifierToken]]. */
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
trait IdentUpperToken extends IdentifierToken

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
trait IdentLowerToken extends IdentifierToken

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