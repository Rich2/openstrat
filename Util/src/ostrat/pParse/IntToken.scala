package ostrat
package pParse

/** A 64 bit Integer Token. */
trait IntToken extends ExprToken
{ def getInt: Int
}

object IntToken
{ def unapply(token: Token): Option[(TextPosn, String)] = token match
{ case it: IntToken => Some((it.startPosn, it.srcStr))
  case _ => None
}
}

/** A 64 bit integer token in standard decimal format, that can be used for standard 32 bit Ints and 64 bit Longs, as well as less used integer
 *  formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class IntDeciToken(startPosn: TextPosn, srcStr: String) extends IntToken //with IntLikeDeciToken
{ override def toString: String = "IntDeciToken".appendParenthSemis(srcStr.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def exprName: String = "IntDeciTokenExpr"
  override def tokenTypeStr: String = "IntDeciToken"
  //override def  = intValue.toString
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

/** A 64 bit integer token in hexadecimal format, that can be used for standard 32 bit Ints and 64 bit Longs, as well as less used integer
 *  formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class IntHexaToken(startPosn: TextPosn, digitsStr: String) extends IntToken
{ override def srcStr: String = "0x" + digitsStr
  override def exprName: String = "IntHexTokenExpr"
  override def tokenTypeStr: String = "IntHexaToken"
  override def getInt: Int =
  { var acc = 0
    implicit val chars = digitsStr.toChars
    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc
      case CharsOff1Tail(HexaDigitChar(_, i), tail)  => { acc = acc * 16 + i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}

/** Function object for parsing expected Hexadecimal number. */
object parseNumberToken
{
  /** Function for parsing expected Hexadecimal number. */
  def apply(rem: CharsOff, tp: TextPosn)(implicit charArr: Chars): EMon3[CharsOff, TextPosn, Token] =
  {
    def hexaLoop(rem: CharsOff, strAcc: String, intAcc: Long): EMon3[CharsOff, TextPosn, IntHexaToken] = rem match
    { case CharsOff0() => Good3(rem, tp.right(strAcc.length + 2), IntHexaToken(tp, strAcc))
      case CharsOff1Tail(HexaDigitChar(c, i), tail) => hexaLoop(tail, strAcc + c, intAcc * 16 + i)
      case CharsOff1Plus(LetterChar(_)) => tp.bad("Badly formed hexadecimal")
      case _ => Good3(rem, tp.addStr(strAcc), IntHexaToken(tp, strAcc))
    }

    def deciLoop(rem: CharsOff, str: String): EMon3[CharsOff, TextPosn, Token] = rem match
    { case CharsOff1Tail(d, tail) if d.isDigit => deciLoop(tail, str + d.toString)
      case _ => Good3(rem, tp.addStr(str), IntDeciToken(tp, str))
    }

    rem match
    { case CharsOff3Tail('0', 'x', HexaDigitChar(c, i), tail) => hexaLoop (tail, c.toString, i)
      case CharsOff3Plus('0', 'x', WhitespaceChar(_)) => tp.bad("Empty hexademicmal token.")
      case CharsOff3Plus('0', 'x', c) => tp.bad("Badly formed hexademicmal token.")
      case CharsOff2('O', 'x') => tp.bad("Unclosed hexadecimal token")
      case CharsOff1Tail(DigitChar(i), tail) => deciLoop(tail, i.toString)
    }
  }
}
