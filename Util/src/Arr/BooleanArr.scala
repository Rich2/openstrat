/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

trait BooleanSeqDef extends Any with ImutSeqDef[Boolean]
{ type ThisT <: BooleanSeqDef
  def unsafeArray: Array[Boolean]

  /** Constructs a new instance of the final type / class from an [[Array]][Boolean]. */
  def fromArray(array: Array[Boolean]): ThisT

  override final def sdLength: Int = unsafeArray.length
  override final def sdIndex(index: Int): Boolean = unsafeArray(index)
  override final def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeArray(i) = value
  override final def unsafeSameSize(length: Int): ThisT = fromArray(new Array[Boolean](length))

  def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Boolean => String = _.toString
}

/** An immutable efficient Array[Boolean] backed sequence class for [[Boolean]]s. */
final class BooleanArr(val unsafeArray: Array[Boolean]) extends AnyVal with SeqImut[Boolean] with BooleanSeqDef
{ type ThisT = BooleanArr
  override def typeStr: String = "Booleans"
  override def fromArray(array: Array[Boolean]): BooleanArr = new BooleanArr(array)

  override def length: Int = unsafeArray.length

  def ++ (op: BooleanArr): BooleanArr =
  { val newArray = new Array[Boolean](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new BooleanArr(newArray)
  }
}

object BooleanArr
{ def apply(input: Boolean*): BooleanArr = new BooleanArr(input.toArray)
  def ofLength(length: Int): BooleanArr = new BooleanArr(new Array[Boolean](length))
}

object BooleansBuild extends ArrBuilder[Boolean, BooleanArr] with ArrFlatBuilder[BooleanArr]
{ type BuffT = BooleanBuff
  override def newArr(length: Int): BooleanArr = new BooleanArr(new Array[Boolean](length))
  override def arrSet(arr: BooleanArr, index: Int, value: Boolean): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): BooleanBuff = new BooleanBuff(new ArrayBuffer[Boolean](length))
  override def buffGrow(buff: BooleanBuff, value: Boolean): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: BooleanBuff, arr: BooleanArr): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: BooleanBuff): BooleanArr = new BooleanArr(buff.unsafeBuffer.toArray)
}

class BooleanBuff(val unsafeBuffer: ArrayBuffer[Boolean]) extends AnyVal with SeqGen[Boolean]
{ override def typeStr: String = "BooleanBuff"
  override def sdIndex(index: Int): Boolean = unsafeBuffer(index)
  override def sdLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeBuffer(i) = value
  override def fElemStr: Boolean => String = _.toString
}