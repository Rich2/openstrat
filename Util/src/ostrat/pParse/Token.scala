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