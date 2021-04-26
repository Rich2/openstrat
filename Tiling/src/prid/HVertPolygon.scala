/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

class HVertPolygon(val arrayUnsafe: Array[Int]) extends AnyVal with HVertsLike
{ override type ThisT = HVertPolygon
  override def typeStr: String = "HVertsPolygon"
  override def unsafeFromArray(array: Array[Int]): HVertPolygon = new HVertPolygon(array)

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  /** Returns the index of the first instance of the HVert value in a [[Some]] otherwise returns [[None]]. */
  /*def indexOf(value: HVert): Option[Int] =
  { var res: Option[Int] = None
    var count = 0
    while(count < vertNum & res == None) { if (apply(count) == value) res = Some(count) }
    res
  }*/

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