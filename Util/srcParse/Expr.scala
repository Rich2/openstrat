/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
trait Expr extends BlockMem with StatementMem
{ def exprName: String
  def exprParseErr[A](implicit ev: Unshow[A]): EMon[A] = startPosn.bad(ev.typeStr -- "is not available from" -- exprName)
}

/** An expression that is a member of the right oe left side of an assignment expression. */
trait AssignMemExpr extends Expr with AssignMem
{
  def toStatements: RArr[Statement] = this match{
    case es: ExprSeq => es.exprs.map{expr => StatementNoneEmpty(expr) }
    case st: Statement => RArr(st)
    case expr => RArr(StatementNoneEmpty(expr))
  }
}

/** An expression that can be a member of a Colon expression operand. */
trait ColonMemExpr extends AssignMemExpr with ColonOpMem

/** An expression that can be a member of a [[Clause]] or the expression of clause. */
trait ClauseMemExpr extends ColonMemExpr with ClauseMem

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundExpr extends Expr with TextSpanCompound

/** A compound expression. The traits sole purpose is to give an Expr, the start and end text positions from its first and last components. */
trait CompoundClauseMemExpr extends CompoundExpr with ClauseMemExpr

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeq extends ColonMemExpr
{ def exprs: RArr[Expr]
}

/** An ExprSeq can be a sequence of Statements or a Sequence of Clauses. */
trait ExprSeqNonEmpty extends CompoundClauseMemExpr with ExprSeq
{ def exprs: RArr[AssignMemExpr]
}

object ExprSeqNonEmpty
{
  def unapply(inp: Any): Option[RArr[Expr]] = inp match {
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
{ def statements: RArr[Statement]
  def startMem: Statement = statements.head
  def endMem: Statement = statements.last
}

/** A syntactic block of [[Statement]]s, may be encapsulated by a file of pair of matching braces." */
trait BlockStatements extends ExprSeqNonEmpty
{ def statements: RArr[Statement]
  def exprs: RArr[ColonMemExpr] = statements.map(_.expr).asInstanceOf[RArr[ColonMemExpr]]
  def startMem: Statement = statements.head
  def endMem: Statement = statements.last
}

case class FileStatements(statements: RArr[Statement]) extends BlockStatements
{ def exprName: String = "FileStatements"
}

case class StringStatements(statements: RArr[Statement]) extends BlockStatements
{ def exprName: String = "StringStatements"
}

case class ClausesExpr(clauses: RArr[Clause]) extends ExprSeqNonEmpty
{ override def exprs: RArr[ClauseMemExpr] = clauses.map(_.expr)
  def startMem: ClauseMemExpr = exprs.head
  def endMem: ClauseMemExpr = exprs.last
  override def exprName: String = "Claused Expr"
}

case class UnimplementedExpr(bMems: RArr[BlockMem]) extends CompoundClauseMemExpr
{ def startMem: BlockMem = bMems.head
  def endMem: BlockMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}

/** An Identifier Token followed by 1 or more brace blocks. */
case class AlphaBracketExpr(name: IdentifierToken, blocks: RArr[BracketedStructure]) extends CompoundClauseMemExpr
{ def startMem: IdentifierToken = name
  def endMem: BracketedStructure = blocks.last
  override def exprName: String = "AlphaBracketExpr"
}

case class PreOpExpr(op: OperatorToken, right: ClauseMemExpr) extends CompoundClauseMemExpr
{ override def startMem: OperatorToken = op
  override def endMem: AssignMemExpr = right
  override def exprName: String = "PreOpExpr"
  def opStr: String = op.srcStr
}

case class InfixOpExpr(left: ClauseMemExpr, op: OperatorToken, right: ClauseMemExpr) extends CompoundClauseMemExpr
{ override def startMem: AssignMemExpr = left
  override def endMem: AssignMemExpr = right
  override def exprName: String = "PreOpExpr"
  def opStr: String = op.srcStr
}

case class AsignExpr(left: AssignMemExpr, asToken: AsignToken, right : AssignMemExpr) extends CompoundExpr
{ override def startMem: AssignMemExpr = left
  override def endMem: AssignMemExpr = right
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
{ override def startMem: ColonMemExpr = left
  override def endMem: ColonMemExpr = right
  override def exprName: String = "ColonExpr"
}

case class SpacedExpr(exprs: RArr[ColonMemExpr]) extends CompoundClauseMemExpr
{ override def startMem: ColonMemExpr = exprs(0)
  override def endMem: ColonMemExpr = exprs.last
  override def exprName: String = "SpacedExprs"
}

object IntExpr
{
  def unapply(inp: Expr): Option[Int] = Unshow.intEv.fromExpr(inp) match {
    case Good(i) => Some(i)
    case _ => None
  }
}