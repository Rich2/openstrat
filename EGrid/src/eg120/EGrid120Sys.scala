/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import egrid._, prid._, phex._

/** An Earth grid system where the hex tiles are 120km across. A C scale of 55km. Maybe a single hrx or multiple hex grids. */
trait EGrid120Sys extends EGridSys
{ override val cScale: Metres = 30.kMetres
}

/** Grid systems for 120km [[EGrid]]s. */
trait EGrid120LongMulti extends EGridLongMulti with EGrid120Sys
{
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object EGrid120LongMulti
{
  def apply(rBottomCen: Int = 370, rTopCen: Int = 376, startLong: Int, endLong: Int): EGrid120LongMulti = new EGrid120LongMulti
  {
    override def grids: RArr[EGridLongFull] = startLong match
    { case sl if endLong > startLong => iToMap(sl, endLong)(i => EGrid120LongFull(rBottomCen, rTopCen, i))
      case sl => {
        val len = endLong - startLong + 13
        iUntilMap(len)(i => EGrid120LongFull(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: RArr[EGridLongMan] = ???

    override def gridsXSpacing: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }
}