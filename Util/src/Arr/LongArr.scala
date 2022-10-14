/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for [[Long]]s. */
class LongArr(val unsafeArray: Array[Long]) extends AnyVal with SeqImut[Long]
{ type ThisT = LongArr

  /** Copy's the backing Array[[Long]] to a new Array[char]. End users should rarely have to use this method. */
  def unsafeArrayCopy(operand: Array[Long], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }

  override def typeStr: String = "Longs"
  override def unsafeSameSize(length: Int): LongArr = new LongArr(new Array[Long](length))
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def sdIndex(index: Int): Long = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Long): Unit = unsafeArray(i) = value
  override def fElemStr: Long => String = _.toString

  def ++ (op: LongArr): LongArr =
  { val newArray = new Array[Long](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new LongArr(newArray)
  }
}

object LongArr
{ def apply(input: Long*): LongArr = new LongArr(input.toArray)

  implicit val EqImplicit: EqT[LongArr] = (a1, a2) =>
    if(a1.sdLength != a2.sdLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.sdLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object LongsBuild extends ArrBuilder[Long, LongArr] with ArrFlatBuilder[LongArr]
{ type BuffT = LongsBuff
  override def newArr(length: Int): LongArr = new LongArr(new Array[Long](length))
  override def arrSet(arr: LongArr, index: Int, value: Long): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): LongsBuff = new LongsBuff(new ArrayBuffer[Long](length))
  override def buffGrow(buff: LongsBuff, value: Long): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: LongsBuff, arr: LongArr): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: LongsBuff): LongArr = new LongArr(buff.unsafeBuffer.toArray)
}

class LongsBuff(val unsafeBuffer: ArrayBuffer[Long]) extends AnyVal with Sequ[Long]
{ override def typeStr: String = "LongsBuff"
  override def sdIndex(index: Int): Long = unsafeBuffer(index)
  override def sdLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Long): Unit = unsafeBuffer(i) = value
  override def fElemStr: Long => String = _.toString

  /** The final type of this object. */
  override type ThisT = LongsBuff

  /** This method should rarely be needed to be used by end users, but returns a new uninitialised [[SeqDef]] of the this [[SeqImut]]'s final type. */
  override def unsafeSameSize(length: Int): LongsBuff = ???
}