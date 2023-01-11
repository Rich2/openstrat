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
trait PolygonValueNMapBuilder[B <: ValueNElem, BB <: PolygonValueN[B]] extends PolygonLikeMapBuilder[B, BB] with ValueNSeqLikeCommonBuilder[BB]

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNMapBuilder[B <: DblNElem, BB <: PolygonLikeDblN[B] ] extends PolygonValueNMapBuilder[B, BB] with DblNSeqLikeCommonBuilder[BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl2]] final classes. Instances for the [[PolygonDbl2MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2MapBuilder[B <: Dbl2Elem, BB <: PolygonLikeDbl2[B]] extends PolygonDblNMapBuilder[B, BB] with Dbl2SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl3]] final classes. Instances for the [[PolygonDbl3MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3MapBuilder[B <: Dbl3Elem, BB <: PolygonLikeDbl3[B]] extends PolygonDblNMapBuilder[B, BB] with Dbl3SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl3Buff[B]

  /*override def indexSet(arr: BB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 3) = value.dbl1; arr.unsafeArray(index * 3 + 1) = value.dbl2; arr.unsafeArray(index * 3 + 2) = value.dbl3
  }*/
}

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNMapBuilder[B <: IntNElem, BB <: PolygonLikeIntN[B] ] extends PolygonValueNMapBuilder[B, BB] with IntNSeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt2]] final classes. Instances for the [[PolygonInt2MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2MapBuilder[B <: Int2Elem, BB <: PolygonLikeInt2[B]] extends PolygonIntNMapBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt3]] final classes. Instances for the [[PolygonInt3MapBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt3MapBuilder[B <: Int3Elem, BB <: PolygonLikeInt3[B]] extends PolygonIntNMapBuilder[B, BB] with Int3SeqLikeMapBuilder[B, BB]

trait PolygonLikeFlatBuilder[VT, +BB <: PolygonLike[VT]] extends SeqLikeFlatBuilder[BB @uncheckedVariance]
{ override type BuffT <: Buff[VT]
  def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit
}

trait PolygonValueNFlatBuilder[VT <: ValueNElem, BB <: PolygonValueN[VT]] extends PolygonLikeFlatBuilder[VT, BB] with ValueNSeqLikeCommonBuilder[BB]

trait PolygonIntNFlatBuilder[VT <: IntNElem, BB <: PolygonLikeIntN[VT]] extends PolygonValueNFlatBuilder[VT, BB] with IntNSeqLikeFlatBuilder[BB]
{
  override def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit = seqLike.ssForeach{_.intForeach(int => buff.unsafeBuffer.append(int)) }
}

trait PolygonInt3FlatBuilder[VT <: Int3Elem, BB <: PolygonLikeInt3[VT]] extends PolygonIntNFlatBuilder[VT, BB] with Int3SeqLikeFlatBuilder[BB]