/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An immutable Array based class for Doubles. */
class Dbls(val array: Array[Double]) extends AnyVal with ArrBase[Double]
{ type ThisT = Dbls
  override def typeStr: String = "Doubles"
  override def unsafeNew(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def length: Int = array.length
  override def apply(index: Int): Double = array(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = array(i) = value
  override def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { array.copyToArray(array, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString
  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](length + op.length)
    array.copyToArray(newArray)
    op.array.copyToArray(newArray, length)
    new Dbls(newArray)
  }
}

object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)
}

object DblsBuild extends ArrBuild[Double, Dbls] with ArrFlatBuild[Dbls]
{ type BuffT = DblsBuff
  override def newArr(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def arrSet(arr: Dbls, index: Int, value: Double): Unit = arr.array(index) = value
  override def newBuff(length: Int = 4): DblsBuff = new DblsBuff(ArrayBuffer[Double](length))
  override def buffGrow(buff: DblsBuff, value: Double): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: DblsBuff, arr: Dbls): Unit = buff.unsafeBuff.addAll(arr.array)
  override def buffToArr(buff: DblsBuff): Dbls = new Dbls(buff.unsafeBuff.toArray)
}

class DblsBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with ArrayLike[Double]
{ override def apply(index: Int): Double = unsafeBuff(index)
  override def length: Int = unsafeBuff.length
}