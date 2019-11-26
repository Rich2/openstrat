package ostrat
package pParse

/** Function object for parsing expected Hexadecimal number. */
object parseNumberToken
{

  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), DecimalToken(tp, str))
    }

    def hexaLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => hexaLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), HexaRawToken(tp, str))
    }

    rem match
    { case CharsOff1Tail(DigitChar(i, _), tail) => deciLoop(tail, i.toString)
      case CharsOff1Tail(HexaDigitChar(i, _), tail) => hexaLoop(tail, i.toString)
    }
  }
}

/** A Natural (non negative) number Token. */
trait NumToken extends ExprToken
{ def getInt: Int
  def tokenTypeStr: String
  override def toString: String = tokenTypeStr.appendParenthSemis(srcStr.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
}

object NumToken
{ def unapply(token: Token): Option[(TextPosn, String)] = token match
  { case it: NumToken => Some((it.startPosn, it.srcStr))
    case _ => None
  }
}

/** A 64 bit integer token in standard decimal format, but which can be infered to be a raw Hexadecimal. It can be used for standard 32 bit Ints and
 *  64 bit Longs, as well as less used integer formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract
 *  Syntax Tree) levels stores data not code, although of course at the higher semantic levels it can be used very well for programming languages. */
case class DecimalToken(startPosn: TextPosn, srcStr: String) extends NumToken
{ override def exprName: String = "IntDeciTokenExpr"
  override def tokenTypeStr: String = "IntDeciToken"

  override def getInt: Int =
  { var acc = 0
    implicit val chars = srcStr.toChars
    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc
      case CharsOff1Tail(DigitChar(_, i), tail)  => { acc = acc * 10 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}

case class HexaRawToken(startPosn: TextPosn, srcStr: String) extends NumToken
{ override def exprName: String = "HexaRawExpr"
  override def tokenTypeStr: String = "HexaRawToken"

  override def getInt: Int = ???
}