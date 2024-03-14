/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for Alaska. Depends on Nothing. */
object Alaska extends EArea2("Alaska", 66.33 ll -151.16, hillyTaiga)
{ val northEast: LatLong = 69.45 ll -141
  val yakut10: LatLong = 59.93 ll -141.03
  val susitnaMouth: LatLong = 61.25 ll -150.61

  val capeNewenham: LatLong = 58.57 ll -161.73
  val p27: LatLong = 58.649 ll -162.169
  val nunivak = 60.211 ll -167.348
  //val p30: LatLong = 60.50 ll -164.55
  val p33: LatLong = 61.60 ll -166.18
  val p37: LatLong = 63.239 ll -164.349
  val koyuk: LatLong = 64.93 ll -161.19
  val p40: LatLong = 64.65 ll -166.39
  val capeDouglas: LatLong = 65.0 ll -166.70
  val teller: LatLong = 65.26 ll -166.36
  val imurukMouth: LatLong = 65.23 ll -166.04
  val brevig: LatLong = 65.34 ll -166.50
  val west: LatLong = 65.66 ll -168.11
  val p60: LatLong = 66.549 ll -164.653
  val capeEspenberg: LatLong = 66.557 ll -163.609
  val p63: LatLong = 66.081 ll -163.772
  val kiwalik: LatLong = 66.022 ll -161.840
  val p67: LatLong = 67.126 ll -163.743
  val pointHope: LatLong = 68.347 ll -166.792
  val p69: LatLong = 68.881 ll -166.214
  val northWest: LatLong = 70.11 ll -161.87
  val p10: LatLong = 70.11 ll -143.20

  override def polygonLL: PolygonLL = PolygonLL(northEast, yakut10, susitnaMouth, AleutPenisula.northEast, AleutPenisula.kvichakMouth, capeNewenham, p27,
    nunivak, p33, p37, koyuk, p40, capeDouglas, teller, imurukMouth, brevig, west, p60, capeEspenberg, p63, kiwalik, p67, pointHope, p69, northWest, p10)
}

/** [[polygonLL]] graphical representation for Alaska / Aleutian Peninsula. Depends on Nothing. */
object AleutPenisula extends EArea2("Aleut Peninsula", 57.797 ll -156.728, hillyTundra)
{ val northEast: LatLong = 58.916 ll -153.337
  val marmot = 58.248 ll -151.800
  val chiniak = 57.618 ll -152.164
  val sitkinak = 56.535 ll -153.891
  val p20: LatLong = 57.18 ll -156.35
  val nikolski: LatLong = 52.88 ll -168.94
  val portHeiden: LatLong = 57.63 ll -157.69
  val kvichakMouth: LatLong = 58.87 ll -157.05

  override def polygonLL: PolygonLL = PolygonLL(northEast, marmot, chiniak, sitkinak, p20, nikolski, portHeiden, kvichakMouth)
}

  /** [[polygonLL]] graphical representation for St Lawrence Island. Depends on Nothing. */
object StLawrenceIsland extends EArea2("St Lawrence", 63.420 ll -170.218, tundra)
{
  val northWest: LatLong = 63.785 ll -171.742
  val savoonga: LatLong = 63.697 ll -170.484
  val east: LatLong = 63.296 ll -168.689
  val southEast: LatLong = 63.147 ll -168.868
  val south: LatLong = 62.940 ll -169.645
  val southWest: LatLong = 63.373 ll -171.740

  override def polygonLL: PolygonLL = PolygonLL(northWest, savoonga,east, southEast, south, southWest)
}