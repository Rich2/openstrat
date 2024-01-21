/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._, collection.mutable.ArrayBuffer

/** A latitude-longitude polygon. A quasi polygon where the points are stored as points of latitude and longitude.Once the points are converted into a
*  view, ie into pixel positions an actual polygon can be drawn or filled as desired. Do not create Polygons that span an arc of greater than 90
*  degrees as this may break the algorithms. preferably keep the arcs significantly smaller. */
class PolygonLL(val unsafeArray: Array[Double]) extends AnyVal with LatLongSeqSpec with PolygonLikeDbl2[LatLong]
{ type ThisT = PolygonLL
  type SideT = LineSegLL
  override def fromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)
  override def typeStr: String = "PolygonLL"
  override def verts: LatLongArr = new LatLongArr(unsafeArray)

  /** maps the vertices of this [[PolygonLike]] from [[LatLong]]s to [[PtM3]]s. */
  def toMetres3: PolygonM3 = map(_.toMetres3)

  /** Performs the side effecting function on the [[LatLong]] value of each vertex. */
  override def vertsForeach[U](f: LatLong => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  /** Maps the [[LatLong]] values of each vertex to an immutable Array like sequence of type B. */
  override def vertsMap[B, ArrB <: Arr[B]](f: LatLong => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (LatLong, LatLong) => U): Unit = ???

  override def sides: LineSegLLArr = ???

  override def sidesForeach[U](f: LineSegLL => U): Unit = ???
}

/** Companion object for the [[PolygonLL]] class. */
object PolygonLL extends CompanionSeqLikeDbl2[LatLong, PolygonLL]
{ override def fromArray(array: Array[Double]): PolygonLL = new PolygonLL(array)

  /** Implicit instance to build specialist sequences of [[PolygonLL]]s for map and related methods. */
  implicit val mapBuilderArrEv: BuilderMapArrArrayDbl[PolygonLL, PolygonLLArr]  = new BuilderMapArrArrayDbl[PolygonLL, PolygonLLArr]
  { override type BuffT = PolygonLLBuff
    override def newBuff(length: Int): PolygonLLBuff = PolygonLLBuff(length)
    override def fromArrayArrayDbl(array: Array[Array[Double]]): PolygonLLArr = new PolygonLLArr(array)
  }

  /** [[Show]] type class instance / evidence for [[PolygonLL]]. */
  implicit val showEv: ShowSeqSpec[LatLong, PolygonLL] = ShowSeqSpec[LatLong, PolygonLL]("PolygonLL")

  /** [[Unshow]] type class instance / evidence for [[PolygonLL]]. */
  implicit val unshowEv: UnshowSeqLike[LatLong, PolygonLL] = UnshowSeqLike[LatLong, PolygonLL]("PolygonLL")

  implicit val llTransImplicit: LLTrans[PolygonLL] = new LLTrans[PolygonLL] {
    override def fLLTrans(orig: PolygonLL, f: LatLong => LatLong): PolygonLL = orig.map(f)
  }
}

/** An [[Arr]] of [[PolygonLL]]s, quasi polygons where the vertices are defined by latitude and longitude. Stored for efficiency as an Array of Arrays
 *  of Doubles. */
class PolygonLLArr(val unsafeArrayOfArrays:Array[Array[Double]]) extends ArrArrayDbl[PolygonLL]
{ override type ThisT = PolygonLLArr
  override def typeStr: String = "PolygonLLArr"
  override def fElemStr: PolygonLL => String = _.toString
  override def apply(index: Int): PolygonLL = new PolygonLL(unsafeArrayOfArrays(index))
  override def unsafeFromArrayArray(array: Array[Array[Double]]): PolygonLLArr = new PolygonLLArr(array)
}

/** An [[BuffSequ]] of [[PolygonLL]]s, quasi polygons where the vertices are defined by latitude and longitude. Stored for efficiency as an ArrayBuffer of
 *  Arrays of Doubles. */
class PolygonLLBuff(val unsafeBuffer: ArrayBuffer[Array[Double]]) extends AnyVal with BuffArrayDbl[PolygonLL]
{ override type ThisT = PolygonLLBuff
  override def typeStr: String = "PolygonLLBuff"
  override def fElemStr: PolygonLL => String = _.toString
  override def fromArrayDbl(array: Array[Double]): PolygonLL = new PolygonLL(array)
}

object PolygonLLBuff
{ def apply(initLen: Int = 4): PolygonLLBuff = new PolygonLLBuff(new ArrayBuffer[Array[Double]](initLen))
}