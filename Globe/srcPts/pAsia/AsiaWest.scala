/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, WTile._

object RusNorth extends EArea2("NRus", 61 ll 54, taiga)
{
  val indiaE = 91.5.east
   
  /** North Coast */
  val chizhaSouth = 66.90 ll 44.52
  val chizhaWest = 67.19 ll 43.77
  val shoynaNorth1 = 68.30 ll 44.21
  val shoynaNorth2 = 68.66 ll 42.28
  val shoynaNorth3 = 68.53 ll 44.07
  val shoynaNorth4 = 68.47 ll 45.78
  val shoynaEast1 = 68.11 ll 46.52
  val shoynaEast2 = 67.81 ll 46.65
  val kiyaEast1 = 67.69 ll 45.32
  val chizhaEast1 = 67.33 ll 44.90
  val vizhas = 66.81 ll 45.96
  val amderma = 69.76 ll 61.68
  val nRusNE = 75.64.north * indiaE
   
  val cAsiaNE = 55.north * indiaE
  val cEuropeE = 51.36.east
  //val nRusSW = 55.north * cEuropeE
  val kazakNE = 55.north * India.wAsiaE

  override val polygonLL: PolygonLL = PolygonLL(pEurope.Baltland.mezenMouth, chizhaSouth, chizhaWest, shoynaNorth1, shoynaNorth2, shoynaNorth3, shoynaNorth4, shoynaEast1,
     shoynaEast2, kiyaEast1, chizhaEast1, vizhas, amderma, nRusNE, cAsiaNE, kazakNE, pEurope.Baltland.southEast)
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
    dhahawnMouth, haswayn, sYemen, sharmas, pEurope.Anatolia.eilat)
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

/** Visual display of Severny Island north of Russia. */
object SevernyIsland extends EArea2("Severny", 74.38 ll 57.29, ice)
{ val wSeverny = 71.81 ll 51.49
  val severny1 = 75.37 ll 57.03
  val severnyN = 76.99 ll 67.91
  val severny2 = 72.28 ll 55.36
  val eSeverny = 70.71 ll 57.59

  override val polygonLL: PolygonLL = PolygonLL(wSeverny, severny1, severnyN, severny2, eSeverny)
}

object Kazak extends EArea2 ("Kazak", 47 ll 60, plain)
{
  val bautino = degs(44.53, 50.24)
  val kendirliBay = degs(42.73, 52.74)
  val mangystau = degs(45.48, 52.78)
  val volodarsky = degs(46.39, 49.03)

  override val polygonLL: PolygonLL = PolygonLL(RusNorth.kazakNE, Persia.persiaNE, Caspian.persiaN, kendirliBay, bautino, mangystau,
  Caspian.northEast, Caspian.north, volodarsky, pEurope.Ukraine.caspianW, pEurope.Baltland.southEast)
}