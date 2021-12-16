/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pParse._, pgui._, prid._, eg80._

/** A hex grid on the surface of the earth. */
abstract class EGrid(bottomTileRow: Int, numTileRows: Int, unsafeRowsArray: Array[Int], val cScale: Length) extends HGridIrrRows(bottomTileRow, numTileRows, unsafeRowsArray)
{

}


/** object to launch EGrid basic Gui. */
object EGridBasicLaunch extends GuiLaunchMore {
  override def settingStr: String = "E80Grid"

  override def default: (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) = (cv => EGridGui(cv, EGrid80Km.scen1), "JavaFx Earth")
}

/** A basic EGrid scenario, containing grid and basic terrain data. */
class EScenBasic(val eGrid: EGridMain)