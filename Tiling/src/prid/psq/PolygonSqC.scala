/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A polygon with the vertices defined by hex tile coordinates  [[HCoord]]s. */
class PolygonSqC(val unsafeArray: Array[Int]) extends AnyVal with SqCoordSeqSpec with PolygonInt2[SqCoord]
{ override type ThisT = PolygonSqC
  override type SideT = LineSegSqC
  override def typeStr: String = "PolygonSqC"
  override def fromArray(array: Array[Int]): PolygonSqC = new PolygonSqC(array)
  def vertNum: Int = unsafeArray.length / 2
  override def ssElem(int1: Int, int2: Int): SqCoord = SqCoord(int1, int2)

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  override def vert(index: Int): SqCoord = ssIndex(index)

  /** Performs the side effecting function on the [[SqCoord]] value of each vertex. */
  override def vertsForeach[U](f: SqCoord => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, SqCoord) => Any): Unit =
  { var i = 0
    vertsForeach{ vert =>
      f(i, vert)
      i += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: SqCoord => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, SqCoord) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (SqCoord, SqCoord) => U): Unit = ???

  /** This applies the index value in a circular manner. So the 6th index of a Sqexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: SqCoord => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(ssLength)
    ssIForeach((i, hv) => res.unsafeSetElem(i, f(hv)))
    res
  }

  def combine(operand: PolygonSqC): Option[PolygonSqC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = ssIndex(0)
    ???
  }

  override def sidesForeach[U](f: LineSegSqC => U): Unit = ???
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonSqC]] trait. */
object PolygonSqC extends Int2SeqLikeCompanion[SqCoord, PolygonSqC]
{ override def fromArray(array: Array[Int]): PolygonSqC = new PolygonSqC(array)

  implicit val arrBuildImplicit: ArrBuilder[PolygonSqC, PolygonSqCArr] = new ArrBuilder[PolygonSqC, PolygonSqCArr] {
    override type BuffT = PolygonSqCBuff
    override def newBuff(length: Int): PolygonSqCBuff = PolygonSqCBuff(length)
    override def newArr(length: Int): PolygonSqCArr = new PolygonSqCArr(new Array[Array[Int]](length))
    override def arrSet(arr: PolygonSqCArr, index: Int, value: PolygonSqC): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray
    override def buffGrow(buff: PolygonSqCBuff, value: PolygonSqC): Unit = buff.unsafeBuff.append(value.unsafeArray)
    override def buffGrowArr(buff: PolygonSqCBuff, arr: PolygonSqCArr): Unit = arr.foreach(p => buff.unsafeBuff.append(p.unsafeArray))
    override def buffToBB(buff: PolygonSqCBuff): PolygonSqCArr = new PolygonSqCArr(buff.unsafeBuff.toArray)
  }
}

class PolygonSqCArr(val unsafeArrayOfArrays:Array[Array[Int]]) extends Arr[PolygonSqC]
{ override type ThisT = PolygonSqCArr
  override def typeStr: String = "PolygonSqCArr"
  override def unsafeSameSize(length: Int): PolygonSqCArr = new PolygonSqCArr(new Array[Array[Int]](length))
  override def length: Int = unsafeArrayOfArrays.length
  override def unsafeSetElem(i: Int, value: PolygonSqC): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
  override def fElemStr: PolygonSqC => String = _.toString
  override def apply(index: Int): PolygonSqC = new PolygonSqC(unsafeArrayOfArrays(index))
}

class PolygonSqCBuff(val unsafeBuff: ArrayBuffer[Array[Int]]) extends AnyVal with Sequ[PolygonSqC]
{ override type ThisT = PolygonSqCBuff
  override def typeStr: String = "PolygonSqCBuff"
  override def length: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: PolygonSqC): Unit = unsafeBuff(i) = value.unsafeArray
  override def fElemStr: PolygonSqC => String = _.toString
  override def apply(index: Int): PolygonSqC = new PolygonSqC(unsafeBuff(index))
}

object PolygonSqCBuff
{ def apply(initLen: Int = 4): PolygonSqCBuff = new PolygonSqCBuff(new ArrayBuffer[Array[Int]](initLen))
}
