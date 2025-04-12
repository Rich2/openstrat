/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Trait for Array[Double] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Double]s. */
trait ArrayDblBacked extends Any, SpecialT
{ def arrayUnsafe: Array[Double]

  /** The length of the backing [[Array]] [[Double]]. */
  @inline final def arrayLen: Int = arrayUnsafe.length
}

/** Base trait for collections of elements that are based on [[array]][Double]s, backed by an underlying Array[Array[Double]]. */
trait ArrArrayDbl[A <: ArrayDblBacked] extends Any, Arr[A]
{ type ThisT <: ArrArrayDbl[A]
  def arrayOfArraysUnsafe: Array[Array[Double]]
  def elemFromArray(array: Array[Double]): A
  def fromArrayArray(array: Array[Array[Double]]): ThisT
  
  final override def apply(index: Int): A = elemFromArray(arrayOfArraysUnsafe(index))
  final override def elem(index: Int): A = elemFromArray(arrayOfArraysUnsafe(index))
  final override def length: Int = arrayOfArraysUnsafe.length
  final override def numElems: Int = arrayOfArraysUnsafe.length  
  final def sameSizeUninitialised(length: Int): ThisT = fromArrayArray(new Array[Array[Double]](length))
  final def setElemUnsafe(index: Int, newElem: A): Unit = arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
}

/** This is the map builder for Arrays of Arrays of Double. It is not to be confused with the builder for Arrays of Double. It requires 3 members to be
 * implemented in the final type BuffT, newBuff and fromArrayArrayDbl. */
trait BuilderMapArrArrayDbl[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]] extends BuilderMapArr[A, ArrT]
{ @inline def fromArrayArrayDbl(array: Array[Array[Double]]): ArrT
  type BuffT <: BuffArrayDbl[A]
  @inline final override def uninitialised(length: Int): ArrT = fromArrayArrayDbl(new Array[Array[Double]](length))
  final override def indexSet(seqLike: ArrT, index: Int, newElem: A): Unit = seqLike.arrayOfArraysUnsafe(index) = newElem.arrayUnsafe
  final override def buffToSeqLike(buff: BuffT): ArrT = fromArrayArrayDbl(buff.bufferUnsafe.toArray)
  final override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.bufferUnsafe.append(newElem.arrayUnsafe); () }
}

class ArrArrayDblEq[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
  else a1.iForAll((i, el1) =>  el1.arrayUnsafe === a2(i).arrayUnsafe)
}

object ArrArrayDblEq
{ def apply[A <: ArrayDblBacked, ArrT <: ArrArrayDbl[A]]: ArrArrayDblEq[A, ArrT] = new ArrArrayDblEq[A, ArrT]
}

/** This is a [[Buff]] efficient buffer class for [[Array]][Double]s Each object contains multiple [[Array]]s. It is not a Buffer class for the elements to
 * be put into [[Array]]s. */
trait BuffArrayDbl[A <: ArrayDblBacked] extends Any, Buff[A]
{ /** Constructs an element of this [[Buff]] from an [[Array]][Double]. */
  def fromArrayDbl(array: Array[Double]): A

  def bufferUnsafe: ArrayBuffer[Array[Double]]
  final override def apply(index: Int): A = fromArrayDbl(bufferUnsafe(index))
  final override def elem(index: Int): A = fromArrayDbl(bufferUnsafe(index))
  final override def length: Int = bufferUnsafe.length
  final override def numElems: Int = bufferUnsafe.length
  def grow(elem: A): Unit = bufferUnsafe.append(elem.arrayUnsafe)
  def arrayArrayDbl: Array[Array[Double]] = bufferUnsafe.toArray
  final override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe(index) = newElem.arrayUnsafe
}