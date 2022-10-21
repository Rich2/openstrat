/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

/* A polygon using distances measured in [[Length]] or metres rather than scalars. */
final class PolygonM2(val unsafeArray: Array[Double]) extends AnyVal with PolygonDbl2[PtM2]
{ type ThisT = PolygonM2
  type SideT = LineSegM
  def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)
  override def typeStr: String = "PolygonMs"
  override def ssElem(d1: Double, d2: Double): PtM2 = new PtM2(d1, d2)
  override def fElemStr: PtM2 => String = _.str

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  @inline override def vert(index: Int): PtM2 = ssIndex(index)

  /** Performs the side effecting function on the [[PtM2]] value of each vertex. */
  override def vertsForeach[U](f: PtM2 => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsIForeach[U](f: (Int, PtM2) => Any): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: PtM2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, PtM2) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (PtM2, PtM2) => U): Unit = ???

  override def sidesForeach[U](f: LineSegM => U): Unit = ???
}

/** The companion object for PolygonDist. Provides an implicit builder. */
object PolygonM2 extends Dbl2SeqLikeCompanion[PtM2, PolygonM2]
{ override def fromArray(array: Array[Double]): PolygonM2 = new PolygonM2(array)

  implicit val persistImplicit: Dbl2SeqDefPersist[PtM2, PolygonM2] = new Dbl2SeqDefPersist[PtM2, PolygonM2]("PolygonMs")
  { override def fromArray(value: Array[Double]): PolygonM2 = new PolygonM2(value)
  }

  implicit val arrBuildImplicit: ArrBuilder[PolygonM2, PolygonM2Arr] = new ArrBuilder[PolygonM2, PolygonM2Arr] {
    override type BuffT = PolygonM2Buff

    override def newBuff(length: Int): PolygonM2Buff = PolygonM2Buff(length)
    override def newArr(length: Int): PolygonM2Arr = new PolygonM2Arr(new Array[Array[Double]](length))
    override def arrSet(arr: PolygonM2Arr, index: Int, value: PolygonM2): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray
    override def buffGrow(buff: PolygonM2Buff, value: PolygonM2): Unit = buff.unsafeBuffer.append(value.unsafeArray)
    override def buffGrowArr(buff: PolygonM2Buff, arr: PolygonM2Arr): Unit = arr.foreach(p => buff.unsafeBuffer.append(p.unsafeArray))
    override def buffToBB(buff: PolygonM2Buff): PolygonM2Arr = new PolygonM2Arr(buff.unsafeBuffer.toArray)
  }
}

class PolygonM2Arr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrayDblArr[PolygonM2]
{ override type ThisT = PolygonM2Arr
  override def typeStr: String = "PolygonMArr"
  override def fElemStr: PolygonM2 => String = _.toString
  override def apply(index: Int): PolygonM2 = new PolygonM2(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonM2Arr = new PolygonM2Arr(array)
}

class PolygonM2Buff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDblBuff[PolygonM2]
{ override type ThisT = PolygonM2Buff
  override def typeStr: String = "PolygonMBuff"
  override def fElemStr: PolygonM2 => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonM2 = new PolygonM2(array)
}

object PolygonM2Buff
{ def apply(initLen: Int = 4): PolygonM2Buff = new PolygonM2Buff(new ArrayBuffer[Array[Double]](initLen))
}