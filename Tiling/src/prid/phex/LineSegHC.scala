/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class LineSegHC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLike[HCoord] with ElemInt4
{ def int1: Int = r1
  def int2: Int = c1
  def int3: Int = r2
  def int4: Int = c2

  /** The start [[HCoord]] point. */
  def startPt: HCoord = HCoord(r1, c1)

  /** The end [[HCoord]] point. */
  def endPt: HCoord = HCoord(r2, c2)

  /** Uses the implicit [[HGriderFlat]] parameter to convert from [[HCen]]s to [[Pt2]]s. */
  def lineSeg(implicit grider: HGriderFlat): LineSeg = LineSeg(startPt.toPt2, endPt.toPt2)
}

/** companion object for [[LineSegHC]] class contains factory apply method. */
object LineSegHC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegHC]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): LineSegHC = new LineSegHC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)

  /** Implicit instance / evidence for [[ArrBuilder]] type class. */
  implicit val buildEv: ArrInt4sBuilder[LineSegHC, LineSegHCs] = new ArrInt4sBuilder[LineSegHC, LineSegHCs]
  { type BuffT = LineSegHCBuff
    override def fromIntArray(array: Array[Int]): LineSegHCs = new LineSegHCs(array)
    def fromIntBuffer(inp: collection.mutable.ArrayBuffer[Int]): LineSegHCBuff = new LineSegHCBuff(inp)
  }
}