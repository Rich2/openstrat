/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqSpecPair[A1E, A1 <: SeqSpec[A1E], A2] extends ElemPair[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** A sequence of [[ElemSeqSpecPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqSpecPairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends PairArr[A1, A1Arr, A2, A]

/** A buffer of [[ElemSeqSpecPair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends PairBuff[A1, A2, A]
{ def a2Buffer: ArrayBuffer[A2]
  override def length: Int = a2Buffer.length
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqSpecPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeMapBuilder[B1E, B1]
}

trait SeqSpecDblNPair[A1E <: ElemDblN, A1 <: DblNSeqSpec[A1E], A2] extends ElemSeqSpecPair[A1E, A1, A2]

trait SeqSpecDblNPairArr[A1E <: ElemDblN, A1 <: DblNSeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends
  SeqSpecPairArr[A1E, A1, A1Arr, A2, A]
{ type ThisT <: SeqSpecDblNPairArr[A1E, A1, A1Arr, A2, A]
  def a1FromArrayDbl(array: Array[Double]): A1
  def a1ArrayArrayDbl: Array[Array[Double]]
  def fromArrays(array1: Array[Array[Double]], array2: Array[A2]): ThisT

  override def a1Index(index: Int): A1 = a1FromArrayDbl(a1ArrayArrayDbl(index))
  def a1Buff: ArrayDblBuff[A1]

  def filterOn1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT = {
    val buff1 = a1Buff
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    fromArrays(buff1.arrayArrayDbl, buff2.toArray)
  }
}

trait SeqSpecDblNPairArrBuilder[B1E <: ElemDblN, B1 <: DblNSeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: SeqSpecDblNPair[B1E, B1, B2], ArrB <: Arr[B]] extends
  SeqSpecPairArrBuilder[B1E, B1, ArrB1, B2, B, ArrB]
{
  type B1BuffT <: ArrayDblBuff[B1]
  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.unsafeArray)

  def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[B2]): ArrB

  final override def fromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = fromArrays(a1Buff.arrayArrayDbl, b2s.toArray)
}

trait SeqSpecIntNPair[A1E <: ElemIntN, A1 <: IntNSeqSpec[A1E], A2] extends ElemSeqSpecPair[A1E, A1, A2]

trait SeqSpecIntNPairArr[A1E <: ElemIntN, A1 <: IntNSeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqSpecPair[A1E, A1, A2]] extends
  SeqSpecPairArr[A1E, A1, A1Arr, A2, A]
{ type ThisT <: SeqSpecIntNPairArr[A1E, A1, A1Arr, A2, A]
  def a1FromArrayInt(array: Array[Int]): A1
  def arrayArrayInt: Array[Array[Int]]
  def fromArrays(array1: Array[Array[Int]], array2: Array[A2]): ThisT
  def a1Buff: ArrayIntBuff[A1]
  override def a1Index(index: Int): A1 = a1FromArrayInt(arrayArrayInt(index))

  def filterOn1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val buff1 = a1Buff
    val buff2 = new ArrayBuffer[A2]()
    var i = 0
    a1Arr.foreach { a1 =>
      if (f(a1)) {
        buff1.grow(a1)
        buff2.append(a2Array(i))
      }
      i += 1
    }
    fromArrays(buff1.arrayArrayInt, buff2.toArray)
  }
}