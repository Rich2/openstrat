/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. */
sealed trait Expr extends TokenOrBlock with ExprMember
{ def exprParseErr[A](implicit ev: Persist[A]): EMon[A] = bad1(startPosn, ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

/** A compound expression. Its sole purpose is to give the start and end text positions from its first and last components. */
trait ExprCompound extends Expr with TextSpanCompound

trait ExprToken extends Expr with ExprMemberToken

trait StatementSeqLike extends Expr { def statements: List[Statement] }

case class ClausesExpr(exprs: List[Expr]) extends ExprCompound
{  def startMem = exprs.head
  def endMem = exprs.last
  override def exprName: String = "Claused Expr"
}

case class UnimplementedExpr(bMems: Seq[BlockMember]) extends ExprCompound
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}
case class AlphaBracketExpr(name: AlphaToken, blocks: List[BracketBlock]) extends ExprCompound
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"
  //def errGet[A](implicit ev: PBuilder[A]): EMon[A] =
}

case class PreOpExpr(op: OperatorToken, right: Expr) extends ExprCompound
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.str
}

case class AsignExpr(asToken: AsignToken, left: Expr, right : Expr) extends ExprCompound
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"  
}
