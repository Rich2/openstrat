/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An immutable Array based class for Doubles. */
class Dbls(val unsafeArray: Array[Double]) extends AnyVal with SeqImut[Double]
{ type ThisT = Dbls
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def sdLength: Int = unsafeArray.length
  override def length: Int = unsafeArray.length
  override def sdIndex(index: Int): Double = unsafeArray(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = unsafeArray(i) = value
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { unsafeArray.copyToArray(unsafeArray, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString
  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](sdLength + op.sdLength)
    unsafeArray.copyToArray(newArray)
    op.unsafeArray.copyToArray(newArray, sdLength)
    new Dbls(newArray)
  }
}

/** Companion object for the Dbls Array based class for Doubles, contains a repeat parameter factory method. */
object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)

  implicit val eqImplicit: EqT[Dbls] = (a1, a2) =>
    if(a1.sdLength != a2.sdLength) false
    else
    { var i = 0
      var acc = true
      while (i < a1.sdLength & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object DblsBuild extends ArrBuilder[Double, Dbls] with ArrFlatBuilder[Dbls]
{ type BuffT = DblsBuff
  override def newArr(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def arrSet(arr: Dbls, index: Int, value: Double): Unit = arr.unsafeArray(index) = value
  override def newBuff(length: Int = 4): DblsBuff = new DblsBuff(new ArrayBuffer[Double](length))
  override def buffGrow(buff: DblsBuff, value: Double): Unit = buff.unsafeBuffer.append(value)
  override def buffGrowArr(buff: DblsBuff, arr: Dbls): Unit = buff.unsafeBuffer.addAll(arr.unsafeArray)
  override def buffToBB(buff: DblsBuff): Dbls = new Dbls(buff.unsafeBuffer.toArray)
}

/** Compile time wrapped Buffer class for [[Double]]s, used to build[[Dbls]]. */
class DblsBuff(val unsafeBuffer: ArrayBuffer[Double]) extends AnyVal with SeqGen[Double]
{ override def typeStr: String = "DblsBuff"
  override def sdIndex(index: Int): Double = unsafeBuffer(index)
  override def sdLength: Int = unsafeBuffer.length
  override def length: Int = unsafeBuffer.length
  override def unsafeSetElem(i: Int, value: Double): Unit = unsafeBuffer(i) = value
  override def fElemStr: Double => String = _.toString
}