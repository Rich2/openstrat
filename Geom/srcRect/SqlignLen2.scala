/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class SqlignLen2(val width: Length, val cenX: Length, val cenY: Length) extends SquareLen2
{ override def slate(operand: VecPtLen2): SqlignLen2 = SqlignLen2(width, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2 = SqlignLen2(width, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Length): SqlignLen2 = SqlignLen2(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): SqlignLen2 = SqlignLen2(width, cenX, cenY + yOperand)
  override def scale(operand: Double): SqlignLen2 = SqlignLen2(width * operand, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Sqlign = ???
}

object SqlignLen2
{ /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There ia an apply name overload that takes the X
   * and Y centre [[Length]] values as parameters. */
  def apply(width: Length, cen: PtLen2 = PtM2.origin): SqlignLen2 = new SqlignLen2(width, cen.x, cen.y)

  /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There is an apply name overload that takes a
   * [[PtLen2]] as a parameter, with the origin as the default value. */
  def apply(width: Length, cenX: Length, cenY: Length): SqlignLen2 = new SqlignLen2(width, cenX, cenY)
}

trait SqlignLen2Graphic extends PolygonLen2Graphic