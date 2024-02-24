/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Khazakstan, depends on [[middleEast.Caspian]], [[middleEast.Persia]], [[SiberiaWest]] and [[Kyrgyyzstan]]. */
object Kazak extends EArea2("Kazak", 47 ll 60, desert)
{
  val bautino = degs(44.53, 50.24)
  val kendirliBay = degs(42.73, 52.74)
  val mangystau = degs(45.48, 52.78)
  val volodarsky = degs(46.39, 49.03)

  override val polygonLL: PolygonLL = PolygonLL(SiberiaWest.p75, SiberiaWest.p55, Kyrgyyzstan.issykKulWest, Kyrgyyzstan.north, Kyrgyyzstan.northWest, Kyrgyyzstan.p65,
    middleEast.Persia.north, middleEast.Caspian.persiaN, kendirliBay, bautino, mangystau, middleEast.Caspian.northEast, middleEast.Caspian.north,
    volodarsky, pEurope.Ukraine.caspianW, pEurope.Baltland.southEast)
}

object Himalayas extends EArea2("Himalayas", degs(32, 75), mtainOld)
{
  override val polygonLL: PolygonLL = PolygonLL(Xinjiang.south, Mongolia.southWest, India.indiaNE, India.kotdwar, Kyrgyyzstan.islamabad, Kyrgyyzstan.p20)
}

object Kyrgyyzstan extends EArea2("Kyrgyzstan", degs(47, 76), mtainOld)
{ val p10: LatLong = 39.377 ll 75.528
  val p20 = 37.381 ll 77.418
  val islamabad = 33.749 ll 73.19
  val p40 = 33.124 ll 69.584
  val kandahar = 31.607 ll 65.730
  val p65 = 35.367 ll 62.146
  val northWest = 42.802 ll 71.400
  val north = 42.787 ll 74.513
  val issykKulWest = 42.443 ll 76.196
  val southWest = 32.244 ll 62.256

  override val polygonLL: PolygonLL = PolygonLL(p10, p20, islamabad, p40, kandahar, southWest, p65, northWest, north, issykKulWest)
}