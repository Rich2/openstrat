/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** A [[HGrider]] hex grid system of data buffers. An [[HCen]] hex tile centre grid of [[ArrayBuffer]]s corresponding to the centres of an [[HGrider]]
 *  hex tile grid system. */
class HCenBuffDGrid[A <: AnyRef](val unsafeArr: Array[Buff[A]])
{ /** Appends value to the array buffer at the given [[HCen]] location. */
  def appendAt(y: Int, c: Int, value: A)(implicit grider: HGrider): Unit = appendAt(HCen(y, c), value)

  /** Appends value to the array buffer at the given [[HCen]] location. */
  def appendAt(hCen: HCen, value: A)(implicit grider: HGrider): Unit = unsafeArr(grider.arrIndex(hCen)).append(value)

  /** Foreach's over the [[HCen]] and the corresponding [[ArrayBuffer]] value. */
  def foreach(f: (HCen, Buff[A]) => Unit)(implicit grider: HGrider): Unit = grider.foreach{ r => f(r, unsafeArr(grider.arrIndex(r))) }
}

/** Companion object for the hex (centres) grid Array of [[ArrayBuffer]] classes. */
object HCenBuffDGrid
{ /** Apply factory method, creates a new [[HCenBuffDGrid]] a hex grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HCenBuffDGrid[A] =
  { val array = new Array[Buff[A]](length)
    iUntilForeach(0, array.length)(array(_) = new Buff[A])
    new HCenBuffDGrid[A](array)
  }
}