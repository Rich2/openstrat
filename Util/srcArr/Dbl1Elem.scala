/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Double]]. These are used in [[ArrDbl1]] Array[Int] based collections. */
trait Dbl1Elem extends Any with DblNElem
{ def dbl1: Double
  def dblsEqual(that: Dbl1Elem): Boolean = dbl1 == that.dbl1
  override def dblForeach(f: Double => Unit): Unit = { f(dbl1) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]) : Unit = { buffer.append(dbl1) }
}

/** A specialised immutable sequence, flat Array[Double] based collection of a type of [[Dbl1Elem]]s. */
trait ArrDbl1[A <: Dbl1Elem] extends Any with ArrDblN[A]
{ final override def elemProdSize: Int = 1
  def newElem(dblValue: Double): A
  final override def length: Int = arrayUnsafe.length
  final override def apply(index: Int): A = newElem(arrayUnsafe(index))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe(index) = newElem.dbl1
  override def elemEq(a1: A, a2: A): Boolean = a1.dbl1 == a2.dbl1

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand.dbl1
    fromArray(newArray)
  }
}