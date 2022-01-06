/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** An array of hex tile or hex centre data. */
class SqCenArr[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TileCenArr[A]
{
  def apply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArray(grid.arrIndex(sc))
}

object SqCenArr
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenArr[A] = new SqCenArr[A](new Array[A](length))
}