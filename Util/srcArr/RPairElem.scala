/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** R for stored by reference. The generalised default [[PairNoA1ParamElem]], that can be stored in the default unspecialised. Note although they are named as
 * reference types the A1 type parameter does not have to inherit from [[AnyRef]]. */
final class RPairElem[A1, A2](val a1: A1, val a2: A2) extends PairElem[A1, A2]

/** R for stored by reference. The generalised default [[PAirArr]] for types that do not have there own specialised classes. Note although they are
 *  named as reference [[PairArr]]s the A1 type parameter does not have to inherit from [[AnyRef]]. */
class RPairArr[A1, A2](val a1Array: Array[A1], val a2Array: Array[A2]) extends PairArr[A1, RArr[A1], A2, RPairElem[A1, A2]]
{ override type ThisT = RPairArr[A1, A2]
  override def typeStr: String = "RPairArr"
  override def a1Arr: RArr[A1] = new RArr[A1](a1Array)
  override def a1Index(index: Int): A1 = a1Array(index)
  override def setA1Unsafe(index: Int, value: A1): Unit = a1Array(index) = value
  override def apply(index: Int): RPairElem[A1, A2] = new RPairElem[A1, A2](a1Array(index), a2Array(index))

  def replaceA1byA2(key: A2, newValue: A1)(implicit ct1: ClassTag[A1]): RPairArr[A1, A2] =
  { val newA1Array = new Array[A1](length)
    var i = 0
    while(i < length){
      newA1Array(i) = ife(a2Array(i) == key, newValue, a1Array(i))
      i += 1
    }
    new RPairArr[A1, A2](newA1Array, a2Array)
  }

