/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Polygon like object, where the points dimensions could be specified in 2D or 3D [[Length]], latitude and longitude etc as well as the regular
 *  scalar values of the standard [[Polygon]].
 *  @tparam VT The type of the vertices in this polygon like trait. For a standard [[Polygon]] this will be a [[Pt2]], but for example for a
 *            [[PolygonM3]] it would be a [[PtM3]]. */
trait PolygonLike[VT] extends Any with SeqSpec[VT]
{
  type SideT <: LineSegLike[VT]

  /** The number of vertices and also the number of sides in this Polygon. */
  def vertsNum: Int

  /** Checks this polygon has at least 3 vertices. */
  def vertsMin3: Boolean = vertsNum >= 3

  /** Performs the side effecting function on the value of each vertex. */
  def vertsForeach[U](f: VT => U): Unit

  /** Index with foreach on each vertx. Applies the side effecting function on the index with the value of each vertex. Note the function signature
   *  follows the foreach based convention of putting the collection element 2nd or last as seen for example in fold methods' (accumulator, element)
   *  => B signature. */
  def vertsIForeach[U](f: (Int, VT) => U): Unit

  /** Maps the vertices of this polygon to an immutable Array like sequence of type B.
   * @tparam B The element type of the returned sequence.
   * @tparam ArrB The type of the immutable Array like sequence of B.
   * @return the immutable sequence collection by applying the supplied function to each vertex. */
  def vertsMap[B, ArrB <: Arr[B]](f: VT => B)(implicit builder: ArrMapBuilder[B, ArrB]): ArrB = ssMap(f)

  /** Folds over the vertices.
   * @tparam B type of the accumulator return value of this method. */
  def vertsFold[B](init: B)(f: (B, VT) => B): B =
  { var res = init
    vertsForeach(v => res = f(res, v))
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  def vertsPrevForEach[U](f: (VT, VT) => U): Unit = if (vertsNum >= 2) {
    f(ssLast, vert(0))
    var i = 2
    while (i <= vertsNum) {
      f(vert(i - 2), vert(i - 1))
      i += 1
    }
  }

  /** Maps the vertices of this [[PolygonLike]] to a new to PolygonLike class of type BB. */
  def map[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => B)(implicit build: PolygonLikeMapBuilder[B, BB]): BB =
  { val res = build.uninitialised(vertsNum)
    vertsIForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** FlatMaps the vertices of this [[PolygonLike]] to a new to PolygonLike class of type BB. */
  def flatMap[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => SeqLike[B])(implicit build: PolygonLikeFlatBuilder[B, BB]): BB =
  { val buff = build.newBuff()
    vertsForeach(a => build.buffGrowSeqLike(buff, f(a)))
    build.buffToSeqLike(buff)
  }

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  def vert(index: Int): VT = ssIndex(index)

  /** This method should be overridden in final classes. */
  def vertsForAll(f: VT => Boolean): Boolean =
  { var count = 0
    var res = true
    while (count < vertsNum & res)
    { if (!f(vert(count))) res = false
      count += 1
    }
    res
  }

  def sidesForeach[U](f: SideT => U): Unit
}

trait PolygonValueN[VT <: ValueNElem] extends Any with PolygonLike[VT] with ValueNSeqSpec[VT]
{ override def vertsForeach[U](f: VT => U): Unit = ssForeach(f)
  override def vertsIForeach[U](f: (Int, VT) => U): Unit = ssIForeach(f)
  override def vertsNum: Int = ssLength
}

/** A polygon whose elements are defined by [[Double]]s. */
trait PolygonLikeDblN[VT <: DblNElem] extends Any with PolygonValueN[VT] with DblNSeqSpec[VT]

/** A polygon whose elements are defined by 2 [[Double]]s. */
trait PolygonLikeDbl2[VT <: Dbl2Elem] extends Any with PolygonLikeDblN[VT] with Dbl2SeqSpec[VT]

/** A polygon whose elements are defined by 3 [[Double]]s. */
trait PolygonLikeDbl3[VT <: Dbl3Elem] extends Any with PolygonLikeDblN[VT] with Dbl3SeqSpec[VT]

/** A polygon whose elements are defined by [[Inte]]s. */
trait PolygonLikeIntN[VT <: IntNElem] extends Any with PolygonValueN[VT] with IntNSeqSpec[VT]

/** A polygon whose elements are defined by 2 [[int]]s. */
trait PolygonLikeInt2[VT <: Int2Elem] extends Any with PolygonLikeIntN[VT] with Int2SeqSpec[VT]

/** A polygon whose elements are defined by 3 [[int]]s. */
trait PolygonLikeInt3[VT <: Int3Elem] extends Any with PolygonLikeIntN[VT] with Int3SeqSpec[VT]