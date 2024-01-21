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
  def numVerts: Int

  /** Checks this polygon has at least 3 vertices. */
  def vertsMin3: Boolean = numVerts >= 3

  def verts: Arr[VT]

  /** Performs the side effecting function on the value of each vertex. */
  def vertsForeach[U](f: VT => U): Unit

  /** Maps the vertices of this polygon to an immutable Array like sequence of type B.
   * @tparam B The element type of the returned sequence.
   * @tparam ArrB The type of the immutable Array like sequence of B.
   * @return the immutable sequence collection by applying the supplied function to each vertex. */
  def vertsMap[B, ArrB <: Arr[B]](f: VT => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB = ssMap(f)


  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  def vertsPrevForEach[U](f: (VT, VT) => U): Unit = if (numVerts >= 2)
  { f(ssLast, vert(0))
    var i = 2
    while (i <= numVerts)
    { f(vert(i - 2), vert(i - 1))
      i += 1
    }
  }

  /** Maps the vertices of this [[PolygonLike]] to a new to PolygonLike class of type BB. */
  def map[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => B)(implicit build: PolygonLikeMapBuilder[B, BB]): BB =
  { val res = build.uninitialised(numVerts)
    verts.iForeach((i, a) => build.indexSet(res, i, f(a)))
    res
  }

  /** FlatMaps the vertices of this [[PolygonLike]] to a new to PolygonLike class of type BB. */
  def flatMap[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => SeqLike[B])(implicit build: PolygonLikeFlatBuilder[B, BB]): BB =
  { val buff = build.newBuff()
    vertsForeach(a => build.buffGrowSeqLike(buff, f(a)))
    build.buffToSeqLike(buff)
  }

  /** Optionally maps the vertices of this [[PolygonLike]] to vertices of a new to PolygonLike class of type BB. If the new [[PolygonLike]] has at
   *  least 3 vertices returns [[Some]] else returns [[None]]. */
  def optMap[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => Option[B])(implicit build: PolygonLikeMapBuilder[B, BB]): Option[BB] = {
    val buff = build.newBuff()
    vertsForeach(a =>f(a).foreach(v => build.buffGrow(buff, v)))
    if(buff.length >= 3) Some(build.buffToSeqLike(buff)) else None
  }

  /** Returns the vertex of the given index. Cycles around if the index is out of range, vert 3 retruns vert 0 on a triangle. */
  def vert(index: Int): VT = ssIndex(index %% numVerts)

  /** This method should be overridden in final classes. */
  def vertsForAll(f: VT => Boolean): Boolean =
  { var count = 0
    var res = true
    while (count < numVerts & res)
    { if (!f(vert(count))) res = false
      count += 1
    }
    res
  }

  def sides: Arr[SideT]

  def sidesForeach[U](f: SideT => U): Unit
}

trait PolygonValueN[VT <: ValueNElem] extends Any with PolygonLike[VT] with SeqSpecValueN[VT]
{ override def vertsForeach[U](f: VT => U): Unit = ssForeach(f)
  override def numVerts: Int = ssLength
}

/** A polygon whose elements are defined by [[Double]]s. */
trait PolygonLikeDblN[VT <: DblNElem] extends Any with PolygonValueN[VT] with SeqSpecDblN[VT]

/** A polygon whose elements are defined by 2 [[Double]]s. */
trait PolygonLikeDbl2[VT <: Dbl2Elem] extends Any with PolygonLikeDblN[VT] with SeqSpecDbl2[VT]

/** A polygon whose elements are defined by 3 [[Double]]s. */
trait PolygonLikeDbl3[VT <: Dbl3Elem] extends Any with PolygonLikeDblN[VT] with SeqSpecDbl3[VT]

/** A polygon whose elements are defined by [[Inte]]s. */
trait PolygonLikeIntN[VT <: IntNElem] extends Any with PolygonValueN[VT] with SeqSpecIntN[VT]

/** A polygon whose elements are defined by 2 [[int]]s. */
trait PolygonLikeInt2[VT <: Int2Elem] extends Any with PolygonLikeIntN[VT] with SeqSpecInt2[VT]

/** A polygon whose elements are defined by 3 [[int]]s. */
trait PolygonLikeInt3[VT <: Int3Elem] extends Any with PolygonLikeIntN[VT] with SeqSpecInt3[VT]