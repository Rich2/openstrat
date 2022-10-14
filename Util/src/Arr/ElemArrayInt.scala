/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Trait for Array[Int] backed classes. The purpose of this trait is to allow for collections of this class to be stored with their underlying
 * Array[Int]s. */
trait ArrayIntBacked extends Any
{ /** The backing Array[Int] of this collection class. End users should not normally need to interact with this directly. */
  def unsafeArray: Array[Int]
}

/** Not sure if this class is needed. */
trait ElemArrayInt extends Any// with SpecialT
{
  def unsafeArray: Array[Int]
}

/** Base trait for collections of elements that are based on [[array]][Int]s, backed by an underlying Array[Array[Int]]. */
trait ArrayIntArr[A <: ArrayIntBacked] extends Any with SeqImut[A]
{ type ThisT <: ArrayIntArr[A]
  def unsafeArrayOfArrays: Array[Array[Int]]
  override final def length: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Int]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Int]](length))
  final def unsafeSetElem(i: Int, value: A): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
}

/** This is the builder for Arrays Arrays of Int. It is not the builder for Arrays of Int.  */
trait ArrayIntArrBuilder[A <: ArrayIntBacked, ArrT <: ArrayIntArr[A]] extends ArrBuilder[A, ArrT]
{ @inline def fromArray(array: Array[Array[Int]]): ArrT
  type BuffT <: ArrayIntBuff[A]
  @inline override def newArr(length: Int): ArrT = fromArray(new Array[Array[Int]](length))
  override def arrSet(arr: ArrT, index: Int, value: A): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray
  override def buffToBB(buff: BuffT): ArrT = fromArray(buff.unsafeBuff.toArray)
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuff.append(value.unsafeArray); () }
  override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = { buff.unsafeBuff.addAll(arr.unsafeArrayOfArrays); () }
  //override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = arr.unsafeArrayOfArrays.foreach{array => buff.unsafeBuff.append(array) }
}

class ArrArrayIntEq[A <: ArrayIntBacked, ArrT <: ArrayIntArr[A]] extends EqT[ArrT]
{ override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.length != a2.length) false
else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayIntEq
{ def apply[A <: ArrayIntBacked, ArrT <: ArrayIntArr[A]]: ArrArrayIntEq[A, ArrT] = new ArrArrayIntEq[A, ArrT]
}

/** This is a buffer class for Arrays of Int. It is not a Buffer class for Arrays. */
trait ArrayIntBuff[A <: ArrayIntBacked] extends Any with Sequ[A]
{ def unsafeBuff: ArrayBuffer[Array[Int]]
  override final def length: Int = unsafeBuff.length

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqSpec]] of the this [[SeqImut]]'s final type. */
  override def unsafeSameSize(length: Int): ThisT = ???
}
