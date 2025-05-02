/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in the
 * companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into the BB
 * companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a function from A => B or
 * A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be used directly by end users. */
trait PolygonLikeBuilderMap[B, +BB <: PolygonBase[B]] extends BuilderMapSeqLike[B, BB @uncheckedVariance]
{ 
  /** The uninitialised polygon must be backed by an [[Array]] to be constructed by this builder, even if all the final classes of the type do not inherit from
   * [[SeqLikeBacked]]. For example this builder can not construct a specialised quadrilateral, rectangle or triangle class. */
  override def uninitialised(length: Int): BB & SeqLikeBacked[B]
}

/** Trait for creating the line path builder instances for the [[PolygonLikeBuilderMap]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait PolygonValueNBuilderMap[B <: ValueNElem, BB <: PolygonValueN[B]] extends PolygonLikeBuilderMap[B, BB] with BuilderSeqLikeValueN[BB]

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[PolygonLikeBuilderMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNBuilderMap[B <: DblNElem, BB <: PolygonDblN[B] ] extends PolygonValueNBuilderMap[B, BB] with BuilderSeqLikeDblN[BB]

/** Trait for creating the line path type class instances for [[PolygonDbl2]] final classes. Instances for the [[PolygonDbl2BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2BuilderMap[B <: Dbl2Elem, BB <: PolygonDbl2[B]] extends PolygonDblNBuilderMap[B, BB] with BuilderMapSeqLikeDbl2[B, BB]

/** Trait for creating the line path type class instances for [[PolygonDbl3]] final classes. Instances for the [[PolygonDbl3BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3BuilderMap[B <: Dbl3Elem, BB <: PolygonDbl3[B]] extends PolygonDblNBuilderMap[B, BB] with BuilderMapSeqLikeDbl3[B, BB]
{ type BuffT <: Dbl3Buff[B]
}

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[PolygonLikeBuilderMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNBuilderMap[B <: IntNElem, BB <: PolygonIntN[B] ] extends PolygonValueNBuilderMap[B, BB] with BuilderSeqLikeIntNMap[B, BB]

/** Trait for creating the line path type class instances for [[PolygonInt2]] final classes. Instances for the [[PolygonInt2BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2BuilderMap[B <: Int2Elem, BB <: PolygonInt2[B]] extends PolygonIntNBuilderMap[B, BB], BuilderMapSeqLikeInt2[B, BB]

/** Trait for creating the line path type class instances for [[PolygonInt3]] final classes. Instances for the [[PolygonInt3BuilderMap]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt3BuilderMap[B <: Int3Elem, BB <: PolygonInt3[B]] extends PolygonIntNBuilderMap[B, BB] with BuilderMapSeqLikeInt3[B, BB]

trait PolygonLikeFlatBuilder[VT, +BB <: PolygonBase[VT]] extends BuilderFlatSeqLike[BB @uncheckedVariance]
{ override type BuffT <: Buff[VT]
  def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit
}

trait PolygonValueNFlatBuilder[VT <: ValueNElem, BB <: PolygonValueN[VT]] extends PolygonLikeFlatBuilder[VT, BB] with BuilderSeqLikeValueN[BB]

trait PolygonIntNFlatBuilder[VT <: IntNElem, BB <: PolygonIntN[VT]] extends PolygonValueNFlatBuilder[VT, BB] with BuilderSeqLikeIntNFlat[BB]
{
  override def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit = seqLike.foreach{_.intForeach(int => buff.bufferUnsafe.append(int)) }
  override def buffToSeqLike(buff: BuffT): BB = fromIntArray(buff.bufferUnsafe.toArray)
}

trait PolygonInt3FlatBuilder[VT <: Int3Elem, BB <: PolygonInt3[VT]] extends PolygonIntNFlatBuilder[VT, BB] with BuilderFlatSeqLikeInt3[BB]