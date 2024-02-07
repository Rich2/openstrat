/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** Pair element where the first component is an [[IntNElem]]A class that can be construct from a fixed number of [[Int]]s. Because of the fixed
 *  length of these elements [[Arr]]s of them can be be stored as and reconstructed from a single Array[Int] of primitive values */
trait PairIntNElem[A1 <: IntNElem, A2] extends PairFinalA1Elem[A1, A2]

/** An [[Arr]] of [[PairIntN]] elements where the first component of the pairs is an [[IntNElem]]. */
trait ArrPairIntN[A1 <: IntNElem, ArrA1 <: ArrIntN[A1], A2, A <: PairIntNElem[A1, A2]] extends PairArrFinalA1[A1, ArrA1, A2, A]
{ type ThisT <: ArrPairIntN[A1, ArrA1, A2, A]

  /** The number of [[Int]]s required to construct the first component of the pairs. */
  def a1NumInt: Int

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayInt: Array[Int]

  final def a1ArrayLength: Int = a1ArrayInt.length

  def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): ThisT

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

  def filterOnA2(f: A2 => Boolean)(implicit ct: ClassTag[A2]): ThisT =
  { val a1Buffer = new ArrayBuffer[Int]()
    val a2Buffer = new ArrayBuffer[A2]()
    pairForeach { (a1, a2) =>
      if (f(a2)) {
        a1.intForeach(a1Buffer.append(_))
        a2Buffer.append(a2)
      }
    }
    newFromArrays(a1Buffer.toArray, a2Buffer.toArray)
  }

  final override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT = newFromArrays(new Array[Int](length *a1NumInt), new Array[A2](length))

  override def replaceA1byA2(key: A2, newValue: A1): ThisT =
  { val newA1s = new Array[Int](length * a1NumInt)
    a1ArrayInt.copyToArray(newA1s)
    val res = newFromArrays(newA1s, a2Array)
    var i = 0
    while (i < length) {
      if (key == a2Index(i)) res.setA1Unsafe(i, newValue); i += 1
    }
    res
  }
}

/** Specialised efficient [[BuffSequ]] classes for accumulating pairs where the first component of the pair is and [[IntNElem]]. */
trait BuffPairIntN[B1 <: IntNElem, B2, B <: PairIntNElem[B1, B2]] extends BuffPair[B1, B2, B]
{ def b1IntBuffer: ArrayBuffer[Int]

  final def growArr(newElems: ArrPairIntN[B1, _, B2, B]): Unit =
  { newElems.a1ArrayInt.foreach(b1IntBuffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_))
  }

  final override def pairGrow(b1: B1, b2: B2): Unit = { b1.intForeach(b1IntBuffer.append(_)); b2Buffer.append(b2) }
}

/** Common builder trait for [[PairArrIntN]] objects via both the map and flatMap methods. */
trait BuilderArrPairIntN[B1 <: IntNElem, ArrB1 <: ArrIntN[B1], B2, ArrB <: ArrPairIntN[B1, ArrB1, B2, _]] extends BuilderArrPair[B1, ArrB1, B2, ArrB]
{ type BuffT <: BuffPairIntN[B1, B2, _]
  type B1BuffT <: BuffIntN[B1]

  /** Constructs the [[Arr]] class from an [[Array]][Int] object for the first components of the pairs and an [[Array]][B2] for the second
   *  components of the pairs. */
  def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): ArrB

  /** Constructs the [[BuffSequ]] class from an [[ArrayBuffer]][Int] object for the first components of the pairs and an [[ArrayBuffer]][B2] for the
   * second components of the pairs. */
  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = newElem.intForeach(buff.unsafeBuffer.append(_))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Int](length), new ArrayBuffer[B2](length))
  final override def buffToSeqLike(buff: BuffT): ArrB = arrFromArrays(buff.b1IntBuffer.toArray, buff.b2Buffer.toArray)
  final override def arrFromBuffs(b1Buff: B1BuffT, b2Buffer: ArrayBuffer[B2]): ArrB = arrFromArrays(b1Buff.toArray, b2Buffer.toArray)
}

/** Builder for [[PairArrIntN]] objects via the map f: A => PairB method. */
trait BuilderArrPairIntNMap[B1 <: IntNElem, ArrB1 <: ArrIntN[B1], B2, B <: PairIntNElem[B1, B2], ArrB <: ArrPairIntN[B1, ArrB1, B2, B]] extends
  BuilderArrPairIntN[B1, ArrB1, B2, ArrB] with BuilderArrPairMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairIntN[B1, B2, B]

  /** The number of [[Int]]s required to construct the first component of the pairs. */
  def a1IntNum: Int

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  final override def arrFromArrAndArray(b1Arr: ArrB1, b2s: Array[B2]): ArrB = arrFromArrays(b1Arr.arrayUnsafe, b2s)

  def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): BuffT
  final override def uninitialised(length: Int): ArrB = arrFromArrays(new Array[Int](length * a1IntNum), new Array[B2](length))
  inline final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.grow(newElem)
}

/** Builder for [[ArrPairIntN]] objects via the flatMap f: A => ArrPairB method. */
trait BuilderArrPairIntNFlat[B1 <: IntNElem, ArrB1 <: ArrIntN[B1], B2, ArrB <: ArrPairIntN[B1, ArrB1, B2, _]] extends
  BuilderArrPairIntN[B1, ArrB1, B2, ArrB] with BuilderArrPairFlat[B1, ArrB1, B2, ArrB]
{
  final override def buffGrowArr(buff: BuffT, arr: ArrB): Unit = { arr.a1ArrayInt.foreach(buff.b1IntBuffer.append(_))
    arr.a2Array.foreach(buff.b2Buffer.append(_)) }
}