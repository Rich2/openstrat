/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** Pair element where the first element is an [[ElemIntN]]A class that can be construct from a fixed number of [[Int]]s. Because of the fixed length
 *  of these elements [[Arr]]s of them can be be stored as and reconstructed from a single Array[Int] of primitive values */
trait ElemIntNPair[A1 <: ElemIntN, A2] extends ElemPair[A1, A2]

/** [[PairArr]] where the first */
trait IntNPairArr[A1 <: ElemIntN, ArrA1 <: IntNArr[A1], A2, A <: ElemIntNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: IntNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayInt: Array[Int]

  def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): ThisT

  def filterOnA1(f: A1 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val a1Buffer = new ArrayBuffer[Int]()
    val a2Buffer = new ArrayBuffer[A2]()
    foreach{ p =>
      if (f(p.a1))
      { p.a1.intForeach(a1Buffer.append(_))
        a2Buffer.append(p.a2)
      }
    }
    newFromArrays(a1Buffer.toArray, a2Buffer.toArray)
  }
}

/** Specialised efficient [[Buff]] classes for accumulating pairs where the first component of the pair is and [[ElemIntN]]. */
trait IntNPairBuff[B1 <: ElemIntN, B2, B <: ElemIntNPair[B1, B2]] extends PairBuff[B1, B2, B]
{ def b1IntBuffer: ArrayBuffer[Int]

  final def growArr(newElems: IntNPairArr[B1, _, B2, B]): Unit =
  { newElems.a1ArrayInt.foreach(b1IntBuffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_))
  }
}

trait IntNPAirArrCommonBuilder[B1 <: ElemIntN, ArrB1 <: IntNArr[B1], B2, ArrB <: IntNPairArr[B1, ArrB1, B2, _]] extends
  PairArrCommonBuilder[B1, ArrB1, B2, ArrB]
{ type BuffT <: IntNPairBuff[B1, B2, _]
  type B1BuffT <: IntNBuff[B1]

  /** Constructs the [[Arr]] class from an [[Array]][Int] object for the first components of the pairs and an [[Array]][B2] for the second
   *  components of the pairs. */
  def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): ArrB

  /** Constructs the [[Buff]] class from an [[ArrayBuffer]][Int] object for the first components of the pairs and an [[ArrayBuffer]][B2] for the
   * second components of the pairs. */
  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = newElem.intForeach(buff.unsafeBuffer.append(_))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Int](length), new ArrayBuffer[B2](length))
  final override def buffToBB(buff: BuffT): ArrB = arrFromArrays(buff.b1IntBuffer.toArray, buff.b2Buffer.toArray)
}

trait IntNPairArrMapBuilder[B1 <: ElemIntN, ArrB1 <: IntNArr[B1], B2, B <: ElemIntNPair[B1, B2], ArrB <: IntNPairArr[B1, ArrB1, B2, B]] extends
  IntNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB] with PairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: IntNPairBuff[B1, B2, B]

  /** The number of [[Int]]s required to construct the first component of the pairs. */
  def a1IntNum: Int

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  final override def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB = arrFromArrays(b1Arr.unsafeArray, b2s)

  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT
  final override def uninitialised(length: Int): ArrB = arrFromArrays(new Array[Int](length * a1IntNum), new Array[B2](length))
  inline final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
  final override def arrFromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = arrFromArrays(a1Buff.toArray, b2s.toArray)
}

/** Helper trait for Companion objects of [[IntNPairArr]] classes. */
trait IntNPairArrCompanion[A1 <: ElemIntN, ArrA1 <: IntNArr[A1]]
{
  /** The number of [[Int]] values that are needed to construct an element of the defining-sequence. */
  def elemNumInts: Int
}

