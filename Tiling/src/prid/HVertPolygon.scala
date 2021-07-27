/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

class HVertPolygon(val arrayUnsafe: Array[Int]) extends AnyVal with HVertsLike
{ override type ThisT = HVertPolygon
  override def typeStr: String = "HVertsPolygon"
  override def unsafeFromArray(array: Array[Int]): HVertPolygon = new HVertPolygon(array)

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: HVert => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(elemsLen)
    iForeach((hv, i) => res.unsafeSetElem(i, f(hv)))
    res
  }

  def combine(operand: HVertPolygon): Option[HVertPolygon] =
  {
    var starts: Option[(Int, Int)] = None
    val a = apply(0)
    ???
  }
}

object HVertPolygon extends Int2sArrCompanion[HVert, HVertPolygon]
{
  //override def buff(initialSize: Int): Int2sBuffer[HVert, HVertPolygon] = ???

  override def fromArray(array: Array[Int]): HVertPolygon = new HVertPolygon(array)
}