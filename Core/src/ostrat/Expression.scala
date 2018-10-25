/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Expr extends TokenOrBlock
{ def exprParseErr[A](implicit ev: Persist[A]): EMon[A] = bad1(startPosn, ev.typeStr -- "is not available from" -- exprName)
  def exprName: String
}

trait MemsExpr extends Expr with FileSpanMems

case class UnimplementedExpr(bMems: Seq[BlockMember]) extends MemsExpr// with FileSpanMems
{ def startMem = bMems.head
  def endMem = bMems.last
  override def exprName: String = "UnimplementedExpr"
}
case class AlphaBracketExpr(name: AlphaToken, blocks: Seq[BracketBlock]) extends MemsExpr// with FileSpanMems
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

//case class SpacedSeq(seq: Seq[Expr0]) extends Expr10
//{
//   override def startPosn: FilePosn = seq.head.startPosn
//   override def endPosn: FilePosn = seq.last.endPosn   
//}


