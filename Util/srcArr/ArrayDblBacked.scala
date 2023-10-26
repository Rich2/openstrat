/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any with SpecialT
{ def unsafeArray: Array[Double]
}

/** Base trait for collections of elements that are based on [[array]][Double]s, backed by an underlying Array[Array[Double]]. */
trait ArrArrayDbl[A <: ArrayDblBacked] extends Any with Arr[A]
{ type ThisT <: ArrArrayDbl[A]
  def unsafeArrayOfArrays: Array[Array[Double]]
  override final def length: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Double]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Double]](length))
  final def setElemUnsafe(i: Int, newElem: A): Unit = unsafeArrayOfArrays(i) = newElem.unsafeArray
}

/** This is the map builder for Arrays of Arrays of Double. It is not to be confused with the builder for Arrays of Double. It requires 3 memebers to
 * be implemented in the final type BuffT, newBuff and fromArrayArrayDbl. */
trait BuilderMapArrArrayDbl[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]] extends BuilderMapArr[A, ArrT]
{ @inline def fromArrayArrayDbl(array: Array[Array[Double]]): ArrT
  type BuffT <: BuffArrayDbl[A]
  @inline final override def uninitialised(length: Int): ArrT = fromArrayArrayDbl(new Array[Array[Double]](length))
  final override def indexSet(seqLike: ArrT, index: Int, elem: A): Unit = seqLike.unsafeArrayOfArrays(index) = elem.unsafeArray
  final override def buffToSeqLike(buff: BuffT): ArrT = fromArrayArrayDbl(buff.unsafeBuffer.toArray)
  final override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.unsafeBuffer.append(newElem.unsafeArray); () }
}

class ArrArrayDblEq[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
  else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayDblEq
{ def apply[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]]: ArrArrayDblEq[A, ArrT] = new ArrArrayDblEq[A, ArrT]
}

/** This is a [[Buff]] efficient buffer class for [[Array]][Double]s Each object contains multiple [[Array]]s. It is not a Buffer class for the
 *  elements to be put into [[Array]]s. */
trait BuffArrayDbl[A <: ArrayDblBacked] extends Any with Buff[A]
{ /** Constructs an lement of this [[Buff]] from an [[Array]][Double]. */
  def fromArrayDbl(array: Array[Double]): A

  def unsafeBuffer: ArrayBuffer[Array[Double]]
  override final def length: Int = unsafeBuffer.length
  def grow(elem: A): Unit = unsafeBuffer.append(elem.unsafeArray)
  def arrayArrayDbl: Array[Array[Double]] = unsafeBuffer.toArray
  final override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer(i) = newElem.unsafeArray
  inline final override def apply(index: Int): A = fromArrayDbl(unsafeBuffer(index))
}