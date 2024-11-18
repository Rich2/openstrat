/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A Polygon like object, where the points dimensions could be specified in 2D or 3D [[Metres]], latitude and longitude etc as well as the regular
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
  def map[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => B)(implicit build: PolygonLikeBuilderMap[B, BB]): BB =
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
  def optMap[B <: ValueNElem, BB <: PolygonLike[B]](f: VT => Option[B])(implicit build: PolygonLikeBuilderMap[B, BB]): Option[BB] = {
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

  /** Returns a side of the appropriate type for the [[PolygonLike]] from the goven index. The index cycles. */
  def side(index: Int): SideT

  def sidesForeach[U](f: SideT => U): Unit
  def sides: Arr[SideT]
}

trait PolygonValueN[VT <: ValueNElem] extends Any with PolygonLike[VT] with SeqSpecValueN[VT]
{ override def vertsForeach[U](f: VT => U): Unit = ssForeach(f)
  override def numVerts: Int = ssLength
}

/** A polygon whose elements are defined by [[Double]]s. */
trait PolygonLikeDblN[VT <: DblNElem] extends Any with PolygonValueN[VT] with SeqSpecDblN[VT]
{ /** Creates the [[Array]][Double] need to implement the sides method. */
  protected def arrayForSides: Array[Double]
}

/** A polygon whose elements are defined by 2 [[Double]]s. */
trait PolygonLikeDbl2[VT <: Dbl2Elem] extends Any with PolygonLikeDblN[VT] with SeqSpecDbl2[VT]
{
  protected override def arrayForSides: Array[Double] =
  { val newLen = numVerts * 4
    val newArray: Array[Double] = new Array[Double](newLen)
    val x0 = arrayUnsafe(0)
    newArray(0) = x0
    newArray(newLen - 2) = x0
    val y0 = arrayUnsafe(1)
    newArray(1) = y0
    newArray(newLen - 1) = y0
    var i = 1
    while (i < numVerts)
    { val x = arrayUnsafe(i * 2)
      newArray(i * 4 - 2) = x
      newArray(i * 4) = x
      val y = arrayUnsafe(i * 2 + 1)
      newArray(i * 4 - 1) = y
      newArray(i * 4 + 1) = y
      i += 1
    }
    newArray
  }
}

/** A polygon whose elements are defined by 3 [[Double]]s. */
trait PolygonLikeDbl3[VT <: Dbl3Elem] extends Any with PolygonLikeDblN[VT] with SeqSpecDbl3[VT]
{
  protected override def arrayForSides: Array[Double] =
  { val newLen = numVerts * 6
    val newArray: Array[Double] = new Array[Double](newLen)
    val x0 = arrayUnsafe(0)
    newArray(0) = x0
    newArray(newLen - 3) = x0
    val y0 = arrayUnsafe(1)
    newArray(1) = y0
    newArray(newLen - 2) = y0
    val z0 = arrayUnsafe(2)
    newArray(2) = z0
    newArray(newLen - 1) = z0
    var i = 1
    while (i < numVerts) {
      val x = arrayUnsafe(i * 3)
      newArray(i * 3) = x
      newArray((i + 1) * 3) = x
      val y = arrayUnsafe(i * 3 + 1)
      newArray(i * 3 + 1) = y
      newArray((i + 1) * 3 + 1) = y
      val z = arrayUnsafe(i * 3 + 2)
      newArray(i * 3 + 2) = z
      newArray((i + 1) * 3 + 2) = z
      i += 1
    }
    newArray
  }
}

/** A polygon whose elements are defined by [[Int]]s. */
trait PolygonLikeIntN[VT <: IntNElem] extends Any with PolygonValueN[VT] with SeqSpecIntN[VT]
{ /** Creates the [[Array]][Int] need to implement the sides method. */
  protected def arrayForSides: Array[Int]
}

/** A polygon whose elements are defined by 2 [[int]]s. */
trait PolygonLikeInt2[VT <: Int2Elem] extends Any with PolygonLikeIntN[VT] with SeqSpecInt2[VT]
{ /** Creates the [[Array]][Int] need to implement the sides method. */
  override protected def arrayForSides: Array[Int] =
  { val newLen = numVerts * 4
    val newArray: Array[Int] = new Array[Int](newLen)
    val x0 = arrayUnsafe(0)
    newArray(0) = x0
    newArray(newLen - 2) = x0
    val y0 = arrayUnsafe(1)
    newArray(1) = y0
    newArray(newLen - 1) = y0
    var i = 1
    while (i < numVerts) {
      val x = arrayUnsafe(i * 2)
      newArray(i * 4 - 2) = x
      newArray(i * 4) = x
      val y = arrayUnsafe(i * 2 + 1)
      newArray(i * 4 - 1) = y
      newArray(i * 4 + 1) = y
      i += 1
    }
    newArray
  }
}

/** A polygon whose elements are defined by 3 [[int]]s. */
trait PolygonLikeInt3[VT <: Int3Elem] extends Any with PolygonLikeIntN[VT] with SeqSpecInt3[VT]
{ /** Creates the [[Array]][Int] need to implement the sides method. */
  override protected def arrayForSides: Array[Int] =
  { val newLen = numVerts * 6
    val newArray: Array[Int] = new Array[Int](newLen)
    val x0 = arrayUnsafe(0)
    newArray(0) = x0
    newArray(newLen - 3) = x0
    val y0 = arrayUnsafe(1)
    newArray(1) = y0
    newArray(newLen - 2) = y0
    val z0 = arrayUnsafe(2)
    newArray(2) = z0
    newArray(newLen - 1) = z0
    var i = 1
    while (i < numVerts) {
      val x = arrayUnsafe(i * 3)
      newArray(i * 3) = x
      newArray((i + 1) * 3) = x
      val y = arrayUnsafe(i * 3 + 1)
      newArray(i * 3 + 1) = y
      newArray((i + 1) * 3 + 1) = y
      val z = arrayUnsafe(i * 3 + 2)
      newArray(i * 3 + 2) = z
      newArray((i + 1) * 3 + 2) = z
      i += 1
    }
    newArray
  }
}