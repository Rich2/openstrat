/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** Compact immutable Array[Int] based collection class for [[LineSegHC]]s. LineSegHC is the library's term for a mathematical straight line segment, but what in
 *  common parlance is often just referred to as a line. */
class LineSegHCs(val unsafeArray: Array[Int]) extends ArrInt4s[LineSegHC]
{ type ThisT = LineSegHCs
  def unsafeFromArray(array: Array[Int]): LineSegHCs = new LineSegHCs(array)
  override def typeStr: String = "Line2s"
  override def fElemStr: LineSegHC => String = _.toString
  //override def toString: String = Line2s.PersistImplict.show(this)
  override def newElem(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}

/** Companion object for the LineSegHCs class. */
object LineSegHCs extends ArrInt4sCompanion[LineSegHC, LineSegHCs]
{
  val factory: Int => LineSegHCs = i => new LineSegHCs(new Array[Int](i * 4))

  /*implicit val persistImplicit: DataInt4sPersist[LineSegHC, LineSegHCs] = new DataDbl4sPersist[LineSegHC, LineSegHCs]("Line2s")
  { override def fromArray(value: Array[Int]): LineSegHCs = new LineSegHCs(value)

    override def showDecT(obj: LineSegHCs, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ???
  }*/

  /*implicit val arrArrBuildImplicit: ArrFlatBuilder[LineSegHCs] = new ArrInt4sFlatBuilder[LineSegHC, LineSegHCs]
  { type BuffT = LineSegHCBuff
    override def fromIntArray(array: Array[Int]): LineSegHCs = new LineSegHCs(array)
    def fromDblBuffer(inp: ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(inp)
  }*/

  //implicit val transImplicit: AffineTrans[LineSegHCs] = (obj, f) => obj.dataMap(_.ptsTrans(f))

  override def buff(initialSize: Int): Int4Buff[LineSegHC, LineSegHCs] = ???
}

/** Efficient expandable buffer for Line2s. */
class LineSegHCBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int4Buff[LineSegHC, LineSegHCs]
{ override def typeStr: String = "Line2sBuff"
  override def intsToT(d1: Int, d2: Int, d3: Int, d4: Int): LineSegHC = new LineSegHC(d1, d2, d3, d4)
}