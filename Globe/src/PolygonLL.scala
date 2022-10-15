/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, collection.mutable.ArrayBuffer

/** A latitude-longitude polygon. A quasi polygon where the points are stored as points of latitude and longitude.Once the points are converted into a
*  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create Polygons that span an arc of greater than 90
*  degrees as this may break the algorithms. preferably keep the arcs significantly smaller. */
class PolygonLL(val unsafeArray: Array[Double]) extends AnyVal with LatLongSeqSpec with PolygonDbl2s[LatLong] with ElemArrayDbl
{ type ThisT = PolygonLL
  type SideT = LineSegLL
  override def fromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)
  override def typeStr: String = "PolygonLL"

  /** maps the vertices of this [[PolygonLike]] from [[LatLong]]s to [[PtM3]]s. */
  def toMetres3: PolygonM3 = map(_.toMetres3)

  //override def toString: String = PolygonLL.persistImplicit.showT(this, Show.Standard, 2, 2)

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  @inline override def vert(index: Int): LatLong = ssIndex(index)

  /** Performs the side effecting function on the [[LatLong]] value of each vertex. */
  override def vertsForeach[U](f: LatLong => U): Unit =
  { var count = 0
    while (count < vertsNum)
    { f(vert(count))
      count += 1
    }
  }

  /** Index with foreach on the vertices .Performs the side effecting function on the index with the [[LatLong]] value of each vertex. */
  override def vertsIForeach[U](f: (Int, LatLong) => Any): Unit =
  { var count = 0
    vertsForeach{ v =>
      f(count, v)
      count += 1
    }
  }

  /** Maps the [[LatLong]] values of each vertex to an immutable Array like sequence of type B. */
  override def vertsMap[B, ArrB <: Arr[B]](f: LatLong => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    vertsForeach{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  override def vertsFold[B](init: B)(f: (B, LatLong) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (LatLong, LatLong) => U): Unit = ???

  override def sidesForeach[U](f: LineSegLL => U): Unit = ???
}

/** Companion object for the [[PolygonLL]] class. */
object PolygonLL extends Dbl2SeqLikeCompanion[LatLong, PolygonLL]
{ override def fromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)

  implicit val arrBuildImplicit: ArrBuilder[PolygonLL, PolygonLLArr] = new ArrBuilder[PolygonLL, PolygonLLArr] {
    override type BuffT = PolygonLLBuff

    override def newBuff(length: Int): PolygonLLBuff = PolygonLLBuff(length)

    override def newArr(length: Int): PolygonLLArr = new PolygonLLArr(new Array[Array[Double]](length))

    override def arrSet(arr: PolygonLLArr, index: Int, value: PolygonLL): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray

    override def buffGrow(buff: PolygonLLBuff, value: PolygonLL): Unit = buff.unsafeBuffer.append(value.unsafeArray)

    override def buffGrowArr(buff: PolygonLLBuff, arr: PolygonLLArr): Unit = arr.foreach(p => buff.unsafeBuffer.append(p.unsafeArray))

    override def buffToBB(buff: PolygonLLBuff): PolygonLLArr = new PolygonLLArr(buff.unsafeBuffer.toArray)
  }

  implicit val persistImplicit: Dbl2SeqDefPersist[LatLong, PolygonLL] = new Dbl2SeqDefPersist[LatLong, PolygonLL]("PolygonLL")
  { override def fromArray(value: Array[Double]): PolygonLL = new PolygonLL(value)
  }

  implicit val llTransImplicit: LLTrans[PolygonLL] = new LLTrans[PolygonLL] {
    override def fLLTrans(orig: PolygonLL, f: LatLong => LatLong): PolygonLL = orig.map(f)
  }
}

class PolygonLLArr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrayDblArr[PolygonLL]
{ override type ThisT = PolygonLLArr
  override def typeStr: String = "PolygonLLArr"
  override def fElemStr: PolygonLL => String = _.toString
  override def apply(index: Int): PolygonLL = new PolygonLL(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonLLArr = new PolygonLLArr(array)
}

class PolygonLLBuff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with ArrayDblBuff[PolygonLL]
{ override type ThisT = PolygonLLBuff
  override def typeStr: String = "PolygonLLBuff"
  override def unsafeSetElem(i: Int, value: PolygonLL): Unit = unsafeBuffer(i) = value.unsafeArray
  override def fElemStr: PolygonLL => String = _.toString
  override def apply(index: Int): PolygonLL = new PolygonLL(unsafeBuffer(index))
}

object PolygonLLBuff
{ def apply(initLen: Int = 4): PolygonLLBuff = new PolygonLLBuff(new ArrayBuffer[Array[Double]](initLen))
}