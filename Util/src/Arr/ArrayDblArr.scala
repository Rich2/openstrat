/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

trait ArrayDblArr[A <: ArrayDblBacked] extends Any with SeqImut[A]
{
  def unsafeArrayOfArrays: Array[Array[Double]]
  override def length: Int = unsafeArrayOfArrays.length
  override def sdLength: Int = unsafeArrayOfArrays.length
  def unsafeFromArrayArray(array: Array[Array[Double]]): ThisT
  final def unsafeSameSize(length: Int): ThisT = unsafeFromArrayArray(new Array[Array[Double]](length))
  def unsafeSetElem(i: Int, value: A): Unit = unsafeArrayOfArrays(i) = value.unsafeArray
}

/** This is the builder for Arrays Arrays of Double. It is not the builder for Arrays of Double.  */
trait ArrayDblArrBuilder[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends ArrBuilder[A, ArrT]
{ @inline def fromArray(array: Array[Array[Double]]): ArrT
  type BuffT <: ArrayDoubleBuff[A]
  @inline override def newArr(length: Int): ArrT = fromArray(new Array[Array[Double]](length))
  override def arrSet(arr: ArrT, index: Int, value: A): Unit = arr.unsafeArrayOfArrays(index) = value.unsafeArray
  //override def buffNew(length: Int = 4): DblsArrayBuff[A] = new DblsArrayBuff[A](new ArrayBuffer[Array[Double]]((length)))
  override def buffToBB(buff: BuffT): ArrT = fromArray(buff.unsafeBuff.toArray)
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuff.append(value.unsafeArray); () }
  override def buffGrowArr(buff: BuffT, arr: ArrT): Unit = { buff.unsafeBuff.addAll(arr.unsafeArrayOfArrays); () }
}

class ArrArrayDblEq[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]] extends EqT[ArrT]
{
  override def eqT(a1: ArrT, a2: ArrT): Boolean = if (a1.sdLength != a2.sdLength) false
    else a1.iForAll((i, el1) =>  el1.unsafeArray === a2(i).unsafeArray)
}

object ArrArrayDblEq
{
  def apply[A <: ArrayDblBacked, ArrT <: ArrayDblArr[A]]: ArrArrayDblEq[A, ArrT] = new ArrArrayDblEq[A, ArrT]
}

/** This is a buffer class for Arrays of Double. It is not a Buffer class for Arrays. */
trait ArrayDoubleBuff[A <: ArrayDblBacked] extends Any with SeqGen[A]
{ //override def apply(index: Int): AArray[Double] = unsafeBuff(index)
  def unsafeBuff: ArrayBuffer[Array[Double]]
  override def sdLength: Int = unsafeBuff.length
}