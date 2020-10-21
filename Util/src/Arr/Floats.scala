/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Floats. */
class Floats(val array: Array[Float]) extends AnyVal with ArrBase[Float]
{ type ThisT = Floats
  override def typeStr: String = "Floats"
  override def unsafeNew(length: Int): Floats = new Floats(new Array[Float](length))
  override def elemsLen: Int = array.length
  override def apply(index: Int): Float = array(index)
  override def unsafeSetElem(i: Int, value: Float): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString


  def ++ (op: Floats): Floats =
  { val newArray = new Array[Float](elemsLen + op.elemsLen)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, elemsLen)
    new Floats(newArray)
  }
}

object Floats
{ def apply(input: Float*): Floats = new Floats(input.toArray)
}

object FloatsBuild extends ArrBuild[Float, Floats] with ArrFlatBuild[Floats]
{ type BuffT = FloatsBuff
  override def newArr(length: Int): Floats = new Floats(new Array[Float](length))
  override def arrSet(arr: Floats, index: Int, value: Float): Unit = arr.array(index) = value
  override def newBuff(length: Int = 4): FloatsBuff = new FloatsBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatsBuff, value: Float): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: FloatsBuff, arr: Floats): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: FloatsBuff): Floats = new Floats(buff.unsafeBuff.toArray)
}

class FloatsBuff(val unsafeBuff: ArrayBuffer[Float]) extends AnyVal with ArrayLike[Float]
{ override def apply(index: Int): Float = unsafeBuff(index)
  override def elemsLen: Int = unsafeBuff.length
}