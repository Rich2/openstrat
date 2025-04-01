/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common base trait for [[VecLen2]] and [[PtLen2]]. */
trait VecPtLen2 extends GeomLen2Elem, TellElemDbl2
{
  def x: Length
  def y: Length

  override def name1: String = "x"
  override def name2: String = "y"

  /** The number of femtometres in the X component of this point / vector. */
  def xFemtometresNum: Double

  /** The number of femtometres in the Y component of this point / vector. */
  def yFemtometresNum: Double

  /** The number of picometres in the X component of this point / vector. */
  def xPicometresNum: Double

  /** The number of picometres in the Y component of this point / vector. */
  def yPicometresNum: Double

  /** The number of metres in the X component of this point / vector. */
  def xMetresNum: Double

  /** The number of metres in the Y component of this point / vector. */
  def yMetresNum: Double

  /** The number of kilometres in the X component of this point / vector. */
  def xKilometresNum: Double

  /** The number of kilometres in the Y component of this point / vector. */
  def yKilometresNum: Double
  def xPos: Boolean
  def xNeg: Boolean
  def yPos: Boolean
  def yNeg: Boolean
}

/** A 2-dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLen2 extends VecPtLen2, PointDbl2
{ override def slate(operand: VecPtLen2): PtLen2
  override def slate(deltaX: Length, deltaY: Length): PtLen2
  override def slateX(xOperand: Length): PtLen2
  override def slateY(yOperand: Length): PtLen2
  def slateFrom(operand: PtLen2): PtLen2
  override def scale(operand: Double): PtLen2
  override def mapGeom2(operator: Length): Pt2

  /** Subtracting a vector from a point returns a point. Subtracting a vector from a vector returns a vector. Subtracting a point from a point returns a
   * vector. */
  def -(operand: VecLen2): PtLen2

  /** Subtracting a point from a point returns a vector. Subtracting a vector from a point returns a point. */
  def -(operand: PtLen2): VecLen2
  
  def revY: PtLen2
  def revYIf(cond: Boolean): PtLen2
  def lineSegTo(endPt: PtLen2): LineSegLen2[? <: PtLen2]
  def lineSegFrom(startPt: PtLen2): LineSegLen2[? <: PtLen2]

  /** Rotates the point 180 degrees around the origin by negating the X and Y components. */
  def rotate180: PtLen2

  /** Rotates th point 180 degrees around the origin if the condition is true. */
  def rotate180If(cond: Boolean): PtLen2

  /** Rotates th point 180 degrees around the origin if the condition is false. */
  def rotate180IfNot(cond: Boolean): PtLen2

  def rotate(a: AngleVec): PtLen2

  def rotateRadians(r: Double): PtLen2
}

object PtLen2
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[PtLen2]]. */
  implicit val slateEv: SlateLen2[PtLen2] = (obj, delta) => obj.slate(delta)

  /** Implicit [[SlateLenXY]] type class instance / evidence for [[PtLen2]]. */
  implicit val slateXYEv: SlateLenXY[PtLen2] = (obj, xDelta, yDelta) => obj.slate(xDelta, yDelta)

  /** Implicit [[Scale]] type class instance / evidence for [[PtLen2]]. */
  implicit val scaleEv: Scale[PtLen2] = (obj, operand) => obj.scale(operand)

  /** Implicit [[MapGeom2]] type class instance / evidence for [[PtLen2]] and [[Pt2]]. */
  implicit val mapGeom2Ev: MapGeom2[PtLen2, Pt2] = (obj, operand) => obj.mapGeom2(operand)
}

/** A 2-dimensional vector specified in [[Length]] units. */
trait VecLen2 extends VecPtLen2
{ def + (op: VecLen2): VecLen2
  def - (operand: VecLen2): VecLen2
  def unary_- : VecLen2
  def * (operator: Double): VecLen2
  def / (operator: Double): VecLen2
  def magnitude: Length

  /** Produces the dot product of this 2-dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLen2): Area

  override def slate(operand: VecPtLen2): VecLen2
  override def slate(xOperand: Length, yOperand: Length): VecLen2
  override def slateX(xOperand: Length): VecLen2
  override def slateY(yOperand: Length): VecLen2
  override def scale(operand: Double): VecLen2
  override def mapGeom2(operator: Length): Vec2
}

/** Companion object for 2-dimensional vector specified in [[Length]] units. Contains various [[GeomLen2Elem]] type classes for [[VecLen2]] */
object VecLen2
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[VecLen2]]. */
  implicit val slateEv: SlateLen2[VecLen2] = (obj, delta) => obj.slate(delta)

  /** Implicit [[SlateLenXY]] type class instance / evidence for [[VecLen2]]. */
  implicit val slateXYEv: SlateLenXY[VecLen2] = (obj, xDelta, yDelta) => obj.slate(xDelta, yDelta)

  /** Implicit [[Scale]] type class instance / evidence for [[VecLen2]]. */
  implicit val scaleEv: Scale[VecLen2] = (obj, operand) => obj.scale(operand)

  /** Implicit [[MapGeom2]] type class instance / evidence for [[VecLen2]] and [[Vec2]]. */
  implicit val mapGeom2Ev: MapGeom2[VecLen2, Vec2] = (obj, operand) => obj.mapGeom2(operand)
}