/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** Immutable Array based class for Floats. */
class Floats(val unsafeArray: Array[Float]) extends AnyVal with SeqImut[Float]
{ type ThisT = Floats

  /** Copy's the backing Array[[Int]] to a new Array[Int]. End users should rarely have to use this method. */
  override def unsafeSameSize(length: Int): Floats = new Floats(new Array[Float](length))

  override def typeStr: String = "Floats"
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def sdIndex(index: Int): Float = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Float): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Float], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Float => String = _.toString

  def ++ (op: Floats): Floats =
  { val newArray = new Array[Float](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new Floats(newArray)
  }
}

object Floats
{ def apply(input: Float*): Floats = new Floats(input.toArray)


  implicit val eqImplicit: EqT[Floats] = (a1, a2) =>
    if(a1.sdLength != a2.sdLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.sdLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object FloatsBuild extends ArrBuilder[Float, Floats] with ArrFlatBuilder[Floats]
{ type BuffT = FloatsBuff
  override def newArr(length: Int): Floats = new Floats(new Array[Float](length))
  override def arrSet(arr: Floats, index: Int, value: Float): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): FloatsBuff = new FloatsBuff(new ArrayBuffer[Float](length))
  override def buffGrow(buff: FloatsBuff, value: Float): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: FloatsBuff, arr: Floats): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: FloatsBuff): Floats = new Floats(buff.unsafeBuffer.toArray)
}

class FloatsBuff(val unsafeBuffer: ArrayBuffer[Float]) extends AnyVal with SeqGen[Float]
{ override def typeStr: String = "FloatsBuff"
  override def sdIndex(index: Int): Float = unsafeBuffer(index)
  override def sdLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Float): Unit = unsafeBuffer(i) = value
  override def fElemStr: Float => String = _.toString
}