/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Long]]s. */
class Longs(val array: Array[Long]) extends AnyVal with ArrBase[Long]
{ type ThisT = Longs
  override def typeStr: String = "Longs"
  override def unsafeNew(length: Int): Longs = new Longs(new Array[Long](length))
  override def length: Int = array.length
  override def apply(index: Int): Long = array(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }
  override def fElemStr: Long => String = _.toString

  def ++ (op: Longs): Longs =
  { val newArray = new Array[Long](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Longs(newArray)
  }
}

object Longs
{ def apply(input: Long*): Longs = new Longs(input.toArray)
}

object LongsBuild extends ArrBuild[Long, Longs] with ArrFlatBuild[Longs]
{ type BuffT = LongsBuff
  override def newArr(length: Int): Longs = new Longs(new Array[Long](length))
  override def arrSet(arr: Longs, index: Int, value: Long): Unit = arr.array(index) = value
  override def newBuff(length: Int = 4): LongsBuff = new LongsBuff(new ArrayBuffer[Long](length))
  override def buffGrow(buff: LongsBuff, value: Long): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: LongsBuff, arr: Longs): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: LongsBuff): Longs = new Longs(buff.unsafeBuff.toArray)
}

class LongsBuff(val unsafeBuff: ArrayBuffer[Long]) extends AnyVal with ArrayLike[Long]
{ override def apply(index: Int): Long = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}