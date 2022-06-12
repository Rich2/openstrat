/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
trait Expr extends BlockMem with StatementMem
{ def exprName: String
  def exprParseErr[A](implicit ev: Unshow[A]): EMon[A] = startPosn.bad(ev.typeStr -- "is not available from" -- exprName)
}

/** An expression that is a member of the right oe left side of an assignment expression. */
trait AssignMemExpr extends Expr with AssignMem

/** An expression that can be a member of a Colon expression operand. */
trait ColonMemExpr extends AssignMemExpr with ColonOpMem

/** An expression that can be a member of a [[Clause]] or the expression of clause. */
trait ClauseMemExpr extends ColonMemExpr with ClauseMem

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundExpr extends Expr with TextSpanCompound

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundClauseMemExpr extends CompoundExpr with ClauseMemExpr// with TextSpanCompound

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeq extends ColonMemExpr
{ def exprs: Arr[Expr]
}

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeqNonEmpty extends CompoundClauseMemExpr with ExprSeq
{ def exprs: Arr[AssignMemExpr]
}

object ExprSeqNonEmpty
{
  def unapply(inp: Any): Option[Arr[Expr]] = inp match {
    case esne: ExprSeqNonEmpty => Some(esne.exprs)
    case _ => None
  }
}

/** A Token that is an Expression. Most tokens are expressions, but some are not such as braces, commas and semicolons. */
trait ClauseMemExprToken extends ClauseMemExpr with ClauseMemToken
{ final override def tokenTypeStr: String = exprName + "Token"
  override def toString: String = tokenTypeStr.appendParenthSemis(srcStr, startPosn.lineNum.toString, startPosn.linePosn.toString)
}

trait BlockRaw
{ def statements: Arr[Statement]
  def startMem = statements.head
  def endMem = statements.last
}

trait BlockStatements extends ExprSeqNonEmpty
{ def statements: Arr[Statement]
  def exprs: Arr[ColonMemExpr] = statements.map(_.expr).asInstanceOf[Arr[ColonMemExpr]]
  def startMem: Statement = statements.head
  def endMem: Statement = statements.last
}

case class FileStatements(statements: Arr[Statement]) extends BlockStatements
{ def exprName: String = "FileStatements"
}

case class StringStatements(statements: Arr[Statement]) extends BlockStatements
{ def exprName: String = "StringStatements"
}

case class ClausesExpr(clauses: Arr[Clause]) extends ExprSeqNonEmpty
{ override def exprs: Arr[ClauseMemExpr] = clauses.map(_.expr)
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

case class PreOpExpr(op: OperatorToken, right: AssignMemExpr) extends CompoundClauseMemExpr
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

object AsignExprName
{
  def unapply(inp: Any): Option[String] = inp match {
    case AsignExpr(ColonExpr(IdentifierToken(settingStr), _, IdentifierToken(_)), _, rExpr) => Some(settingStr)
    case AsignExpr(IdentifierToken(settingStr), _, rExpr) => Some(settingStr)
    case _ => None
  }
}

case class ColonExpr(left: ColonMemExpr, asToken: ColonToken, right : ColonMemExpr) extends CompoundExpr with AssignMemExpr with AssignMem
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "ColonExpr"
}

case class SpacedExpr(exprs: Arr[ColonMemExpr]) extends CompoundClauseMemExpr
{ override def startMem = exprs(0)
  override def endMem = exprs.last
  override def exprName: String = "SpacedExprs"
}