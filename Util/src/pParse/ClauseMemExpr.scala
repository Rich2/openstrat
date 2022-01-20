/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
trait Expr extends BlockMem with StatementMem
{ def exprName: String
  def exprParseErr[A](implicit ev: UnShow[A]): EMon[A] = startPosn.bad(ev.typeStr -- "is not available from" -- exprName)
}

/** An expression that is a member of the right oe left side of an assignment expression. */
trait AssignMemExpr extends Expr with AssignMem

/** An expression that can be a member of a [[Clause]]. */
trait ClauseMemExpr extends AssignMemExpr with ClauseMem

trait Expr0 extends Expr1
trait Expr1 extends ClauseMemExpr

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundExpr extends Expr with TextSpanCompound

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundClauseMemExpr extends CompoundExpr with ClauseMemExpr// with TextSpanCompound

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeq extends ClauseMemExpr
{ def exprs: Arr[Expr]
}

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeqNonEmpty extends CompoundClauseMemExpr with ExprSeq
{ def exprs: Arr[ClauseMemExpr]
}

/** A Token that is an Expression. Most tokens are expressions, but some are not such as braces, commas and semicolons. */
trait ExprToken extends ClauseMemExpr with ClauseMemToken
{ /** This with the addition of the "Expr" [[String]] gives the exprName property. */
  def exprTypeStr: String

  def exprName: String = exprTypeStr + "Expr"
  final override def tokenTypeStr: String = exprTypeStr + "Token"
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
  def exprs: Arr[ClauseMemExpr] = statements.map(_.expr).asInstanceOf[Arr[ClauseMemExpr]]
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

case class ClausesExpr(clauses: Arr[Clause]) extends ExprSeqNonEmpty
{ def exprs: Arr[ClauseMemExpr] = clauses.map(_.expr)
  def startMem = exprs.head
  def endMem = exprs.last
  override def exprName: String = "Claused Expr"
}

case class UnimplementedExpr(bMems: Arr[BlockMem]) extends CompoundClauseMemExpr
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}

case class AlphaBracketExpr(name: IdentifierToken, blocks: Arr[BracketedStatements]) extends CompoundClauseMemExpr
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"
}

case class PreOpExpr(op: OperatorToken, right: ClauseMemExpr) extends CompoundClauseMemExpr
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.srcStr
}

case class AsignExpr(left: AssignMemExpr, asToken: AsignToken, right : AssignMemExpr) extends CompoundExpr
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"
}

case class SpacedExpr(exprs: Arr[ClauseMemExpr]) extends CompoundClauseMemExpr
{ override def startMem = exprs(0)
  override def endMem = exprs.last
  override def exprName: String = "SpacedExprs"
}