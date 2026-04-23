/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pglobe.*, pgui.*, pParse.*

/** object to launch Unit locator  Gui. */
object ULocLaunch extends GuiLaunchMore
{ override def settingStr: String = "uloc"
  override def default: (CanvasPlatform => Any, String) = (cv => ULocGui.apply(cv, TimeMin(1939, 9, 15)), "JavaFx Unit Locations")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    def multisett: EarthView =
    { val scale: Double = sts.findSettingElse[Double]("scale", 2)
      val lat: Double = sts.findSettingElse("latitude", 50)
      val long: Double = sts.findSettingElse("longitude", 10)
      EarthView(lat ll long, scale.kilometres, true)
    }

    val view: EarthView = sts.findType[EarthView].getElse(multisett)
    val oDate: ErrBi[Exception, TimeMin] = sts.findSettingOrUniqueT[TimeMin]("date")
    val date: TimeMin = oDate.getElse(TimeMin(1930, 9, 15))
    (cv => ULocGui(cv, date, view), "JavaFx Unit Locations")
  }
}