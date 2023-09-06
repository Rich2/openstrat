/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, pParse._

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchMore
{
  override def settingStr: String = "earth"
  override def default: (CanvasPlatform => Any, String) = (cv => EarthBasicGui.apply(cv), "JavaFx Earth")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    def multisett: EarthView = {
      val scale = sts.findSettingElse[Double]("scale", 10)
      val lat: Double = sts.findSettingElse("latitude", 40)
      val long: Double = sts.findSettingElse("longitude", 10)
      EarthView(lat ll long, scale * 1.km, true)
    }
    val view = sts.findType[EarthView].getElse(multisett)
    (cv => EarthBasicGui(cv, view), "JavaFx Earth")
  }
}