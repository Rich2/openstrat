/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Long]]s. */
class LongArr(val unsafeArray: Array[Long]) extends AnyVal with ArrNonParam[Long]
{ type ThisT = LongArr

  /** Copy's the backing Array[[Long]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Longs"
  override def unsafeSameSize(length: Int): LongArr = new LongArr(new Array[Long](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Long = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = unsafeArray(i) = value
  override def fElemStr: Long => String = _.toString

  def ++ (op: LongArr): LongArr =
  { val newArray = new Array[Long](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new LongArr(newArray)
  }

  override def reverse: LongArr = ???

  override def tail: LongArr =
  { val newArray = new Array[Long]((length - 1).max0)
    iUntilForeach(1, length) { i => newArray(i - 1) = unsafeArray(i) }
    new LongArr(newArray)
  }
}

object LongArr
{ def apply(input: Long*): LongArr = new LongArr(input.toArray)

  implicit val EqImplicit: EqT[LongArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object LongArrBuilder extends ArrMapBuilder[Long, LongArr] with ArrFlatBuilder[LongArr]
{ type BuffT = LongBuff
  override def uninitialised(length: Int): LongArr = new LongArr(new Array[Long](length))
  override def indexSet(seqLike: LongArr, index: Int, value: Long): Unit = seqLike.unsafeArray(index) = value
  override def newBuff(length: Int = 4): LongBuff = new LongBuff(new ArrayBuffer[Long](length))
  override def buffGrow(buff: LongBuff, value: Long): Unit = buff.unsafeBuffer.append(value)
  override def buffToSeqLike(buff: LongBuff): LongArr = new LongArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: LongBuff, arr: LongArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class LongBuff(val unsafeBuffer: ArrayBuffer[Long]) extends AnyVal with Buff[Long]
{ override type ThisT = LongBuff
  override def typeStr: String = "LongsBuff"
  override def apply(index: Int): Long = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Long): Unit = unsafeBuffer(i) = value
  override def fElemStr: Long => String = _.toString
  override def grow(newElem: Long): Unit = unsafeBuffer.append(newElem)
}