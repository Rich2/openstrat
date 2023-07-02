/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pgui._, pParse._

/** object to launch EarthBasic Gui. */
object ULocLaunch extends GuiLaunchMore
{
  override def settingStr: String = "uloc"
  override def default: (CanvasPlatform => Any, String) = (cv => ULocGui.apply(cv, MTime(1939)), "JavaFx Unit Locations")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    def multisett: EarthView =
    { val scale = sts.findSettingElse[Double]("scale", 10)
      val lat: Double = sts.findSettingElse("latitude", 40)
      val long: Double = sts.findSettingElse("longitude", 10)
      EarthView(lat ll long, scale * 1.km, true)
    }

    val view: EarthView = sts.findType[EarthView].getElse(multisett)
    val oDate: EMon[MTime] = sts.findSettingOrUniqueT[MTime]("date")
    debvar(oDate)
    val date = oDate.getElse(MTime(1939))
    (cv => ULocGui(cv, date, view), "JavaFx Unit Locations")
  }
}