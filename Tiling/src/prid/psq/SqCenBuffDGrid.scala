/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** An Array of [[ArrayBuffer]]s corresponding to (the centres of a) Hex tile Grid. */
class SqCenBuffDGrid[A <: AnyRef](val unsafeArr: Array[ArrayBuffer[A]])
{
  def appendAt(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = appendAt(SqCen(y, c), value)
  def appendAt(hCen: SqCen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(hCen)).append(value)
  def foreach(f: (SqCen, ArrayBuffer[A]) => Unit)(implicit grid: SqGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.arrIndex(r))) }
}

/** Companion object for the Hex (centres) grid Array of ArrayBuffer classes. */
object SqCenBuffDGrid
{ /** Apply factory method, creates a Hex Grid Array of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenBuffDGrid[A] =
  { val array = new Array[ArrayBuffer[A]](length)
    iUntilForeach(0, array.length)(array(_) = new ArrayBuffer[A])
    new SqCenBuffDGrid[A](array)
  }
}