/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, math._, reflect.ClassTag

/** A 2 dimensional point specified in [[Kilometres]] as units rather than pure scalar numbers. */
final class PtKM2(val xKiloMetresNum: Double, val yKiloMetresNum: Double) extends PtLength2 with PointDbl2 with TellElemDbl2
{ override type ThisT = PtKM2
  override type LineSegT = LineSegKM2

  override def xMetresNum: Double = xKiloMetresNum * 1000
  override def yMetresNum: Double = yKiloMetresNum * 1000

  override def slate(operand: PtLength2): PtLength2 = ???
  override def slateFrom(operand: PtLength2): PtLength2 = ???

  override def +(operand: VecLength2): PtLength2 = ???
  override def -(operand: VecLength2): PtLength2 = ???

  override def addXY(otherX: Length, otherY: Length): PtLength2 = ???
  override def subXY(otherX: Length, otherY: Length): PtLength2 = ???

  override def addX(operand: Length): PtLength2 = ???

  /** Element 1 of this Tell2+ element product. */
  override def tell1: Double = ???

  /** Element 2 of this Tell2+ element product. */
  override def tell2: Double = ???

  /** 2nd parameter name. */
  override def name2: String = ???

  /** 1st parameter name. */
  override def name1: String = ???

  /** The type of the object to be persisted. */
  override def typeStr: String = ???

  /** [[LineSegLike]] from this point to the parameter point. */
  override def lineSegTo(endPt: PtKM2): LineSegKM2 = ???

  /** [[LinSegLike]] from the parameter point to this point. */
  override def lineSegFrom(startPt: PtKM2): LineSegKM2 = ???
}