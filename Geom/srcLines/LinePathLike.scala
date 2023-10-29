/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

/** A generalisation of a line path where the type of the vertices is not restricted to [[Pt2]]. */
trait LinePathLike[VT] extends Any with SeqSpec[VT]
{ type ThisT <: LinePathLike[VT]
  type PolygonT <: PolygonLike[VT]
  type LineSegT <: LineSegLike[VT]
  type LineSegArrT <: Arr[LineSegT]

  /** maps to a [[LinePathLike]]. This map operates on a single [[LinePathLike]] its not to be confused with a map on Arr of [[LinePathLike]]s. */
  def map[B <: ValueNElem, BB <: LinePathLike[B]](f: VT => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(ssLength)
    ssIForeach((i, p) => res.setElemUnsafe(i, f(p)))
    res
  }

  /** This line path with the first and last vertex's removed. This can be useful for borders where the end points may show up in multiple line paths
   * and therefore sometimes need to be excluded when appending. */
  def inner: ThisT

  /** Appends another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("append") def ++ (operand: ThisT): ThisT

  /** Appends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("appendVert") def +%[AA >: VT](op: VT): ThisT

  /** Prepends a single vertex of type VT. Returns a new extended [[LinePathLike]]. */
  @targetName("prependVert") def %:(operand: VT): ThisT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("appendReverse") def ++<(operand: ThisT): ThisT

  /** Appends another [[LinePathLike]] of this type. Returns a [[PolygonLike]]. */
  @targetName("appendToPolygon") def |++|(operand: ThisT): PolygonT

  /** Appends a single vertex of type A. Returns a  [[PolygonLike]]. */
  @targetName("appendVertToPolygon") def |+|[AA >: VT](op: VT): PolygonT

  /** Appends the reverse vertex order of another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("appendReverseToPolygon") def |++<|(operand: ThisT): PolygonT

  /** Closes this [[LinePathLike]] into a [[PolygonLike]] by adding a [[LineSegLike]] from the last vertex to the first. */
  def toPolygon: PolygonT

  final def numVerts: Int = ssLength
}

