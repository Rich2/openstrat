/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The base trait for all integer tokens. A Natural (non negative) number Token. It contains a single property, the digitStr. The digitStr depending
* on the class may be interpreted in 1 to 3 ways, as a normal decimal number, a hexadecimal number, or a trigdual (base 32) number. */
trait NatToken extends ExprToken
{ def digitsStr: String
}

/** A raw valid natural number Token. All of these tokens are valid raw Base 32 tokens. A subset will be valid hexadecimal natural numbers and a
 * subset of them will be valid decimal numbers. */
trait NatRawToken extends NatBase32Token

/** A raw natural number token. */
trait DigitsRawToken extends NatRawToken

/** A raw integer token could be negative. */
trait IntDeciToken extends DigitsRawToken
{
  /** gets the natural integer value from this token interpreting it as a standard Base10 notation. */
  def getInt: Int =
  { var acc = 0
    implicit val chars: Chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    { case CharsOff0() => acc
      case CharsOff1Tail(DigitCharNum(i), tail)  => { acc = acc * 10 + i; loop(tail) }
    }

    loop(chars.offsetter0)
  }
}

/** Companion object for the [[IntDecToken]] trait, only contains an unapply method. */
object IntDeciToken
{ /** Factory unapply method for the [[IntDecToken]] trait. */
  def unapply(inp: Token): Option[Int] = inp match {
    case idt: IntDeciToken => Some(idt.getInt)
    case _ => None
  }
}

/** A 64 bit natural number token in standard decimal format, but which can be inferred to be a raw Hexadecimal. It can be used for standard 32 bit
 *  Ints and 64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST
 *  (Abstract Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming
 *  languages. */
case class NatDeciToken(startPosn: TextPosn, srcStr: String) extends NatHexaToken with IntDeciToken with DigitSeqsCode
{ override def exprTypeStr: String = "Decimal"
  override def digitsStr: String = srcStr
  override def digitSeqs: Strings = Strings(digitsStr)
  override def trail: String = ???
}

/** Negative natural number token. There must be no space between the '-' character and the digits. */
case class IntNegToken(startPosn: TextPosn, digitsStr: String) extends IntDeciToken
{ override def exprTypeStr: String = "IntNeg"
  override def srcStr: String = "-" + digitsStr
  override def getInt: Int = -super.getInt
}