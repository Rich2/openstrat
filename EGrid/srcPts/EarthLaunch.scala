/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, pParse._

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchMore
{ override def settingStr: String = "earth"
  override def default: (CanvasPlatform => Any, String) = (cv => EarthBasicGui.apply(cv), "JavaFx Earth")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  {
    def multisett: EarthView =
    { val scale = sts.findSettingElseOld[Double]("scale", 10)
      val lat: Double = sts.findSettingElseOld("latitude", 40)
      val long: Double = sts.findSettingElseOld("longitude", 10)
      EarthView(lat ll long, scale.kiloMetres, true)
    }
    val view = sts.findTypeOld[EarthView].getElse(multisett)
    (cv => EarthBasicGui(cv, view), "JavaFx Earth")
  }
}