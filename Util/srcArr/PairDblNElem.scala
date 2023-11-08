/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** [[PairElem]] where the first component is a [[DblNElem]]. */
trait PairDblNElem[A1 <: DblNElem, A2] extends PairFinalA1Elem[A1, A2]

/** [[Arr]] for [[PairDblNElem]]s. [[PairElem]]s where the first component of the pair is a [[DblNElem]]. */
trait ArrPairDblN[A1 <: DblNElem, ArrA1 <: ArrDblN[A1], A2, A <: PairDblNElem[A1, A2]] extends PairArrFinalA1[A1, ArrA1, A2, A]
{ type ThisT <: ArrPairDblN[A1, ArrA1, A2, A]

  def a1NumDbl: Int

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayDbl: Array[Double]

  /** The length of the Array[[Double]] backing a1. */
  final def a1ArrayLength: Int = a1ArrayDbl.length

  def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): ThisT

  override def replaceA1byA2(key: A2, newValue: A1): ThisT =
  { val newA1s = new Array[Double](length * a1NumDbl)
    a1ArrayDbl.copyToArray(newA1s)
    val res = newFromArrays(newA1s, a2Array)
    var i = 0
    while(i < length){ if (key == a2Index(i)) res.setA1Unsafe(i, newValue); i += 1 }
    res
  }

  final override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT = newFromArrays(new Array[Double](length *a1NumDbl), new Array[A2](length))
}

/** Efficent buffer classes for [[PairDblN]] elements. */
trait BuffPairDblN[B1 <: DblNElem, B2, B <: PairDblNElem[B1, B2]] extends BuffPair[B1, B2, B]
{ /** The backing buffer for the B1 components. */
  def b1DblBuffer: ArrayBuffer[Double]

  final def growArr(newElems: ArrPairDblN[B1, _, B2, B]): Unit = { newElems.a1ArrayDbl.foreach(b1DblBuffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_)) }

  final override def pairGrow(b1: B1, b2: B2): Unit = { b1.dblForeach(b1DblBuffer.append(_)); b2Buffer.append(b2) }
}

/** Base trait for builders of [[ArrPairDblN]] objects by both map and flatMap methods. */
trait BuilderArrPairDblN[B1 <: DblNElem, ArrB1 <: ArrDblN[B1], B2, ArrB <: ArrPairDblN[B1, ArrB1, B2, _]] extends
BuilderArrPair[B1, ArrB1, B2, ArrB]
{ type BuffT <: BuffPairDblN[B1, B2, _]
  type B1BuffT <: BuffDblN[B1]

  /** Constructs the [[Arr]] class from an [[Array]][Double] object for the first components of the pairs and an [[Array]][B2] for the second
   *  components of the pairs. */
  def arrFromArrays(b1ArrayDbl: Array[Double], b2Array: Array[B2]): ArrB

  /** Constructs the [[BuffSequ]] class from an [[ArrayBuffer]][Double] object for the first components of the pairs and an [[ArrayBuffer]][B2] for the
   * second components of the pairs. */
  def buffFromBuffers(b1Buffer: ArrayBuffer[Double], b2Buffer: ArrayBuffer[B2]): BuffT

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = newElem.dblForeach(buff.unsafeBuffer.append(_))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Double](length), new ArrayBuffer[B2](length))
  final override def buffToSeqLike(buff: BuffT): ArrB = arrFromArrays(buff.b1DblBuffer.toArray, buff.b2Buffer.toArray)
  final override def arrFromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = arrFromArrays(a1Buff.toArray, b2s.toArray)
}

/** Builder for [[ArrPairDblN]] objects by both map f: A => pairB methods. */
trait BuilderArrPairDblNMap[B1 <: DblNElem, ArrB1 <: ArrDblN[B1], B2, B <: PairDblNElem[B1, B2], ArrB <: ArrPairDblN[B1, ArrB1, B2, B]] extends
BuilderArrPairDblN[B1, ArrB1, B2, ArrB] with BuilderArrPairMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairDblN[B1, B2, B]

  /** The number of [[Double]]s required to construct the first component of the pairs. */
  def a1DblNum: Int

  final override def uninitialised(length: Int): ArrB = arrFromArrays(new Array[Double](length * a1DblNum), new Array[B2](length))
  inline final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.grow(newElem)
}

/** Builder for [[ArrPairDblN]] objects by both flatMap f: A => ArrpairB methods. */
trait BuilderArrPairDblNFlat[B1 <: DblNElem, ArrB1 <: ArrDblN[B1], B2, ArrB <: ArrPairDblN[B1, ArrB1, B2, _]] extends
BuilderArrPairDblN[B1, ArrB1, B2, ArrB] with BuilderArrPairFlat[B1, ArrB1, B2, ArrB]
{
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { arr.a1ArrayDbl.foreach(buff.b1DblBuffer.append(_))
    arr.a2Arr.foreach(buff.b2Buffer.append(_)) }
}

/** Helper trait for Companion objects of [[ArrPairDblN]] classes. */
trait CompanionArrPairDblN[A1 <: DblNElem, ArrA1 <: ArrDblN[A1]]
{
  /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int
}