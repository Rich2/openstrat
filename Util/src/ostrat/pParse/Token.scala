/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

trait Token extends TextSpan
{
   def str: String
   override def endPosn: TextPosn = startPosn.addLinePosn(str.length - 1)
}

trait BlockMemberToken extends BlockMember with Token
trait EmptyExprToken extends BlockMemberToken with ExprToken

case class SemicolonToken(startPosn: TextPosn) extends EmptyExprToken
{ def str = ";"
  override def exprName: String = "EmptyStatementExpr"
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with StatementMember
{ def str = ","
  override def exprName: String = "EmptyClauseExpr"
}

/** The Dot or Stop Token. */
case class DotToken(startPosn: TextPosn) extends ExprMemberToken { def str = "." }

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better 
 *  better performance than the equivalent Strings. */
case class AlphaToken(startPosn: TextPosn, sym: Symbol) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def str: String = sym.name
}

case class CharToken(startPosn: TextPosn, char: Char) extends ExprToken
{ def exprName = "CharTokenExpr"
  def str = char.toString.enqu1
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ExprToken
{ def exprName = "StringTokenExpr"
  def str = stringStr.enqu
}

trait IntLikeToken extends ExprToken
{
  def intValue: Int
}

/** A 32 bit integer token */
case class IntToken(startPosn: TextPosn, str: String, intValue: Int) extends IntLikeToken
{
   override def exprName: String = "IntTokenExpr"
}

case class HexIntToken(startPosn: TextPosn, str: String, intValue: Int) extends IntLikeToken
{
   override def exprName: String = "HexIntIntTokenExpr"
}


/** A 64bit signed integer token */
case class LongIntToken(startPosn: TextPosn, str: String, longValue: Long) extends ExprToken
{
   override def exprName: String = "LongIntTokenExpr"
}


/** A Double Floating point token. */
case class FloatToken(startPosn: TextPosn, str: String, floatValue: Double) extends ExprToken
{
   override def exprName: String = "FloatTokenExpr"
}

/** An Operator token. */
trait OperatorToken extends ExprMemberToken
case class OtherOperatorToken(startPosn: TextPosn, str: String) extends OperatorToken
/** A + or - infix Operator token */
case class PlusInToken(startPosn: TextPosn, str: String) extends OperatorToken
/** A + or - Prefix Operator token */
case class PlusPreToken(startPosn: TextPosn, str: String) extends OperatorToken
case class AsignToken(startPosn: TextPosn) extends ExprMemberToken { def str = "=" }

