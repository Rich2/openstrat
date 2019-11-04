/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

trait Token extends TextSpan
{ def srcStr: String
  override def endPosn: TextPosn = startPosn.addLinePosn(srcStr.length - 1)
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

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class AlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
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

/** A 32 bit Integer Token. */
trait IntToken extends ExprToken
{ def intValue: Int
}

object IntToken
{ def unapply(token: Token): Option[(TextPosn, String, Int)] = token match
  { case it: IntToken => Some(it.startPosn, it.srcStr, it.intValue)
    case _ => None
  }
}

/** An 32 or 64 bit Integer token in standard decimal format. */
trait IntLikeDeciToken extends ExprToken

/** An 32 or 64 bit Integer tiken in hexadecimal format. */
trait IntLikeHexaToken extends ExprToken

/** A 32 bit integer token in standard decimal format. */
case class IntDeciToken(startPosn: TextPosn, intValue: Int) extends IntToken with IntLikeDeciToken
{ override def toString: String = "IntDecToken".appendParenthSemis(srcStr.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def exprName: String = "IntTokenExpr"
  override def tokenTypeStr: String = "IntDecToken"
  override def srcStr: String = intValue.toString
}


/** A 32 bit interger token in hexadecimal */
case class IntHexaToken(startPosn: TextPosn, srcStr: String, intValue: Int) extends IntToken with IntLikeHexaToken
{ override def exprName: String = "HexIntTokenExpr"
  override def tokenTypeStr: String = "IntHexToken"
}

/** A 64 bit Long integer Token. */
trait LongIntToken extends ExprToken
{ def longValue: Long
}

/** A 64bit signed integer token in standard decimal format. */
case class LongDeciToken(startPosn: TextPosn, srcStr: String, longValue: Long) extends LongIntToken with IntLikeDeciToken
{ override def exprName: String = "LongDeciTokenExpr"
  override def tokenTypeStr: String = "LongDeciToken"
}

/** A 64bit signed integer token in hexadecimal format. */
case class LongHexaToken(startPosn: TextPosn, srcStr: String, longValue: Long) extends LongIntToken with IntLikeHexaToken
{ override def exprName: String = "LongHexaokenExpr"
  override def tokenTypeStr: String = "LongHexaToken"
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
