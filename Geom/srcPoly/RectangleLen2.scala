/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait RectangleLen2 extends ShapeLen2

trait SquareLen2 extends RectangleLen2

class SqlignLen2(val width: Length, val cenX: Length, val cenY: Length) extends SquareLen2
{ override def slate(operand: VecPtLen2): SqlignLen2 = ???
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2 = ???
  override def scale(operand: Double): SqlignLen2 = ???
  override def slateX(xOperand: Length): SqlignLen2 = ???
  override def slateY(yOperand: Length): SqlignLen2 = ???
  override def mapGeom2(operand: Length): Sqlign = ???
}

object SqlignLen2
{
  def apply(width: Length, cenX: Length, cenY: Length): SqlignLen2 = new SqlignLen2(width, cenX, cenY)


  def apply(width: Length, cen: PtLen2 = PtM2.origin): SqlignLen2 = new SqlignLen2(width, cen.x, cen.y)
}