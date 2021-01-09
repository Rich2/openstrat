/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse

/** Function object for parsing a raw Int number, could be a normal decimal, hexadecimal or trigdual number. */
object parseRawIntToken
{
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), DeciIntToken(tp, str))
    }

    def hexaLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => hexaLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), HexaOnlyIntToken(tp, str))
    }

    rem match
    { case CharsOff1Tail(DigitChar(i, _), tail) => deciLoop(tail, i.toString)
      case CharsOff1Tail(HexaDigitChar(i, _), tail) => hexaLoop(tail, i.toString)
    }
  }
}

/** A Natural (non negative) number Token. */
trait IntToken extends ExprToken
{ def getInt: Int
}

object IntToken
{
  def unapply(token: Token): Option[(TextPosn, String)] = token match
  { case it: IntToken => Some((it.startPosn, it.srcStr))
    case _ => None
  }
}

trait MaybeHexaToken extends IntToken
{
  def digitsStr: String

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
case class DeciIntToken(startPosn: TextPosn, srcStr: String) extends MaybeHexaToken
{ override def subTypeStr: String = "Decimal"
  override def digitsStr: String = srcStr

  override def getInt: Int =
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
case class HexaOnlyIntToken(startPosn: TextPosn, srcStr: String) extends MaybeHexaToken
{ override def subTypeStr: String = "HexaRaw"
  override def digitsStr = srcStr
  override def getInt: Int = asHexaInt
}