  /** Returns a new uninitialised [[RPairArr]]. */
  def uninitialised(length: Int)(implicit cl1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
    new RPairArr[A1, A2](new Array[A1](length), new Array[A2](length))

  @targetName("append") def +%(operand: RPairElem[A1, A2])(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
    appendPair(operand.a1, operand.a2)

  def appendPair(a1: A1, a2: A2)(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
  { val newA1Array = new Array[A1](length + 1)
    a1Array.copyToArray(newA1Array)
    newA1Array(length) = a1
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    new RPairArr(newA1Array, newA2Array)
  }

  override def setElemUnsafe(i: Int, newElem: RPairElem[A1, A2]): Unit = { a1Array(i) = newElem.a1; a2Array(i) = newElem.a2 }
  override def fElemStr: RPairElem[A1, A2] => String = _.toString

  def init(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] = dropRight(1)

  def dropRight(n: Int)(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
  { val newLen = (length - n).max0
    val newA1s = new Array[A1](newLen)
    val newA2s = new Array[A2](newLen)
    iUntilForeach(length - n){ i =>
      newA1s(i) = a1Array(i)
      newA2s(i) = a2Array(i)
    }
    new RPairArr[A1, A2](newA1s, newA2s)
  }
}

/** Companion object for [[RPairArr]], an un-specialised default [[Arr]] class for [[PairElem]]s contains factory methods for construction. */
object RPairArr
{ /** Factory apply method for constructing an [[RPairArr]] from the components of the pairs. */
  def apply[A1, A2](pairs: (A1, A2)*)(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
  { val len = pairs.length
    val a1s = new Array[A1](len)
    val a2s = new Array[A2](len)
    pairs.iForeach{(i, p) => a1s(i) = p._1; a2s(i) = p._2 }
    new RPairArr[A1, A2](a1s, a2s)
  }

  /** Factory method for constructing an [[RPairArr]] from [[PairElem]]s. */
  def pairs[A1, A2](pairs: PairElem[A1, A2]*)(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] = {
    val len = pairs.length
    val a1s = new Array[A1](len)
    val a2s = new Array[A2](len)
    pairs.iForeach { (i, p) => a1s(i) = p.a1; a2s(i) = p.a2 }
    new RPairArr[A1, A2](a1s, a2s)
  }

  def fromBuff[A1, A2](buff: RPairBuff[A1, A2])(implicit ct1: ClassTag[A1], ct2: ClassTag[A2]): RPairArr[A1, A2] =
    new RPairArr[A1, A2](buff.b1Buffer.toArray, buff.b2Buffer.toArray)
}

/** R for the first component of the [[PairNoA1ParamElem]] is stored by reference. [[Buff]] for [[RPairElem]]s. Note although they are named as
 *  reference types the A1 type parameter does not have to inherit from [[AnyRef]]. */
class RPairBuff[B1, B2](val b1Buffer: ArrayBuffer[B1], val b2Buffer: ArrayBuffer[B2]) extends PairBuff[B1, B2, RPairElem[B1, B2]]
{ override type ThisT = RPairBuff[B1, B2]
  override def typeStr: String = "GenPairBuff"
  override def pairGrow(b1: B1, b2: B2): Unit = { b1Buffer.append(b1); b2Buffer.append(b2) }
  override def grow(newElem: RPairElem[B1, B2]): Unit = { b1Buffer.append(newElem.a1); b2Buffer.append(newElem.a2) }
  override def apply(index: Int): RPairElem[B1, B2] = new RPairElem[B1, B2](b1Buffer(index), b2Buffer(index))
  override def setElemUnsafe(i: Int, newElem: RPairElem[B1, B2]): Unit = { b1Buffer(i) = newElem.a1; b2Buffer(i) = newElem.a2 }
}

object RPairBuff
{ /** Factory apply method for [[RPairBuff]] class. Creates an empty [[Buff]] class with a default buffer for expansion of 4 elements. */
  def apply[B1, B2](buffLen: Int = 4): RPairBuff[B1, B2] = new RPairBuff[B1, B2](new ArrayBuffer[B1](buffLen), new ArrayBuffer[B2](buffLen))
}

class RPairArrMapBuilder[B1, B2](implicit ct: ClassTag[B1]) extends PairArrMapBuilder[B1, RArr[B1], B2, RPairElem[B1, B2], RPairArr[B1, B2]]
{ override type BuffT = RPairBuff[B1, B2]
  override type B1BuffT = RBuff[B1]
  override def b1ArrBuilder: BuilderArrMap[B1, RArr[B1]] = BuilderArrMap.rMapImplicit

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. */
  override def arrFromArrAndArray(b1Arr: RArr[B1], b2s: Array[B2]): RPairArr[B1, B2] = new RPairArr[B1, B2](b1Arr.unsafeArray, b2s)

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  override def buffGrow(buff: RPairBuff[B1, B2], newElem: RPairElem[B1, B2]): Unit = ???

  /** Creates a new uninitialised [[Arr]] of type ArrB of the given length. */
  override def uninitialised(length: Int): RPairArr[B1, B2] = ???

  /** Sets the value in a [[SeqLike]] of type BB. This is usually used in conjunction with uninitialised method. */
  override def indexSet(seqLike: RPairArr[B1, B2], index: Int, elem: RPairElem[B1, B2]): Unit = ???

  /** ClassTag for building Arrays and ArrayBuffers of B2s. */
  override implicit def b2ClassTag: ClassTag[B2] = ???

  /** Constructs a new empty [[Buff]] for the B1 components of the pairs. */
  override def newB1Buff(): RBuff[B1] = ???

  /** Expands / appends the B1 [[Buff]] with a songle element of B1. */
  override def b1BuffGrow(buff: RBuff[B1], newElem: B1): Unit = ???

  /** Constructs an [[Arr]] of B from the [[Buff]]s of the B1 and B2 components. */
  override def arrFromBuffs(a1Buff: RBuff[B1], b2s: ArrayBuffer[B2]): RPairArr[B1, B2] = ???

  /** Creates a new empty [[Buff]] with a default capacity of 4 elements. */
  override def newBuff(length: Int): RPairBuff[B1, B2] = ???

  /** converts a the buffer type to the target compound class. */
  override def buffToSeqLike(buff: RPairBuff[B1, B2]): RPairArr[B1, B2] = ???
}