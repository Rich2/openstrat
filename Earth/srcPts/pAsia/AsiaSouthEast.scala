/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, WTile._

object Taiwan extends EArea2("Taiwan", 23.85 ll 120.99, hills)
{ val north = 25.29 ll 121.57
  val northEast = 25.01 ll 122.01
  val south = 21.90 ll 120.86
  val west = 23.10 ll 120.04
  val p10 = 25.05 ll 121.06

  override val polygonLL = PolygonLL(north, northEast, south, west, p10)
}

/** [[PolygonLL]] graphic for south east Asia */
object seAsia extends EArea2("SEAsia", 26.0 ll 104.0, plain)
{ val p5 = 38.15 ll 118.81
  val jiolaiMouth = 37.11 ll 119.57
  val p10 = 37.82 ll 120.75
  val p12 = 36.90 ll 122.52
  val rongcheng = degs(37.39, 122.69)
  val haitzhou = degs(34.95, 119.20)
  val putuo = degs(29.9, 122.34)
  val longhai = degs(24.26, 118.14)
  val hongKong = degs(22.44, 114.16)
  val xuwen = degs(20.24, 110.18)
  val yingzaiMouth = degs(21.45, 109.90)

  val neVietnamLong = 108.east
  val neVietnam = 21.54.north * neVietnamLong

  val eVietnam = degs(12.93, 109.37)
  val dienChau = degs(18.99, 105.56)
  val sVietnam = degs(8.68, 104.92)
  val bankok = degs(13.59, 100.39)
  val seMalaysia = degs(1.39, 104.25)
  val swMalaysia = degs(1.32, 103.47)
  val selekoh = degs(3.89, 100.73)
  val neMalayPen = degs(13.48, 98.45)
  val sittangMouth = degs(17.36, 96.89)
  val pathein = degs(16.17, 94.31)

  val chittagong = degs(22.74, 91.54)
  val seAsiaNE = India.mianiLat * neVietnamLong

  override val polygonLL = PolygonLL(CEAsia.binhai, p5, jiolaiMouth, p10, rongcheng, p12, haitzhou, putuo, longhai, hongKong, xuwen, yingzaiMouth, neVietnam,
    dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth, pathein, chittagong, India.magdhara,
    India.indiaNE,  CentralAsia.southEast)
}