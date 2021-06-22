/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Floats. */
class Floats(val arrayUnsafe: Array[Float]) extends AnyVal with ArrImut[Float]
{ type ThisT = Floats

  /** Copy's the backing Array[[Int]] to a new Array[Int]. End users should rarely have to use this method. */
  override def unsafeNew(length: Int): Floats = new Floats(new Array[Float](length))

  override def typeStr: String = "Floats"
  override def elemsLen: Int = arrayUnsafe.length
  override def apply(index: Int): Float = arrayUnsafe(index)
  override def unsafeSetElem(i: Int, value: Float): Unit = arrayUnsafe(i) = value
  def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString

  def ++ (op: Floats): Floats =
  { val newArray = new Array[Float](elemsLen + op.elemsLen)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, elemsLen)
    new Floats(newArray)
  }
}

object Floats
{ def apply(input: Float*): Floats = new Floats(input.toArray)
}

object FloatsBuild extends ArrTBuilder[Float, Floats] with ArrTFlatBuilder[Floats]
{ type BuffT = FloatsBuff
  override def newArr(length: Int): Floats = new Floats(new Array[Float](length))
  override def arrSet(arr: Floats, index: Int, value: Float): Unit = arr.arrayUnsafe(index) = value
  override def newBuff(length: Int = 4): FloatsBuff = new FloatsBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatsBuff, value: Float): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: FloatsBuff, arr: Floats): Unit = buff.unsafeBuff.addAll(arr.arrayUnsafe)
  override def buffToArr(buff: FloatsBuff): Floats = new Floats(buff.unsafeBuff.toArray)
}

class FloatsBuff(val unsafeBuff: ArrayBuffer[Float]) extends AnyVal with ArrayLike[Float]
{ override def apply(index: Int): Float = unsafeBuff(index)
  override def elemsLen: Int = unsafeBuff.length
}