/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Common trait for all tokens that can be valid hexadecimal natural numbers as well as valid base32 numbers. */
trait ValidHexaIntToken extends ValidIntToken
{
  def asHexaInt: Int =
  { var acc = 0
    implicit val chars: Chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    { case CharsOff0() => acc
      case CharsOff1Tail(HexaDigitChar(_, i), tail)  => { acc = acc * 16 + i; loop(tail) }
      case _ => excep("Case not implemented")
    }
    loop(chars.offsetter0)
  }
}

/** Valid Raw natural number compatible with hexadecimal format. This trait exists for its natAsRawHexa method and the associated unapply method.in
 *  the companion object. */
trait ValidRawHexaIntToken extends ValidHexaIntToken

object ValidRawHexaIntToken
{ /** unapply method needs modifying to use natAsRawHex method. */
  def unapply(input: Any): Option[(TextPosn, String)] = input match {
    case vrhit: ValidRawHexaIntToken => Some((vrhit.startPosn, vrhit.digitsStr))
    case _ => None
  }
}

/** Valid raw negative hexadecimal Int Token. */
trait ValidRawHexaNatToken extends ValidRawHexaIntToken
{ /** Interpreting this token as a raw hexdecimal returns a natural number. */
  def asHexaNat: Int = asHexaInt
}

object ValidRawHexaNatToken
{
  //def unapply
}

/** Valid raw negative hexadecimal Int Token. */
trait ValidRawHexaNegToken extends ValidRawHexaIntToken
{ override def asHexaInt: Int = -super.asHexaInt
}

/** Raw hexadecimal natural number token, starting with a digit that includes one or more 'A' .. 'F' digits. */
case class RawHexaNatToken(startPosn: TextPosn, srcStr: String) extends ValidRawHexaNatToken
{ override def exprTypeStr: String = "HexaRaw"
  override def digitsStr: String = srcStr
}

/** Raw hexadecimal natural number token, starting with a digit that includes one or more 'A' .. 'F' digits. */
case class RawHexaNegToken(startPosn: TextPosn, srcStr: String) extends ValidRawHexaNegToken
{ override def exprTypeStr: String = "HexaRaw"
  override def digitsStr: String = srcStr

}