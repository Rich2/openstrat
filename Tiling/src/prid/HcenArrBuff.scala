/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid
import reflect.ClassTag

class HcenArrBuff[A <: AnyRef](val unsafeArr: Array[Buff[A]])
{
  def appendAt(y: Int, c: Int, value: A)(implicit grid: HGrid): Unit = appendAt(Hcen(y, c), value)
  def appendAt(hCen: Hcen, value: A)(implicit grid: HGrid): Unit = unsafeArr(grid.arrIndex(hCen)).append(value)
  def foreach(f: (Hcen, Buff[A]) => Unit)(implicit grid: HGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.arrIndex(r))) }
}

object HcenArrBuff
{
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HcenArrBuff[A] =
  {
    val array = new Array[Buff[A]](length)
    ???
  }
}