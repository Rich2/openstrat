/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import reflect.ClassTag

/** An [[HCen]] hex tile centre grid Arr of [[ArrayBuffer]]s corresponding to the centres of an [[HGrid]] hex tile grid. */
class HCenArrOfBuff[A <: AnyRef](val unsafeArr: Array[Buff[A]])
{
  def appendAt(y: Int, c: Int, value: A)(implicit grid: HGrid): Unit = appendAt(HCen(y, c), value)

  /** Appends value to the array buffer at the given location. */
  def appendAt(hCen: HCen, value: A)(implicit grid: HGrid): Unit = unsafeArr(grid.arrIndex(hCen)).append(value)

  /** Foreach's over the [[HCen]] and the corresponding [[ArrayBuffer]] value. */
  def foreach(f: (HCen, Buff[A]) => Unit)(implicit grid: HGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.arrIndex(r))) }
}

/** Companion object for the hex (centres) grid Array of [[ArrayBuffer]] classes. */
object HCenArrOfBuff
{ /** Apply factory method, creates a new [[HCenArrOfBuff]] a hex grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HCenArrOfBuff[A] =
  { val array = new Array[Buff[A]](length)
    iUntilForeach(0, array.length)(array(_) = new Buff[A])
    new HCenArrOfBuff[A](array)
  }
}

/** A [[SqCen]] hex tile centre grid Arr of [[ArrayBuffer]]s corresponding to the centres of an [[SqGrid]] square tile grid. */
class SqCenArrOfBuff[A <: AnyRef](val unsafeArr: Array[Buff[A]])

/** Companion object for the square (centres) grid Array of [[ArrayBuffer]] classes. */
object SqCenArrOfBuff
{ /** Apply factory method, creates a new [[SqCenArrOfBuff]] a square grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArrOfBuff[A] =
  { val array = new Array[Buff[A]](length)
    iUntilForeach(0, array.length)(array(_) = new Buff[A])
    new SqCenArrOfBuff[A](array)
  }
}