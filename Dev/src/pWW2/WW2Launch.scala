/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import geom._, pglobe._, pParse._, pgui._

/** Object for launching WW2 app. */
object WW2Launch extends GuiLaunchMore
{
  override def settingStr: String = "ww2"

  override def default: (CanvasPlatform => Any, String) = (cv => WWIIGuiOld(cv, WW1940Old, None, None), "World War II")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScale = sts.findSettingPosDbl("scale")
    val scale: Option[Length] = oScale.mapToOption(1.km * _)
    val latLong: EMon[LatLong] =sts.findSetting[LatLong]("latLong")
    (cv => WWIIGuiOld(cv, WW1940Old, scale, latLong.toOption), "World War II")
  }
}