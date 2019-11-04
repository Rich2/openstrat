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

object LetterChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case _ => None
  }
}

object HexaDigitChar
{
  def unapply(input: Char): Option[(Char, Int)] = input match
  {
    case d if d.isDigit => Some((d, d - '0'))
    case l if l <= 'F' && l >= 'A' => Some((l, l - 'A' + 10))
    case l if l <= 'f' && l >= 'a' => Some((l, l - 'a' + 10))
    case c => excep(c.toString -- "is not a hexadecimal digit.")
  }
}

object WhitespaceChar
{
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isWhitespace => Some(c)
    case _ => None
  }
}