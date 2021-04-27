/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Double]]. These are used in [[Dbl1sArr]] Array[Int] based collections. */
trait Dbl1Elem extends Any with DblNElem
{ def dbl1: Double
  def dblsEqual(that: Dbl1Elem): Boolean = dbl1 == that.dbl1
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl1Elem]]s. */
trait Dbl1sArr[A <: Dbl1Elem] extends Any with DblNsArr[A]
{ final override def elemProductNum: Int = 1
  def newElem(dblValue: Double): A
  final override def apply(index: Int): A = newElem(arrayUnsafe(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = arrayUnsafe(index) = elem.dbl1
}
