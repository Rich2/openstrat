/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance

trait SqlignLen2[+VT <: PtLen2]() extends SquareLen2[VT], RectLen2[VT]
{ type ThisT <: SqlignLen2[VT]  @uncheckedVariance
  type SideT <: LineSegLen2[VT]  @uncheckedVariance

  override def typeStr: String = ???

  def width: Length
  def cenX: Length
  def cenY: Length
  
  def rb: PtLen2
  override def slate(operand: VecPtLen2): SqlignLen2[VT]// = SqlignLen2(width, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2[VT]// = SqlignLen2(width, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Length): SqlignLen2[VT]// = SqlignLen2(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): SqlignLen2[VT]// = SqlignLen2(width, cenX, cenY + yOperand)
  override def scale(operand: Double): SqlignLen2[VT]// = SqlignLen2(width * operand, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Sqlign// = Sqlign(width / operand, cenX / operand, cenY / operand)

  override def fElemStr: VT  @uncheckedVariance => String// = ???
  override def elemsStr: String// = ???
  def revY: SqlignLen2[VT]// = ???
  def revYIf(cond: Boolean): SqlignLen2[VT]// = ???
  def rotate180: SqlignLen2[VT]// = ???
  def rotate180If(cond: Boolean): SqlignLen2[VT]// = ???
  def rotate180IfNot(cond: Boolean): SqlignLen2[VT]// = ???
  override def arrayUnsafe: Array[Double]// = ???
//  override def side(index: Int): LineSegLen2[VT]// = ???
 // override def sides: Arr[LineSegLen2[VT]]// = ???
  //override def sidesForeach[U](f: LineSegLen2[VT] => U): Unit// = ???
  override def verts: Arr[VT]// = ???
//  override def fromArray(array: Array[Double]): SqlignLen2[VT]// = ???
  override def ssElem(d1: Double, d2: Double): VT// = ???
  def fill(fillFacet: FillFacet): SqlignLen2Fill = SqlignLen2Fill(this, fillFacet)
}

object SqlignLen2
{ /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There ia an apply name overload that takes the X
   * and Y centre [[Length]] values as parameters. */
//  def apply[VT <: PtLen2](width: Length, cen: PtLen2 = PtM2.origin): SqlignLen2[VT] =  new SqlignLen2(width, cen.x, cen.y)

  /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There is an apply name overload that takes a
   * [[PtLen2]] as a parameter, with the origin as the default value. */
 // def apply[VT <: PtLen2](width: Length, cenX: Length, cenY: Length): SqlignLen2[VT] = new SqlignLen2(width, cenX, cenY)

 // def br[VT <: PtLen2](width: Length, bottomRight: PtLen2): SqlignLen2[VT] =  new SqlignLen2(width, bottomRight.x - width / 2, bottomRight.y + width / 2)

 // def rb[VT <: PtLen2](width: Length, right: Length, bottom: Length): SqlignLen2[VT] = new SqlignLen2(width, right - width / 2, bottom + width / 2)
}

class SqlignM2(val widthMetresNum: Double, val xCenMetresNum: Double, val yCenMetresNum: Double) extends SqlignLen2[PtM2]
{ type ThisT = SqlignM2
  override type SideT = LineSegM2

  override def typeStr: String = ???

  override def width: Metres = Metres(widthMetresNum)
  override def cenX: Metres = Metres(xCenMetresNum)
  override def cenY: Metres = Metres(yCenMetresNum)

  def rb: PtLen2 = PtM2(cenX + width / 2, cenY - width / 2)
  override def slate(operand: VecPtLen2): SqlignM2 = SqlignM2(widthMetresNum, xCenMetresNum + operand.xMetresNum, yCenMetresNum + operand.yMetresNum)

  override def slate(xOperand: Length, yOperand: Length): SqlignM2 =
    SqlignM2(widthMetresNum, xCenMetresNum + xOperand.metresNum, yCenMetresNum + yOperand.metresNum)

  override def slateX(xOperand: Length): SqlignM2 = ??? // SqlignM2(width, cenX + xOperand, cenY)
  override def slateY(yOperand: Length): SqlignM2 = ??? // SqlignM2(width, cenX, cenY + yOperand)
  override def scale(operand: Double): SqlignM2 = ??? // SqlignM2(width * operand, cenX * operand, cenY * operand)
  override def mapGeom2(operand: Length): Sqlign = Sqlign(width / operand, cenX / operand, cenY / operand)

  override def fElemStr: PtM2 => String = ???
  override def elemsStr: String = ???
  def revY: SqlignM2 = ???
  def revYIf(cond: Boolean): SqlignM2 = ???
  def rotate180: SqlignM2 = ???
  def rotate180If(cond: Boolean): SqlignM2 = ???
  def rotate180IfNot(cond: Boolean): SqlignM2 = ???
  override def arrayUnsafe: Array[Double] = ???
  override def side(index: Int): LineSegM2 = ???
  override def sides: Arr[LineSegM2] = ???
  //override def sidesForeach[U](f: LineSegLen2[PtM2] => U): Unit = ???
  override def sidesForeach[U](f: LineSegM2 => U): Unit = ???
  override def verts: Arr[PtM2] = ???
  override def fromArray(array: Array[Double]): SqlignM2 = ???
  override def ssElem(d1: Double, d2: Double): PtM2 = ???
  //def fill(fillFacet: FillFacet): SqlignLen2Fill = SqlignLen2Fill(this, fillFacet)
}

object SqlignM2
{ /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There ia an apply name overload that takes the X
 * and Y centre [[Length]] values as parameters. */
  def apply(width: Length, cen: PtLen2 = PtM2.origin): SqlignM2 =  new SqlignM2(width.metresNum, cen.xMetresNum, cen.yMetresNum)

  /** Factory apply method to create a square defined in [[Length]] units that is aligned to the X and Y axes. There is an apply name overload that takes a
   * [[PtLen2]] as a parameter, with the origin as the default value. */
  def apply(width: Double, cenX: Double, cenY: Double): SqlignM2 = new SqlignM2(width, cenX, cenY)

  def rb(width: Length, bottomRight: PtLen2): SqlignM2 =
    new SqlignM2(width.metresNum, bottomRight.xMetresNum - width.metresNum / 2, bottomRight.yMetresNum + width.metresNum / 2)

  def rb(width: Double, right: Double, bottom: Double): SqlignM2 = new SqlignM2(width, right - width / 2, bottom + width / 2)
}