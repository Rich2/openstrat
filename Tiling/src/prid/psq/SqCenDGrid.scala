/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** A [[SqGrider]] of immutable [[SqCen]] data, stored for efficiency as a flat [[Array]]. Most methods will rquire the [[SqGrider]] as an implicit
 * parameter. */
class SqCenDGrid[A <: AnyRef](val unsafeArray: Array[A]) extends AnyVal with TCenDGrid[A]
{
  def apply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArray(grid.arrIndex(sc))

  /** Set tile row from the [[SqCen]]. */
  final def setRow(startCen: SqCen, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen = setRow(startCen.r, startCen.c, multiValues: _*)(grid)

  /** Note set Row starts with the r (row) parameter. */
  final def setRow(r: Int, cStart: Int, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  {
    multiValues.iForeachSingle { (i, e) =>
      val c = cStart + i * 2
      val dataI = grid.arrIndex(r, c)
      unsafeArray(dataI) = e
    }
    SqCen(r, cStart + (multiValues.sumBy(_.num) - 1) * 2)
  }

  /** Set part column of data. */
  final def setColumn[ArrA <: SeqImut[A]](c: Int, rStart: Int, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  { multiValues.iForeachSingle{(i, el) =>
      val r: Int = rStart + i * 2
      val index = grid.arrIndex(r, c)
      unsafeArray(index) =  el
    }
    SqCen(rStart + (multiValues.sumBy(_.num) - 1) * 2, c)
  }

  /*def setTerrPath(startRoord: SqCen, value: A, dirns: Multiple[SqStepNear]*)(implicit grid: SqGrid): SqCen =
  {
    var curr = startRoord

    dirns.foreach {
      case Multiple(Rt, i) => curr = setRow(curr, value * i)
      case Multiple(Lt, i) => curr = setRowBack(curr, value * i)
      case Multiple(Up, i) => curr = setColumn(curr, value * i)
      case Multiple(Dn, i) => curr = setColumnDown(curr, value * i)
    }
    curr
  }*/
}

object SqCenDGrid
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenDGrid[A] = new SqCenDGrid[A](new Array[A](length))
}