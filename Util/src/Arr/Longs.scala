/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Long]]s. */
class Longs(val arrayUnsafe: Array[Long]) extends AnyVal with ArrBase[Long]
{ type ThisT = Longs

  /** Copy's the backing Array[[Long]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }

  override def typeStr: String = "Longs"
  override def unsafeSameSize(length: Int): Longs = new Longs(new Array[Long](length))
  override def elemsNum: Int = arrayUnsafe.length
  override def indexData(index: Int): Long = arrayUnsafe(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = arrayUnsafe(i) = value
  override def fElemStr: Long => String = _.toString

  def ++ (op: Longs): Longs =
  { val newArray = new Array[Long](elemsNum + op.elemsNum)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, elemsNum)
    new Longs(newArray)
  }
}

object Longs
{ def apply(input: Long*): Longs = new Longs(input.toArray)
}

object LongsBuild extends ArrBuilder[Long, Longs] with SeqFlatBuilder[Longs]
{ type BuffT = LongsBuff
  override def newArr(length: Int): Longs = new Longs(new Array[Long](length))
  override def arrSet(arr: Longs, index: Int, value: Long): Unit = arr.arrayUnsafe(index) = value
  override def newBuff(length: Int = 4): LongsBuff = new LongsBuff(new ArrayBuffer[Long](length))
  override def buffGrow(buff: LongsBuff, value: Long): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: LongsBuff, arr: Longs): Unit = buff.unsafeBuff.addAll(arr.arrayUnsafe)
  override def buffToArr(buff: LongsBuff): Longs = new Longs(buff.unsafeBuff.toArray)
}

class LongsBuff(val unsafeBuff: ArrayBuffer[Long]) extends AnyVal with SeqGen[Long]
{ override def indexData(index: Int): Long = unsafeBuff(index)
  override def elemsNum: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: Long): Unit = unsafeBuff(i) = value
  override def fElemStr: Long => String = _.toString
}