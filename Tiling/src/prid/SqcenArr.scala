/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class SqcenArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(sc: Sqcen)(implicit grid: SqGrid): A = unsafeArr(grid.arrIndex(sc))
  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }
}

object SqcenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqcenArr[A] = new SqcenArr[A](new Array[A](length))
}