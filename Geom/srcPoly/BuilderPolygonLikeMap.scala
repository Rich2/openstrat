/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import annotation.unchecked.uncheckedVariance

/** A type class for the eeficient building of [[PolygonLike]] classes. Instances for this type class for classes / traits you control should go in the
 * companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into the BB
 * companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a function from A => B or
 * A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be used directly by end users. */
trait BuilderPolygonLikeMap[B, +BB <: PolygonBase[B]] extends BuilderSeqLikeMap[B, BB @uncheckedVariance]
{ /** The uninitialised polygon must be backed by an [[Array]] to be constructed by this builder, even if all the final classes of the type do not inherit from
   * [[SeqLikeBacked]]. For example this builder can not construct a specialised quadrilateral, rectangle or triangle class. */
  override def uninitialised(length: Int): BB & SeqLikeBacked[B]
}

/** Trait for creating the line path builder instances for the [[BuilderPolygonLikeMap]] type class, for classes / traits you control, should go in the
 * companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderPolygonValueNMap[B <: ValueNElem, BB <: PolygonValueN[B]] extends BuilderPolygonLikeMap[B, BB], BuilderSeqLikeValueN[BB]

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[BuilderPolygonLikeMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderPolygonDblNMap[B <: DblNElem, BB <: PolygonDblN[B] ] extends BuilderPolygonValueNMap[B, BB], BuilderSeqLikeDblN[BB]

/** Trait for creating the line path type class instances for [[PolygonDbl2]] final classes. Instances for the [[BuilderPolygonDbl2Map]] type class, for classes
 * / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is called B, because it
 * corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderPolygonDbl2Map[B <: Dbl2Elem, BB <: PolygonDbl2[B]] extends BuilderPolygonDblNMap[B, BB], BuilderMapSeqLikeDbl2[B, BB]

/** Trait for creating the line path type class instances for [[PolygonDbl3]] final classes. Instances for the [[BuilderPolygonDbl3Map]] type class, for classes
 * / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called B, because it
 * corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderPolygonDbl3Map[B <: Dbl3Elem, BB <: PolygonDbl3[B]] extends BuilderPolygonDblNMap[B, BB], BuilderMapSeqLikeDbl3[B, BB]
{ type BuffT <: Dbl3Buff[B]
}

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[BuilderPolygonLikeMap]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderPolygonIntNMap[B <: IntNElem, BB <: PolygonIntN[B] ] extends BuilderPolygonValueNMap[B, BB], BuilderSeqLikeIntNMap[B, BB]

/** Trait for creating the line path type class instances for [[PolygonInt2]] final classes. Instances for the [[BuilderPolygonInt2Map]] type class, for classes
 * / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called  B, because it
 * corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderPolygonInt2Map[B <: Int2Elem, BB <: PolygonInt2[B]] extends BuilderPolygonIntNMap[B, BB], BuilderSeqLikeInt2Map[B, BB]

/** Trait for creating the line path type class instances for [[PolygonInt3]] final classes. Instances for the [[BuilderPolygonInt3Map]] type class, for classes
 * / traits you control, should go in the companion object of type B, which will extend [[Int2Elem]]. The first type parameter is called  B, because it
 * corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait BuilderPolygonInt3Map[B <: Int3Elem, BB <: PolygonInt3[B]] extends BuilderPolygonIntNMap[B, BB], BuilderMapSeqLikeInt3[B, BB]

trait BuilderPolygonLikeFlat[VT, +BB <: PolygonBase[VT]] extends BuilderFlatSeqLike[BB @uncheckedVariance]
{ override type BuffT <: Buff[VT]
  def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit
}

trait BuilderPolygonValueNFlat[VT <: ValueNElem, BB <: PolygonValueN[VT]] extends BuilderPolygonLikeFlat[VT, BB], BuilderSeqLikeValueN[BB]

trait BuilderPolygonIntNFlat[VT <: IntNElem, BB <: PolygonIntN[VT]] extends BuilderPolygonValueNFlat[VT, BB], BuilderSeqLikeIntNFlat[BB]
{ override def buffGrowSeqLike(buff: BuffT, seqLike: SeqLike[VT]): Unit = seqLike.foreach{_.intForeach(int => buff.bufferUnsafe.append(int)) }
  override def buffToSeqLike(buff: BuffT): BB = fromIntArray(buff.bufferUnsafe.toArray)
}

trait BuilderPolygonInt3Flat[VT <: Int3Elem, BB <: PolygonInt3[VT]] extends BuilderPolygonIntNFlat[VT, BB], BuilderFlatSeqLikeInt3[BB]