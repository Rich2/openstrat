/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Extractor function object for upper case letter character. */
object LetterUpper
{ /** Extractor unapply method for upper case letter character. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isUpper => Some(c)
    case _ => None
  }
}

/** Extractor function object for lower case letter character. */
object LetterLower
{ /** Extractor unapply method for upper case letter character. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLower => Some(c)
    case _ => None
  }
}

/** Extractor function object for letter character. */
object LetterChar
{ /** Extractor unapply method for letter character. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case _ => None
  }
}

/** Extractor function object for digit character. */
object DigitCharNum
{ /** Extractor unapply method for digit character. */
  def unapply(input: Char): Option[Int] = input match
  { case c if c.isDigit => Some(c - '0')
    case _ => None
  }
}

/** Extractor function object for digit character. */
object DigitChar
{ /** Extractor unapply method for digit character. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isDigit => Some(c)
    case _ => None
  }
}

/** Extractor function object for letter or digit character. */
object LetterOrDigitChar
{ /** Extractor unapply method for letter or digit character. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case c if c.isDigit => Some(c)
    case _ => None
  }
}

/** Extractor function object for letter or underscore. */
object LetterOrUnderscoreChar
{ /** Extractor unapply method for letter or underscore. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isLetter => Some(c)
    case '_' => Some('_')
    case _ => None
  }
}

/** Extractor function object for hexadecimal characters. */
object HexaDigitChar
{ /** Extractor unapply method for hexadecimal characters. */
  def unapply(input: Char): Option[(Char, Int)] = input match
  {
    case n if n.isDigit => Some((n, n - '0'))
    case l if l <= 'F' && l >= 'A' => Some((l, l - 'A' + 10))
    case l if l <= 'f' && l >= 'a' => Some((l, l - 'a' + 10))
    case c => None
  }
}

/** Extractor function object for upper case Hexadecimal letter, 'A' .. 'F'. */
object HexaUpperChar
{ /** Extractor unapply method for upper case Hexadecimal letter, 'A' .. 'F'. */
  def unapply(input: Char): Option[Char] = input match
  { case l if l <= 'F' && l >= 'A' => Some(l)
    case c => None
  }
}

/** Extractor function object for lower case hexadecimal letter, 'a' .. 'f'. */
object HexaLowerChar
{ /** Extractor unapply method for lower case hexadecimal letter, 'a' .. 'f'. */
  def unapply(input: Char): Option[Char] = input match
  { case l if l <= 'f' && l >= 'a' => Some(l)
    case c => None
  }
}

/** Extractor function object for a Base32 upper case letter, 'A' .. 'N' and 'P'.. 'W'. */
object Base32UpperChar
{ /** Extractor unapply method for a Base32 upper case letter, 'A' .. 'N' and 'P'.. 'W'. */
  def unapply(input: Char): Option[Char] = input match
  { case l if l >= 'A' && l <= 'N' => Some(l)
    case l if l >= 'P' && l <= 'W' => Some(l)
    case c => None
  }
}

/** Extractor function object for a Base32 lower case letter, 'a' .. 'n' and 'p'.. 'w'. */
object Base32LowerChar
{ /** Extractor unapply method for a Base32 lower case letter, 'a' .. 'n' and 'p'.. 'w'. */
  def unapply(input: Char): Option[Char] = input match
  { case n if n.isDigit => Some(n)
    case c if c >= 'a' & c <= 'n' => Some(c)
    case c if c >= 'p' & c <= 'w' => Some(c)
    case _ => None
  }
}

/** Extractor object for whitespace characters. */
object WhitespaceChar
{ /** Extractor unapply method for whitespace characters. */
  def unapply(input: Char): Option[Char] = input match
  { case c if c.isWhitespace => Some(c)
    case _ => None
  }
}

/** Extractor object for operator characters. */
object OperatorChar
{ /** Extractor unapply method for operator characters. */
  def unapply(input: Char): Option[Char] = input match
  { case c if pParse.isOperator(c) => Some(c)
    case _ => None
  }
}