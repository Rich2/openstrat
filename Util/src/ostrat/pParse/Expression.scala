/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** The fundamental expression trait. As it currently stands properly formed Statements either is empty or contains an expression or a sequence of
 *  clauses that contain each contain an expression. Not quite sure why this extends TokenOrBlock. That doesn't seem right. */
sealed trait Expr extends TokenOrBlock
{ def exprParseErr[A](implicit ev: Persist[A]): EMon[A] = bad1(startPosn, ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

/** I'm renaming this a CompoundExpr from MemsExpr. I think this is not an Expression Sequence Expression. */
trait CompoundExpr extends Expr with TextSpanMems

trait ExprToken extends Expr with ExprMemberToken

case class UnimplementedExpr(bMems: Seq[BlockMember]) extends CompoundExpr
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}
case class AlphaBracketExpr(name: AlphaToken, blocks: List[BracketBlock]) extends CompoundExpr
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"
  //def errGet[A](implicit ev: PBuilder[A]): EMon[A] =
}

case class PreOpExpr(op: OperatorToken, right: Expr) extends CompoundExpr
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.str
}

case class AsignExpr(asToken: AsignToken, left: Expr, right : Expr) extends CompoundExpr
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"  
}
