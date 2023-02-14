/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A generalisation of a line path where the type of the points is not resriscted to [[Pt2]]. */
trait LinePathLike[A] extends Any with SeqSpec[A]
{ /** maps to a [[LinePathLike]]. This map operates on a single [[LinePathLike]] its not to be confused with a map on Arr of [[LinePathLike]]s. */
  def map[B <: ValueNElem, BB <: LinePathLike[B]](f: A => B)(implicit build: LinePathBuilder[B, BB]): BB =
  { val res = build.uninitialised(ssLength)
    ssIForeach((i, p) => res.setElemUnsafe(i, f(p)))
    res
  }
}

trait LinePathDblN[A <: DblNElem] extends  Any with LinePathLike[A] with DblNSeqSpec[A]
trait LinePathDbl2[A <: Dbl2Elem] extends Any with LinePathDblN[A] with Dbl2SeqSpec[A]
trait LinePathDbl3[A <: Dbl3Elem] extends Any with LinePathDblN[A] with Dbl3SeqSpec[A]

trait LinePathIntN[A <: IntNElem] extends  Any with LinePathLike[A] with IntNSeqSpec[A]
trait LinePathInt2[A <: Int2Elem] extends Any with LinePathIntN[A] with Int2SeqSpec[A]

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