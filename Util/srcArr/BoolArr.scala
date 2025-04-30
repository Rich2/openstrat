/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, scala.collection.mutable.ArrayBuffer

trait BoolSeqLike extends Any, SeqLikeBacked[Boolean]
{ type ThisT <: BoolSeqLike
  def arrayUnsafe: Array[Boolean]

  /** Constructs a new instance of the final type / class from an [[Array]][Boolean]. */
  def fromArray(array: Array[Boolean]): ThisT

//  override final def setElemUnsafe(index: Int, newElem: Boolean): Unit = arrayUnsafe(index) = newElem

  def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Boolean => String = _.toString
}

trait BoolSeqSpec extends Any with BoolSeqLike with SeqSpec[Boolean]
{ override final def numElems: Int = arrayUnsafe.length
  override final def elem(index: Int): Boolean = arrayUnsafe(index)
}

/** An immutable efficient Array[Boolean] backed sequence class for [[Boolean]]s. */
final class BoolArr(val arrayUnsafe: Array[Boolean]) extends AnyVal, ArrNoParam[Boolean], BoolSeqLike
{ type ThisT = BoolArr
  override def typeStr: String = "Booleans"
  override def apply(index: Int): Boolean = arrayUnsafe(index)
  override def elem(index: Int): Boolean = arrayUnsafe(index)
  override def fromArray(array: Array[Boolean]): BoolArr = new BoolArr(array)
  override def length: Int = arrayUnsafe.length
  override def numElems: Int = arrayUnsafe.length
  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Boolean](length))
  override def mutateElemUnsafe(index: Int, f: Boolean => Boolean): Unit = { arrayUnsafe(index) = f(arrayUnsafe(index)) }

  /** Sets / mutates an element in the Arr at the given index. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def setElemUnsafe(index: Int, newElem: Boolean): Unit = ???

  /** append. Appends operand [[Boolean]] to this [[BoolArr]]. */
  @targetName("appendElem") override def +%(operand: Boolean): BoolArr =
  { val newArray = new Array[Boolean](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand
    new BoolArr(newArray)
  }

  @targetName("append") override def ++(op: BoolArr): BoolArr =
  { val newArray = new Array[Boolean](length + op.length)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, length)
    new BoolArr(newArray)
  }

  override def drop(n: Int): BoolArr =
  { val nn = n.max0
    val newArray = new Array[Boolean]((length - n).max0)
    iUntilForeach(length - nn) { i => newArray(i) = arrayUnsafe(i + nn) }
    new BoolArr(newArray)
  }

  override def reverse: BoolArr =
  { val newArray = new Array[Boolean](length)
    iUntilForeach(0, length) { i => newArray(i) = arrayUnsafe(length - 1 - i) }
    new BoolArr(newArray)
  }
}

object BoolArr
{ def apply(input: Boolean*): BoolArr = new BoolArr(input.toArray)
  def ofLength(length: Int): BoolArr = new BoolArr(new Array[Boolean](length))
}

object BooleanArrBuilder extends BuilderArrMap[Boolean, BoolArr], BuilderArrFlat[BoolArr]
{ type BuffT = BooleanBuff
  override def uninitialised(length: Int): BoolArr = new BoolArr(new Array[Boolean](length))
  override def indexSet(seqLike: BoolArr, index: Int, newElem: Boolean): Unit = seqLike.arrayUnsafe(index) = newElem
  override def newBuff(length: Int = 4): BooleanBuff = new BooleanBuff(new ArrayBuffer[Boolean](length))
  override def buffGrow(buff: BooleanBuff, newElem: Boolean): Unit = buff.bufferUnsafe.append(newElem)
  override def buffToSeqLike(buff: BooleanBuff): BoolArr = new BoolArr(buff.bufferUnsafe.toArray)
  override def buffGrowArr(buff: BooleanBuff, arr: BoolArr): Unit = arr.arrayUnsafe.foreach(el => buff.bufferUnsafe.append(el))
}

class BooleanBuff(val bufferUnsafe: ArrayBuffer[Boolean]) extends AnyVal, Buff[Boolean]
{ override type ThisT = BooleanBuff
  override def typeStr: String = "BooleanBuff"
  override def apply(index: Int): Boolean = bufferUnsafe(index)
  override def elem(index: Int): Boolean = bufferUnsafe(index)
  override def length: Int = bufferUnsafe.length
  override def numElems: Int = bufferUnsafe.length
  override def setElemUnsafe(index: Int, newElem: Boolean): Unit = bufferUnsafe(index) = newElem
  override def fElemStr: Boolean => String = _.toString
  override def grow(newElem: Boolean): Unit = bufferUnsafe.append(newElem)
  override def mutateElemUnsafe(index: Int, f: Boolean => Boolean): Unit = ( bufferUnsafe(index) = f(bufferUnsafe(index)) )
}