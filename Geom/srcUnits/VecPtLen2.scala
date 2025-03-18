/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common base trait for [[VecLen2]] and [[PtLen2]]. */
trait VecPtLen2 extends GeomLen2Elem, TellElemDbl2
{ override def name1: String = "x"
  override def name2: String = "y"

  /** A vector can be added to a point or a vector, returning an object of the same type. */
  def +(operand: VecLen2): VecPtLen2

  /** A vector can be sbutracted from a point or a vector, returning an object of the same type. */
  def -(operand: VecLen2): VecPtLen2

  /** The number of [[Femtometres]] in the X component of this point / vector */
  def xFemtometresNum: Double

  /** The number of [[Femtometres]] in the Y component of this point / vector */
  def yFemtometresNum: Double

  def xPicometresNum: Double
  def yPicometresNum: Double
  def xMetresNum: Double
  def yMetresNum: Double
  def xKilometresNum: Double
  def yKilometresNum: Double
  def xPos: Boolean
  def xNeg: Boolean
  def yPos: Boolean
  def yNeg: Boolean
}


/** A 2-dimensional point specified in units of [[Length]] rather than pure scalar numbers. */
trait PtLen2 extends VecPtLen2, PointDbl2
{ override def slate(operand: VecPtLen2): PtLen2
  override def slate (deltaX: Length, deltaY: Length): PtLen2
  override def slateX(operand: Length): PtLen2
  override def slateY(operand: Length): PtLen2
  def slateFrom(operand: PtLen2): PtLen2
  override def scale(operand: Double): PtLen2
  override def mapScalars(operator: Length): Pt2
  override def + (operand: VecLen2): PtLen2
  override def - (operand: VecLen2): PtLen2
  
  def magnitude: Length
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
{ /** Implicit [[SlateLen]] type class instance / evidence for [[PtLen2]]. */
  implicit val slateEv: SlateLen[PtLen2] = (obj, delta) => obj.slate(delta)

  /** Implicit [[SlateLenXY]] type class instance / evidence for [[PtLen2]]. */
  implicit val slateXYEv: SlateLenXY[PtLen2] = (obj, xDelta, yDelta) => obj.slate(xDelta, yDelta)

  /** Implicit [[Scale]] type class instance / evidence for [[PtLen2]]. */
  implicit val scaleEv: Scale[PtLen2] = (obj, operand) => obj.scale(operand)
}

/** A 2-dimensional vector specified in [[Length]] units. */
trait VecLen2 extends VecPtLen2
{ override def + (op: VecLen2): VecLen2
  override def - (operand: VecLen2): VecLen2
  def * (operator: Double): VecLen2
  def / (operator: Double): VecLen2
  def magnitude: Length

  /** Produces the dot product of this 2-dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLen2): Area

  override def slate(operand: VecPtLen2): VecLen2
  override def slate(xDelta: Length, yDelta: Length): VecLen2
  override def slateX(operand: Length): VecLen2
  override def slateY(operand: Length): VecLen2
  override def scale(operand: Double): VecLen2
  override def mapScalars(operator: Length): Vec2
}

/** Companion object for 2-dimensional vector specified in [[Length]] units. Contains various [[GeomLen2Elem]] type classes for [[VecLen2]] */
object VecLen2
{ /** Implicit [[SlateLen]] type class instance / evidence for [[VecLen2]]. */
  implicit val slateEv: SlateLen[VecLen2] = (obj, delta) => obj.slate(delta)

  /** Implicit [[SlateLenXY]] type class instance / evidence for [[VecLen2]]. */
  implicit val slateXYEv: SlateLenXY[VecLen2] = (obj, xDelta, yDelta) => obj.slate(xDelta, yDelta)

  /** Implicit [[Scale]] type class instance / evidence for [[VecLen2]]. */
  implicit val scaleEv: Scale[VecLen2] = (obj, operand) => obj.scale(operand)
}