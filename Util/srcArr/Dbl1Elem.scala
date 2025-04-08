/* Copyright 2018-2% Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Double]]. These are used in [[ArrDbl1]] Array[Int] based collections. */
trait Dbl1Elem extends Any, DblNElem
{ def dbl1: Double
  def dblsEqual(that: Dbl1Elem): Boolean = dbl1 == that.dbl1
  override def dblForeach(f: Double => Unit): Unit = { f(dbl1) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]) : Unit = { buffer.append(dbl1) }
}

trait SeqLikeDbl1[A <: Dbl1Elem] extends Any, SeqLikeValueN[A]
{ override def elemEq(a1: A, a2: A): Boolean = a1.dbl1 == a2.dbl1
}

/** A specialised immutable sequence, flat Array[Double] based collection of a type of [[Dbl1Elem]]s. */
trait ArrDbl1[A <: Dbl1Elem] extends Any, ArrDblN[A], SeqLikeDbl1[A]
{ final override def elemProdSize: Int = 1
  def elemFromDbl(dblValue: Double): A  
  final override def apply(index: Int): A = elemFromDbl(arrayUnsafe(index))
  final override def elem(index: Int): A = elemFromDbl(arrayUnsafe(index))
  final override def length: Int = arrayUnsafe.length
  final override def numElems: Int = arrayUnsafe.length
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe(index) = newElem.dbl1
  

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand.dbl1
    fromArray(newArray)
  }
}