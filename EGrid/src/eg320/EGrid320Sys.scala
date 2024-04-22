/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import geom._, egrid._, prid._, phex._

/** An Earth grid system where the hex tiles are 80km across. A C scale of 20km. Maybe a single hrx or multiple hex grids. */
trait EGrid320Sys extends EGridSys
{ override val cScale: MetricLength = 80.kiloMetres
}

/** Grid systems for 320km [[EGrid]]s. */
trait EGrid320LongMulti extends EGridLongMulti with EGrid320Sys
{
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}

/*object EGrid320LongMulti
{
  /*def apply(rBottomCen: Int, rTopCen: Int = 166, startLong: Int, endLong: Int): EGrid320LongMulti = new EGrid320LongMulti
  {
    override def grids: RArr[EGridLongFull] = startLong match
    { case sl if endLong > startLong => iToMap(sl, endLong)(i => EGrid320LongFull(rBottomCen, rTopCen, i))
      case sl =>
      { val len = endLong - startLong + 13
        iUntilMap(len)(i => EGrid320LongFull(rBottomCen, rTopCen, (i + startLong) %% 12))
      }
    }

    override val gridMans: RArr[EGridLongMan] = ???

    override def gridsXSpacing: Double = ???

    /** The longitude Int for the head grid. */
    override def headGridInt: Int = ???
  }*/
}*/