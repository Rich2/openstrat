/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

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

/** Companion object for [[Token]] trait contains [[ShowDec]] implicit instance. */
object Token
{
  implicit val showImplicit: ShowDecT[Token] = new ShowSimpleT[Token]
  { override def typeStr: String = "Token"
    def strT(obj: Token): String = obj.str
  }
}

/** Token that is member of a block. Includes all tokens except the brace tokens. */
trait BlockMemToken extends BlockMem with Token

trait EmptyExprToken extends BlockMemToken with ClauseMemExprToken with ExprSeq
{ override def exprs: Arr[Expr] = Arr()
}

case class SemicolonToken(startPosn: TextPosn) extends EmptyExprToken
{ def srcStr = ";"
  override def exprName: String = "EmptyStatementExpr"
  override def exprTypeStr: String = "SemicolonToken"
  override def toString: String = tokenTypeStr.appendParenthSemis(startPosn.lineNum.toString, startPosn.linePosn.toString)
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with AssignMem
{ def srcStr = ","
  override def exprName: String = "EmptyClauseExpr"
  override def exprTypeStr: String = "CommaToken"
}

/** A Token that can be a member of a Clause. */
trait ClauseMemToken extends BlockMemToken with ClauseMem

/** The Dot or Stop Token. */
case class DotToken(startPosn: TextPosn) extends ClauseMemToken
{ def srcStr = "."
  override def tokenTypeStr: String = "DotToken"
}

/** The double Dot or Stop Token. */
case class Dot2Token(startPosn: TextPosn) extends ClauseMemToken
{ def srcStr = ".."
  override def tokenTypeStr: String = "DotToken"
}

/** The triple Dot or Stop Token. */
case class Dot3Token(startPosn: TextPosn) extends ClauseMemToken
{ def srcStr = "..."
  override def tokenTypeStr: String = "DotToken"
}

case class CharToken(startPosn: TextPosn, char: Char) extends ClauseMemExprToken
{ def srcStr = char.toString.enquote1
  override def exprTypeStr: String = "CharToken"
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ClauseMemExprToken
{ def srcStr = stringStr.enquote
  override def exprTypeStr: String = "StringToken"
}

/** An Operator token. */
//trait OperatorToken extends ClauseMemberToken
case class OperatorToken(startPosn: TextPosn, srcStr: String) extends AssignMemExprToken
{ override def exprTypeStr: String = "OtherOperatorToken"
}

/** A + or - infix Operator token */
/*case class PlusInToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusInToken"
}

/** A + or - Prefix Operator token */
case class PrefixToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusPreToken"
}*/

case class AsignToken(startPosn: TextPosn) extends BlockMemToken with StatementMem
{ def srcStr = "="
  override def tokenTypeStr: String = "AsignToken"
}