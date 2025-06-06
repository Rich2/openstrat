/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class LSegHC(r1: Int, c1: Int, r2: Int, c2: Int) extends LSegInt4[HCoord] with Int4Elem
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[HCoord]] point. */
  def startPt: HCoord = HCoord(r1, c1)

  /** The end [[HCoord]] point. */
  def endPt: HCoord = HCoord(r2, c2)

  /** Uses the implicit [[HGridSysProjecton]] parameter to convert from [[HCoord]]s to [[Pt2]]s. */
  def projLineSeg(implicit proj: HSysProjection): LSeg2 = LSeg2(startPt.projPt2, endPt.projPt2)

  /** Transforms from  [[HCoord]]s to [[Pt2]]s. */
  def lineSeg: LSeg2 = LSeg2(startPt.toPt2Reg, endPt.toPt2Reg)

  def transLineSeg(scale: Double, rOrigin: Int, cOrigin: Int): LSeg2 = transLineSeg(scale, HCoord(rOrigin, cOrigin))

  /** Incorrect. */
  def transLineSeg(scale: Double, origin: HCoord = HCen(0, 0)): LSeg2 =
  { val offset = -origin.toPt2Reg.toVec
    map(_.toPt2Reg.slate(offset).scale(scale))
  }
}

/** companion object for [[LSegHC]] class contains factory apply method. */
object LSegHC
{ /** Factory apply method to create a hex coordinate line segment a [[LSegHC]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): LSegHC = new LSegHC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[BuilderArrMap]] type class. */
  implicit val arrMapBuilderEv: LineSegHCMapBuilder = new LineSegHCMapBuilder

  implicit def pairArrMapBuider[B2](implicit ct: ClassTag[B2]): LineSegHCPairArrMapBuilder[B2] = new LineSegHCPairArrMapBuilder[B2]
}

/** Compact immutable Array[Int] based collection class for [[LSegHC]]s. LineSegHC is the library's term for a mathematical straight line segment, but what
 * in common parlance is often just referred to as a line. */
class LineSegHCArr(val arrayUnsafe: Array[Int]) extends ArrInt4[LSegHC]
{ type ThisT = LineSegHCArr
  def fromArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LSegHC => String = _.toString  
  override def elemFromInts(d1: Int, d2: Int, d3: Int, d4: Int): LSegHC = new LSegHC(d1, d2, d3, d4)
}

/** Companion object for the LineSegHCs class. */
object LineSegHCArr extends CompanionArrInt4[LSegHC, LineSegHCArr]
{
  implicit def pairArrFlatBuilder[B2](implicit ct: ClassTag[B2]): LineSegHCPairArrFlatBuilder[B2] = new LineSegHCPairArrFlatBuilder[B2]

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  override def fromArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)

  override def buff(initialSize: Int): BuffInt4[LSegHC] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegHCBuff(val bufferUnsafe: ArrayBuffer[Int]) extends AnyVal, BuffInt4[LSegHC]
{ override def typeStr: String = "LineSegBuff"
  override def elemFromInts(d1: Int, d2: Int, d3: Int, d4: Int): LSegHC = new LSegHC(d1, d2, d3, d4)
}

object LineSegHCBuff
{ def apply(initLen: Int = 4): LineSegHCBuff = new LineSegHCBuff(new ArrayBuffer[Int](initLen * 4))
}

class LineSegHCMapBuilder extends BuilderMapArrInt4[LSegHC, LineSegHCArr]
{ type BuffT = LineSegHCBuff
  override def fromIntArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
  def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(buffer)
}