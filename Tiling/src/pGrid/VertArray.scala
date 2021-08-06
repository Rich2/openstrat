/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

class VertInts(val unsafeArr: Array[Int]) extends AnyVal
{
  def gridMap[A, AA <: ArrBase[A]](f: (Roord, Int) => A)(implicit grid: TileGridOld, build: ArrBuilder[A, AA]): AA =
    grid.sidesMap(r => f(r, unsafeArr(grid.sideArrIndex(r))))

  def meth1(vertNum: Int): Pt2s = ???

}

