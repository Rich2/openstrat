/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package middleEast
import geom._, pglobe._, LatLong._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Kurdistan depends on [[Anatolia]]. */
object Kurdistan extends EarthArea("Kurdistan", 39.36 ll 40.00, hillyOce)
{ val p70: LatLong = 41.10 ll 39.42
  val yorosBurnu: LatLong = 41.107 ll 39.419
  val surmene: LatLong = 40.91 ll 40.12
  val cizre: LatLong = 37.30 ll 42.15
  val delicaymouth: LatLong = 36.83 ll 36.17

  override val polygonLL: PolygonLL = PolygonLL(p70, yorosBurnu, surmene, pEurope.RussiaSouth.blackSeaE, LakeVan.p85, LakeVan.p80, LakeVan.northWest,
    LakeVan.west, LakeVan.southEast, cizre, delicaymouth, Anatolia.yukanbumaz, Anatolia.northEast)
}

/** [[PolygonLL]] graphic for the Caucasus, depends on [[Kurdistan]] and [[pEurope.Ukraine]]. */
object Armenia extends EarthArea("Armenia", 40.0 ll 45.0, hillyOce)
{
  val asiaMinorE: Longitude = 50.03.east
  import pEurope.Caspian
  override val polygonLL: PolygonLL = PolygonLL(pEurope.RussiaSouth.blackSeaE, Caspian.sumqayit, Caspian.baku, Caspian.sangachal, Caspian.southWest,
    LakeVan.southEast, LakeVan.northEast, LakeVan.north, LakeVan.p85)
}

/** [[PolygonLL]] graphic for Lake Van depends on nothing. */
object LakeVan extends EarthArea("LakeVan", 38.62 ll 42.90, Lake)
{ val north: LatLong =  39.00 ll 43.39
  val northEast: LatLong = 38.94 ll 43.65
  val southEast: LatLong = 38.31 ll 43.12
  val west: LatLong = 38.51 ll 42.29
  val northWest: LatLong = 38.74 ll 42.45
  val p80: LatLong = 38.81 ll 43.10
  val p85: LatLong = 38.93 ll 43.06
  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, west, northWest, p80, p85)
}

/** [[PolygonLL]] graphic for Lake Van depends on nothing. Sit on top of [[Anatolia]] */
object LakeTuz extends EarthArea("LakeTuz", 38.79 ll 33.56, Lake)
{ val northEast: LatLong = 39.12 ll 33.34
  val p10: LatLong = 38.99 ll 33.46
  val southEast: LatLong = 38.60 ll 33.49
  val south: LatLong = 38.58 ll 33.40
  val southWest: LatLong = 38.63 ll 33.29
  val west: LatLong = 38.80 ll 33.18

  override val polygonLL: PolygonLL = PolygonLL(northEast, p10, southEast, south, southWest, west)
}

/** [[PolygonLL]] graphic for Anatolia depends on [[pEurope.BalkansEast]]. */
object Anatolia extends EarthArea("Anatolia", 39.00 ll 32.50, hillyOce)
{ val northEast: LatLong = 41.27 ll 37.01
  val yukanbumaz: LatLong = 36.94 ll 36.04
  val p10: LatLong = 36.54 ll 35.34
  val delicayMouth: LatLong = 36.81 ll 34.72
  val p20: LatLong = 36.27 ll 33.98
  val anamurFeneri: LatLong = 36.02 ll 32.80
  val alanya = 36.54 ll 31.99
  val p42: LatLong = 36.84 ll 30.61
  val p44: LatLong = 36.23 ll 30.42
  val p50: LatLong = 36.17 ll 29.69
  val p53: LatLong = 36.70 ll 28.63
  val p55: LatLong = 36.58 ll 27.99
  val bodrum: LatLong = 37.06 ll 27.35
  val p57 = 38.27 ll 26.23
  val p58 = 38.66 ll 26.36
  val p60: LatLong = 38.76 ll 26.94
  val p65: LatLong = 39.27 ll 26.60
  val akcay = 39.58 ll 26.92

  val babakale: LatLong = 39.48 ll 26.06

  val p70: LatLong = 41.13 ll 31.34
  val p75: LatLong = 41.32 ll 31.40
  val p77: LatLong = 41.72 ll 32.29
  val p85: LatLong = 42.01 ll 33.33
  val sinopeN: LatLong = 42.09 ll 34.99

  override val polygonLL: PolygonLL = PolygonLL(northEast, yukanbumaz, p10, delicayMouth, p20, anamurFeneri, alanya, p42, p44, p50, p53, p55, bodrum,
    p57, p58, p60, p65, akcay, babakale, pEurope.BalkansEast.seddElBahr, pEurope.MarmaraSea.dardanellesE, pEurope.MarmaraSea.bandirama, pEurope.MarmaraSea.darica,
    pEurope.MarmaraSea.istanbul, pEurope.BalkansEast.bosphorusN, p70, p75, p77, p85, sinopeN)
}


/** [[PolygonLL]] graphic for Iraq depends on [[Levant]]. */
object Iraq extends EarthArea("Iraq", 34.0 ll 44.5, deshot)
{ override def toString: String = "Iraq"

  import pMed.Sinai
  override val polygonLL: PolygonLL = PolygonLL(Levant.damascus, Kurdistan.cizre, LakeVan.southEast, pEurope.Caspian.southWest, Persia.mahshahr,
    ArabiaNorth.alFaw, Sinai.eilat, Sinai.deadSeaSE)
}

/** [[PolygonLL]] graphic for Persia. Depends on [[Caspian]] and [[pAsia.India]]. */
object Persia extends EarthArea("Persia", 32.4 ll 60, hillyOce)
{ val mahshahr: LatLong = 30.22.north * Armenia.asiaMinorE
  val north: LatLong = 38.285 ll 57.209
  val southEast: LatLong = 25.179 ll 61.618
  val p40: LatLong = 25.415 ll 59.030
  val kuhmobarak: LatLong = 25.80 ll 57.30
  val nwHormuz: LatLong = 27.131 ll 56.780
  val hormuzNorth: LatLong = 27.152 ll 56.557
  val larak = 26.859 ll 56.403
  val hengam = 26.611 ll 55.863
  val queshmSouth = 26.541 ll 55.889
  val hendorabi = 26.663 ll 53.639
  val zeydan: LatLong = 27.88 ll 51.50

  import pAsia.Kyrgyyzstan
  override val polygonLL: PolygonLL = LinePathLL(mahshahr) ++< pEurope.Caspian.persianCoast |++| LinePathLL(north, Kyrgyyzstan.p65, Kyrgyyzstan.southWest,
    southEast, p40,  kuhmobarak, nwHormuz, hormuzNorth, larak, hengam, queshmSouth, hendorabi, zeydan)
}