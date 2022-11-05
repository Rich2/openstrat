/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait PolygonLikeMapBuilder[B, +BB <: PolygonLike[B]] extends SeqLikeMapBuilder[B, BB @uncheckedVariance]
{ type BuffT <: Buff[B]

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.length) if (buff(count) == newElem) res = true else count += 1
    res
  }

  def iterMap[A](inp: Iterable[A], f: A => B): BB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToBB(buff)
  }
}

trait PolygonBuilderData[B, +BB <: PolygonLike[B]] extends PolygonLikeMapBuilder[B, BB]

/** Trait for creating the line path builder instances for the [[PolygonLikeMapBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait PolygonValueNsBuilder[B <: ElemValueN, BB <: PolygonValueN[B]] extends PolygonBuilderData[B, BB] with ValueNSeqLikeCommonBuilder[BB]

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNsBuilder[B <: ElemDblN, BB <: PolygonDblN[B] ] extends PolygonValueNsBuilder[B, BB] with DblNSeqLikeCommonBuilder[BB]

/** Trait for creating the line path type class instances for [[PolygonDbl2]] final classes. Instances for the [[PolygonDbl2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2sBuilder[B <: ElemDbl2, BB <: PolygonDbl2[B]] extends PolygonDblNsBuilder[B, BB] with Dbl2SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl2Buff[B]
  //override def indexSet(arr: BB, index: Int, value: B): Unit = { arr.unsafeArray(index * 2) = value.dbl1; arr.unsafeArray(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the line path type class instances for [[PolygonDbl3]] final classes. Instances for the [[PolygonDbl3sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl3]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3sBuilder[B <: ElemDbl3, BB <: PolygonDbl3[B]] extends PolygonDblNsBuilder[B, BB] with Dbl3SeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl3Buff[B]

  override def indexSet(arr: BB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 3) = value.dbl1; arr.unsafeArray(index * 3 + 1) = value.dbl2; arr.unsafeArray(index * 3 + 2) = value.dbl3
  }
}

/** Trait for creating the builder type class instances for [[PolygonDblN]] final classes. Instances for the [[PolygonLikeMapBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNsBuilder[B <: ElemIntN, BB <: PolygonIntN[B] ] extends PolygonValueNsBuilder[B, BB] with IntNSeqLikeMapBuilder[B, BB]

/** Trait for creating the line path type class instances for [[PolygonInt2]] final classes. Instances for the [[PolygonInt2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemInt2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2sBuilder[B <: ElemInt2, BB <: PolygonInt2[B]] extends PolygonIntNsBuilder[B, BB] with Int2SeqLikeMapBuilder[B, BB]