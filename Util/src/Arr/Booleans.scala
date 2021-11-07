/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An immutable efficient Array[Boolean] backed sequence class for [[Boolean]]s. */
class Booleans(val array: Array[Boolean]) extends AnyVal with SeqImut[Boolean]
{ type ThisT = Booleans

  /** Copy's the backing Array[[Boolean]] to a new Array[char]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): Booleans = new Booleans(new Array[Boolean](length))
  override def typeStr: String = "Booleans"

  override def elemsNum: Int = array.length
  override def indexData(index: Int): Boolean = array(index)
  override def unsafeSetElem(i: Int, value: Boolean): Unit = array(i) = value
  def unsafeArrayCopy(operand: Array[Boolean], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }
  override def fElemStr: Boolean => String = _.toString

  def ++ (op: Booleans): Booleans =
  { val newArray = new Array[Boolean](elemsNum + op.elemsNum)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, elemsNum)
    new Booleans(newArray)
  }
}

object Booleans
{ def apply(input: Boolean*): Booleans = new Booleans(input.toArray)
  def ofLength(length: Int): Booleans = new Booleans(new Array[Boolean](length))
}

object BooleansBuild extends ArrBuilder[Boolean, Booleans] with ArrFlatBuilder[Booleans]
{ type BuffT = BooleanBuff
  override def newArr(length: Int): Booleans = new Booleans(new Array[Boolean](length))
  override def arrSet(arr: Booleans, index: Int, value: Boolean): Unit = arr.array(index) = value
  override def newBuff(length: Int = 4): BooleanBuff = new BooleanBuff(new ArrayBuffer[Boolean](length))
  override def buffGrow(buff: BooleanBuff, value: Boolean): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: BooleanBuff, arr: Booleans): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToBB(buff: BooleanBuff): Booleans = new Booleans(buff.unsafeBuff.toArray)
}

class BooleanBuff(val unsafeBuff: ArrayBuffer[Boolean]) extends AnyVal with SeqGen[Boolean]
{ override def typeStr: String = "BooleanBuff"
  override def indexData(index: Int): Boolean = unsafeBuff(index)
  override def elemsNum: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: Boolean): Unit = unsafeBuff(i) = value
  override def fElemStr: Boolean => String = _.toString
}