/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

class SqlignLen2[VT <: PtLen2](val width: Length, val cenX: Length, val cenY: Length) extends SquareLen2[VT], RectLen2[VT]
{ type ThisT = SqlignLen2[VT]
  override type SideT = LineSegLen2[VT]

  override def typeStr: String = ???
  override def slate(operand: VecPtLen2): SqlignLen2[VT] = SqlignLen2(width, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2[VT] = SqlignLen2(width, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Length): SqlignLen2[VT] = SqlignLen2(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): SqlignLen2[VT] = SqlignLen2(width, cenX, cenY + yOperand)
  override def scale(operand: Double): SqlignLen2[VT] = SqlignLen2(width * operand, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Sqlign = Sqlign(width / operand, cenX / operand, cenY / operand)

  override def fElemStr: VT => String = ???
  override def elemsStr: String = ???
  def revY: SqlignLen2[VT] = ???
  def revYIf(cond: Boolean): SqlignLen2[VT] = ???
  def rotate180: SqlignLen2[VT] = ???
  def rotate180If(cond: Boolean): SqlignLen2[VT] = ???
  def rotate180IfNot(cond: Boolean): SqlignLen2[VT] = ???
  override def arrayUnsafe: Array[Double] = ???
  override def side(index: Int): LineSegLen2[VT] = ???
  override def sides: Arr[LineSegLen2[VT]] = ???
  override def sidesForeach[U](f: LineSegLen2[VT] => U): Unit = ???
  override def verts: Arr[VT] = ???
  override def fromArray(array: Array[Double]): SqlignLen2[VT] = ???
  override def ssElem(d1: Double, d2: Double): VT = ???
}

object SqlignLen2
{ /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There ia an apply name overload that takes the X
   * and Y centre [[Length]] values as parameters. */
  def apply[VT <: PtLen2](width: Length, cen: PtLen2 = PtM2.origin): SqlignLen2[VT] =  new SqlignLen2(width, cen.x, cen.y)

  /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There is an apply name overload that takes a
   * [[PtLen2]] as a parameter, with the origin as the default value. */
  def apply[VT <: PtLen2](width: Length, cenX: Length, cenY: Length): SqlignLen2[VT] = new SqlignLen2(width, cenX, cenY)
}

trait SqlignLen2Graphic extends SquareLen2Graphic, RectLen2Graphic
{
  override def shape: SqlignLen2[PtLen2]
}

case class SqlignLen2Fill(shape: SqlignLen2[PtLen2], fillFacet: FillFacet) extends SqlignLen2Graphic, SquareLen2Fill, RectLen2Fill
{ override def slate(operand: VecPtLen2): SqlignLen2Fill = SqlignLen2Fill(shape.slate(operand), fillFacet)
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slate(xOperand, yOperand), fillFacet)
  override def slateX(xOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slateX(xOperand), fillFacet)
  override def slateY(yOperand: Length): SqlignLen2Fill = SqlignLen2Fill(shape.slateY(yOperand), fillFacet)
  override def scale(operand: Double): SqlignLen2Fill = SqlignLen2Fill(shape.scale(operand), fillFacet)
  override def mapGeom2(operand: Length): SqlignFill = SqlignFill(shape.mapGeom2(operand), fillFacet)
}