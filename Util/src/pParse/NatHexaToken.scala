/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Common trait for all tokens that can be valid hexadecimal natural numbers as well as valid base32 numbers. */
trait NatHexaToken extends ValidIntToken
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
trait ValidRawHexaNatToken extends ValidIntToken
{ /** Interpreting the token in raw hexadecimal format, returns a natural number Int. An Int by Scala type, that is non negative. This method should
      only be use in narrowly defined data formats not for general purpose programming. */
  def natAsRawHexa: Int = ???
}

object ValidRawHexaNatToken
{ /** unapply method needs modifying to use natAsRawHex method. */
  def unapply(input: Any): Option[(TextPosn, String)] = input match {
    case nrht: ValidRawHexaNatToken => Some((nrht.startPosn, nrht.digitsStr))
    case _ => None
  }
}

/** Raw hexadecimal natural number token, starting with a digit that includes one or more 'A' .. 'F' digits. */
case class RawHexaToken(startPosn: TextPosn, srcStr: String) extends ValidRawHexaNatToken
{ override def exprTypeStr: String = "HexaRaw"
  override def digitsStr: String = srcStr
}