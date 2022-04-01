/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A polygon with the vertices defined by hex tile coordinates  [[HCoord]]s. */
class PolygonHC(val unsafeArray: Array[Int]) extends AnyVal with HCoordSeqDef with PolygonInt2s[HCoord]
{ override type ThisT = PolygonHC
  override type SideT = LineSegHC
  override def typeStr: String = "PolygonHC"
  override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
  def vertNum: Int = unsafeArray.length / 2

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  override def vert(index: Int): HCoord = sdIndex(index)

  /** Performs the side effecting function on the [[HCoord]] value of each vertex. */
  override def vertsForeach[U](f: HCoord => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, HCoord) => Any): Unit =
  { var i = 0
    vertsForeach{ vert =>
      f(i, vert)
      i += 1
    }
  }

  override def vertsMap[B, ArrB <: SeqImut[B]](f: HCoord => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, HCoord) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (HCoord, HCoord) => U): Unit = ???

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: HCoord => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(sdLength)
    dataIForeach((i, hv) => res.unsafeSetElem(i, f(hv)))
    res
  }

  def combine(operand: PolygonHC): Option[PolygonHC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = sdIndex(0)
    ???
  }

  override def sidesForeach[U](f: LineSegHC => U): Unit = ???
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonHC]] trait. */
object PolygonHC extends Int2SeqDefCompanion[HCoord, PolygonHC]
{
  //override def buff(initialSize: Int): Int2sBuffer[HVert, HVertPolygon] = ???

  override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
}