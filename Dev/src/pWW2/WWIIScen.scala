/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWW2
import geom._, pglobe._,pEarth._, pgui._, pParse._

/** Primitive WWII scenario using ancient deprecated tile grid system. */
class WWIIScen extends EarthAllMap[W2TileAncient, W2SideAncient](W2TileAncient.apply, W2SideAncient.apply)
{
  val fArmy: (W2TileAncient, Polity) => Unit = (tile, p: Polity) => tile.lunits = Army(tile, p) +: tile.lunits
}

/** Object for launching WW2 app. */
object WW2Launch extends GuiLaunchMore
{
  override def settingStr: String = "ww2"

  override def default: (CanvasPlatform => Any, String) = (cv => WWIIGuiOld(cv, WW1940, None, None), "World War II")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScale = sts.findSettingT[Int]("scale")
    val scale: Option[Metres] = oScale.mapToOption(1.kmsOld * _)
    val oLat: EMon[Double] = sts.findSettingDbl("latitude")
    sts.foreach(println)
    debvar(oLat)
    val oLong: EMon[Double] = sts.findSettingDbl("longitude")
    debvar(oLong)
    val oll = oLat.flatMap2ToOption[Double, LatLong](oLong, (la, lo) => LatLong.degs(la, lo))
    (cv => WWIIGuiOld(cv, WW1940, scale, oll), "World War II")
  }
}

object WW1940 extends WWIIScen
{
  fTiles[Polity](fArmy, (212, 464, Germany), (216, 464, Germany), (210, 462, Britain), (218, 462, Germany), (214, 462, Britain), (216, 460, Britain),
      (218, 458, France))   
}