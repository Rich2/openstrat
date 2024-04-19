/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import egrid._, prid._, phex._

/** An Earth grid system where the hex tiles are 80km across. A C scale of 20km. Maybe a single hrx or multiple hex grids. */
trait EGrid460Sys extends EGridSys
{ override val cScale: Metres = 115.kMetres
}

/** Grid systems for 460km [[EGrid]]s. */
trait EGrid460LongMulti extends EGridLongMulti with EGrid460Sys
{
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object EGrid460LongMulti
{
  def apply(rBottomCen: Int = 130, rTopCen: Int = 160, startLong: Int, endLong: Int): EGrid460LongMulti = new EGrid460LongMulti
  {
    override def grids: RArr[EGridLongFull] = startLong match
    { case sl if endLong > startLong => iToMap(sl, endLong)(i => EGrid460LongFull(rBottomCen, rTopCen, i))
      case sl =>
      { val len = endLong - startLong + 13
        iUntilMap(len)(i => EGrid460LongFull(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: RArr[EGridLongMan] = ???

    override def gridsXSpacing: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }
}