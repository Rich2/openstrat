/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Token extends FileSpan
{
   def str: String
   override def endPosn: FilePosn = FilePosn(startPosn.lineNum, startPosn.linePosn + str.length - 1, startPosn.fileName)
}

trait BlockMemberToken extends BlockMember with Token
trait EmptyExprToken extends BlockMemberToken with Expr

case class SemicolonToken(startPosn: FilePosn) extends EmptyExprToken
{ def str = ";"
  override def exprName: String = "EmptyStatementExpr"
}

case class CommaToken(startPosn: FilePosn) extends EmptyExprToken with StatementMember
{ def str = ","
  override def exprName: String = "EmptyClauseExpr"
}

trait ExprToken extends Expr with ExprMemberToken

/** The Dot or Stop Token. */
case class DotToken(startPosn: FilePosn) extends ExprMemberToken { def str = "." }

/** An Alphanumeric Token. It contains a symbol rather than a String to represent the AlphaNumeric token as commonly used Symbols have better 
 *  better performance than the equivalent Strings. */
case class AlphaToken(startPosn: FilePosn, sym: Symbol) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def str: String = sym.name
}

case class StringToken(startPosn: FilePosn, stringStr: String) extends ExprToken
{ def exprName = "StringTokenExpr"
  def str = stringStr.enqu
}

/** A token that can yield a Double Float value */

/** A 32 bit integer token */
case class IntToken(startPosn: FilePosn, str: String, intValue: Int) extends ExprToken
{
   override def exprName: String = "IntTokenExpr"
}
/** A 64bit signed integer token */
case class LongIntToken(startPosn: FilePosn, str: String, longValue: Long) extends ExprToken
{
   override def exprName: String = "LongIntTokenExpr"
}


/** A Double Floating point token. */
case class FloatToken(startPosn: FilePosn, str: String, floatValue: Double) extends ExprToken
{
   override def exprName: String = "FloatTokenExpr"
}

/** An Operator token. */
trait OperatorToken extends ExprMemberToken
case class OtherOperatorToken(startPosn: FilePosn, str: String) extends OperatorToken
/** A + or - infix Operator token */
case class PlusInToken(startPosn: FilePosn, str: String) extends OperatorToken
/** A + or - Prefix Operator token */
case class PlusPreToken(startPosn: FilePosn, str: String) extends OperatorToken
