/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Float]]s. */
class FloatArr(val arrayUnsafe: Array[Float]) extends AnyVal, ArrNoParam[Float]
{ type ThisT = FloatArr
  override def typeStr: String = "FloatArr"
  override def unsafeSameSize(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def apply(index: Int): Float = arrayUnsafe(index)
  override def elem(index: Int): Float = arrayUnsafe(index)
  override def length: Int = arrayUnsafe.length
  override def numElems: Int = arrayUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Float): Unit = arrayUnsafe(index) = newElem
  def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString
  override def mutateElemUnsafe(index: Int, f: Float => Float): Unit = { arrayUnsafe(index) = f(arrayUnsafe(index)) }

  /** append. Appends operand [[Float]] to this [[FloatArr]]. */
  @targetName("appendElem") def +%(operand: Float): FloatArr =
  { val newArray = new Array[Float](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand
    new FloatArr(newArray)
  }

  @targetName("append") def ++(op: FloatArr): FloatArr =
  { val newArray = new Array[Float](length + op.length)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, length)
    new FloatArr(newArray)
  }

  override def drop(n: Int): FloatArr =
  { val nn = n.max0
    val newArray = new Array[Float]((length - nn).max0)
    iUntilForeach(length - nn) { i => newArray(i) = arrayUnsafe(i + nn) }
    new FloatArr(newArray)
  }

  override def reverse: FloatArr =
  { val newArray = new Array[Float](length)
    iUntilForeach(0, length) { i => newArray(i) = arrayUnsafe(length - 1 - i) }
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

object FloatArrBuilder extends BuilderArrMap[Float, FloatArr], BuilderArrFlat[FloatArr]
{ type BuffT = FloatBuff
  override def uninitialised(length: Int): FloatArr = new FloatArr(new Array[Float](length))
  override def indexSet(seqLike: FloatArr, index: Int, newElem: Float): Unit = seqLike.arrayUnsafe(index) = newElem
  override def newBuff(length: Int = 4): FloatBuff = new FloatBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatBuff, newElem: Float): Unit = buff.bufferUnsafe.append(newElem)
  override def buffToSeqLike(buff: FloatBuff): FloatArr = new FloatArr(buff.bufferUnsafe.toArray)
  override def buffGrowArr(buff: FloatBuff, arr: FloatArr): Unit = arr.arrayUnsafe.foreach(el => buff.bufferUnsafe.append(el))
}

class FloatBuff(val bufferUnsafe: ArrayBuffer[Float]) extends AnyVal, Buff[Float]
{ override type ThisT = FloatBuff
  override def typeStr: String = "FloatsBuff"
  override def apply(index: Int): Float = bufferUnsafe(index)
  override def elem(index: Int): Float = bufferUnsafe(index)
  override def length: Int = bufferUnsafe.length
  override def numElems: Int = bufferUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Float): Unit = bufferUnsafe(index) = newElem
  override def fElemStr: Float => String = _.toString
  override def grow(newElem: Float): Unit = bufferUnsafe.append(newElem)
  override def mutateElemUnsafe(index: Int, f: Float => Float): Unit = { bufferUnsafe(index) = f(bufferUnsafe(index)) }
}