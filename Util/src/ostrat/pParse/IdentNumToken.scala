package ostrat
package pParse

class IdentNum {

}

trait IdentUpperToken extends ExprToken
trait IdentLowerToken extends ExprToken

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class IdentLowerOnlyToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def tokenTypeStr: String = "AlphaToken"
}

case class IdentHexaToken(startPosn: TextPosn, srcStr: String) extends IdentUpperToken
{
  def exprName: String = "IdentHexaExpr"
  override def tokenTypeStr: String = "IdentHexaToken"
}

case class IdentLowerTrigToken(startPosn: TextPosn, srcStr: String) extends IdentLowerToken
{
  def exprName: String = "IdentLowerTrigExpr"
  override def tokenTypeStr: String = "IdentLowerTrigToken"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "HashAlphaTokenExpr"
  override def tokenTypeStr: String = "HashAlphaToken"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def tokenTypeStr: String = "CommaToken"
}

/** A Natural (non negative) number Token. */
trait NumToken extends ExprToken
{ def getInt: Int
}

object NumToken
{ def unapply(token: Token): Option[(TextPosn, String)] = token match
{ case it: NumToken => Some((it.startPosn, it.srcStr))
  case _ => None
}
}

/** A 64 bit integer token in standard decimal format, that can be used for standard 32 bit Ints and 64 bit Longs, as well as less used integer
 *  formats such as Byte. This is in accord with the principle that RSON at the Token and AST (Abstract Syntax Tree) levels stores data not code,
 *  although of course at the higher semantic levels it can be used very well for programming languages. */
case class DecimalToken(startPosn: TextPosn, srcStr: String) extends NumToken
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
case class HexaDecimalToken(startPosn: TextPosn, digitsStr: String) extends NumToken
{ override def srcStr: String = "0x" + digitsStr
  override def exprName: String = "IntHexTokenExpr"
  override def tokenTypeStr: String = "IntHexaToken"
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