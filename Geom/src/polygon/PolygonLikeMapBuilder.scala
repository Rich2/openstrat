/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait PolygonLikeMapBuilder[B, +BB <: PolygonLike[B]] extends SeqLikeMapBuilder[B, BB @uncheckedVariance]

/** Trait for creating the line path builder instances for the [[PolygonLikeMapBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait PolygonValueNsBuilder[B <: ValueNElem, BB <: PolygonValueN[B]] extends PolygonLikeMapBuilder[B, BB] with ValueNSeqLikeCommonBuilder[BB]

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNsBuilder[B <: DblNElem, BB <: PolygonLikeDblN[B] ] extends PolygonValueNsBuilder[B, BB] with DblNSeqLikeCommonBuilder[BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl2]] final classes. Instances for the [[PolygonDbl2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2sBuilder[B <: Dbl2Elem, BB <: PolygonLikeDbl2[B]] extends PolygonDblNsBuilder[B, BB] with Dbl2SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl3]] final classes. Instances for the [[PolygonDbl3sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3sBuilder[B <: Dbl3Elem, BB <: PolygonLikeDbl3[B]] extends PolygonDblNsBuilder[B, BB] with Dbl3SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl3Buff[B]

  /*override def indexSet(arr: BB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 3) = value.dbl1; arr.unsafeArray(index * 3 + 1) = value.dbl2; arr.unsafeArray(index * 3 + 2) = value.dbl3
  }*/
}

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNsBuilder[B <: IntNElem, BB <: PolygonLikeIntN[B] ] extends PolygonValueNsBuilder[B, BB] with IntNSeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt2]] final classes. Instances for the [[PolygonInt2Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2Builder[B <: Int2Elem, BB <: PolygonLikeInt2[B]] extends PolygonIntNsBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt3]] final classes. Instances for the [[PolygonInt3Builder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt3Builder[B <: Int3Elem, BB <: PolygonLikeInt3[B]] extends PolygonIntNsBuilder[B, BB] with Int3SeqLikeMapBuilder[B, BB]