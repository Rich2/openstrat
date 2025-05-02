/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Square defined in [[Length]] units, aligned to the X and Y axes. */
trait SqlignLen2[+VT <: PtLen2]() extends SquareLen2[VT], RectLen2[VT]
{ type ThisT <: SqlignLen2[VT]
  type SideT <: LineSegLen2[VT]
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
  /** Implicit [[SlateLen2]] type class instance evidence for [[SqlignLen2]]. */
  given slateLen2Ev: SlateLen2[SqlignLen2[PtLen2]] = new SlateLen2[SqlignLen2[PtLen2]]
  { override def slateT(obj: SqlignLen2[PtLen2], delta: VecPtLen2): SqlignLen2[PtLen2] = obj.slate(delta)
    override def slateXY(obj: SqlignLen2[PtLen2], xDelta: Length, yDelta: Length): SqlignLen2[PtLen2] = obj.slate(xDelta, yDelta)
    override def slateX(obj: SqlignLen2[PtLen2], xDelta: Length): SqlignLen2[PtLen2] = obj.slateX(xDelta)
    override def slateY(obj: SqlignLen2[PtLen2], yDelta: Length): SqlignLen2[PtLen2] = obj.slateY(yDelta)
  }
}

/** Square specified in metres aligned to the X and Y axes. */
class SqlignM2(val widthMNum: Double, val xCenMNum: Double, val yCenMNum: Double) extends SqlignLen2[PtM2], RectM2
{ type ThisT = SqlignM2
  override def typeStr: String = "SqlignM2"
  inline def heightMNum: Double = widthMNum
  def hWidthMNum: Double = widthMNum / 2

  override def diags: LineSegM2Arr = LineSegM2Arr(lbrtDiag, ltrbDiag)
  override def slate(operand: VecPtLen2): SqlignM2 = SqlignM2(widthMNum, xCenMNum + operand.xMetresNum, yCenMNum + operand.yMetresNum)
  override def slate(xOperand: Length, yOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum + xOperand.metresNum, yCenMNum + yOperand.metresNum)
  override def slateX(xOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum + xOperand.metresNum, yCenMNum)
  override def slateY(yOperand: Length): SqlignM2 = SqlignM2(widthMNum, xCenMNum, yCenMNum + yOperand.metresNum)
  override def scale(operand: Double): SqlignM2 = SqlignM2(widthMNum * operand, xCenMNum * operand, yCenMNum * operand)
  override def mapGeom2(operand: Length): Sqlign = Sqlign(width / operand, cenX / operand, cenY / operand)

  override def fElemStr: PtM2 => String = ???
  override def elemsStr: String = ???
  def revY: SqlignM2 = ???
  def revYIf(cond: Boolean): SqlignM2 = ???
  def rotate180: SqlignM2 = ???
  def rotate180If(cond: Boolean): SqlignM2 = ???
  def rotate180IfNot(cond: Boolean): SqlignM2 = ???
  override def arrayUnsafe: Array[Double] = ???
  override def side(index: Int): LSegM2 = ???

  override def sidesForeach[U](f: LSegM2 => U): Unit = ???
  override def verts: Arr[PtM2] = ???
  override def fromArray(array: Array[Double]): SqlignM2 = ???
  override def elemFromDbls(d1: Double, d2: Double): PtM2 = ???
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