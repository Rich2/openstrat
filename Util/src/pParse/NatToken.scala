/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Function object for parsing a raw natural integer number, could be a normal decimal, hexadecimal or trigdual number. Not all natural numbers are
 * parsed with this function object. Raw hex and trigdual numbers can be encoded as alpha numeric identity tokens. */
object parseRawNatToken
{
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), NatDeciToken(tp, str))
    }

    def hexaLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => hexaLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), NatRawHexaToken(tp, str))
    }

    def trigLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = ???

    rem match
    { case CharsOff1Tail(DigitChar(i, _), tail) => deciLoop(tail, i.toString)
      case CharsOff1Tail(HexaDigitChar(i, _), tail) => hexaLoop(tail, i.toString)
    }
  }
}

/** The base trait for all integer tokens. A Natural (non negative) number Token. It contains a single property, the digitStr. The digitStr depending
 * on the class may be interpreted in 1 to 3 ways, as a normal decimal number, a hexadecimal number, or a trigdual (base 32) number. */
trait NatToken extends ExprToken
{ def digitsStr: String
}

object NatToken
{
  def unapply(token: Token): Option[(TextPosn, String)] = token match
  { case it: NatToken => Some((it.startPosn, it.srcStr))
    case _ => None
  }
}

trait IntHexaToken extends NatToken
{
  def asHexaInt: Int =
  { var acc = 0
    implicit val chars: Chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    { case CharsOff0() => acc
      case CharsOff1Tail(HexaDigitChar(_, i), tail)  => { acc = acc * 16 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}

/** A 64 bit integer token in standard decimal format, but which can be inferred to be a raw Hexadecimal. It can be used for standard 32 bit Ints and
 *  64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract
 *  Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming languages. */
case class NatDeciToken(startPosn: TextPosn, srcStr: String) extends IntHexaToken
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

/** Raw hexadecimal integer starting with a digit that includes one or more 'A' .. 'F' digits */
case class NatRawHexaToken(startPosn: TextPosn, srcStr: String) extends IntHexaToken
{ override def subTypeStr: String = "HexaRaw"
  override def digitsStr = srcStr
  def getInt: Int = asHexaInt
}