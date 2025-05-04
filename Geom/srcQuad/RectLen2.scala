/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait RectLen2[+VT <: PtLen2] extends RectangleLen2[VT]
{ type ThisT <: RectLen2[VT]

  /** The width of this rectangle in [[Length]] units. */
  def width: Length

  /** The height of this rectangle in [[Length]] units. */
  def height: Length


  /** Right top point. */
  def rt: PtLen2
  
  /** Right bottom point. */
  def rb: PtLen2

  /** left bottom point. */
  def lb: PtLen2

  /** Left top point. */
  def lt: PtLen2

  /** leftBottom => topRight diagonal */
  def lbrtDiag: LSegLen2[VT]

  /** leftTop => bottomRight diagonal */
  def ltrbDiag: LSegLen2[VT]
  
  override def slate(operand: VecPtLen2): RectLen2[VT]
  override def slate(xOperand: Length, yOperand: Length): RectLen2[VT]
  override def slateX(xOperand: Length): RectLen2[VT]
  override def slateY(yOperand: Length): RectLen2[VT]
  override def scale(operand: Double): RectLen2[VT]
  override def mapGeom2(operand: Length): Rect
}

trait RectM2 extends RectLen2[PtM2], RectangleM2
{ type ThisT <: RectM2
  override type SideT = LSegM2
  def widthMNum: Double
  def heightMNum: Double
  def xCenMNum: Double
  def yCenMNum: Double

  /** The number of metres in the left X coordinate. */
  def leftMNum = xCenMNum - widthMNum / 2

  /** The number of metres in the right X coordinate. */
  def rightMNum = xCenMNum + widthMNum / 2

  /** The number of metres in the bottom Y coordinate. */
  def bottomMNum = yCenMNum - widthMNum / 2

  /** The number of metres in the top Y coordinate. */
  def topMNum = yCenMNum + widthMNum / 2
  
  override def width: Metres = Metres(widthMNum)
  override def height: Metres = Metres(heightMNum)
  override def cenX: Metres = Metres(xCenMNum)
  override def cenY: Metres = Metres(yCenMNum)
  override def rt: PtM2 = PtM2(rightMNum, topMNum)
  override def rb: PtM2 = PtM2(rightMNum, bottomMNum)
  override def lb: PtM2 = PtM2(leftMNum, bottomMNum)
  override def lt: PtM2 = PtM2(leftMNum, topMNum)
  override def lbrtDiag: LSegM2 = LSegM2(leftMNum, bottomMNum, rightMNum, topMNum)
  override def ltrbDiag: LSegM2 = LSegM2(leftMNum, topMNum, rightMNum, bottomMNum)

  override def sidesDiags: LineSegLen2Arr[PtM2] = sides ++ diags

  override def sides: LineSegM2Arr =
  { val newArray = Array[Double](rightMNum, topMNum, rightMNum, bottomMNum, rightMNum, bottomMNum, leftMNum, bottomMNum,
      leftMNum, bottomMNum, leftMNum, topMNum, leftMNum, topMNum, rightMNum, topMNum)
    new LineSegM2Arr(newArray)
  }
}