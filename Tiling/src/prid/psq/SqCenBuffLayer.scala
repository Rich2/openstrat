/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** A [[SqCen]] square tile centre grid Arr of [[ArrayBuffer]]s corresponding to the centres of a [[SqGridSys]] square tile grid system. */
class SqCenBuffLayer[A <: AnyRef](val unsafeArr: Array[ArrayBuffer[A]])
{ /** Appends value to the array buffer at the given location [[SqCen]] location. */
  def appendAt(r: Int, c: Int, value: A)(implicit gridSys: SqGridSys): Unit = appendAt(SqCen(r, c), value)

  /** Appends value to the array buffer at the given [[SqCen]] location. */
  def appendAt(sqCen: SqCen, value: A)(implicit gridSys: SqGridSys): Unit = unsafeArr(gridSys.arrIndex(sqCen)).append(value)

  /** Foreach's over the [[sqCen]] and the corresponding [[ArrayBuffer]] value. */
  def foreach(f: (SqCen, TBuff[A]) => Unit)(implicit gridSys: SqGridSys): Unit = gridSys.foreach{ r => f(r, new TBuff( unsafeArr(gridSys.arrIndex(r)))) }
}

/** Companion object for the square (centres) grid Array of [[ArrayBuffer]] classes. */
object SqCenBuffLayer
{ /** Apply factory method, creates a new [[SqCenBuffLayer]] a square grid Arr of ArrayBuffers, all of length 0. */
  def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenBuffLayer[A] =
  { val array = new Array[ArrayBuffer[A]](length)
    iUntilForeach(array.length)(array(_) = new ArrayBuffer[A])
    new SqCenBuffLayer[A](array)
  }
}