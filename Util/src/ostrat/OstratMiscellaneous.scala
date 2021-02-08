/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait IsType[A <: AnyRef]
{ def isType(obj: AnyRef): Boolean
  def asType(obj: AnyRef): A
  def optType(obj: AnyRef): Option[A] = ife(isType(obj), Some(asType(obj)), None)
}

object IsType
{
  implicit object AnyRefIsType extends IsType[AnyRef]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[AnyRef]
    override def asType(obj: AnyRef): AnyRef = obj.asInstanceOf[AnyRef]
  }
}

object LetterUpper
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isUpper => Some(c)
    case _ => None
  }
}

object LetterLower
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLower => Some(c)
    case _ => None
  }
}

object LetterChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case _ => None
  }
}

object DigitChar
{
  def unapply(input: Char): Option[(Char, Int)] = input match
  { case c if c.isDigit => Some((c, c - '0'))
    case _ => None
  }
}

object LetterOrDigitChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case c if c.isDigit => Some(c)
    case _ => None
  }
}

object LetterOrUnderscoreChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case '_' => Some('_')
    case _ => None
  }
}

object HexaDigitChar
{
  def unapply(input: Char): Option[(Char, Int)] = input match
  {
    case n if n.isDigit => Some((n, n - '0'))
    case l if l <= 'F' && l >= 'A' => Some((l, l - 'A' + 10))
    case l if l <= 'f' && l >= 'a' => Some((l, l - 'a' + 10))
    case c => None
  }
}

/** An upper case Hexadecimal letter, 'A' .. 'F'. */
object HexaUpperChar
{
  def unapply(input: Char): Option[Char] = input match
  { case l if l <= 'F' && l >= 'A' => Some(l)
    case c => None
  }
}

/** An upper case Hexadecimal letter, 'A' .. 'F'. */
object Base32UpperChar
{
  def unapply(input: Char): Option[(Char, Int)] = input match
  { case l if l <= 'H' && l >= 'A' => Some((l, l - 'A' + 10))
    case l if l <= 'N' && l >= 'J' => Some((l, l - 'A' + 10))
    case c => None
  }
}



object Base32Char
{
  def unapply(input: Char): Option[(Char, Int)] = input match
  {
    case n if n.isDigit => Some((n, n - '0'))
    case l if l <= 'N' && l >= 'A' => Some((l, l - 'A' + 10))
    case l if l <= 'n' && l >= 'a' => Some((l, l - 'a' + 10))
    case l if l <= 'W' && l >= 'P' => Some((l, l - 'A' + 10))
    case l if l <= 'w' && l >= 'p' => Some((l, l - 'a' + 10))
    case c => None
  }
}

/** Extractor object for hexadecimal alphabetic upper case characters. */
object HexaUpChar
{ /** Extractor method for hexadecimal alphabetic upper case characters. */
  def unapply(input: Char): Option[Char] = input match
  { case l if l <= 'F' && l >= 'A' => Some(l)
    case _ => None
  }
}

/** Extractor object for Base 32 digits or alphabetic lower case characters. */
object Base32LowChar
{ /** Extractor method for Base 32 alphabetic lower case characters. */
  def unapply(input: Char): Option[Char] = input match
  {
    case n if n.isDigit => Some(n)
    case c if 'n' >= c & c >= 'a' => Some(c)
    case c if 'w' >= c & c >= 'n' => Some(c)
    case _ => None
  }
}

/** Extractor object for Base 32 alphabetic upper case characters. */
object Base32UpChar
{ /** Extractor method for Base 32 digits and alphabetic upper case characters. */
  def unapply(input: Char): Option[Char] = input match
  { case n if n.isDigit => Some(n)
    case c if 'N' >= c & c >= 'A' => Some(c)
    case c if 'W' >= c & c >= 'N' => Some(c)
    case _ => None
  }
}

/** Extractor object for whitespace characters. */
object WhitespaceChar
{ /** Extractor method for whitespace characters. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isWhitespace => Some(c)
    case _ => None
  }
}

/** Extractor object for operator characters. */
object OperatorChar
{ /** Extractor method for operator characters. */
  def unapply(input: Char): Option[Char] = input match
  { case c if pParse.isOperator(c) => Some(c)
    case _ => None
  }
}