/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pGrid

class SideBooleans(val unsafeArr: Array[Boolean]) extends AnyVal
{
  def gridSetTrues(roords: Roords)(implicit grid: TileGridOld): Unit = roords.foreach(r => unsafeArr(grid.sideArrIndex(r)) = true)
  def gridSetTrues(roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach(r => unsafeArr(grid.sideArrIndex(r)) = true)

  def gridMap[A, AA <: SeqImut[A]](f: (Roord, Boolean) => A)(implicit grid: TileGridOld, build: ArrBuilder[A, AA]): AA =
    grid.sidesMap(r => f(r, unsafeArr(grid.sideArrIndex(r))))
}