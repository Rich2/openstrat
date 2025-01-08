/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of north Sweden and north Norway. Depends on nothing. */
object SwedenNorth extends EarthPoly("Sweden North", 67.489 ll 20.872, hillyCont)
{ val sorvagen: LatLong = 67.83 ll 12.82
  val andenes: LatLong = 69.32 ll 16.11
  val gapoyholman: LatLong = 68.88 ll 16.06
  val sandsvika: LatLong = 69.37 ll 16.87
  val torsvag: LatLong = 70.28 ll 19.59
  val slida: LatLong = 70.392 ll 21.702
  val p85: LatLong = 70.669 ll 21.979
  val ingoya: LatLong = 71.073 ll 23.948
  val northSeaCoast: LinePathLL = LinePathLL(sorvagen, andenes, gapoyholman, sandsvika, torsvag, slida, p85, ingoya)

  val nordkapp: LatLong = 71.16 ll 25.78
  val p98: LatLong = 70.370 ll 25.092
  val reinoya: LatLong = 70.298 ll 25.309
  val barentsCoast: LinePathLL = LinePathLL(ingoya, nordkapp, p98, reinoya)

  val haparanda: LatLong = 65.77 ll 24.17
  val balticNW: LatLong = 65.856 ll 22.350

  val vegaoyan: LatLong = 65.615 ll 11.754
  val p78: LatLong = 66.011 ll 12.146
  val bodo: LatLong = 67.26 ll 14.32
  val hadseloya: LatLong = 68.56 ll 14.63
  val nordskot: LatLong = 67.82 ll 14.70
  val baroya: LatLong = 68.33 ll 16.03

  override val polygonLL: PolygonLL = northSeaCoast +-+ barentsCoast |++| LinePathLL(haparanda, balticNW, vegaoyan, p78, bodo, nordskot, baroya,
  )
}

/** [[polygonLL]] graphical representation of middle (between north and south) Sweden and middle Norway. Depends on [[SwedenNorth]] and [[SwedenSouth]]. */
object SwedenMid extends EarthPoly("Sweden Middle", 64.883 ll 17.125, hillyTaiga) {
  val hertsonEast: LatLong = 65.53 ll 22.39
  val ostanbackSouth: LatLong = 64.82 ll 21.15
  val eLappviken: LatLong = 64.44 ll 21.60
  val skeppsMalen: LatLong = 63.19 ll 19.03
  val skeppshamnSouth: LatLong = 62.38 ll 17.74
  val spikarna: LatLong = 62.36 ll 17.53
  val bredsand: LatLong = 62.35 ll 17.37
  val junibosand: LatLong = 62.23 ll 17.65
  val holick: LatLong = 61.62 ll 17.48

  val balticWestCoast: LinePathLL = LinePathLL(SwedenNorth.balticNW, hertsonEast, ostanbackSouth, eLappviken, skeppsMalen, skeppshamnSouth, spikarna, bredsand, junibosand,
    holick, SwedenSouth.gavie)

  /** Start of South Coast. */
  val hvasser: LatLong = 59.07 ll 10.47
  val nevlunghavn: LatLong = 58.96 ll 9.85
  val lindesnes: LatLong = 57.98 ll 7.05
  val flekkeroy: LatLong = 58.06 ll 8.00
  val borhag: LatLong = 58.11 ll 6.55

  /** Start of West Coast. */
  val bryne: LatLong = 58.75 ll 5.49
  val rennesoy: LatLong = 59.12 ll 5.56
  val swKarmoy: LatLong = 59.14 ll 5.19
  val ytreSula: LatLong = 61.04 ll 4.63
  val bremangerlandet: LatLong = 61.85 ll 4.82
  val wRunde: LatLong = 62.41 ll 5.58
  val bud: LatLong = 62.910 ll 6.903
  val svelllingen: LatLong = 63.79 ll 8.68
  val uthaug: LatLong = 63.72 ll 9.55
  val p75: LatLong = 64.885 ll 10.728

  override val polygonLL: PolygonLL = balticWestCoast |++| LinePathLL(/* South Coast */ SwedenSouth.oslo, hvasser, nevlunghavn, flekkeroy, lindesnes, borhag,
    /* West Coast */ bryne, rennesoy, swKarmoy, ytreSula, bremangerlandet, wRunde, bud, svelllingen, uthaug, p75, SwedenNorth.vegaoyan)
}

/** [[polygonLL]] graphical representation of south Sweden. Depends on nothing. */
object SwedenSouth extends EarthPoly("SwedenSouth", 58.25 ll 15.14, continental)
{ //South Baltic Coast
  val gavie: LatLong = 60.68 ll 17.21
  val gardskarE: LatLong = 60.63 ll 17.67
  val klungstenN: LatLong = 60.60 ll 17.99
  val stenskar: LatLong = 60.36 ll 18.31
  val orskar: LatLong = 60.53 ll 18.39
  val kappelskar: LatLong = 59.75 ll 19.08
  val herrhamra: LatLong = 58.80 ll 17.84
  val hummelvik: LatLong = 58.62 ll 17.01
  val torhamn: LatLong = 56.07 ll 15.84

  //South Baltic Coast
  val stenshamn: LatLong = 56.01 ll 15.78
  val pukavik: LatLong = 56.16 ll 14.68
  val ahus: LatLong = 55.92 ll 14.32
  val simrishamn: LatLong = 55.55 ll 14.35
  val sandhammaren: LatLong = 55.38 ll 14.19
  val vellinge: LatLong = 55.41 ll 13.02
  val helsingborg: LatLong = 56.04 ll 12.69
  val kullens: LatLong = 56.30 ll 12.44
  val torekov: LatLong = 56.42 ll 12.62
  val bastad: LatLong = 56.43 ll 12.85
  val andersberg: LatLong = 56.65 ll 12.87
  val sTylosand: LatLong = 56.64 ll 12.72
  val wHono: LatLong = 57.69 ll 11.60
  val p95: LatLong = 59.07 ll 10.85
  val rauer: LatLong = 59.23 ll 10.68
  val oslo: LatLong = 59.57 ll 10.59

  override val polygonLL: PolygonLL = PolygonLL(gavie, gardskarE, klungstenN, stenskar, orskar, kappelskar, herrhamra, hummelvik, torhamn, stenshamn, pukavik,
    ahus, simrishamn, sandhammaren, vellinge, helsingborg, kullens, torekov, bastad, andersberg, sTylosand, wHono, p95, rauer, oslo)
}

/** [[PolygonLL]] graphical representation for the island of Oland. */
object Oland extends EarthPoly("Oland", 56.77 ll 16.67, oceanic)
{ val north: LatLong = 57.37 ll 17.08
  val p10: LatLong = 57.31 ll 17.15
  val p20: LatLong = 56.85 ll 16.87
  val south: LatLong = 56.19 ll 16.39
  val p30: LatLong = 56.22 ll 16.37
  val p40: LatLong = 56.88 ll 16.65
  val p50: LatLong = 57.29 ll 16.96

  override val polygonLL: PolygonLL = PolygonLL(north, p10, p20, south, p30, p40, p50)
}