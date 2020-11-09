/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class HexArr[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length
  def apply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))
}
object HexArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HexArr[A] = new HexArr[A](new Array[A](length))
}

class HexBuffArr[A <: AnyRef](val unsafeArr: Array[Buff[A]])

object HexBuffArr
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HexBuffArr[A] =
  {
    val array = new Array[Buff[A]](length)
    ???
  }
}