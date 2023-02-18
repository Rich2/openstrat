/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package middleEast
import geom._, pglobe._, LatLong._, WTile._

object Anatolia extends EArea2("Anatolia", 39.46 ll 33.07, hills)
{ val sinopeN = 42.09 ll 34.99
  val bandirama = 40.35 ll 27.96
  val surmene = 40.91 ll 40.12
  val cizre = 37.30 ll 42.15
  val delicaymouth  = 36.83 ll 36.17
  val yukanbumaz = 36.94 ll 36.04
  val anamurFeneri = 36.02 ll 32.80
  val p50 = 36.17 ll 29.69
  val bodrum = 37.06 ll 27.35
  val babakale = 39.48 ll 26.06
  val darica = 40.76 ll 28.36

  override val polygonLL: PolygonLL = PolygonLL(sinopeN, surmene, cizre, delicaymouth, yukanbumaz, anamurFeneri, p50, bodrum, babakale,
    pEurope.Balkans.seddElBahr, pEurope.Balkans.dardanellesE, bandirama, darica, pEurope.Balkans.istanbul, pEurope.Balkans.bosphorusN)
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

  override val polygonLL: PolygonLL = PolygonLL(Anatolia.surmene, blackSeaE, p60, p70, p72, p75, p77, llich, pEurope.Ukraine.rostov, pEurope.Ukraine.caspianW, sumqayit, baku,
    sangachal, Caspian.southWest, Anatolia.cizre)
}

object Persia extends EArea2("Persia", 32.4 ll 60, hills)
{
  /** 38.86N */
  val persiaN = 38.86.north

  val mahshahr = 30.22.north * Caucasus.asiaMinorE

  val persiaNE = persiaN * pAsia.India.wAsiaE

  val seIran = degs(25.37, 61.67)
  val kuhmobarak = 25.80 ll 57.30
  val nHormuz = 27.17 ll 56.47
  val nwHormuz = 26.49 ll 54.79
  val zeydan = 27.88 ll 51.50
  override val polygonLL: PolygonLL = PolygonLL(mahshahr, Caspian.southWest, Caspian.southEast, Caspian.persiaN, persiaNE, pAsia.India.mianiHor,
    seIran, kuhmobarak, nHormuz, nwHormuz, zeydan)
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
{ override val polygonLL: PolygonLL = PolygonLL(Levant.damascus, Anatolia.cizre, Caspian.southWest, Persia.mahshahr, Arabia.alFaw)
}