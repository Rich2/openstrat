/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common base trait for [[VecMetric2]] and [[PtMetric2]] specified in [[LengthMetric]] units. */
trait VecPtMetric2 extends VecPtLen2, GeomMetric2Elem
{ override def x: LengthMetric
  override def y: LengthMetric
}

/** A 2-dimensional point specified in units of [[LengthMetric]] rather than pure scalar numbers. */
trait PtMetric2 extends PtLen2, VecPtMetric2
{ override def slate(operand: VecPtLen2): PtMetric2
  override def slate(deltaX: Length, deltaY: Length): PtMetric2
  override def slateX(xOperand: Length): PtMetric2
  override def slateY(yOperand: Length): PtMetric2
  override def slateFrom(operand: PtLen2): PtMetric2
  override def scale(operand: Double): PtMetric2
  override def mapGeom2(operator: Length): Pt2
  override def -(operand: VecLen2): PtMetric2
  override def -(operand: PtLen2): VecMetric2  
  override def negY: PtMetric2
  override def negYIf(cond: Boolean): PtMetric2
  override def lineSegTo(endPt: PtLen2): LSegLen2[? <: PtMetric2]
  override def lineSegFrom(startPt: PtLen2): LSegLen2[? <: PtMetric2]
  override def rotate180: PtMetric2
  override def rotate180If(cond: Boolean): PtMetric2  
  override def rotate180IfNot(cond: Boolean): PtMetric2
  override def rotate(a: AngleVec): PtMetric2
  override def rotateRadians(r: Double): PtMetric2
  override def ptAtAngle(angle: Angle, delta: Length): PtMetric2
  override def ptAtDegs(numDegs: Double, delta: Length): PtMetric2
}

object PtMetric2
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[PtMetric2]]. */
  implicit val slateEv: SlateLen2[PtMetric2] = new SlateLen2[PtMetric2]
  { override def slateT(obj: PtMetric2, delta: VecPtLen2): PtMetric2 = obj.slate(delta)
    override def slateXY(obj: PtMetric2, xDelta: Length, yDelta: Length): PtMetric2 = obj.slate(xDelta, yDelta)
    override def slateX(obj: PtMetric2, xDelta: Length): PtMetric2 = obj.slateX(xDelta)
    override def slateY(obj: PtMetric2, yDelta: Length): PtMetric2 = obj.slateY(yDelta)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[PtMetric2]]. */
  given scaleEv: Scale[PtMetric2] = (obj, operand) => obj.scale(operand)
}

/** A 2-dimensional vector specified in [[Length]] units. */
trait VecMetric2 extends VecLen2, VecPtMetric2
{ def + (op: VecLen2): VecMetric2
  def - (operand: VecLen2): VecMetric2
  def unary_- : VecMetric2
  def * (operator: Double): VecMetric2
  def / (operator: Double): VecMetric2
  def magnitude: LengthMetric

  /** Produces the dot product of this 2-dimensional distance Vector and the operand. */
  @inline def dot(operand: VecLen2): AreaMetric

  override def slate(operand: VecPtLen2): VecMetric2
  override def slate(xOperand: Length, yOperand: Length): VecMetric2
  override def slateX(xOperand: Length): VecMetric2
  override def slateY(yOperand: Length): VecMetric2
  override def scale(operand: Double): VecMetric2
  override def mapGeom2(operator: Length): Vec2
}

/** Companion object for 2-dimensional vector specified in [[Length]] units. Contains various [[GeomMetric2Elem]] type classes for [[VecMetric2]] */
object VecMetric2
{ /** Implicit [[SlateLen2]] type class instance / evidence for [[VecMetric2]]. */
  implicit val slateEv: SlateLen2[VecMetric2] = new SlateLen2[VecMetric2]
  { override def slateT(obj: VecMetric2, delta: VecPtLen2): VecMetric2 = obj.slate(delta)
    override def slateXY(obj: VecMetric2, xDelta: Length, yDelta: Length): VecMetric2 = obj.slate(xDelta, yDelta)
    override def slateX(obj: VecMetric2, xDelta: Length): VecMetric2 = obj.slateX(xDelta)
    override def slateY(obj: VecMetric2, yDelta: Length): VecMetric2 = obj.slateY(yDelta)
  }
  /** Implicit [[Scale]] type class instance / evidence for [[VecMetric2]]. */
  given scaleEv: Scale[VecMetric2] = (obj, operand) => obj.scale(operand)
}