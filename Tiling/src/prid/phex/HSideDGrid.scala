/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

class HSideDGrid[A <: AnyRef](val unsafeArr: Array[A])
{

}

/** Boolean data corresponding to the sides of a hex grid, stored using an underlying Array[Boolean]. Thhese classes should be created, initalised and
 *  used using an [HGrid]] class. For convenience the [[HGrid]] is passed as an implicit parameter. */
final class HSideBoolDGrid(val unsafeArray: Array[Boolean]) extends AnyVal with BooleanSeqDef
{ override type ThisT = HSideBoolDGrid
  override def typeStr: String = "HSideBoolDGrid"
  override def fromArray(array: Array[Boolean]): HSideBoolDGrid = new HSideBoolDGrid(array)

  def apply(hs: HSide)(implicit gridSys: HGridSys): Boolean = unsafeArray(gridSys.sideArrIndex(hs))

  def truesForeach(f: HSide => Unit)(implicit gridSys: HGridSys): Unit = {
    var i = 0
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i) == true) f(hs)
      i += 1
    }
  }
  def truesMap[B, ArrB <: SeqImut[B]](f: HSide => B)(implicit gridSys: HGridSys, build: ArrBuilder[B, ArrB]): ArrB = truesMap(gridSys)(f)(build)

  def truesMap[B, ArrB <: SeqImut[B]](gridSys: HGridSys)(f: HSide => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToBB(buff)
  }

  def set(hs: HSide, value: Boolean)(implicit grid: HGridSys): Unit = { unsafeArray(grid.sideArrIndex(hs)) = value }
  def set(r: Int, c: Int, value: Boolean)(implicit grid: HGridSys): Unit = { unsafeArray(grid.sideArrIndex(r, c)) = value }
  def setTrues(hSides: HSideArr)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideArrIndex(r)) = true)
  def setTrues(hSides: HSide*)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideArrIndex(r)) = true)
  def setTruesInts(hSides: (Int, Int)*)(implicit grid: HGridSys): Unit = hSides.foreach(p => unsafeArray(grid.sideArrIndex(p._1, p._2)) = true)
}

object HSideBoolDGrid
{ def uniinitialised(length: Int) = new HSideBoolDGrid(new Array[Boolean](length))
}