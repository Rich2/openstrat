/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait PolygonLikeBuilderMap[B, +BB <: PolygonLike[B]] extends BuilderSeqLikeMap[B, BB @uncheckedVariance]

/** Trait for creating the line path builder instances for the [[PolygonLikeBuilderMap]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait PolygonValueNBuilderMap[B <: ValueNElem, BB <: PolygonValueN[B]] extends PolygonLikeBuilderMap[B, BB] with BuilderSeqLikeValueN[BB]

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeBuilderMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNBuilderMap[B <: DblNElem, BB <: PolygonLikeDblN[B] ] extends PolygonValueNBuilderMap[B, BB] with BuilderSeqLikeDblN[BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl2]] final classes. Instances for the [[PolygonDbl2BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2BuilderMap[B <: Dbl2Elem, BB <: PolygonLikeDbl2[B]] extends PolygonDblNBuilderMap[B, BB] with BuilderSeqLikeDbl2Map[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeDbl3]] final classes. Instances for the [[PolygonDbl3BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3BuilderMap[B <: Dbl3Elem, BB <: PolygonLikeDbl3[B]] extends PolygonDblNBuilderMap[B, BB] with BuilderSeqLikeDbl3Map[B, BB]
{ type BuffT <: Dbl3Buff[B]
}

/** Trait for creating the builder type class instances for [[PolygonLikeDblN]] final classes. Instances for the [[PolygonLikeBuilderMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNBuilderMap[B <: IntNElem, BB <: PolygonLikeIntN[B] ] extends PolygonValueNBuilderMap[B, BB] with BuilderSeqLikeIntNMap[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt2]] final classes. Instances for the [[PolygonInt2BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2BuilderMap[B <: Int2Elem, BB <: PolygonLikeInt2[B]] extends PolygonIntNBuilderMap[B, BB] with BuilderSeqLikeInt2Map[B, BB]

/** Trait for creating the line path type class instances for [[PolygonLikeInt3]] final classes. Instances for the [[PolygonInt3BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt3BuilderMap[B <: Int3Elem, BB <: PolygonLikeInt3[B]] extends PolygonIntNBuilderMap[B, BB] with BuilderSeqLikeInt3Map[B, BB]

trait PolygonLikeFlatBuilder[VT, +BB <: PolygonLike[VT]] extends BuilderSeqLikeFlat[BB @uncheckedVariance]
{ override type BuffT <: BuffSequ[VT]
  def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit
}

trait PolygonValueNFlatBuilder[VT <: ValueNElem, BB <: PolygonValueN[VT]] extends PolygonLikeFlatBuilder[VT, BB] with BuilderSeqLikeValueN[BB]

trait PolygonIntNFlatBuilder[VT <: IntNElem, BB <: PolygonLikeIntN[VT]] extends PolygonValueNFlatBuilder[VT, BB] with BuilderSeqLikeIntNFlat[BB]
{
  override def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit = seqLike.ssForeach{_.intForeach(int => buff.unsafeBuffer.append(int)) }
  override def buffToSeqLike(buff: BuffT): BB = fromIntArray(buff.unsafeBuffer.toArray)
}

trait PolygonInt3FlatBuilder[VT <: Int3Elem, BB <: PolygonLikeInt3[VT]] extends PolygonIntNFlatBuilder[VT, BB] with BuilderSeqLikeInt3Flat[BB]