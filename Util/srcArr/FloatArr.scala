/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Float]]s. */
class FloatArr(val unsafeArray: Array[Float]) extends AnyVal with ArrNoParam[Float]
{ type ThisT = FloatArr
  override def typeStr: String = "FloatArr"
  override def unsafeSameSize(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Float = unsafeArray(index)
  override def setElemUnsafe(i: Int, newElem: Float): Unit = unsafeArray(i) = newElem
  def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString

  /** append. Appends operand [[Float]] to this [[FloatArr]]. */
  @targetName("append") override def +%(operand: Float): FloatArr =
  { val newArray = new Array[Float](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = operand
    new FloatArr(newArray)
  }

  @targetName("appendArr") override def ++(op: FloatArr): FloatArr =
  { val newArray = new Array[Float](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new FloatArr(newArray)
  }

  override def drop(n: Int): FloatArr =
  { val nn = n.max0
    val newArray = new Array[Float]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
    new FloatArr(newArray)
  }

  override def reverse: FloatArr =
  { val newArray = new Array[Float](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new FloatArr(newArray)
  }
}

object FloatArr
{ def apply(input: Float*): FloatArr = new FloatArr(input.toArray)

  implicit val eqImplicit: EqT[FloatArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object FloatArrBuilder extends ArrMapBuilder[Float, FloatArr] with ArrFlatBuilder[FloatArr]
{ type BuffT = FloatBuff
  override def uninitialised(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def indexSet(seqLike: FloatArr, index: Int, elem: Float): Unit = seqLike.unsafeArray(index) = elem
  override def newBuff(length: Int = 4): FloatBuff = new FloatBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatBuff, newElem: Float): Unit = buff.unsafeBuffer.append(newElem)
  override def buffToSeqLike(buff: FloatBuff): FloatArr = new FloatArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: FloatBuff, arr: FloatArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class FloatBuff(val unsafeBuffer: ArrayBuffer[Float]) extends AnyVal with Buff[Float]
{ override type ThisT = FloatBuff
  override def typeStr: String = "FloatsBuff"
  override def apply(index: Int): Float = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def setElemUnsafe(i: Int, newElem: Float): Unit = unsafeBuffer(i) = newElem
  override def fElemStr: Float => String = _.toString
  override def grow(newElem: Float): Unit = unsafeBuffer.append(newElem)
}