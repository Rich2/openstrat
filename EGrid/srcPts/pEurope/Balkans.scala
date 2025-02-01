/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** Balkans polygon depends on [[Alpsland]] and [[GreeceNorth]]. */
object BalkansWest extends EarthPoly("BalkansWest", 44.0 ll 19.65, hillyCont) {
  val northEast: LatLong = 46.0 ll 22.59
  val shengjin: LatLong = 41.80 ll 19.59
  val dubrovnik: LatLong = 42.65 ll 18.06
  val lastovo: LatLong = 42.733 ll 16.828
  val vis: LatLong = 43.008 ll 16.070
  val movar: LatLong = 43.507 ll 15.954
  val lojena: LatLong = 43.820 ll 15.242
  val veliRat: LatLong = 44.138 ll 14.843
  val pula: LatLong = 44.81 ll 13.91
  val basanija = 45.48 ll 13.48
  val trieste = 45.70 ll 13.73

  override val polygonLL: PolygonLL = PolygonLL(northEast, GreeceNorth.northEast, GreeceNorth.northWest, shengjin, dubrovnik, lastovo, vis, movar, lojena, veliRat, pula,
    basanija, trieste, Alpsland.monfalcone, Alpsland.zagreb,
  )
}

/** [[PolygonLL]] graphic for east Balkans depends on [[BalkansWest]], [[Alpsland]] and [[GreeceNorth]]. */
object BalkansEast extends EarthPoly("BalkansEast", 44.0 ll 25.5, hillyCont) {
  val odessa: LatLong = 46.48 ll 30.74
  val ochakivskeMouth: LatLong = 45.46 ll 29.78
  val p10: LatLong = 44.84 ll 29.59
  val p12: LatLong = 44.76 ll 29.11
  val capekaliakra: LatLong = 43.36 ll 28.47
  val p20: LatLong = 43.38 ll 28.10
  val burgas: LatLong = 42.51 ll 27.58
  val p25: LatLong = 41.60 ll 28.13
  val bosphorusN: LatLong = 41.22 ll 29.13
  val seddElBahr: LatLong = 40.06 ll 26.18
  val p40: LatLong = 40.32 ll 26.22
  val p47: LatLong = 40.66 ll 26.79
  val p50: LatLong = 40.61 ll 26.13
  val p55: LatLong = 40.84 ll 25.96
  val p57: LatLong = 40.861 ll 24.629
  val p62: LatLong = 40.74 ll 24.06
  val p66: LatLong = 40.53 ll 23.91
  val p70: LatLong = 40.14 ll 24.40
  val p80: LatLong = 39.91 ll 23.74
  val p85: LatLong = 40.48 ll 22.82
  val thessalonika: LatLong = 40.65 ll 22.9

  override val polygonLL: PolygonLL = LinePathLL(BalkansWest.northEast, odessa, ochakivskeMouth, p10, p12, capekaliakra, p20, burgas, p25, bosphorusN) ++<
    MarmaraSea.northCoast |++| LinePathLL(seddElBahr, p40, p47, p50, p55, p57, p62, p66, p70, p80, p85, thessalonika, GreeceNorth.northEast)
}

/** [[PolygonLL]] graphic for Marmara Sea, depends on nothing. */
object MarmaraSea extends EarthPoly("Marmara", 40.73 ll 28.21, sea)
{ val istanbul: LatLong = 41.00 ll 29.00
  val p5: LatLong = 40.757 ll 29.354
  val darica: LatLong = 40.76 ll 28.36
  val bandirama: LatLong = 40.35 ll 27.96
  val dardanellesE: LatLong = 40.23 ll 26.74

  val southCoast: LinePathLL = LinePathLL(istanbul, p5, darica, bandirama, dardanellesE)

  val p70: LatLong = 40.85 ll 27.45
  val tekirdag: LatLong = 40.97 ll 27.51
  val north: LatLong = 41.08 ll 28.23

  val northCoast: LinePathLL = LinePathLL(dardanellesE, p70, tekirdag, north, istanbul)

  override val polygonLL: PolygonLL = southCoast.init |++| northCoast.init
}