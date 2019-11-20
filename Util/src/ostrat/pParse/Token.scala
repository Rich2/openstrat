/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

trait Token extends TextSpan
{ def srcStr: String
  override def endPosn: TextPosn = startPosn.right(srcStr.length - 1)
  final def str: String = tokenTypeStr
  def tokenTypeStr: String
  def canEqual(a: Any) = a.isInstanceOf[Token]

  override def equals(that: Any): Boolean = that match
  { case that: Token => tokenTypeStr == that.tokenTypeStr & startPosn == that.startPosn
    case _ => false
  }

  override def hashCode(): Int = tokenTypeStr.hashCode * 31 + startPosn.hashCode
}

object Token
{
  implicit val showImplicit: Show[Token] = new ShowSimple[Token]("Token")
  { def show(obj: ostrat.pParse.Token): String = obj.str
  }
}

trait BlockMemberToken extends BlockMember with Token
trait EmptyExprToken extends BlockMemberToken with ExprToken

case class SemicolonToken(startPosn: TextPosn) extends EmptyExprToken
{ def srcStr = ";"
  override def exprName: String = "EmptyStatementExpr"
  override def tokenTypeStr: String = "SemicolonToken"
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = ","
  override def exprName: String = "EmptyClauseExpr"
  override def tokenTypeStr: String = "CommaToken"
}

/** The Dot or Stop Token. */
case class DotToken(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = "."
  override def tokenTypeStr: String = "DotToken"
}

/** The double Dot or Stop Token. */
case class Dot2Token(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = ".."
  override def tokenTypeStr: String = "DotToken"
}

/** The triple Dot or Stop Token. */
case class Dot3Token(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = "..."
  override def tokenTypeStr: String = "DotToken"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
  override def tokenTypeStr: String = "CommaToken"
}

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class IdentifierToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def tokenTypeStr: String = "AlphaToken"
}

case class CharToken(startPosn: TextPosn, char: Char) extends ExprToken
{ def exprName = "CharTokenExpr"
  def srcStr = char.toString.enquote1
  override def tokenTypeStr: String = "CharToken"
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ExprToken
{ def exprName = "StringTokenExpr"
  def srcStr = stringStr.enquote
  override def tokenTypeStr: String = "StringToken"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "HashAlphaTokenExpr"
  override def tokenTypeStr: String = "HashAlphaToken"
}

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
  override def getInt: Int = ???
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
    implicit val chars = digitsStr.reverse.toChars
    def loop(rem: CharsOff): Int = rem match
    {
      case CharsOff0() => acc
      case CharsOff1Tail(HexaDigitChar(_, i), tail)  => { acc += i; loop(tail) }
    }
    loop(chars.offsetter0)
  }
}

/** A Double Floating point token. */
case class FloatToken(startPosn: TextPosn, srcStr: String, floatValue: Double) extends ExprToken
{ override def exprName: String = "FloatTokenExpr"
  override def tokenTypeStr: String = "FloatToken"
}

/** An Operator token. */
trait OperatorToken extends ExprMemberToken
case class OtherOperatorToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "OtherOperatorToken"
}
/** A + or - infix Operator token */
case class PlusInToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusInToken"
}
/** A + or - Prefix Operator token */
case class PrefixToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusPreToken"
}
case class AsignToken(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = "="
  override def tokenTypeStr: String = "AsignToken"
}