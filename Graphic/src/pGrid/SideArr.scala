/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class SideBooleans(val unsafeArr: Array[Boolean]) extends AnyVal
{
  def gridSetTrues(roords: Roords)(implicit grid: TileGrid): Unit = roords.foreach(r => unsafeArr(grid.sideIndex(r)) = true)
  def gridSetTrues(roords: Roord*)(implicit grid: TileGrid): Unit = roords.foreach(r => unsafeArr(grid.sideIndex(r)) = true)

  def gridMap[A, AA <: ArrBase[A]](f: (Roord, Boolean) => A)(implicit grid: TileGrid, build: ArrBuild[A, AA]): AA =
    grid.sidesMap(r => f(r, unsafeArr(grid.sideIndex(r))))
}