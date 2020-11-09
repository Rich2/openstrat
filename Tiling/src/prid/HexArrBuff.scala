/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

class HexArrBuff[A <: AnyRef](val unsafeArr: Array[Buff[A]])
{
  def appendAt(y: Int, c: Int, value: A)(implicit grid: HGrid): Unit = appendAt(HCen(y, c), value)
  def appendAt(roord: HCen, value: A)(implicit grid: HGrid): Unit = unsafeArr(grid.arrIndex(roord)).append(value)
}

object HexArrBuff
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HexArrBuff[A] =
  {
    val array = new Array[Buff[A]](length)
    ???
  }
}