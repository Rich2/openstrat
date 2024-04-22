/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import geom._, egrid._, prid._, phex._

/** An Earth grid system where the hex tiles are 80km across. A C scale of 20km. Maybe a single hrx or multiple hex grids. */
trait EGridMegaSys extends EGridSys
{ override val cScale: MetricLength = 250.kMetres
}

/** Grid systems for Megakm [[EGrid]]s. */
trait EGridMegaLongMulti extends EGridLongMulti with EGridMegaSys
{
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

object EGridMegaLongMulti
{
  def apply(rBottomCen: Int = 82, rTopCen: Int = 118, startLong: Int, endLong: Int): EGridMegaLongMulti = new EGridMegaLongMulti
  {
    override def grids: RArr[EGridLongFull] = startLong match
    { case sl if endLong > startLong => iToMap(sl, endLong)(i => EGridMegaLongFull(rBottomCen, rTopCen, i))
      case sl => {
        val len = endLong - startLong + 13
        iUntilMap(len)(i => EGridMegaLongFull(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: RArr[EGridLongMan] = ???

    override def gridsXSpacing: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }
}