/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pEarth._, prid._, phex._, WTile._

/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrr(bottomTileRow, unsafeRowsArray) with
  EGridSys
{

}

/** A basic EGrid scenario, containing grid and basic terrain data. */
class EScenBasic(val eGrid: EGridMainSys, val terrs: HCenDGrid[WTile])