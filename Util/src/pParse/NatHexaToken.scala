/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Common trait for all tokens that can be valid hexadecimal natural numbers. */
trait NatHexaToken extends NatToken
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

trait NatRawHexaToken extends NatRawToken

object NatRawHexaToken
{
  def unapply(input: Any): Option[(TextPosn, String)] = input match {
    case nrht: NatRawHexaToken => Some((nrht.startPosn, nrht.digitsStr))
    case _ => None
  }
}

/** Raw hexadecimal natural number token, starting with a digit that includes one or more 'A' .. 'F' digits. */
case class RawHexaToken(startPosn: TextPosn, srcStr: String) extends NatRawHexaToken
{ override def exprTypeStr: String = "HexaRaw"
  override def digitsStr: String = srcStr
}