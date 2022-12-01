/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A polygon with the vertices defined by hex tile coordinates  [[HCoord]]s. */
class PolygonHC(val unsafeArray: Array[Int]) extends AnyVal with HCoordSeqSpec with PolygonLikeInt2[HCoord] with ArrayIntBacked
{ override type ThisT = PolygonHC
  override type SideT = LineSegHC
  override def typeStr: String = "PolygonHC"
  override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)
  def vertNum: Int = unsafeArray.length / 2
  override def vert(index: Int): HCoord = ssIndex(index)

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

  override def vertsMap[B, ArrB <: Arr[B]](f: HCoord => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB =
  { val res = builder.uninitialised(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
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

  def toPolygon(f: HCoord => Pt2): Polygon = new PolygonGen(toPolygonArray(f))
  /*{
    val res = PolygonGen.uninitialised(sdLength)
    dataIForeach((i, hv) => res.unsafeSetElem(i, f(hv)))
    res
  }*/

  def toPolygonArray(f: HCoord => Pt2): Array[Double] =
  { val res = new Array[Double](ssLength * 2)
    ssIForeach{ (i, hv) =>
      val newVal = f(hv)
      res(i * 2) = newVal.dbl1
      res(i * 2 + 1) = newVal.dbl2
    }
    res
  }

  def combine(operand: PolygonHC): Option[PolygonHC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = ssIndex(0)
    ???
  }

  override def sidesForeach[U](f: LineSegHC => U): Unit = ???
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonHC]] trait. */
object PolygonHC extends Int2SeqLikeCompanion[HCoord, PolygonHC]
{ override def fromArray(array: Array[Int]): PolygonHC = new PolygonHC(array)

  implicit val arrBuildImplicit: ArrMapBuilder[PolygonHC, PolygonHCArr] = new ArrMapBuilder[PolygonHC, PolygonHCArr] {
    override type BuffT = PolygonHCBuff
    override def newBuff(length: Int): PolygonHCBuff = PolygonHCBuff(length)
    override def uninitialised(length: Int): PolygonHCArr = new PolygonHCArr(new Array[Array[Int]](length))
    override def indexSet(seqLike: PolygonHCArr, index: Int, value: PolygonHC): Unit = seqLike.unsafeArrayOfArrays(index) = value.unsafeArray
    override def buffGrow(buff: PolygonHCBuff, value: PolygonHC): Unit = buff.unsafeBuffer.append(value.unsafeArray)
    override def buffToSeqLike(buff: PolygonHCBuff): PolygonHCArr = new PolygonHCArr(buff.unsafeBuffer.toArray)
  }
}

class PolygonHCArr(val unsafeArrayOfArrays:Array[Array[Int]]) extends ArrayIntBackedArr[PolygonHC]
{ override type ThisT = PolygonHCArr
  override def typeStr: String = "PolygonHCArr"
  override def fElemStr: PolygonHC => String = _.toString
  override def apply(index: Int): PolygonHC = new PolygonHC(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Int]]): PolygonHCArr = new PolygonHCArr(array)
}

class PolygonHCBuff(val unsafeBuffer: ArrayBuffer[Array[Int]]) extends AnyVal with ArrayIntBuff[PolygonHC]
{ override type ThisT = PolygonHCBuff
  override def typeStr: String = "PolygonHCBuff"
  override def unsafeSetElem(i: Int, newElem: PolygonHC): Unit = unsafeBuffer(i) = newElem.unsafeArray
  override def fElemStr: PolygonHC => String = _.toString
  override def fromArrayInt(array: Array[Int]): PolygonHC = new PolygonHC(array)
}

object PolygonHCBuff
{ def apply(initLen: Int = 4): PolygonHCBuff = new PolygonHCBuff(new ArrayBuffer[Array[Int]](initLen))
}
