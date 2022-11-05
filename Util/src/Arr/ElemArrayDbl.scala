/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any with SpecialT
{ def unsafeArray: Array[Double]
}

/** Base trait for collections of elements that are based on [[array]][Double]s, backed by an underlying Array[Array[Double]]. */
trait ArrayDblArr[A <: ArrayDblBacked] extends Any with Arr[A]
{ type ThisT <: ArrayDblArr[A]
  def unsafeArrayOfArrays: Array[Array[Double]]
  override final def length: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Double]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Double]](length))
  final def unsafeSetElem(i: Int, value: A): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
}

/** This is the map builder for Arrays of Arrays of Double. It is not to be confused with the builder for Arrays of Double. It requires 3 memebers to
 * be implemented in the final type BuffT, newBuff and fromArrayArrayDbl. */
trait ArrayDblArrMapBuilder[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends ArrMapBuilder[A, ArrT]
{ @inline def fromArrayArrayDbl(array: Array[Array[Double]]): ArrT
  type BuffT <: ArrayDblBuff[A]
  @inline final override def uninitialised(length: Int): ArrT = fromArrayArrayDbl(new Array[Array[Double]](length))
  final override def indexSet(seqLike: ArrT, index: Int, value: A): Unit = seqLike.unsafeArrayOfArrays(index) = value.unsafeArray
  final override def buffToBB(buff: BuffT): ArrT = fromArrayArrayDbl(buff.unsafeBuffer.toArray)
  final override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuffer.append(value.unsafeArray); () }
}

class ArrArrayDblEq[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
  else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayDblEq
{ def apply[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]]: ArrArrayDblEq[A, ArrT] = new ArrArrayDblEq[A, ArrT]
}

/** This is a buffer class for Arrays of Double. It is not a Buffer class for Arrays. */
trait ArrayDblBuff[A <: ArrayDblBacked] extends Any with Buff[A]
{ /** Constructs an lement of this [[Buff]] from an [[Array]][Double]. */
  def fromArrayDbl(array: Array[Double]): A

  def unsafeBuffer: ArrayBuffer[Array[Double]]
  override final def length: Int = unsafeBuffer.length
  def grow(elem: A): Unit = unsafeBuffer.append(elem.unsafeArray)
  def arrayArrayDbl: Array[Array[Double]] = unsafeBuffer.toArray
  final override def unsafeSetElem(i: Int, value: A): Unit = unsafeBuffer(i) = value.unsafeArray
  inline final override def apply(index: Int): A = fromArrayDbl(unsafeBuffer(index))
}