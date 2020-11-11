/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class HcenArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(hc: Hcen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))
  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }

}
object HcenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HcenArr[A] = new HcenArr[A](new Array[A](length))
}
