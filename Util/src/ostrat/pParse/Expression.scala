/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse

/** Not quite sure why this extends TokenOrBlock. That doesn't seem right. */
trait Expr extends TokenOrBlock
{ def exprParseErr[A](implicit ev: Persist[A]): EMon[A] = bad1(startPosn, ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

trait MemsExpr extends Expr with TextSpanMems

case class UnimplementedExpr(bMems: Seq[BlockMember]) extends MemsExpr// with TextSpanMems
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}
case class AlphaBracketExpr(name: AlphaToken, blocks: List[BracketBlock]) extends MemsExpr// with TextSpanMems
{ def startMem = name
  def endMem = blocks.last
  override def exprName: String = "AlphaBracketExpr"
  //def errGet[A](implicit ev: PBuilder[A]): EMon[A] =
}

case class PreOpExpr(op: OperatorToken, right: Expr) extends MemsExpr
{ override def startMem = op
  override def endMem = right
  override def exprName: String = "PreOpExpr"
  def opStr = op.str
}

case class AsignExpr(asToken: AsignToken, left: Expr, right : Expr) extends MemsExpr
{ override def startMem = left
  override def endMem = right
  override def exprName: String = "AsignExpr"  
}
