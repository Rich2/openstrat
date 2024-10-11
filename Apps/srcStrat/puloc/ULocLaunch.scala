/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pgui._, pParse._

/** object to launch Unit locator  Gui. */
object ULocLaunch extends GuiLaunchMore
{
  override def settingStr: String = "uloc"
  override def default: (CanvasPlatform => Any, String) = (cv => ULocGui.apply(cv, MTime(1939, 9, 15)), "JavaFx Unit Locations")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    def multisett: EarthView =
    { val scale = sts.findSettingElseOld[Double]("scale", 2)
      val lat: Double = sts.findSettingElseOld("latitude", 50)
      val long: Double = sts.findSettingElseOld("longitude", 10)
      EarthView(lat ll long, scale.kiloMetres, true)
    }

    val view: EarthView = sts.findTypeOld[EarthView].getElse(multisett)
    val oDate: EMonOld[MTime] = sts.findSettingOrUniqueTOld[MTime]("date")
    val date = oDate.getElse(MTime(1930, 9, 15))
    (cv => ULocGui(cv, date, view), "JavaFx Unit Locations")
  }
}