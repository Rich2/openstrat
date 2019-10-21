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
case class AlphaToken(startPosn: TextPosn, str: String) extends ExprToken
{ override def exprName: String = "AlphaTokenExpr"
  override def toString: String =  "AlphaToken".appendParenthSemis(str, startPosn.lineNum.toString, startPosn.linePosn.toString)
}

case class CharToken(startPosn: TextPosn, char: Char) extends ExprToken
{ def exprName = "CharTokenExpr"
  def str = char.toString.enquote1
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ExprToken
{ def exprName = "StringTokenExpr"
  def str = stringStr.enquote
}

/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, str: String) extends ExprToken
{
  override def exprName: String = "HashAlphaTokenExpr"
}

/** A 32 bit Integer Token. */
trait IntToken extends ExprToken
{ def intValue: Int
}

object IntToken
{ def unapply(token: Token): Option[(TextPosn, String, Int)] = token match
  { case it: IntToken => Some(it.startPosn, it.str, it.intValue)
    case _ => None
  }
}

/** A 32 bit integer token in standard decimal format. */
case class IntDecToken(startPosn: TextPosn, str: String, intValue: Int) extends IntToken
{
  override def toString: String = "IntDecToken".appendParenthSemis(str.toString, startPosn.lineNum.toString, startPosn.linePosn.toString)
  override def exprName: String = "IntTokenExpr"
}
case class IntHexToken(startPosn: TextPosn, str: String, intValue: Int) extends IntToken
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
