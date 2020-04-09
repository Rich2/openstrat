package ostrat
package pGrid

class SideBooleans(val unsafeArr: Array[Boolean]) extends AnyVal
{
  def gridSetTrues(roords: Roords)(implicit grid: TileGrid): Unit = roords.foreach(r => unsafeArr(grid.sideIndex(r)) = true)

  def gridMap[A, AA <: Arr[A]](f: (Roord, Boolean) => A)(implicit  grid: TileGrid, build: ArrBuild[A, AA]): AA =
    grid.sidesMap(r => f(r, unsafeArr(grid.sideIndex(r))))
}