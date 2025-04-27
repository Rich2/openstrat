/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Long]]s. */
class LongArr(val unsafeArray: Array[Long]) extends AnyVal, ArrNoParam[Long]
{ type ThisT = LongArr

  /** Copy's the backing Array[[Long]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Longs"
  override def unsafeSameSize(length: Int): LongArr = new LongArr(new Array[Long](length)) 
  override def apply(index: Int): Long = unsafeArray(index)
  override def elem(index: Int): Long = unsafeArray(index)
  override def length: Int = unsafeArray.length
  override def numElems: Int = unsafeArray.length
  override def setElemUnsafe(index: Int, newElem: Long): Unit = unsafeArray(index) = newElem
  override def fElemStr: Long => String = _.toString

  @targetName("append") def ++(op: LongArr): LongArr =
  { val newArray = new Array[Long](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new LongArr(newArray)
  }


  /** append. Appends operand [[Long]] to this [[LongArr]]. */
  @targetName("appendElem") override def +%(operand: Long): LongArr =
  { val newArray = new Array[Long](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new LongArr(newArray)
  }

  override def reverse: LongArr =
  { val newArray = new Array[Long](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new LongArr(newArray)
  }

  override def drop(n: Int): LongArr =
  { val nn = n.max0
    val newArray = new Array[Long]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
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

object LongArrBuilder extends BuilderArrMap[Long, LongArr], BuilderArrFlat[LongArr]
{ type BuffT = LongBuff
  override def uninitialised(length: Int): LongArr = new LongArr(new Array[Long](length))
  override def indexSet(seqLike: LongArr, index: Int, newElem: Long): Unit = seqLike.unsafeArray(index) = newElem
  override def newBuff(length: Int = 4): LongBuff = new LongBuff(new ArrayBuffer[Long](length))
  override def buffGrow(buff: LongBuff, newElem: Long): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: LongBuff): LongArr = new LongArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: LongBuff, arr: LongArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class LongBuff(val unsafeBuffer: ArrayBuffer[Long]) extends AnyVal, Buff[Long]
{ override type ThisT = LongBuff
  override def typeStr: String = "LongsBuff"
  override def apply(index: Int): Long = unsafeBuffer(index)
  override def elem(index: Int): Long = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def numElems: Int = unsafeBuffer.length
  override def setElemUnsafe(index: Int, newElem: Long): Unit = unsafeBuffer(index) = newElem
  override def fElemStr: Long => String = _.toString
  override def grow(newElem: Long): Unit = unsafeBuffer.append(newElem)
}