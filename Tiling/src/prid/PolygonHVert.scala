/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

class PolygonHVert(val arrayUnsafe: Array[Int]) extends AnyVal with HVertsLike with PolygonInt2s[HVert]
{ override type ThisT = PolygonHVert
  override def typeStr: String = "HVertsPolygon"
  override def unsafeFromArray(array: Array[Int]): PolygonHVert = new PolygonHVert(array)

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: HVert => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(elemsNum)
    dataIForeach((hv, i) => res.unsafeSetElem(i, f(hv)))
    res
  }

  def combine(operand: PolygonHVert): Option[PolygonHVert] =
  {
    var starts: Option[(Int, Int)] = None
    val a = indexData(0)
    ???
  }
}

object PolygonHVert extends DataInt2sCompanion[HVert, PolygonHVert]
{
  //override def buff(initialSize: Int): Int2sBuffer[HVert, HVertPolygon] = ???

  override def fromArray(array: Array[Int]): PolygonHVert = new PolygonHVert(array)
}