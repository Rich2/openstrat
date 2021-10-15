/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class SqCenArr[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{ def length: Int = unsafeArr.length
  def apply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArr(grid.arrIndex(sc))
  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }
}

object SqCenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArr[A] = new SqCenArr[A](new Array[A](length))
}