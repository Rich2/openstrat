/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
trait Expr extends BlockMember with ClauseMember
{ def exprParseErr[A](implicit ev: UnShow[A]): EMon[A] = startPosn.bad(ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

trait Expr0 extends Expr1
trait Expr1 extends Expr

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait ExprCompound extends Expr with TextSpanCompound

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeq extends Expr
{ def exprs: Arr[Expr]
}

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeqNonEmpty extends ExprCompound with ExprSeq
{ def exprs: Arr[Expr]
}

/** A Token that is an Expression. Most tokens are expressions, but some are not such as braces, commas and semicolons. */
trait ExprToken extends Expr with ClauseMemberToken
{ def subTypeStr: String
  def exprName: String = subTypeStr + "Expr"
  final override def tokenTypeStr: String = subTypeStr + "Token"
  override def toString: String = tokenTypeStr.appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
}

trait BlockRaw
{ def statements: Arr[Statement]
 // def exprs: Refs[Expr] = statements.map(_.expr)
  def startMem = statements.head
  def endMem = statements.last
}

trait BlockStatements extends ExprSeqNonEmpty
{ def statements: Arr[Statement]
  def exprs: Arr[Expr] = statements.map(_.expr).asInstanceOf[Arr[Expr]]
  def startMem = statements.head
  def endMem = statements.last
}

case class FileStatements(statements: Arr[Statement]) extends BlockStatements
{ def exprName: String = "FileStatements"
//  def startPosn: TextPosn = statements.head.startPosn
  //def endPosn: TextPosn = statements.last.endPosn
}

case class StringStatements(statements: Arr[Statement]) extends BlockStatements
{ def exprName: String = "StringStatements"
  //def startPosn: TextPosn = statements.head.startPosn
  //def endPosn: TextPosn = statements.last.endPosn
}

case class ClausesExpr(exprs: Arr[Expr]) extends ExprSeqNonEmpty
{ def startMem = exprs.head
  def endMem = exprs.last
  override def exprName: String = "Claused Expr"
}

case class UnimplementedExpr(bMems: Arr[BlockMember]) extends ExprCompound
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}

case class AlphaBracketExpr(name: IdentifierToken, blocks: Arr[BracketedStatements]) extends ExprCompound
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"
}

case class PreOpExpr(op: OperatorToken, right: Expr) extends ExprCompound
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.srcStr
}

case class AsignExpr(left: Expr, asToken: AsignToken, right : Expr) extends ExprCompound
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"  
}

case class SpacedExpr(exprs: Arr[Expr]) extends ExprCompound
{ override def startMem = exprs(0)
  override def endMem = exprs.last
  override def exprName: String = "SpacedExprs"
}