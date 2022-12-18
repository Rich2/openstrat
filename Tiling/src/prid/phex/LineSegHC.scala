/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class LineSegHC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLikeInt4[HCoord] with Int4Elem
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[HCoord]] point. */
  def startPt: HCoord = HCoord(r1, c1)

  /** The end [[HCoord]] point. */
  def endPt: HCoord = HCoord(r2, c2)

  /** Uses the implicit [[HGridSysFlat]] parameter to convert from [[HCen]]s to [[Pt2]]s. */
  def lineSeg(implicit grider: HGridSys): LineSeg = LineSeg(startPt.toPt2, endPt.toPt2)

  def transLineSeg(scale: Double, rOrigin: Int, cOrigin: Int): LineSeg = transLineSeg(scale, HCoord(rOrigin, cOrigin))

  /** Incorrect. */
  def transLineSeg(scale: Double, origin: HCoord = HCen(0, 0)): LineSeg =
  { val offset = -origin.toPt2Reg.toVec
    map(_.toPt2Reg.slate(offset).scale(scale))
  }
}

/** companion object for [[LineSegHC]] class contains factory apply method. */
object LineSegHC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegHC]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): LineSegHC = new LineSegHC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val arrMapBuilderEv: LineSegHCMapBuilder = new LineSegHCMapBuilder

  implicit def pairArrMapBuider[B2](implicit ct: ClassTag[B2]): LineSegHCPairArrMapBuilder[B2] = new LineSegHCPairArrMapBuilder[B2]
}

/** Compact immutable Array[Int] based collection class for [[LineSegHC]]s. LineSegHC is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegHCArr(val unsafeArray: Array[Int]) extends Int4Arr[LineSegHC]
{ type ThisT = LineSegHCArr
  def fromArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSegHC => String = _.toString
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}

/** Companion object for the LineSegHCs class. */
object LineSegHCArr extends Int4ArrCompanion[LineSegHC, LineSegHCArr]
{
  implicit def pairArrFlatBuilder[B2](implicit ct: ClassTag[B2]): LineSegHCPairArrFlatBuilder[B2] = new LineSegHCPairArrFlatBuilder[B2]

  /*implicit val persistImplicit: DataInt4sPersist[LineSegHC, LineSegHCs] = new DataDbl4sPersist[LineSegHC, LineSegHCs]("Line2s")
  { override def fromArray(value: Array[Int]): LineSegHCs = new LineSegHCs(value)

    override def showDecT(obj: LineSegHCs, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }*/


  //implicit val transImplicit: AffineTrans[LineSegHCs] = (obj, f) => obj.dataMap(_.ptsTrans(f))

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  override def fromArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)

  override def buff(initialSize: Int): Int4Buff[LineSegHC] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegHCBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int4Buff[LineSegHC]
{ override def typeStr: String = "Line2sBuff"
  override def intsToElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}

object LineSegHCBuff
{ def apply(initLen: Int = 4): LineSegHCBuff = new LineSegHCBuff(new ArrayBuffer[Int](initLen * 4))
}

class LineSegHCMapBuilder extends Int4ArrMapBuilder[LineSegHC, LineSegHCArr]
{ type BuffT = LineSegHCBuff
  override def fromIntArray(array: Array[Int]): LineSegHCArr = new LineSegHCArr(array)
  def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(buffer)
}