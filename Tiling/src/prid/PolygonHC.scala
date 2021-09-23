/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A polygon with the vertices defined by hex tile corrdinates  [[HCoord]]s. */
class PolygonHC(val arrayUnsafe: Array[Int]) extends AnyVal with PolygonInt2s[HCoord]
{ override type ThisT = PolygonHC
  override def typeStr: String = "PolygonHC"
  override def unsafeFromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
  def vertNum: Int = arrayUnsafe.length / 2

  override def dataElem(i1: Int, i2: Int): HCoord = HCoord(i1, i2)


  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  override def vert(index: Int): HCoord = ???

  override def vertsIForeach[U](f: (HCoord, Int) => Unit): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(v, count)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: ArrBase[B]](f: HCoord => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
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

  override def fElemStr: HCoord => String = _.toString

  /** This applies the index value in a circular manner. So the 6th index of a Hexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: HCoord => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(elemsNum)
    dataIForeach((hv, i) => res.unsafeSetElem(i, f(hv)))
    res
  }

  def combine(operand: PolygonHC): Option[PolygonHC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = indexData(0)
    ???
  }
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonHC]] trait. */
object PolygonHC extends DataInt2sCompanion[HCoord, PolygonHC]
{
  //override def buff(initialSize: Int): Int2sBuffer[HVert, HVertPolygon] = ???

  override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
}