/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, prid._, phex._

trait EGrid320Sys extends EGridSys
{ override val cScale: Length = 80.kMetres
}

/** Grid systems for 320km [[EGrid]]s. */
trait EGrid320LongMulti extends EGridLongMulti with EGrid320Sys {
  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object EGrid320LongMulti
{
  def apply(rBottomCen: Int = 138, rTopCen: Int = 160, startLong: Int, endLong: Int): EGrid320LongMulti = new EGrid320LongMulti
  {
    override def grids: Arr[EGridLongFull] = startLong match {
      case sl if endLong > startLong => iToMap(sl, endLong)(i => EGrid320LongFull(rBottomCen, rTopCen, i))
      case sl => {
        val len = endLong - startLong + 13
        iUntilMap(len)(i => EGrid320LongFull(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: Arr[EGridWarmMan] = ???

    override def cGridDelta: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }
}