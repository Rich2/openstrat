/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid.phex._

/** Hex tile grids for Earth with a hex scale of 320km, a C scale of 80km. */
package object eg320
{
  val fullTerrs: RArr[Long320Terrs] = RArr(Terr320E0, Terr320E30, Terr320E60, Terr320E90, Terr320E120, Terr320E150,Terr320E180,
    Terr320W150, Terr320W120, Terr320W90, Terr320W60, Terr320W30)

  def fullTerrsSubSideLayer(fromGrid: Int, toGrid: Int)(implicit subGrid: EGrid320LongMulti): HSideBoolLayer =
  { val arr = fullTerrs.indexMapTo[(HGrid, HSideBoolLayer), RArr[(HGrid, HSideBoolLayer)]](fromGrid, toGrid) { ft => (ft.grid, ft.sTerrs) }(new RArrAllBuilder[(HGrid, HSideBoolLayer)]())
    subGrid.sideBoolsFromPairs(arr)
  }
}