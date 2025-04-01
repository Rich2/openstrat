/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait SqlignLen2[+VT <: PtLen2]() extends SquareLen2[VT], RectLen2[VT]
{ type ThisT <: SqlignLen2[VT]
  type SideT <: LineSegLen2[VT]
  def width: Length
  def cenX: Length
  def cenY: Length

  override def slate(operand: VecPtLen2): SqlignLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): SqlignLen2[VT]
  override def slateX(xOperand: Length): SqlignLen2[VT]
  override def slateY(yOperand: Length): SqlignLen2[VT]
  override def scale(operand: Double): SqlignLen2[VT]
  override def mapGeom2(operand: Length): Sqlign

  def revY: SqlignLen2[VT]
  def revYIf(cond: Boolean): SqlignLen2[VT]
  def rotate180: SqlignLen2[VT]
  def rotate180If(cond: Boolean): SqlignLen2[VT]
  def rotate180IfNot(cond: Boolean): SqlignLen2[VT]
  override def arrayUnsafe: Array[Double]

  def fill(fillFacet: FillFacet): SqlignLen2Fill = SqlignLen2Fill(this, fillFacet)
}

object SqlignLen2
{
}

class SqlignM2(val widthMNum: Double, val xCenMNum: Double, val yCenMNum: Double) extends SqlignLen2[PtM2]
{ type ThisT = SqlignM2
  override type SideT = LineSegM2
  override def typeStr: String = "SqlignM2"
  override def width: Metres = Metres(widthMNum)
  override def cenX: Metres = Metres(xCenMNum)
  override def cenY: Metres = Metres(yCenMNum)

  def leftMNum = xCenMNum - widthMNum / 2
  def rightMetresNum = xCenMNum + widthMNum / 2
  def bottomMetresNum = yCenMNum - widthMNum / 2
  def topMetresNum = yCenMNum + widthMNum / 2
  override def rt: PtM2 = PtM2(xCenMNum + widthMNum / 2, yCenMNum + widthMNum / 2)
  override def rb: PtM2 = PtM2(xCenMNum + widthMNum / 2, yCenMNum - widthMNum / 2)
  override def lb: PtM2 = PtM2(xCenMNum - widthMNum / 2, yCenMNum - widthMNum / 2)
  override def lt: PtM2 = PtM2(xCenMNum - widthMNum / 2, yCenMNum + widthMNum / 2)

  override def lbrtDiag: LineSegM2 = LineSegM2(leftMNum, bottomMetresNum, rightMetresNum, topMetresNum)
  override def slate(operand: VecPtLen2): SqlignM2 = SqlignM2(widthMNum, xCenMNum + operand.xMetresNum, yCenMNum + operand.yMetresNum)
  override def slate(xOperand: Length, yOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum + xOperand.metresNum, yCenMNum + yOperand.metresNum)

  override def slateX(xOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum + xOperand.metresNum, yCenMNum)
  override def slateY(yOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum, yCenMNum + yOperand.metresNum)
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
  override def sidesForeach[U](f: LineSegM2 => U): Unit = ???
  override def verts: Arr[PtM2] = ???
  override def fromArray(array: Array[Double]): SqlignM2 = ???
  override def ssElem(d1: Double, d2: Double): PtM2 = ???
}

object SqlignM2
{ /** Factory apply method to create a square defined in metres that is aligned to the X and Y axes. There are 3 other apply name overloads. */
  def apply(width: Double, cenX: Double, cenY: Double): SqlignM2 = new SqlignM2(width, cenX, cenY)

  /** Factory apply method to create a square defined in metres that is aligned to the X and Y axes, with its centre at the origin. There are 3 other apply name
   * overloads. */
  def apply(width: Double): SqlignM2 = new SqlignM2(width, 0, 0)

  /** Factory apply method to create a square defined in metres is aligned to the X and Y axes. The default centre is the origin. There are 3 other apply name
   *  overloads. */
  def apply(width: Length, cen: PtLen2 = PtM2.origin): SqlignM2 =  new SqlignM2(width.metresNum, cen.xMetresNum, cen.yMetresNum)

  /** Factory apply method to create a square defined in metres that is aligned to the X and Y axes. There are 3 other apply name overloads. */
  def apply(width: Length, cenX: Length, cenY: Length): SqlignM2 = new SqlignM2(width.metresNum, cenX.metresNum, cenY.metresNum)

  /** Factory method to create a square defined in metres that is aligned to the X and Y axes from its right-bottom vertex. */
  def rb(width: Double, right: Double, bottom: Double): SqlignM2 = new SqlignM2(width, right - width / 2, bottom + width / 2)

  /** Factory method to create a square defined in metres, that is aligned to the X and y axes from its right-bottom vertex. The default value places the
   * right-bottom corner at the origin. */
  def rb(width: Length, bottomRight: PtLen2 = PtM2.origin): SqlignM2 =
    new SqlignM2(width.metresNum, bottomRight.xMetresNum - width.metresNum / 2, bottomRight.yMetresNum + width.metresNum / 2)
}