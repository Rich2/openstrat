/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

trait BooleanSeqSpec extends Any with SeqSpec[Boolean]
{ type ThisT <: BooleanSeqSpec
  def unsafeArray: Array[Boolean]

  /** Constructs a new instance of the final type / class from an [[Array]][Boolean]. */
  def fromArray(array: Array[Boolean]): ThisT

  override final def ssLength: Int = unsafeArray.length
  override final def ssIndex(index: Int): Boolean = unsafeArray(index)
  override final def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeArray(i) = value
  def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Boolean](length))

  def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Boolean => String = _.toString
}

/** An immutable efficient Array[Boolean] backed sequence class for [[Boolean]]s. */
final class BooleanArr(val unsafeArray: Array[Boolean]) extends AnyVal with Arr[Boolean] with BooleanSeqSpec
{ type ThisT = BooleanArr
  override def typeStr: String = "Booleans"
  override def fromArray(array: Array[Boolean]): BooleanArr = new BooleanArr(array)

  override def length: Int = unsafeArray.length

  def ++ (op: BooleanArr): BooleanArr =
  { val newArray = new Array[Boolean](ssLength + op.ssLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, ssLength)
    new BooleanArr(newArray)
  }

  override def apply(index: Int): Boolean = unsafeArray(index)
}

object BooleanArr
{ def apply(input: Boolean*): BooleanArr = new BooleanArr(input.toArray)
  def ofLength(length: Int): BooleanArr = new BooleanArr(new Array[Boolean](length))
}

object BooleanArrBuilder extends ArrMapBuilder[Boolean, BooleanArr] with ArrFlatBuilder[BooleanArr]
{ type BuffT = BooleanBuff
  override def uninitialised(length: Int): BooleanArr = new BooleanArr(new Array[Boolean](length))
  override def indexSet(arr: BooleanArr, index: Int, value: Boolean): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): BooleanBuff = new BooleanBuff(new ArrayBuffer[Boolean](length))
  override def buffGrow(buff: BooleanBuff, value: Boolean): Unit = buff.unsafeBuffer.append(value)
  override def buffToBB(buff: BooleanBuff): BooleanArr = new BooleanArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: BooleanBuff, arr: BooleanArr): Unit = arr.unsafeArray.foreach(el => buff.unsafeBuffer.append(el))
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