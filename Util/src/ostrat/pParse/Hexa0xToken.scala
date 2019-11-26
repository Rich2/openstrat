package ostrat
package pParse

/** A hexadecimal token with a leading "0x", that can be used for standard 32 bit Ints, 64 bit Longs, as well as less used integer
 *  formats such as BigInteger and Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class Hexa0xToken(startPosn: TextPosn, digitsStr: String) extends NumToken
{ override def srcStr: String = "0x" + digitsStr
  override def subTypeStr: String = "IntHexa"
  override def getInt: Int =
  { var acc = 0
    implicit val chars = digitsStr.toChars

    def loop(rem: CharsOff): Int = rem match
    { case CharsOff0() => acc
      case CharsOff1Tail(HexaDigitChar(_, i), tail)  => { acc = acc * 16 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}

object parseHexa0xToken
{
  /** Function for parsing explicit Hexadecimal Token. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def loop(rem: CharsOff, strAcc: String, intAcc: Long): EMon3[CharsOff, TextPosn, Hexa0xToken] = rem match
    { case CharsOff0() => Good3(rem, tp.right(strAcc.length + 2), Hexa0xToken(tp, strAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => loop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOff1Plus(LetterChar(_)) => tp.bad("Badly formed hexadecimal")
      case _ => Good3(rem, tp.addStr(strAcc), Hexa0xToken(tp, strAcc))
    }

    rem match
    { case CharsOff1Tail(HexaDigitChar(c, i), tail) => loop (tail, c.toString, i)
    case CharsOff1Plus(WhitespaceChar(_)) => tp.bad("Empty hexademicmal token.")
    case CharsOff1Plus(c) => tp.bad("Badly formed hexademicmal token.")
    case CharsOff0() => tp.bad("Unclosed hexadecimal token")
    }
  }
}