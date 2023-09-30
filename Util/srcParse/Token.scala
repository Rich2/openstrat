/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** An RSON token. */
trait Token extends TextSpan
{ def srcStr: String
  override def endPosn: TextPosn = startPosn.right(srcStr.length - 1)
  final def str: String = tokenTypeStr
  def tokenTypeStr: String
  def canEqual(a: Any): Boolean = a.isInstanceOf[Token]

  override def equals(that: Any): Boolean = that match
  { case that: Token => tokenTypeStr == that.tokenTypeStr & startPosn == that.startPosn
    case _ => false
  }

  override def hashCode(): Int = tokenTypeStr.hashCode * 31 + startPosn.hashCode
}

/** Companion object for [[Token]] trait contains [[ShowDec]] implicit instance. */
object Token
{
  implicit val showImplicit: Showing[Token] = new ShowSimpleT[Token]
  { override def typeStr: String = "Token"
    def strT(obj: Token): String = obj.str
  }
}

/** Token that is member of a block. Includes all tokens except the brace tokens. */
trait BlockMemToken extends BlockMem with Token

/** A [[Token]] that is an AssignMem expression. */
trait AssignMemExprToken extends Token with AssignMemExpr

trait EmptyExprToken extends BlockMemToken with ClauseMemExprToken with ExprSeq
{ override def exprs: RArr[Expr] = RArr()
}

case class SemicolonToken(startPosn: TextPosn) extends EmptyExprToken
{ def srcStr = ";"
  override def exprName: String = "EmptyStatementExpr"
  override def toString: String = tokenTypeStr.appendParenthSemis(startPosn.lineNum.toString, startPosn.linePosn.toString)
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with ClauseMemExpr
{ def srcStr = ","
  override def exprName: String = "EmptyClauseExpr"
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
  override def exprName: String = "CharToken"
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ClauseMemExprToken
{ def srcStr = stringStr.enquote
  override def exprName: String = "StringToken"
}

/** An Operator token. */
trait OperatorToken extends ClauseMemExprToken

case class OperatorGenToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def exprName: String = "OperatorGenToken"
}

case class SlashToken(startPosn: TextPosn) extends OperatorToken
{ override def exprName: String = "SlashToken"
  override def srcStr: String = "/"
}

case class AsignToken(startPosn: TextPosn) extends BlockMemToken with StatementMem
{ def srcStr = "="
  override def tokenTypeStr: String = "AsignToken"
}

case class ColonToken(startPosn: TextPosn) extends BlockMemToken with ClauseMem
{ def srcStr = ":"
  override def tokenTypeStr: String = "AsignToken"
}
/** The purpose of this token is for use at the beginning of a file, to make the the rest of the Statements, sub-statements. As if they were the
 *  statements inside parenthesis. */
case class HashAlphaToken(startPosn: TextPosn, srcStr: String) extends ClauseMemExprToken
{ override def exprName: String = "HashAlpha"
}

case class UnderscoreToken(startPosn: TextPosn) extends EmptyExprToken with StatementMem
{ def srcStr = "_"
  override def exprName: String = "EmptyClauseExpr"
}

/** File path token. */
case class PathToken(startPosn: TextPosn, arrayUnsafe: Array[String]) extends ClauseMemExprToken
{ override def exprName: String = "PathTokenExpr"
  override def srcStr: String = arrayUnsafe.foldLeft("")(_ + "/" + _)
}