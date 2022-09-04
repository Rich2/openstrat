/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[HGridSys]] [[HCen]] layer of data buffers. An [[HCen]] hex tile centre grid of [[ArrayBuffer]]s corresponding to the centres of an
 *  [[HGridSys]] hex tile grid system. */
class HCenBuffLayer[A <: AnyRef](val unsafeArr: Array[ArrayBuffer[A]])
{ /** Appends value to the array buffer at the given [[HCen]] location. */
  def appendAt(y: Int, c: Int, value: A)(implicit grider: HGridSys): Unit = appendAt(HCen(y, c), value)

  /** Appends value to the array buffer at the given [[HCen]] location. */
  def appendAt(hCen: HCen, value: A)(implicit grider: HGridSys): Unit = unsafeArr(grider.arrIndex(hCen)).append(value)

  /** Foreach's over the [[HCen]] and the corresponding [[ArrayBuffer]] value. */
  def foreach(f: (HCen, ArrayBuffer[A]) => Unit)(implicit grider: HGridSys): Unit = grider.foreach{ r => f(r, unsafeArr(grider.arrIndex(r))) }
}

/** Companion object for the hex (centres) grid Array of [[ArrayBuffer]] classes. */
object HCenBuffLayer
{ /** Apply factory method, creates a new [[HCenBuffLayer]] a hex grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): HCenBuffLayer[A] =
  { val array = new Array[ArrayBuffer[A]](length)
    iUntilForeach(array.length)(array(_) = new ArrayBuffer[A])
    new HCenBuffLayer[A](array)
  }
}