/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait IsType[A <: AnyRef]
{ def isType(obj: AnyRef): Boolean
  def asType(obj: AnyRef): A
  def optType(obj: AnyRef): Option[A] = ifSome(isType(obj), asType(obj))
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
  { case c if c.isDigit => Some(c, c - '0')
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

object WhitespaceChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isWhitespace => Some(c)
    case _ => None
  }
}