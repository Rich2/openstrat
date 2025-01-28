/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom.*, pglobe.*, egrid.*, WTiles.*

/** A reference object for the Island of Borneo,rperesented by multiple [[PolygonLL]] */
object Borneo extends IslandPolys("Borneo", 743330.kilares)

/** [[PolygonLL]] graphical representation of the island of Borneo. 743330km². Depends on nothing. */
object BorneoNorth extends IslandPartPoly("Borneo north", 3.152 ll 115.749, hillyJungle)
{ override val island: IslandPolys = Borneo

  val north: LatLong = 6.99 ll 117.12
  val northEast: LatLong = 5.382 ll 119.241
  val lgangLgang: LatLong = 4.456 ll 118.751
  val borderEast: LatLong = 4.165 ll 117.906
  val tandjoengbatoe: LatLong = 2.282 ll 118.094
  val cenEast: LatLong = 1.022 ll 118.986
  val p40: LatLong = 1.043 ll 118.981
  val p42: LatLong = 0.809 ll 118.798
  val p50: LatLong =  0.757 ll 117.752

  val batangLuparMouth: LatLong = 1.512 ll 110.988
  val p70: LatLong = 2.798 ll 111.333
  val p75: LatLong = 3.268 ll 113.058
  val kulalaBaram: LatLong = 4.598 ll 113.973
  val p90: LatLong = 5.630 ll 115.593

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, lgangLgang, borderEast, tandjoengbatoe, p40, p42, p50, batangLuparMouth, p70, p75,
    kulalaBaram, p90)
}

/** [[PolygonLL]] graphical representation for south of the island of Borneo. Island total = 743330km². Depends on [[BorneoNorth]]. */
object BorneoSouth extends IslandPartPoly("Borneo south", -0.592 ll 113.541, hillyJungle)
{ override val island: IslandPolys = Borneo

  val p25: LatLong = -2.177 ll 116.589
  val southEast: LatLong = -4.03 ll 116.09
  val p45: LatLong = -4.172 ll 114.650
  val p52: LatLong = -3.525 ll 111.779
  val southWest: LatLong = -2.96 ll 110.29
  val p55: LatLong = -1.259 ll 109.398
  val p60: LatLong = 0.816 ll 108.841
  val nwSarawak: LatLong = 2.08 ll 109.64

  override val polygonLL: PolygonLL = PolygonLL(BorneoNorth.p50, p25, southEast, p45, p52, southWest, p55, p60, nwSarawak, BorneoNorth.batangLuparMouth)
}

/** [[PolygonLL]] graphical representation of the island of Sulawesi 186216.16km². Depends on nothing. */
object Sulawesi extends EarthPoly("Sulawesi", -2.16 ll 120.58, mtainJungle)
{ val northEast: LatLong = 1.67 ll 125.15
  val p5: LatLong = 0.479 ll 124.507
  val ambesia: LatLong = 0.52 ll 120.62
  val poso: LatLong = -1.42 ll 120.68
  val teku: LatLong = -0.76 ll 123.45
  val southWest: LatLong = -5.66 ll 122.78
  val nGulfBoni: LatLong = -2.61 ll 120.81
  val southEast: LatLong = -5.41 ll 119.38
  val northWest: LatLong = 0.72 ll 120.06
  val p92: LatLong = 1.324 ll 120.808

  override val polygonLL: PolygonLL = PolygonLL(northEast, p5, ambesia, poso, teku, southWest, nGulfBoni, southEast, northWest, p92)
}

/** [[polygonLL]] graphical representation of the island of Halmahera Java 124413km² + Bali 5780km². Depends on nothing. */
object Halmahera extends IslandPoly("Halmahera", 0.662 ll 127.976, hillyJungle)
{ val halmahera: Kilares = 17780.kilares
  val morotai: Kilares = 2336.6.kilares
  val bacan: Kilares = 2719.03.kilares

  override def area: Kilares = halmahera + morotai + bacan

  val morotaiNorth: LatLong = 2.645 ll 128.568
  val east: LatLong = 0.213 ll 128.895
  val p30: LatLong = 0.466 ll 127.928
  val southEast: LatLong = -0.904 ll 128.452
  val mandioli: LatLong = -0.753 ll 127.162
  val ternate = 0.808 ll 127.293

  override val polygonLL: PolygonLL = PolygonLL(morotaiNorth, east, p30, southEast, mandioli, ternate)
}