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

  //override def toString = "Unnamed Token"
}

object Token
{
  implicit val showImplicit: ShowT[Token] = new ShowSimpleT[Token]
  { override def typeStr: String = "Token"
    def strT(obj: Token): String = obj.str
  }
}

trait BlockMemberToken extends BlockMember with Token

trait EmptyExprToken extends BlockMemberToken with ExprToken with ExprSeq
{ override def exprs: Arr[ParseExpr] = Arr()
}

case class SemicolonToken(startPosn: TextPosn) extends EmptyExprToken
{ def srcStr = ";"
  override def exprName: String = "EmptyStatementExpr"
  override def subTypeStr: String = "SemicolonToken"
  override def toString: String = tokenTypeStr.appendParenthSemis(startPosn.lineNum.toString, startPosn.linePosn.toString)
}

case class CommaToken(startPosn: TextPosn) extends EmptyExprToken with AssignmentMember
{ def srcStr = ","
  override def exprName: String = "EmptyClauseExpr"
  override def subTypeStr: String = "CommaToken"
}

/** A Token that can be a member of a Clause. */
trait ClauseMemberToken extends BlockMemberToken with ClauseMember

/** The Dot or Stop Token. */
case class DotToken(startPosn: TextPosn) extends ClauseMemberToken
{ def srcStr = "."
  override def tokenTypeStr: String = "DotToken"
}

/** The double Dot or Stop Token. */
case class Dot2Token(startPosn: TextPosn) extends ClauseMemberToken
{ def srcStr = ".."
  override def tokenTypeStr: String = "DotToken"
}

/** The triple Dot or Stop Token. */
case class Dot3Token(startPosn: TextPosn) extends ClauseMemberToken
{ def srcStr = "..."
  override def tokenTypeStr: String = "DotToken"
}

case class CharToken(startPosn: TextPosn, char: Char) extends ExprToken
{ def srcStr = char.toString.enquote1
  override def subTypeStr: String = "CharToken"
}

case class StringToken(startPosn: TextPosn, stringStr: String) extends ExprToken
{ def srcStr = stringStr.enquote
  override def subTypeStr: String = "StringToken"
}

/** An Operator token. */
//trait OperatorToken extends ClauseMemberToken
case class OperatorToken(startPosn: TextPosn, srcStr: String) extends ClauseMemberToken // OperatorToken
{ override def tokenTypeStr: String = "OtherOperatorToken"
}

/** A + or - infix Operator token */
/*case class PlusInToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusInToken"
}

/** A + or - Prefix Operator token */
case class PrefixToken(startPosn: TextPosn, srcStr: String) extends OperatorToken
{ override def tokenTypeStr: String = "PlusPreToken"
}*/

case class AsignToken(startPosn: TextPosn) extends BlockMemberToken with StatementMember
{ def srcStr = "="
  override def tokenTypeStr: String = "AsignToken"
}