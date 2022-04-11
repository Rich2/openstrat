/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** A [[SqGrider]] of immutable [[SqCen]] data, stored for efficiency as a flat [[Array]]. Most methods will rquire the [[SqGrider]] as an implicit
 * parameter. */
class SqCenDGrid[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TCenDGrid[A]
{
  def apply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArray(grid.arrIndex(sc))

  final def setColumn[ArrA <: SeqImut[A]](c: Int, rStart: Int, tileMakers: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  { tileMakers.iForeachSingle{(i, el) =>
      val r: Int = rStart + i * 2
      val index = grid.arrIndex(r, c)
      unsafeArray(index) =  el
    }
    SqCen(rStart + (tileMakers.sumBy(_.num) - 1) * 2, c)
  }
}

object SqCenDGrid
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenDGrid[A] = new SqCenDGrid[A](new Array[A](length))
}