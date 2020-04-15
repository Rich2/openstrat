/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class VertInts(val unsafeArr: Array[Int]) extends AnyVal
{
  def gridMap[A, AA <: ArrBase[A]](f: (Roord, Int) => A)(implicit grid: TileGrid, build: ArrBuild[A, AA]): AA =
    grid.sidesMap(r => f(r, unsafeArr(grid.sideIndex(r))))
}