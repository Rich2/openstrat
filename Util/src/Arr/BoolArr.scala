/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

trait BoolSeqLike extends Any with SeqLike[Boolean]
{ type ThisT <: BoolSeqLike
  def unsafeArray: Array[Boolean]

  /** Constructs a new instance of the final type / class from an [[Array]][Boolean]. */
  def fromArray(array: Array[Boolean]): ThisT

  override final def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeArray(i) = value

  def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Boolean => String = _.toString
}

trait BoolSeqSpec extends Any with BoolSeqLike with SeqSpec[Boolean]
{ override final def ssLength: Int = unsafeArray.length
  override final def ssIndex(index: Int): Boolean = unsafeArray(index)
}

/** An immutable efficient Array[Boolean] backed sequence class for [[Boolean]]s. */
final class BoolArr(val unsafeArray: Array[Boolean]) extends AnyVal with ArrNoParam[Boolean] with BoolSeqLike
{ type ThisT = BoolArr
  override def typeStr: String = "Booleans"
  override def fromArray(array: Array[Boolean]): BoolArr = new BoolArr(array)
  override def length: Int = unsafeArray.length
  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Boolean](length))

  override def drop(n: Int): BoolArr =
  { val nn = n.max0
    val newArray = new Array[Boolean]((length - n).max0)
    iUntilForeach(length - nn) { i => newArray(i) = unsafeArray(i + nn) }
    new BoolArr(newArray)
  }

  override def reverse: BoolArr =
  { val newArray = new Array[Boolean](length)
    iUntilForeach(0, length) { i => newArray(i) = unsafeArray(length - 1 - i) }
    new BoolArr(newArray)
  }

  override def append(op: BoolArr): BoolArr =
  { val newArray = new Array[Boolean](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new BoolArr(newArray)
  }

  override def apply(index: Int): Boolean = unsafeArray(index)
}

object BoolArr
{ def apply(input: Boolean*): BoolArr = new BoolArr(input.toArray)
  def ofLength(length: Int): BoolArr = new BoolArr(new Array[Boolean](length))
}

object BooleanArrBuilder extends ArrMapBuilder[Boolean, BoolArr] with ArrFlatBuilder[BoolArr]
{ type BuffT = BooleanBuff
  override def uninitialised(length: Int): BoolArr = new BoolArr(new Array[Boolean](length))
  override def indexSet(seqLike: BoolArr, index: Int, value: Boolean): Unit = seqLike.unsafeArray(index) = value
  override def newBuff(length: Int = 4): BooleanBuff = new BooleanBuff(new ArrayBuffer[Boolean](length))
  override def buffGrow(buff: BooleanBuff, value: Boolean): Unit = buff.unsafeBuffer.append(value)
  override def buffToSeqLike(buff: BooleanBuff): BoolArr = new BoolArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BooleanBuff, arr: BoolArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
}

class BooleanBuff(val unsafeBuffer: ArrayBuffer[Boolean]) extends AnyVal with Buff[Boolean]
{ override type ThisT = BooleanBuff
  override def typeStr: String = "BooleanBuff"
  override def apply(index: Int): Boolean = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeBuffer(i) = value
  override def fElemStr: Boolean => String = _.toString
  override def grow(newElem: Boolean): Unit = unsafeBuffer.append(newElem)
}