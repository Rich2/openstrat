/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation._

/** A generalisation of a line path where the type of the points is not resriscted to [[Pt2]]. */
trait LinePathLike[VT] extends Any with SeqSpec[VT]
{ type ThisT <: LinePathLike[VT]
  type PolygonT <: PolygonLike[VT]

  /** maps to a [[LinePathLike]]. This map operates on a single [[LinePathLike]] its not to be confused with a map on Arr of [[LinePathLike]]s. */
  def map[B <: ValueNElem, BB <: LinePathLike[B]](f: VT => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(ssLength)
    ssIForeach((i, p) => res.setElemUnsafe(i, f(p)))
    res
  }

  /** Appends another [[LinePathLike]] of this type. Returns a new extended [[LinePathLike]]. */
  @targetName("append") def ++ (operand: ThisT): ThisT

  /** Appends a single vertex of type A. Returns a new extended [[LinePathLike]]. */
  @targetName("appendVert") def +%[AA >: VT](op: VT): ThisT

  /** Appends another [[LinePathLike]] of this type. Returns a [[PolygonLike]]. */
  @targetName("appendToPolygon") def |++|(operand: ThisT): PolygonT

  /** Appends a single vertex of type A. Returns a  [[PolygonLike]]. */
  @targetName("appendVertToPolygon") def |+|[AA >: VT](op: VT): PolygonT

  def toPolygon: PolygonT
}

trait LinePathDblN[VT <: DblNElem] extends  Any with LinePathLike[VT] with DblNSeqSpec[VT]
{ type ThisT <: LinePathDblN[VT]
  type PolygonT <: PolygonLikeDblN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Double]. */
  def polygonFromArray(array: Array[Double]): PolygonT

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

  override def toPolygon: PolygonT = polygonFromArray(unsafeArray)
}

trait LinePathDbl2[VT <: Dbl2Elem] extends Any with LinePathDblN[VT] with Dbl2SeqSpec[VT]
{ type ThisT <: LinePathDbl2[VT]
  type PolygonT <: PolygonLikeDbl2[VT]
}

trait LinePathDbl3[VT <: Dbl3Elem] extends Any with LinePathDblN[VT] with Dbl3SeqSpec[VT]
{ type ThisT <: LinePathDbl3[VT]
  type PolygonT <: PolygonLikeDbl3[VT]
}

trait LinePathIntN[VT <: IntNElem] extends  Any with LinePathLike[VT] with IntNSeqSpec[VT]
{ type ThisT <: LinePathIntN[VT]
  type PolygonT <: PolygonLikeIntN[VT]

  /** Constructs a [[PolygonLike]] for this vertex type from an [[Array]][Int]. */
  def polygonFromArray(array: Array[Int]): PolygonT

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

  @targetName("appendToPolygon") override def |++|(operand: ThisT): PolygonT = polygonFromArray(unsafeArray ++ operand.unsafeArray)

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

  /** Closes this [[LinePathLike]] into a [[PolygonLike]] with a line Segment from the last point to the first point. */
  @inline override def toPolygon: PolygonT = polygonFromArray(unsafeArray)
}

trait LinePathInt2[VT <: Int2Elem] extends Any with LinePathIntN[VT] with Int2SeqSpec[VT]
{ type ThisT <: LinePathInt2[VT]
  type PolygonT <: PolygonLikeInt2[VT]
}

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait LinePathBuilder[B, BB <: LinePathLike[B]] extends SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path builder instances for the [[LinePathBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait LinePathValueNBuilder[B <: ValueNElem, BB <: LinePathLike[B]] extends LinePathBuilder[B, BB] with ValueNSeqLikeCommonBuilder[BB]

/** Trait for creating the builder type class instances for [[LinePathDblN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathDblNMapBuilder[B <: DblNElem, BB <: LinePathDblN[B] ] extends LinePathValueNBuilder[B, BB] with DblNSeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl2]] final classes. Instances for the [[LinePathDbl2Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl2Builder[B <: Dbl2Elem, BB <: LinePathDbl2[B]] extends LinePathDblNMapBuilder[B, BB] with Dbl2SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[LinePathDbl3]] final classes. Instances for the [[LinePathDbl3MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathDbl3MapBuilder[B <: Dbl3Elem, BB <: LinePathDbl3[B]] extends LinePathDblNMapBuilder[B, BB] with Dbl3SeqLikeMapBuilder[B, BB]

/** Trait for creating the builder type class instances for [[LinePathIntN]] final classes. Instances for the [[LinePathBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait LinePathIntNMapBuilder[B <: IntNElem, BB <: LinePathIntN[B] ] extends LinePathValueNBuilder[B, BB] with IntNSeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[LinePathInt2]] final classes. Instances for the [[LinePathInt2MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait LinePathInt2MapBuilder[B <: Int2Elem, BB <: LinePathInt2[B]] extends LinePathIntNMapBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]