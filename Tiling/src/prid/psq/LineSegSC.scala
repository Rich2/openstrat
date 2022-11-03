/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A 2d line upon a HexGrid defined by its start and end [[SqGrid]] [[SqCoord]]s. */
case class LineSegSC(int1: Int, int2: Int, int3: Int, int4: Int) extends LineSegLikeInt4[SqCoord]
{ def startR: Int = int1
  def startC: Int = int2
  def endR: Int = int3
  def endC: Int = int4

  /** The start [[SqCoord]] point. */
  override def startPt: SqCoord = SqCoord(startR, startC)

  /** The end [[SqCoord]] point. */
  override def endPt: SqCoord = SqCoord(endR, endC)

  /** Method needs removing. Uses the implicit [[SqGridSysFlat]] parameter to convert from [[SqCen]]s to [[Pt2]]s. */
  def oldLineSeg: LineSeg = LineSeg(startPt.toPt2Reg, endPt.toPt2Reg)
}

/** companion object for [[LineSegSC]] class contains factory apply method. */
object LineSegSC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegSC]] from the start and end hex coordinates [[SqCoord]]s. */
  def apply(hCoord1: SqCoord, hCoord2: SqCoord): LineSegSC = new LineSegSC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[ArrMapBuilder]] type class. */
  implicit val arrMapBuilderEv: Int4ArrMapBuilder[LineSegSC, LineSegSCArr] = new Int4ArrMapBuilder[LineSegSC, LineSegSCArr]
  { type BuffT = LineSegSCBuff
    override def fromIntArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)
    def fromIntBuffer(buffer: ArrayBuffer[Int]): LineSegSCBuff = new LineSegSCBuff(buffer)
  }

  implicit def pairArrMapBuilderEv[B2](implicit ct: ClassTag[B2]): LineSegSCPairArrMapBuilder[B2] = new LineSegSCPairArrMapBuilder[B2]
}

/** Compact immutable Array[Int] based collection class for [[LineSegSC]]s. LineSegSqC is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegSCArr(val unsafeArray: Array[Int]) extends Int4Arr[LineSegSC]
{ type ThisT = LineSegSCArr
  def fromArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSegSC => String = _.toString
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegSC = new LineSegSC(d1, d2, d3, d4)
}

/** Companion object for the LineSegSqCs class. */
object LineSegSCArr extends Int4ArrCompanion[LineSegSC, LineSegSCArr]
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
  override def fromArray(array: Array[Int]): LineSegSCArr = new LineSegSCArr(array)

  override def buff(initialSize: Int): Int4Buff[LineSegSC] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegSCBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int4Buff[LineSegSC]
{ override def typeStr: String = "Line2sBuff"
  override def intsToT(d1: Int, d2: Int, d3: Int, d4: Int): LineSegSC = new LineSegSC(d1, d2, d3, d4)
}

object LineSegSCBuff
{ def apply(initLen: Int = 4): LineSegSCBuff = new LineSegSCBuff(new ArrayBuffer[Int](initLen * 4))
}
