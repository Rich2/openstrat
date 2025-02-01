/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for north Greece depends on [[GreeceCentral]]. */
object GreeceNorth extends EarthPoly("Greece North", 39.54 ll 21.62, hillySub)
{ val northEast: LatLong = 40.46 ll 22.59
  val p20: LatLong = 39.19 ll 23.35

  val kanali: LatLong = 39.07 ll 20.69
  val p73: LatLong = 39.54 ll 20.15
  val p75: LatLong = 39.69 ll 19.98
  val p77: LatLong = 39.87 ll 20.01
  val vlore = 40.30 ll 19.38
  val northWest: LatLong = 40.67 ll 19.32

  override val polygonLL: PolygonLL = PolygonLL(northEast, p20, GreeceCentral.malianNW, GreeceCentral.ambracianWest, GreeceCentral.ambracianMouth,
    kanali, p73, p75, p77, vlore, northWest)
}

/** [[PolygonLL]] graphic for Central Greece depends on [[Peloponnese]]. */
object GreeceCentral extends EarthPoly("Greece Central", 39.54 ll 21.62, hillySub)
{ val malianNW = 38.881 ll 22.548
  val p25: LatLong = 38.199 ll 24.071
  val p30: LatLong = 38.00 ll 24.03
  val sAttica: LatLong = 37.64 ll 24.02
  val piraeus: LatLong = 37.927 ll 23.632
  val salminaSW: LatLong = 37.895 ll 23.405

  val monstrika: LatLong = 38.40 ll 21.92
  val oxia: LatLong = 38.31 ll 21.15
  val p70: LatLong = 38.50 ll 21.03
  val lefkadaSE: LatLong = 38.56 ll 20.54
  val lefkadaNE: LatLong = 38.83 ll 20.65
  val p71: LatLong = 38.86 ll 20.79

  val ambracianMouth: LatLong = 38.947 ll 20.758
  val ambracianWest: LatLong = 38.980 ll 21.154

  override val polygonLL: PolygonLL = PolygonLL(malianNW, Euboea.chalcisMouth, Euboea.lilasMouth, p25, p30, sAttica, piraeus, salminaSW,
    Peloponnese.peninsulaSE, Peloponnese.peninsulaNE, monstrika, oxia, p70, lefkadaSE, lefkadaNE, p71, ambracianMouth, ambracianWest)
}

/** [[PolygonLL]] graphic for Central Greece depends on [[Peloponnese]]. */
object Euboea extends EarthPoly("Euboea", 38.551 ll 23.860, mtainSub)
{ val north: LatLong = 39.03 ll 23.31
  val p25: LatLong = 38.65 ll 24.15
  val capeKafireas: LatLong = 38.16 ll 24.60
  val south: LatLong = 37.95 ll 24.51
  val megalonisos: LatLong = 37.985 ll 24.230
  val anthoupoli: LatLong = 38.389 ll 24.050
  val lilasMouth: LatLong = 38.400 ll 23.646
  val chalcisMouth: LatLong = 38.472 ll 23.595
  val northWest: LatLong = 38.831 ll 22.834

  override val polygonLL: PolygonLL = PolygonLL(north, p25, capeKafireas, south, megalonisos, anthoupoli, lilasMouth, chalcisMouth, northWest)
}

/** [[PolygonLL]] graphic for the Peloponnese, depends on nothing. */
object Peloponnese extends EarthPoly("Peloponnese", 37.56 ll 22.10, mtainSavannah)
{ val peninsulaSE: LatLong = 37.974 ll 23.360
  val kechries: LatLong = 37.88 ll 22.99
  val p1: LatLong = 37.44 ll 23.51
  val spetses: LatLong = 37.238 ll 23.152

  val neaKios: LatLong = 37.58 ll 22.74
  val capeMalea: LatLong =  36.438 ll 23.198
  val elafonisos: LatLong = 36.467 ll 22.926
  val eElos: LatLong = 36.79 ll 22.78
  val wElos: LatLong = 36.80 ll 22.61
  val sGreece: LatLong = 36.38 ll 22.48
  val p42: LatLong = 36.385 ll 22.482
  val p45: LatLong = 36.915 ll 22.126
  val p52: LatLong = 36.720 ll 21.876
  val sapientza: LatLong = 36.737 ll 21.695

  val p60: LatLong = 37.168 ll 21.567
  val kaloNero: LatLong = 37.298 ll 21.694
  val katakola: LatLong = 37.633 ll 21.310
  val kyllini: LatLong = 37.93 ll 21.13
  val rioPio: LatLong = 38.30 ll 21.77
  val corinth: LatLong = 37.94 ll 22.93
  val peninsulaNE: LatLong = 38.100 ll 23.216

  val polygonLL: PolygonLL = PolygonLL(peninsulaSE, kechries, p1, spetses, neaKios, capeMalea, elafonisos, eElos, wElos, sGreece, p42, p45, p52, sapientza, p60,
    kaloNero, katakola, kyllini, rioPio, corinth, peninsulaNE)
}