/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LineSegLen2[+VT <: PtLen2] extends LineSegLike[VT], DrawableLen2
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT
  override def slate(operand: VecPtLen2): LineSegLen2[VT]  
  override def slate(xOperand: Length, yOperand: Length): LineSegLen2[VT]

  override def slateX(xOperand: Length): LineSegLen2[VT]
  override def slateY(xOperand: Length): LineSegLen2[VT]
  def scale(operand: Double): LineSegLen2[VT]  
  def mapGeom2 (operand: Length): LineSeg

  override def draw(lineWidth: Double, lineColour: Colour): LineSegLen2Draw = LineSegLen2Draw(this, lineWidth, lineColour)

  def xStartFemtometresNum: Double
  def yStartFemtometresNum: Double
  def xEndFemtometresNum: Double
  def yEndFemtometresNum: Double
  def xStartPicometresNum: Double
  def yStartPicometresNum: Double
  def xEndPicometresNum: Double
  def yEndPicometresNum: Double
  def xStartMetresNum: Double
  def yStartMetresNum: Double
  def xEndMetresNum: Double
  def yEndMetresNum: Double
  def xStartKilometresNum: Double
  def yStartKilometresNum: Double
  def xEndKilometresNum: Double
  def yEndKilometresNum: Double
}

object LineSegLen2
{ /** [[Slate]] type class instances / evidence for [[PtLen2]]. */
  implicit val slateEv: SlateLen2[LineSegLen2[PtLen2]] = (obj, delta) => obj.slate(delta)

  /** [[SlateXY]] type class instances / evidence for [[PtLen2]]. */
  implicit val slateXYEv: SlateLenXY[LineSegLen2[PtLen2]] = (obj, dx, dy) => obj.slate(dx, dy)
  
  /** [[Scale]] type class instances / evidence for [[PtLen2]]. */
  implicit val scaleEv: Scale[LineSegLen2[PtLen2]] = (obj, operand) => obj.scale(operand)

  implicit val drawerEv: Drawer[LineSegLen2[PtLen2], LineSegLen2Draw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)
}

trait LineSegLen2Arr[+VT <: PtLen2] extends Arr[LineSegLen2[VT]]

object LineSegLen2Arr
{
  implicit def drawerEv[VT <: PtLen2]: Drawer[LineSegLen2Arr[VT], RArr[LineSegLen2Draw]] = (obj, lineWidth, colour) => obj.map(_.draw(lineWidth, colour))
}