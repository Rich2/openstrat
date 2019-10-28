/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

trait Token extends TextSpan
{ def srcStr: String
  override def endPosn: TextPosn = startPosn.addLinePosn(srcStr.length - 1)
  def str: String
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
  override def str: String = "SemicolonToken"
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def srcStr = ","
  override def exprName: String = "EmptyClauseExpr"
  override def str: String = "CommaToken"
}

/** The Dot or Stop Token. */
case class DotToken(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = "."
  override def str: String = "DotToken"
}

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better
 *  better performance than the equivalent Strings. */
case class AlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def str: String = "AlphaToken"
}

case class CharToken(startPosn: TextPosn, char: Char) extends ExprToken
{ def exprName = "CharTokenExpr"
  def srcStr = char.toString.enquote1
  override def str: String = "CharToken"
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ExprToken
{ def exprName = "StringTokenExpr"
  def srcStr = stringStr.enquote
  override def str: String = "StringToken"
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ExprToken
{ override def exprName: String = "HashAlphaTokenExpr"
  override def str: String = "HashAlphaToken"
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

/** A 32 bit integer token in standard decimal format. */
case class IntDecToken(startPosn: TextPosn, srcStr: String, intValue: Int) extends IntToken
{ override def toString: String = "IntDecToken".appendParenthSemis(srcStr.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def exprName: String = "IntTokenExpr"
  override def str: String = "IntDecToken"
}
case class IntHexToken(startPosn: TextPosn, srcStr: String, intValue: Int) extends IntToken
{ override def exprName: String = "HexIntIntTokenExpr"
  override def str: String = "IntHexToken"
}


/** A 64bit signed integer token */
case class LongIntToken(startPosn: TextPosn, srcStr: String, longValue: Long) extends ExprToken
{ override def exprName: String = "LongIntTokenExpr"
  override def str: String = "LongIntToken"
}


/** A Double Floating point token. */
case class FloatToken(startPosn: TextPosn, srcStr: String, floatValue: Double) extends ExprToken
{ override def exprName: String = "FloatTokenExpr"
  override def str: String = "FloatToken"
}

/** An Operator token. */
trait OperatorToken extends ExprMemberToken
case class OtherOperatorToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def str: String = "OtherOperatorToken"
}
/** A + or - infix Operator token */
case class PlusInToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def str: String = "PlusInToken"
}
/** A + or - Prefix Operator token */
case class PlusPreToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def str: String = "PlusPreToken"
}
case class AsignToken(startPosn: TextPosn) extends ExprMemberToken
{ def srcStr = "="
  override def str: String = "AsignToken"
}
