/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An immutable Array based class for Doubles. */
class DblArr(val unsafeArray: Array[Double]) extends AnyVal with ArrSingle[Double]
{ type ThisT = DblArr
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def length: Int = unsafeArray.length
  override def apply(index: Int): Double = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString
  def ++ (op: DblArr): DblArr =
  { val newArray = new Array[Double](length + op.length)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, length)
    new DblArr(newArray)
  }
}

/** Companion object for the Dbls Array based class for Doubles, contains a repeat parameter factory method. */
object DblArr
{ def apply(input: Double*): DblArr = new DblArr(input.toArray)

  implicit val eqImplicit: EqT[DblArr] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var i = 0
      var acc = true
      while (i < a1.length & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object DblArrBuilder extends ArrMapBuilder[Double, DblArr] with ArrFlatBuilder[DblArr]
{ type BuffT = DblBuff
  override def arrUninitialised(length: Int): DblArr = new DblArr(new Array[Double](length))
  override def arrSet(arr: DblArr, index: Int, value: Double): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): DblBuff = new DblBuff(new ArrayBuffer[Double](length))
  override def buffGrow(buff: DblBuff, value: Double): Unit = buff.unsafeBuffer.append(value)
  override def buffToBB(buff: DblBuff): DblArr = new DblArr(buff.unsafeBuffer.toArray)
  override def buffGrowArr(buff: DblBuff, arr: DblArr): Unit = buff.grows(arr)
}

/** Compile time wrapped Buff class for [[Double]]s, used to build [[DblArr]]. */
class DblBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with Buff[Double]
{ override type ThisT = DblBuff
  override def typeStr: String = "DblsBuff"
  override def apply(index: Int): Double = unsafeBuffer(index)
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Double): Unit = unsafeBuffer(i) = value
  override def fElemStr: Double => String = _.toString
  override def grow(newElem: Double): Unit = unsafeBuffer.append(newElem)
}