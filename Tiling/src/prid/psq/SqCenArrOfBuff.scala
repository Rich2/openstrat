/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[SqCen]] hex tile centre grid Arr of [[ArrayBuffer]]s corresponding to the centres of an [[SqGrid]] square tile grid. */
class SqCenArrOfBuff[A <: AnyRef](val unsafeArr: Array[ArrayBuffer[A]])
{ /** Appends value to the array buffer at the given location [[SqCen]] location. */
  def appendAt(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = appendAt(SqCen(y, c), value)

  /** Appends value to the array buffer at the given [[SqCen]] location. */
  def appendAt(sqCen: SqCen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(sqCen)).append(value)

  /** Foreach's over the [[sqCen]] and the corresponding [[ArrayBuffer]] value. */
  def foreach(f: (SqCen, AnyBuff[A]) => Unit)(implicit grid: SqGrid): Unit = grid.foreach{ r => f(r, new AnyBuff( unsafeArr(grid.arrIndex(r)))) }
}

/** Companion object for the square (centres) grid Array of [[ArrayBuffer]] classes. */
object SqCenArrOfBuff
{ /** Apply factory method, creates a new [[SqCenArrOfBuff]] a square grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArrOfBuff[A] =
  { val array = new Array[ArrayBuffer[A]](length)
    iUntilForeach(0, array.length)(array(_) = new ArrayBuffer[A])
    new SqCenArrOfBuff[A](array)
  }
}