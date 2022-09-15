/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom._, pglobe._

trait EGrid80Sys extends EGridSys
{ override val cScale: Length = 20.kMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid80Long(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridLongFull(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid80Sys

/** object for creating 80km hex scale earth grids. */
object EGrid80
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 540): EGrid80Long = new EGrid80Long(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid80Long = new EGrid80Long(rBottomCen, rTopCen, 1)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0b446: EGrid80Long = new EGrid80Long(446, 540, 0)
  def l30b446: EGrid80Long = new EGrid80Long(446, 540, 1)

  def scen0: EScenWarm =
  { val grid: EGrid80Long = e0(446)
    EScenWarm(grid, Terr80E0.terrs, Terr80E0.sTerrs, "80km 0E")
  }

  def scen1: EScenWarm =
  { val grid: EGrid80Long = e30(446)
    EScenWarm(grid, Terr80E30.terrs, Terr80E30.sTerrs)
  }
}

trait EGrid80WarmMulti extends EGridWarmMulti with EGrid80Sys