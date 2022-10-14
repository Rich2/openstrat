/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

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
  def vertsIForeach[U](f: (Int, VT) => Any): Unit

  /** Maps the vertices of this polygon to an immutable Array like sequence of type B.
   * @tparam B The element type of the returned sequence.
   * @tparam ArrB The type of the immutable Array like sequence of B.
   * @return the immutable sequence collection by applying the supplied function to each vertex. */
  def vertsMap[B, ArrB <: SeqImut[B]](f: VT => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB

  /** Folds over the vertices.
   * @tparam B type of the accumulator return value of this method. */
  def vertsFold[B](init: B)(f: (B, VT) => B): B

  /** Map this collection of data elements to PolygonLike class of type BB. */
  def map[B <: ElemValueN, BB <: PolygonLike[B]](f: VT => B)(implicit build: PolygonLikeBuilder[B, BB]): BB =
  {
    val res = build.newPolygonT(vertsNum)
    vertsIForeach((i, a) => build.arrSet(res, i, f(a)))
    res
  }

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  def vert(index: Int): VT

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

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  def vertsPrevForEach[U](f: (VT, VT) => U): Unit

  def sidesForeach[U](f: SideT => U): Unit
}

trait PolygonValueN[VT <: ElemValueN] extends Any with PolygonLike[VT] with ValueNSeqSpec[VT]
{ override def vertsForeach[U](f: VT => U): Unit = ssForeach(f)
  override def vertsNum: Int = ssLength
}

/** A polygon whose elements are defined by [[Double]]s. */
trait PolygonDblNs[VT <: ElemDblN] extends Any with PolygonValueN[VT] with DblNSeqSpec[VT]

/** A polygon whose elements are defined by 2 [[Double]]s. */
trait PolygonDbl2s[VT <: ElemDbl2] extends Any with PolygonDblNs[VT] with Dbl2SeqSpec[VT]

/** A polygon whose elements are defined by 3 [[Double]]s. */
trait PolygonDbl3s[VT <: ElemDbl3] extends Any with PolygonDblNs[VT] with Dbl3SeqSpec[VT]

/** A polygon whose elements are defined by [[Inte]]s. */
trait PolygonIntNs[VT <: ElemIntN] extends Any with PolygonValueN[VT] with IntNSeqSpec[VT]

/** A polygon whose elements are defined by 2 [[int]]s. */
trait PolygonInt2s[VT <: ElemInt2] extends Any with PolygonIntNs[VT] with Int2SeqSpec[VT]