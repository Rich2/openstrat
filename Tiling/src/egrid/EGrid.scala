/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, pEarth._, prid._, WTile._

/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, numTileRows: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrrRows(bottomTileRow, numTileRows, unsafeRowsArray)
{

}


/** A basic EGrid scenario, containing grid and basic terrain data. */
class EScenBasic(val eGrid: EGridMain, val terrs: HCenArr[WTile])