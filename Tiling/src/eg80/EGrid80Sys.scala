/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom.pglobe._, prid.phex._

trait EGrid80Sys extends EGridSys
{ override val cScale: Length = 20.kMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid80Long(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid80Sys

class EGrid80LongPart(rBottomCen: Int, cenLongInt: Int, rArray: Array[Int]) extends
  EGridLong(rBottomCen, cenLongInt, 20.kMetres, 300, rArray)
{
  /** The latitude and longitude [[LatLong]] of an [[HCoord]] within the grid. */
  override def hCoordLL(hc: HCoord): LatLong = hCoordMiddleLL(hc)
}

/** object for creating 80km hex scale earth grids. */
object EGrid80
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 540): EGrid80Long = new EGrid80Long(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid80Long = new EGrid80Long(rBottomCen, rTopCen, 1)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0b446: EGrid80Long = new EGrid80Long(446, 540, 0)
  def l30b446: EGrid80Long = new EGrid80Long(446, 540, 1)



  def scen0: EScenBasic =
  { val grid: EGrid80Long = e0(446)
    EScenBasic(grid, Terr80E0.terrs, Terr80E0.sTerrs, Terr80E0.sTerrsDepr, Terr80E0.corners, "80km 0E")
  }

  def scen1: EScenBasic =
  { val grid: EGrid80Long = e30(446)
    EScenBasic(grid, Terr80E30.terrs, ???, Terr80E30.sTerrsDepr, Terr80E0.corners)
  }


}

trait EGrid80LongMulti extends EGridLongMulti with EGrid80Sys