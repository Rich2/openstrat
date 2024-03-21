/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for Alaska. Depends on Nothing. */
object AlaskaNorth extends EArea2("Alaska north", 66.33 ll -151.16, hillyTaiga)
{
  val south = 64.691.north

  val northEast: LatLong = 69.45 ll -141
  val southEast: LatLong = south ll -141
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

  val southWest: LatLong = south ll -160.788

  override def polygonLL: PolygonLL = PolygonLL(northEast, southEast, southWest, koyuk, p40, capeDouglas, teller, imurukMouth, brevig, west, p60, capeEspenberg, p63, kiwalik, p67,
    pointHope, p69, northWest, p10)
}

/** [[polygonLL]] graphical representation for south Alaska. Depends on Nothing. */
object AlaskaSouth extends EArea2("Alaska south", 63.261 ll -151.041, hillyTaiga)
{ val p5: LatLong = 59.93 ll -141.03
  val p10: LatLong = 60.013 ll -143.905
  val perlIsland = 59.104 ll -151.702
  val capeNewenham: LatLong = 58.57 ll -161.73
  val p27: LatLong = 58.649 ll -162.169
  val kuskokwimMouth: LatLong = 60.019 ll -162.347
  val p29: LatLong = 59.845 ll -164.135
  val p31: LatLong = 60.554 ll -165.411
  val p33: LatLong = 61.60 ll -166.18
  val p37: LatLong = 63.239 ll -164.349
  val stuart: LatLong = 63.633 ll -162.425
  val p38: LatLong = 63.502 ll -161.141
  
  override def polygonLL: PolygonLL = PolygonLL(AlaskaNorth.southEast, p5, p10, perlIsland, AleutPenisula.northEast, AleutPenisula.kvichakMouth, capeNewenham,
    p27, kuskokwimMouth, p29, p31, p33, p37, stuart, p38, AlaskaNorth.southWest)
}

/** [[polygonLL]] graphical representation for St Lawrence Island. Depends on Nothing. */
object StLawrenceIsland extends EArea2("St Lawrence", 63.420 ll -170.218, tundra)
{ val northWest: LatLong = 63.785 ll -171.742
  val savoonga: LatLong = 63.697 ll -170.484
  val east: LatLong = 63.296 ll -168.689
  val southEast: LatLong = 63.147 ll -168.868
  val south: LatLong = 62.940 ll -169.645
  val southWest: LatLong = 63.373 ll -171.740

  override def polygonLL: PolygonLL = PolygonLL(northWest, savoonga,east, southEast, south, southWest)
}

/** [[polygonLL]] graphical representation for Nunivak Island. Depends on Nothing. */
object Nunivak extends EArea2("Nunivak", 60.119 ll -166.351, hillyTaiga)
{ val north: LatLong = 60.439 ll -166.147
  val northEast: LatLong = 60.295 ll -165.692
  val southEast: LatLong = 59.923 ll -165.566
  val south: LatLong = 59.749 ll -166.154
  val southWest: LatLong = 60.069 ll -167.334
  val west: LatLong = 60.22 ll -167.458

  override def polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, southWest, west)
}

/** [[polygonLL]] graphical representation for Alaska / Aleutian Peninsula. Depends on Nothing. */
object AleutPenisula extends EArea2("Aleut Peninsula", 57.797 ll -156.728, hillyTundra)
{ val northEast: LatLong = 58.916 ll -153.337
  val marmot: LatLong = 58.248 ll -151.800
  val chiniak: LatLong = 57.618 ll -152.164
  val sitkinak: LatLong = 56.535 ll -153.891
  val p20: LatLong = 57.18 ll -156.35
  val nikolski: LatLong = 52.88 ll -168.94
  val portHeiden: LatLong = 57.63 ll -157.69
  val kvichakMouth: LatLong = 58.87 ll -157.05

  override def polygonLL: PolygonLL = PolygonLL(northEast, marmot, chiniak, sitkinak, p20, nikolski, portHeiden, kvichakMouth)
}

/** [[polygonLL]] graphical representation for Alaska / Aleutian Peninsula. Depends on Nothing. */
object KodiakIsland extends EArea2("Kodiak Island", 57.568 ll -153.276, mtainTaiga)
{
  val north = 58.631 ll -152.352
  override def polygonLL: PolygonLL = PolygonLL(north)
}