trait LinePathDblN[VT <: DblNElem] extends  Any with LinePathLike[VT] with SeqSpecDblN[VT]
{ type ThisT <: LinePathDblN[VT]
  type PolygonT <: PolygonLikeDblN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Double]. */
  def polygonFromArray(array: Array[Double]): PolygonT

  override def inner: ThisT =
  { val newArrayLen = (numVerts - 2).max0 * elemProdSize
    val newArray = new Array[Double](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    ssInnerForeach{el => res.setElemUnsafe(i, el); i += 1}
    res
  }

  @targetName("append") final override def ++(operand: ThisT): ThisT = fromArray(unsafeArray ++ operand.unsafeArray)

  @targetName("appendVert") @inline final override def +%[AA >: VT](op: VT): ThisT =
  { val newArray = new Array[Double](unsafeLength + elemProdSize)
    unsafeArray.copyToArray(newArray)
    var i = unsafeLength
    op.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    fromArray(newArray)
  }

  @targetName("appendToPolygon") final override def |++|(operand: ThisT): PolygonT = polygonFromArray(unsafeArray ++ operand.unsafeArray)

  @targetName("appendVertToPolygon") final override def |+|[AA >: VT](op: VT): PolygonT =
  { val newArray = new Array[Double](unsafeLength + elemProdSize)
    unsafeArray.copyToArray(newArray)
    var i = unsafeLength
    op.dblForeach { d =>
      newArray(i) = d
      i += 1
    }
    polygonFromArray(newArray)
  }

  @targetName("appendReverse") final override def ++<(operand: ThisT): ThisT =
  { val newArray = new Array[Double](unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    val res = fromArray(newArray)
    var i = ssLength
    operand.ssReverseForeach{vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  override def toPolygon: PolygonT = polygonFromArray(unsafeArray)

  @targetName("appendReverseToPolygon") final override def |++<|(operand: ThisT): PolygonT =
    polygonFromArray((this ++< operand).unsafeArray)

  @targetName("prependVert") @inline final override def %: (operand: VT): ThisT =
  { val newArray = new Array[Double](unsafeLength + elemProdSize)
    Array.copy(unsafeArray, 0, newArray, elemProdSize, unsafeLength)
    var i = 0
    operand.dblForeach{d => newArray(i) = d; i += 1 }
    fromArray(newArray)
  }
}

trait LinePathDbl2[VT <: Dbl2Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl2[VT]
{ type ThisT <: LinePathDbl2[VT]
  type PolygonT <: PolygonLikeDbl2[VT]
}

trait LinePathDbl3[VT <: Dbl3Elem] extends Any with LinePathDblN[VT] with SeqSpecDbl3[VT]
{ type ThisT <: LinePathDbl3[VT]
  type PolygonT <: PolygonLikeDbl3[VT]
}

trait LinePathIntN[VT <: IntNElem] extends  Any with LinePathLike[VT] with SeqSpecIntN[VT]
{ type ThisT <: LinePathIntN[VT]
  type PolygonT <: PolygonLikeIntN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Int]. */
  def polygonFromArray(array: Array[Int]): PolygonT

  override def inner: ThisT =
  { val newArrayLen = (numVerts - 2).max0 * elemProdSize
    val newArray = new Array[Int](newArrayLen)
    val res = fromArray(newArray)
    var i = 0
    ssInnerForeach { el => res.setElemUnsafe(i, el); i += 1 }
    res
  }

  @targetName("append") final override def ++(operand: ThisT): ThisT = fromArray(unsafeArray ++ operand.unsafeArray)

  @targetName("appendVert") @inline final override def +%[AA >: VT](op: VT): ThisT =
  { val newArray = new Array[Int](unsafeLength + elemProdSize)
    unsafeArray.copyToArray(newArray)
    var i = unsafeLength
    op.intForeach { ii =>
      newArray(i) = ii
      i += 1
    }
    fromArray(newArray)
  }

  @targetName("prependVert") @inline final override def %:(operand: VT): ThisT = {
    val newArray = new Array[Int](unsafeLength + elemProdSize)
    Array.copy(unsafeArray, 0, newArray, elemProdSize, unsafeLength)
    var i = 0
    operand.intForeach { j => newArray(i) = j; i += 1 }
    fromArray(newArray)
  }

  @targetName("appendReverse") final override def ++<(operand: ThisT): ThisT = {
    val newArray = new Array[Int](unsafeLength + operand.unsafeLength)
    unsafeArray.copyToArray(newArray)
    val res = fromArray(newArray)
    var i = ssLength
    operand.ssReverseForeach { vt =>
      res.setElemUnsafe(i, vt)
      i += 1
    }
    res
  }

  @inline override def toPolygon: PolygonT = polygonFromArray(unsafeArray)
  @targetName("appendToPolygon") @inline override def |++|(operand: ThisT): PolygonT = polygonFromArray(unsafeArray ++ operand.unsafeArray)

  @targetName("appendVertToPolygon") override def |+|[AA >: VT](op: VT): PolygonT =
  { val newArray = new Array[Int](unsafeLength + elemProdSize)
    unsafeArray.copyToArray(newArray)
    var i = unsafeLength
    op.intForeach { ii =>
      newArray(i) = ii
      i += 1
    }
    polygonFromArray(newArray)
  }

  @targetName("appendReverseToPolygon") final override def |++<|(operand: ThisT): PolygonT =
    polygonFromArray((this ++< operand).unsafeArray)
}

trait LinePathInt2[VT <: Int2Elem] extends Any with LinePathIntN[VT] with SeqSpecInt2[VT]
{ type ThisT <: LinePathInt2[VT]
  type PolygonT <: PolygonLikeInt2[VT]
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait LinePathBuilder[B, BB <: LinePathLike[B]] extends BuilderSeqLikeMap[B, BB]

/** Trait for creating the line path builder instances for the [[LinePathBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LinePathBuilderValueN[B <: ValueNElem, BB <: LinePathLike[B]] extends LinePathBuilder[B, BB] with BuilderSeqLikeValueN[BB]

/** Trait for creating the builder type class instances for [[LinePathDblN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathMapBuilderDblN[B <: DblNElem, BB <: LinePathDblN[B] ] extends LinePathBuilderValueN[B, BB] with BuilderSeqLikeDblNMap[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl2]] final classes. Instances for the [[LinePathDbl2Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl2Builder[B <: Dbl2Elem, BB <: LinePathDbl2[B]] extends LinePathMapBuilderDblN[B, BB] with BuilderSeqLikeDbl2Map[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl3]] final classes. Instances for the [[LinePathDbl3MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl3MapBuilder[B <: Dbl3Elem, BB <: LinePathDbl3[B]] extends LinePathMapBuilderDblN[B, BB] with BuilderSeqLikeDbl3Map[B, BB]

/** Trait for creating the builder type class instances for [[LinePathIntN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathIntNMapBuilder[B <: IntNElem, BB <: LinePathIntN[B] ] extends LinePathBuilderValueN[B, BB] with BuilderSeqLikeIntNMap[B, BB]

/** Trait for creating the line path type class instances for [[LinePathInt2]] final classes. Instances for the [[LinePathInt2MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathInt2MapBuilder[B <: Int2Elem, BB <: LinePathInt2[B]] extends LinePathIntNMapBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]