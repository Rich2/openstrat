/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphical representation for Poland and adjacent territory. */
object Polandia extends EarthArea("Polandia", 50.07 ll 20.13, oceanic)
{ val mielno: LatLong = 54.26 ll 16.06
  val jaroslawiec: LatLong = 54.54 ll 16.53
  val jastrzebia: LatLong = 54.83 ll 18.33
  val wladyslawowo: LatLong = 54.79 ll 18.42
  val danzig: LatLong = 54.39 ll 18.65
  val p10: LatLong = 54.46 ll 19.64
  val capeTaran: LatLong = 54.96 ll 19.98

  /** 54.93 ll 21.26 */
  val kaliningrad: LatLong = 54.93 ll 21.26

  val cenEast: LatLong = 52 ll 24

  override val polygonLL: PolygonLL = PolygonLL(Germania.swinoujscie, mielno, jaroslawiec,
    jastrzebia, wladyslawowo, danzig, p10, capeTaran, kaliningrad, cenEast, BalkansEast.odessa,
    BalkansWest.northEast, Alpsland.zagreb, Alpsland.vienna)

  val stettin: LatLong = 53.43 ll 14.55
  val neidenburg: LatLong = 53.36 ll 20.43
  val schlochau: LatLong = 53.67 ll 17.36
  val allenstein: LatLong = 53.78 ll 20.48
  val osteroda: LatLong = 53.70 ll 19.96
}

/** [[PolygonLL]] graphical representation for the Baltic states and adjacent territory. */
object Baltland extends EarthArea("BaltLand", 56.46 ll 27.83, oceanic)
{ val klaipeda: LatLong = 55.73 ll 21.08
  val ziemupe: LatLong = 56.83 ll 21.06
  val osvalki = 57.047 ll 21.408
  val ovsi: LatLong = 57.57 ll 21.71
  val kolka: LatLong = 57.75 ll 22.60
  val meinsils: LatLong = 57.651 ll 22.582
  val mersrags = 57.366 ll 23.121
  val kesterclems: LatLong = 57.11 ll 23.24
  val jurmala: LatLong = 56.96 ll 23.66
  val saulkrasti: LatLong = 57.28 ll 24.41
  val parnu: LatLong = 58.37 ll 24.47
  val lao: LatLong = 58.23 ll 24.12
  val virtsu: LatLong = 58.56 ll 23.50
  val noarootsi: LatLong = 59.2 ll 23.5
  val paldiski: LatLong = 59.40 ll 24.04
  val udria: LatLong = 59.40 ll 27.92
  val krasnoselsky = 59.86 ll 30.14
  val piterland: LatLong = 59.97 ll 30.21
  val nevaMouth: LatLong = 59.95 ll 31.04
  val ladogaEast: LatLong = 60.66 ll 32.96
  val svirMouth: LatLong = 61.01 ll 35.49
   
  val onegaSouth: LatLong = 60.88 ll 35.67
  val ustye: LatLong = 61.19 ll 36.43
  val onegaEast: LatLong = 61.39 ll 36.47
  val peschanoyeSouth: LatLong = 62.04 ll 35.68
  val pudozhgorskiy: LatLong = 62.32 ll 35.86
  val chelmuzhiEast: LatLong = 62.53 ll 35.75
   
  /** Start of North coast. */
  val onezhsky: LatLong = 63.79 ll 37.35
  val onegaRiver: LatLong = 63.93 ll 37.99
  val pushlakhtaNorth: LatLong = 64.92 ll 36.44
  val letniyNavolok: LatLong = 65.16 ll 37.04
  val uyma: LatLong = 64.55 ll 39.71
  val niznyayaWest: LatLong = 65.58 ll 39.79
  val intsy: LatLong = 65.98 ll 40.75
  val koyda: LatLong = 66.51 ll 42.25
  val koydaEast: LatLong = 66.41 ll 43.24
  val mezenMouth: LatLong = 66.07 ll 44.10
  val southEast: LatLong = 52 ll 45
  val voronezh = 51.539 ll 39.147
   
  override val polygonLL: PolygonLL = PolygonLL(Polandia.kaliningrad, klaipeda, ziemupe, osvalki, ovsi, kolka, meinsils, mersrags, kesterclems, jurmala, saulkrasti, parnu, lao,
    virtsu, noarootsi, paldiski, udria, krasnoselsky, piterland, nevaMouth, ladogaEast, svirMouth, onegaSouth, ustye, onegaEast, peschanoyeSouth,
    pudozhgorskiy, chelmuzhiEast,
    /* North coast */onezhsky, onegaRiver, pushlakhtaNorth, letniyNavolok, uyma, niznyayaWest, intsy, koyda, koydaEast, mezenMouth,southEast, voronezh,
    Polandia.cenEast)

  val konigsberg: LatLong = 54.71 ll 20.45
  val slutsk = 53.03 ll 27.55
}

/** [[PolygonLL]] graphical representation for the island of Goland. */
object Gotland extends EarthArea("Gotland", 57.46 ll 18.47, oceanic)
{ val southWest: LatLong = 56.90 ll 18.12
  val west: LatLong = 57.26 ll 18.09
  val tofta: LatLong = 57.53 ll 18.10
  val hallshuk: LatLong = 57.92 ll 18.73
  val east: LatLong = 57.96 ll 19.35
   
  override val polygonLL: PolygonLL = PolygonLL(southWest, west, tofta, hallshuk, east)
}

/** [[PolygonLL]] graphical representation for the island of Saaremaa. */
object Saaremaa extends EarthArea("Saaremaa", 58.43 ll 22.52, oceanic)
{ val south: LatLong = 57.91 ll 22.03
  val uudibe: LatLong = 58.15 ll 22.21
  val west: LatLong = 58.03 ll 21.82
  val northWest: LatLong = 58.51 ll 21.91
  val nommkula: LatLong = 58.68 ll 23.19
  val loetsa: LatLong = 58.64 ll 23.34
  val east: LatLong = 58.55 ll 23.40
  val tehumardi: LatLong = 58.18 ll 22.25
   
  override val polygonLL: PolygonLL = PolygonLL(south, uudibe, west, northWest, nommkula, loetsa, east, tehumardi)
}

/** [[PolygonLL]] graphical representation for the island of Hiiumaa. */
object Hiiumaa extends EarthArea("Hiiumaa", 58.90 ll 22.63, oceanic)
{ val west: LatLong = 58.92 ll 22.04
  val north: LatLong = 59.08 ll 22.65
  val sarve: LatLong = 58.83 ll 23.05
  val southEast: LatLong = 58.70 ll 22.67
  val southWest: LatLong = 58.7 ll 22.49

  override val polygonLL: PolygonLL = PolygonLL(west, north, sarve, southEast, southWest)
}