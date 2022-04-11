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
    SqCen(r, cStart + (multiValues.numSingles - 1) * 2)
  }

  /** Note set RowBack starts with the r (row) parameter. */
  final def setRowBack(r: Int, cStart: Int, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  {
    multiValues.iForeachSingle{(i, el) =>
      val c = cStart - i * 2
      val index = grid.arrIndex(r, c)
      unsafeArray(index) = el
    }
    SqCen(r, cStart - (multiValues.numSingles - 1) * 2)
  }

  final def setRowBack(startCen: SqCen, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen = setRowBack(startCen.r, startCen.c, multiValues: _*)(grid)

  /** Set part column of data. */
  final def setColumn[ArrA <: SeqImut[A]](c: Int, rStart: Int, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  {
    multiValues.iForeachSingle{(i, el) =>
      val r: Int = rStart + i * 2
      val index = grid.arrIndex(r, c)
      unsafeArray(index) =  el
    }
    SqCen(rStart + (multiValues.numSingles - 1) * 2, c)
  }

  final def setColumn(startCen: SqCen, multis: Multiple[A]*)(implicit grid: SqGrid): SqCen = setColumn(startCen.c, startCen.r, multis: _*)(grid)

  final def setColumnDown(c: Int, rStart: Int, multiValues: Multiple[A]*)(implicit grid: SqGrid): SqCen =
  {
    multiValues.iForeachSingle{(i, el) =>
      val r = rStart - i * 2
      val index = grid.arrIndex(r, c)
      unsafeArray(index) = el
    }
    SqCen(c, rStart - (multiValues.numSingles - 1) * 2)
  }

  def setColumnDown(startCen: SqCen, tileValues: Multiple[A]*)(implicit grid: SqGrid): SqCen = setColumnDown(startCen.c, startCen.r, tileValues: _*)(grid)

  def setTerrPath(startR: Int, startC: Int, value: A, dirns: Multiple[SqStepNear]*)(implicit grid: SqGrid): SqCen = setTerrPath(startR sc startC, value, dirns:_*)

  def setTerrPath(startCen: SqCen, value: A, dirns: Multiple[SqStepNear]*)(implicit grid: SqGrid): SqCen =
  {
    var curr = startCen

    dirns.foreach {
      case Multiple(SqStepRt, i) => curr = setRow(curr, value * i)
      case Multiple(SqStepLt, i) => curr = setRowBack(curr, value * i)
      case Multiple(SqStepUp, i) => curr = setColumn(curr, value * i)
      case Multiple(SqStepDn, i) => curr = setColumnDown(curr, value * i)
    }
    curr
  }

  /** Sets a rectangle of tiles to the same terrain type. */
  def setRect(yFrom: Int, yTo: Int, cFrom: Int, cTo: Int, tileValue: A)(implicit grid: SqGrid): Unit =
    ijToForeach(yFrom, yTo, 2)(cFrom, cTo, 2) { (y, c) => unsafeArray(grid.arrIndex(y, c)) =  tileValue }
}

object SqCenDGrid
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): SqCenDGrid[A] = new SqCenDGrid[A](new Array[A](length))
}