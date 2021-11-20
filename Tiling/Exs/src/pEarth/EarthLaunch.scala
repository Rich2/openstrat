/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, pParse._


/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchMore
{
  override def settingStr: String = "earth"
  override def default: (CanvasPlatform => Any, String) = (cv => EarthBasicGui.apply(cv), "JavaFx Earth")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScale = sts.findSettingT[Int]("scale")
    val scale: Option[Length] = oScale.mapToOption(1.km * _)
    val oLat: EMon[Double] = sts.findSettingDbl("latitude")
    sts.foreach(println)
    debvar(oLat)
    val oLong: EMon[Double] = sts.findSettingDbl("longitude")
    debvar(oLong)
    val oll = oLat.flatMap2ToOption[Double, LatLong](oLong, (la, lo) => LatLong.degs(la, lo))
    (cv => EarthBasicGui(cv, scale, oll), "JavaFx Earth")
  }
}