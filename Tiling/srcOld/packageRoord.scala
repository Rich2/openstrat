/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, pGrid._

package object proord
{
  /** Not sure about the use of List in this class. */
  implicit class TilesListImplicit[A](thisRefs: TilesArr[List[A]])
  { def prepend(y: Int, c: Int, value: A)(implicit grid: TileGridOld): Unit = prepend(Roord(y, c), value)
    def prepend(roord: Roord, value: A)(implicit grid: TileGridOld): Unit = thisRefs.unsafeArr(grid.arrIndex(roord)) ::= value
    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }
  }
}
