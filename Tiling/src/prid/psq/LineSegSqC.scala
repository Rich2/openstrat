/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A 2d line upon a HexGrid defined by its start and end [[SqGrid]] [[SqCoord]]s. */
case class LineSegSqC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLike[SqCoord] with ElemInt4
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[SqCoord]] point. */
  def startPt: SqCoord = SqCoord(r1, c1)

  /** The end [[SqCoord]] point. */
  def endPt: SqCoord = SqCoord(r2, c2)

  /** Uses the implicit [[SqGridSysFlat]] parameter to convert from [[SqCen]]s to [[Pt2]]s. */
  //def oldLineSeg(implicit grider: SqGridSys): LineSeg = LineSeg(startPt.toPt2, endPt.toPt2)
}

/** companion object for [[LineSegSqC]] class contains factory apply method. */
object LineSegSqC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegSqC]] from the start and end hex coordinates [[SqCoord]]s. */
  def apply(hCoord1: SqCoord, hCoord2: SqCoord): LineSegSqC = new LineSegSqC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val buildEv: Int4ArrMapBuilder[LineSegSqC, LineSegSqCArr] = new Int4ArrMapBuilder[LineSegSqC, LineSegSqCArr]
  { type BuffT = LineSegSqCBuff
    override def fromIntArray(array: Array[Int]): LineSegSqCArr = new LineSegSqCArr(array)
    def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegSqCBuff = new LineSegSqCBuff(buffer)
  }
}

/** Compact immutable Array[Int] based collection class for [[LineSegSqC]]s. LineSegSqC is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegSqCArr(val unsafeArray: Array[Int]) extends Int4Arr[LineSegSqC]
{ type ThisT = LineSegSqCArr
  def fromArray(array: Array[Int]): LineSegSqCArr = new LineSegSqCArr(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSegSqC => String = _.toString
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegSqC = new LineSegSqC(d1, d2, d3, d4)
}

/** Companion object for the LineSegSqCs class. */
object LineSegSqCArr extends Int4ArrCompanion[LineSegSqC, LineSegSqCArr]
{
  /*implicit val persistImplicit: DataInt4sPersist[LineSegSqC, LineSegSqCs] = new DataDbl4sPersist[LineSegSqC, LineSegSqCs]("Line2s")
  { override def fromArray(value: Array[Int]): LineSegSqCs = new LineSegSqCs(value)

    override def showDecT(obj: LineSegSqCs, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }*/

  /*implicit val arrArrBuildImplicit: ArrFlatBuilder[LineSegSqCs] = new ArrInt4sFlatBuilder[LineSegSqC, LineSegSqCs]
  { type BuffT = LineSegSqCBuff
    override def fromIntArray(array: Array[Int]): LineSegSqCs = new LineSegSqCs(array)
    def fromDblBuffer(inp: ArrayBuffer[Int]): LineSegSqCBuff = new LineSegSqCBuff(inp)
  }*/

  //implicit val transImplicit: AffineTrans[LineSegSqCs] = (obj, f) => obj.dataMap(_.ptsTrans(f))

  /** This method allows a flat Array[Int] based collection class of type M, the final type, to be created from an Array[Int]. */
  override def fromArray(array: Array[Int]): LineSegSqCArr = new LineSegSqCArr(array)

  override def buff(initialSize: Int): Int4Buff[LineSegSqC, LineSegSqCArr] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegSqCBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int4Buff[LineSegSqC, LineSegSqCArr]
{ override def typeStr: String = "Line2sBuff"
  override def intsToT(d1: Int, d2: Int, d3: Int, d4: Int): LineSegSqC = new LineSegSqC(d1, d2, d3, d4)
}
