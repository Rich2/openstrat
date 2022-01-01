/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An immutable Array based class for Doubles. */
class Dbls(val arrayUnsafe: Array[Double]) extends AnyVal with SeqImut[Double]
{ type ThisT = Dbls
  override def typeStr: String = "Doubles"
  override def unsafeSameSize(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def elemsNum: Int = arrayUnsafe.length
  override def indexData(index: Int): Double = arrayUnsafe(index)
  override def unsafeSetElem(i: Int, value: Double): Unit = arrayUnsafe(i) = value
  def unsafeArrayCopy(operand: Array[Double], offset: Int, copyLength: Int): Unit = { arrayUnsafe.copyToArray(arrayUnsafe, offset, copyLength); () }
  override def fElemStr: Double => String = _.toString
  def ++ (op: Dbls): Dbls =
  { val newArray = new Array[Double](elemsNum + op.elemsNum)
    arrayUnsafe.copyToArray(newArray)
    op.arrayUnsafe.copyToArray(newArray, elemsNum)
    new Dbls(newArray)
  }
}

/** Companion object for the Dbls Array based class for Doubles, contains a repeat parameter factory method. */
object Dbls
{ def apply(input: Double*): Dbls = new Dbls(input.toArray)

  implicit val eqImplicit: EqT[Dbls] = (a1, a2) =>
    if(a1.elemsNum != a2.elemsNum) false
    else
    { var i = 0
      var acc = true
      while (i < a1.elemsNum & acc) if (a1(i) == a2(i)) i += 1 else acc = false
      acc
    }
}

object DblsBuild extends ArrBuilder[Double, Dbls] with ArrFlatBuilder[Dbls]
{ type BuffT = DblsBuff
  override def newArr(length: Int): Dbls = new Dbls(new Array[Double](length))
  override def arrSet(arr: Dbls, index: Int, value: Double): Unit = arr.arrayUnsafe(index) = value
  override def newBuff(length: Int = 4): DblsBuff = new DblsBuff(ArrayBuffer[Double](length))
  override def buffGrow(buff: DblsBuff, value: Double): Unit = buff.unsafeBuff.append(value)
  override def buffGrowArr(buff: DblsBuff, arr: Dbls): Unit = buff.unsafeBuff.addAll(arr.arrayUnsafe)
  override def buffToBB(buff: DblsBuff): Dbls = new Dbls(buff.unsafeBuff.toArray)
}

class DblsBuff(val unsafeBuff: ArrayBuffer[Double]) extends AnyVal with SeqGen[Double]
{ override def typeStr: String = "DblsBuff"
  override def indexData(index: Int): Double = unsafeBuff(index)
  override def elemsNum: Int = unsafeBuff.length
  override def unsafeSetElem(i: Int, value: Double): Unit = unsafeBuff(i) = value
  override def fElemStr: Double => String = _.toString
}