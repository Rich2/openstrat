/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package middleEast
import geom._, pglobe._, LatLong._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Anatolia depends on [[pEurope.BalkansEast]] and [[AnatoliaSW]]. */
object AnatoliaNW extends EarthArea("Anatolia north-west", 39.469 ll 29.380, mtainSavannah)
{ val northEast: LatLong = 41.139 ll 31.342

  val p5: LatLong = 38.444 ll 26.950
  val hayirsizAda: LatLong = 38.713 ll 26.709
  val p60: LatLong = 38.76 ll 26.94
  val p65: LatLong = 39.27 ll 26.60
  val akcay = 39.58 ll 26.92
  val babakale: LatLong = 39.48 ll 26.06

  val uzunkum: LatLong = 41.208 ll 30.267

  override val polygonLL: PolygonLL = LinePathLL(northEast, LakeEgirdir.north, LakeEgirdir.northWest, LakeEgirdir.p85, AnatoliaSW.northWest, p5, hayirsizAda,
    p60, p65, akcay, babakale, pEurope.BalkansEast.seddElBahr) ++< pEurope.MarmaraSea.southCoast |++| LinePathLL(pEurope.BalkansEast.bosphorusN, uzunkum)
}

/** [[PolygonLL]] graphic for south-west Anatolia. depends on nothing. */
object AnatoliaSW extends EarthArea("Anatolia south-west", 37.468 ll 29.176, mtainOceForest)
{ val antalya: LatLong = 36.885 ll 30.702
  val p15: LatLong = 36.801 ll 30.576
  val p20: LatLong = 36.23 ll 30.42
  val p25: LatLong = 36.17 ll 29.69
  val p28: LatLong = 36.70 ll 28.63
  val p32: LatLong = 36.58 ll 27.99
  val bodrum: LatLong = 37.06 ll 27.35
  val mycale: LatLong = 37.686 ll 27.017
  val p36: LatLong = 37.723 ll 27.232
  val p60: LatLong = 37.839 ll 27.236
  val p65: LatLong = 38.035 ll 26.871
  val p67: LatLong = 38.171 ll 26.777
  val p72: LatLong = 38.104 ll 26.591
  val p80: LatLong = 38.27 ll 26.23
  val p85: LatLong = 38.66 ll 26.36
  val p94: LatLong = 38.367 ll 26.824

  val northWest: LatLong = 38.447 ll 27.170

  override val polygonLL: PolygonLL = PolygonLL(LakeEgirdir.p85, LakeEgirdir.beydere, LakeEgirdir.southEast, antalya, p15, p20, p25, p28, p32, bodrum, mycale,
    p36, p60, p65, p67, p72, p80, p85, p94, northWest
  )
}

/** [[PolygonLL]] graphic for Lake Van depends on nothing. */
object LakeEgirdir extends EarthArea("LakeEgirdir", 38.055 ll 30.884, Lake)
{ val north: LatLong = 38.279 ll 30.874
  val east: LatLong = 38.004 ll 30.968
  val southEast: LatLong = 37.845 ll 30.888
  val beydere: LatLong = 37.930 ll 30.775
  val p85: LatLong = 38.144 ll 30.751
  val northWest: LatLong = 38.243 ll 30.770

  override val polygonLL: PolygonLL = PolygonLL(north, east, southEast, beydere, p85, northWest)
}

/** [[PolygonLL]] graphic for Anatolia depends on [[pEurope.BalkansEast]]. */
object AnatoliaCentral extends EarthArea("Anatolia central", 39.00 ll 32.50, hillySavannah)
{ val northEast: LatLong = 41.27 ll 37.01
  val yukanbumaz: LatLong = 36.94 ll 36.04
  val p10: LatLong = 36.54 ll 35.34
  val delicayMouth: LatLong = 36.81 ll 34.72
  val p20: LatLong = 36.27 ll 33.98
  val anamurFeneri: LatLong = 36.02 ll 32.80
  val alanya = 36.54 ll 31.99

  val p70: LatLong = 41.13 ll 31.34
  val p75: LatLong = 41.32 ll 31.40
  val p77: LatLong = 41.72 ll 32.29
  val p85: LatLong = 42.01 ll 33.33
  val sinopeN: LatLong = 42.09 ll 34.99

  override val polygonLL: PolygonLL = PolygonLL(northEast, yukanbumaz, p10, delicayMouth, p20, anamurFeneri, alanya, AnatoliaSW.antalya, LakeEgirdir.southEast,
    LakeEgirdir.east, LakeEgirdir.north, p70, p75, p77, p85, sinopeN)
}

/** [[PolygonLL]] graphic for Lake Van depends on nothing. Sit on top of [[AnatoliaCentral]] */
object LakeTuz extends EarthArea("LakeTuz", 38.79 ll 33.56, Lake)
{ val northEast: LatLong = 39.12 ll 33.34
  val p10: LatLong = 38.99 ll 33.46
  val southEast: LatLong = 38.60 ll 33.49
  val south: LatLong = 38.58 ll 33.40
  val southWest: LatLong = 38.63 ll 33.29
  val west: LatLong = 38.80 ll 33.18

  override val polygonLL: PolygonLL = PolygonLL(northEast, p10, southEast, south, southWest, west)
}