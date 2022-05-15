/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, prid._, phex._

trait EGrid320Sys extends EGridSys
{ override val cScale: Length = 80.kMetres
}

trait EGrid320MainMulti extends EGridMainMulti with EGrid320Sys {
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   * departure and the tile of arrival. */
  override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object EGrid320MainMulti{
  def apply(rBottomCen: Int = 138, rTopCen: Int = 160, startLong: Int, endLong: Int): EGrid320MainMulti = new EGrid320MainMulti
  {
    override def grids: Arr[EGridMain] = startLong match {
      case sl if endLong > startLong => iToMap(sl, endLong)(i => EGrid320Main(rBottomCen, rTopCen, i))
      case sl => {
        val len = endLong - startLong + 13
        iUntilMap(0, len)(i => EGrid320Main(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: Arr[EGridMainMan] = ???

    override def cGridDelta: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }
}