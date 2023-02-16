/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, WTile._

/** [[PolygonLL]] graphic for the Sinai peninsular depends on nothing. */
object Sinai extends EArea2 ("Sinai", 29.88 ll 33.75, desert)
{ val eGaza = 31.32 ll 34.22
  val eilat = 29.54 ll 34.98
  val south = 27.73 ll 34.25
  val suez = 29.93 ll 32.56
  val portSaid = 31.27 ll 32.32

  override val polygonLL: PolygonLL = PolygonLL(eGaza, eilat, south, suez, portSaid)
}

/** Displays Arabian Peninsular. Depends on [[Anatolia]]. */
object Arabia extends EArea2 ("Arabia", degs (25, 45.0), desert)
{
  /** South West Turkey */
  val salwa = 24.71 ll 50.77
  val nQatar = 26.15 ll 51.26
  val doha = 25.25 ll 51.61
  val alGharbia = degs(23.97, 51.94)
  val icad = 24.30 ll 54.44
  val kumzar = 26.36 ll 56.38
  val alKhaburah = 23.98 ll 57.10
  val eOman = degs(22.48, 59.72)
  val mirbat = 16.94 ll 54.80
  val dhahawnMouth = 16.20 ll 52.23
  val haswayn = 15.63 ll 52.22
  val sYemen = degs(12.65, 43.98)
  val sharmas = degs(28.03, 35.23)
  val alFaw = degs(29.93, 48.47)

  override val polygonLL: PolygonLL = PolygonLL(pEurope.Anatolia.damascus, alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar, alKhaburah, eOman, mirbat,
    dhahawnMouth, haswayn, sYemen, sharmas, Sinai.eilat)
}

object Persia extends EArea2("Persia", 32.4 ll 60, hills)
{
  /** 38.86N */
  val persiaN = 38.86.north

  val mahshahr = 30.22.north * pEurope.Caucasus.asiaMinorE

  val persiaNE = persiaN * India.wAsiaE

  val seIran = degs(25.37, 61.67)
  val kuhmobarak = 25.80 ll 57.30
  val nHormuz = 27.17 ll 56.47
  val nwHormuz = 26.49 ll 54.79
  val zeydan = 27.88 ll 51.50
  override val polygonLL: PolygonLL = PolygonLL(mahshahr, Caspian.southWest, Caspian.southEast, Caspian.persiaN, persiaNE, India.mianiHor, seIran, kuhmobarak,
    nHormuz, nwHormuz, zeydan)
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
{ override val polygonLL: PolygonLL = PolygonLL(pEurope.Anatolia.damascus, pEurope.Anatolia.cizre, Caspian.southWest, Persia.mahshahr, Arabia.alFaw)
}

object Himalayas extends EArea2 ("Himalayas", degs (32, 75), mtain)
{
  override val polygonLL: PolygonLL = PolygonLL(CentralAsia.southEast, Persia.persiaNE, India.mianiHor, India.indiaNE)
}

object CentralAsia extends EArea2("CAsia", degs(47, 76), plain)
{
  val southEast = Persia.persiaN * 91.5.east//RusNorth.indiaE
  override val polygonLL: PolygonLL = PolygonLL(RusNorth.cAsiaNE, RusNorth.kazakNE, Persia.persiaNE, southEast)
}
