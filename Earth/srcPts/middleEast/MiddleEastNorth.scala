/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package middleEast
import geom._, pglobe._, LatLong._, WTile._

/** [[PolygonLL]] graphic for Kurdistan depends on [[Anatolia]]. */
object Kurdistan extends EArea2("Kurdistan", 39.36 ll 41.75, forest)
{ val p70 = 41.10 ll 39.42
  val surmene = 40.91 ll 40.12
  val cizre = 37.30 ll 42.15
  val delicaymouth  = 36.83 ll 36.17

  override val polygonLL: PolygonLL = PolygonLL(p70, surmene, cizre, delicaymouth, Anatolia.yukanbumaz, Anatolia.northEast)
}

/** [[PolygonLL]] graphic for Anatolia depends on [[pEurope.Balkans]]. */
object Anatolia extends EArea2("Anatolia", 39.00 ll 32.50, hills)
{ val northEast = 41.27 ll 37.01
  val bandirama = 40.35 ll 27.96
  val yukanbumaz = 36.94 ll 36.04
  val anamurFeneri = 36.02 ll 32.80
  val p50 = 36.17 ll 29.69
  val bodrum = 37.06 ll 27.35
  val babakale = 39.48 ll 26.06
  val darica = 40.76 ll 28.36
  val p70 = 41.13 ll 31.34
  val p75 = 41.32 ll 31.40
  val p77 = 41.72 ll 32.29
  val p85 = 42.01 ll 33.33
  val sinopeN = 42.09 ll 34.99

  override val polygonLL: PolygonLL = PolygonLL(northEast, yukanbumaz, anamurFeneri, p50, bodrum, babakale, pEurope.Balkans.seddElBahr,
    pEurope.Balkans.dardanellesE, bandirama, darica, pEurope.Balkans.istanbul, pEurope.Balkans.bosphorusN, p70, p75, p77, p85, sinopeN)
}

object Caucasus extends EArea2("Caucasus", 42.0 ll 45.0, hills)
{ val blackSeaE = 41.84 ll 41.77
  val p60 = 42.74 ll 41.44
  val p70 = 44.53 ll 38.09
  val p72 = 44.95 ll 37.29
  val p75 = 45.11 ll 36.73
  val p77 = 45.20 ll 36.60
  val llich = 45.41 ll 36.76

  val sumqayit = 40.64 ll 49.55
  val baku = 40.44 ll 50.21
  val sangachal = 40.18 ll 49.47
  val asiaMinorE = 50.03.east

  override val polygonLL: PolygonLL = PolygonLL(Kurdistan.surmene, blackSeaE, p60, p70, p72, p75, p77, llich, pEurope.Ukraine.rostov, pEurope.Ukraine.caspianW, sumqayit, baku,
    sangachal, Caspian.southWest, Kurdistan.cizre)
}

object Caspian extends EArea2("CaspianSea", degs (42.10, 50.64), sea)
{
  val north: LatLong = 47.05 ll 51.36
  val northEast: LatLong = 46.66 ll 53.03
  val persiaN: LatLong = 38.86 ll 53.99
  val southEast: LatLong = degs(36.92, 54.03)
  val southWest = 37.41 ll 50.03

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, persiaN, southEast, southWest)
}

object Iraq extends EArea2("Iraq", 34.0 ll 44.5, desert)
{ override val polygonLL: PolygonLL = PolygonLL(Levant.damascus, Kurdistan.cizre, Caspian.southWest, Persia.mahshahr, Arabia.alFaw)
}