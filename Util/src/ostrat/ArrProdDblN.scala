/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import collection.mutable.ArrayBuffer

/** Base trait for Array[Double] based collections of Products of Doubles. */
trait ArrProdDblN[A] extends Any with ArrProdHomo[A]
{ type ThisT <: ArrProdDblN[A]
  def array: Array[Double]
  def unsafeFromArray(array: Array[Double]): ThisT
  final override def buildThis(length: Int): ThisT = unsafeFromArray(new Array[Double](length * productSize))
  def arrLen = array.length
  //def toArrs: Arr[Arr[Double]]
  def foreachArr(f: Arr[Double] => Unit): Unit

  override def toString: String =
  { var body = ""
    var start = true
    foreachArr { arr =>
      val el = arr.toStrsCommaNoSpaceFold(_.toString)
      if(start == true) {body = el; start = false}
      else  body = body + ";  " + el
    }
    typeStr + body.enParenth
  }

  /** Builder helper method that provides a longer array, with the underlying array copied into the new extended Array.  */
  def appendArray(appendProductsLength: Int): Array[Double] =
  {
    val acc = new Array[Double](arrLen + appendProductsLength * productSize)
    array.copyToArray(acc)
    acc
  }
}

trait ArrBuffDblN[A, M <: ArrProdDblN[A]] extends Any with ArrBuffHomo[A, M]
{ def buffer: ArrayBuffer[Double]
  def toArray: Array[Double] = buffer.toArray[Double]
  def unBuff: M
  def append(newElem: A): Unit
  def addAll(newElems: M): Unit = { buffer.addAll(newElems.array); () }
}

/** Builds persists */
abstract class ArrHomoDblNBuilder[A, M <: ArrProdDblN[A]](typeStr: String) extends ArrHomoBuilder[A, M](typeStr) with Eq[M]
{ type VT = Double
  override def fromBuffer(buf: ArrayBuffer[Double]): M = fromArray(buf.toArray)
  override def newBuffer: ArrayBuffer[Double] = new ArrayBuffer[Double](0)
  //implicit val eqImplicit: Eq[M] = (m1, m2) => m1.array.eq(m2.array)
  override def eqv(m1: M, m2: M): Boolean = m1.array equ m2.array
}
