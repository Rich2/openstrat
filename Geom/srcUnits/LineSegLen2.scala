/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A line segment whose coordinates are specified in [[Length]] units. */
trait LineSegLen2[VT <: PtLen2] extends LineSegLike[VT]
{ def xStart: Length
  def yStart: Length
  def xEnd: Length
  def yEnd: Length
  def startPt: VT
  def endPt: VT

  /** Translates this line segment in 2 [[Length]] dimensions space. */
  def slate(operand: VecPtLen2): LineSegLen2[VT]

  /** Translates this line segment in 2 [[Length]] dimensions space. */
  def slate(xOperand: Length, yOperand: Length): LineSegLen2[VT]

  /** Scales this line segment in 2 [[Length]] dimensions space. */
  def scale(operand: Double): LineSegLen2[VT]

  /** Divides by a [[Length]] to produce a scalar [[LineSeg]]. */
  def / (operand: Length): LineSeg

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
  implicit def slateEv: SlateLen[LineSegLen2[PtLen2]] = (obj, delta) => obj.slate(delta)

  /** [[SlateXY]] type class instances / evidence for [[PtLen2]]. */
  implicit def slateXYEv: SlateLenXY[LineSegLen2[PtLen2]] = (obj, dx, dy) => obj.slate(dx, dy)
  
  /** [[Scale]] type class instances / evidence for [[PtLen2]]. */
  implicit def scaleEv: Scale[LineSegLen2[PtLen2]] = (obj, operand) => obj.scale(operand)
}