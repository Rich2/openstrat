/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

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

object NatRawHexaToken {

  /** Raw hexadecimal integer starting with a digit that includes one or more 'A' .. 'F' digits */
  def apply(startPosnIn: TextPosn, srcStrIn: String): NatRawHexaToken = new NatRawHexaToken with NatHexaToken
  { val startPosn: TextPosn = startPosnIn
    val srcStr: String = srcStrIn
    override def subTypeStr: String = "HexaRaw"
    override def digitsStr = srcStr

  }

}

/** A 64 bit integer token in standard decimal format, but which can be inferred to be a raw Hexadecimal. It can be used for standard 32 bit Ints and
 *  64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract
 *  Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming languages. */
case class NatDeciToken(startPosn: TextPosn, srcStr: String) extends NatHexaToken
{ override def subTypeStr: String = "Decimal"
  override def digitsStr: String = srcStr

  def getInt: Int =
  { var acc = 0
    implicit val chars: Chars = srcStr.toChars
    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc
      case CharsOff1Tail(DigitChar(_, i), tail)  => { acc = acc * 10 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}