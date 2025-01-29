/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom.*, pglobe.*, egrid.*, WTiles.*

/** [[PolygonLL]] graphical representation of the island of New Guinea. Depends on nothing. */
object GuineaWest extends EarthPoly("West Guinea", -5.19 ll 141.03, hillyJungle)
{ val waigeoWest: LatLong = -0.113 ll 130.295
  val waigeoNorth: LatLong = -0.007 ll 130.814
  val manokwari: LatLong = -0.73 ll 133.98
  val sCenderawasih: LatLong = -3.39 ll 135.33
  val tebe: LatLong = -1.46 ll 137.93
  val northEast: LatLong = -2.606 ll 141

  val southEast: LatLong = -9.126 ll 141
  val p55: LatLong = -9.231 ll 141.135
  val p60: LatLong = -8.113 ll 139.951
  val southWest: LatLong = -8.431 ll 137.655
  val p70: LatLong = -7.518 ll 138.145
  val heilwigMouth: LatLong = -5.359 ll 137.866
  val aindua: LatLong = -4.46 ll 135.21
  val p85: LatLong = -4.083 ll 132.915
  val wNewGuinea: LatLong = -0.82 ll 130.45

  override val polygonLL: PolygonLL = PolygonLL(waigeoWest, waigeoNorth, manokwari, sCenderawasih, tebe, northEast, southEast, p55, p60, southWest, p70,
    heilwigMouth, aindua, p85, wNewGuinea)
}

/** [[PolygonLL]] graphical representation for Papua New Guinea. Depends on nothing. */
object PapuaNewGuinea extends EarthPoly("Papua New Guinea", -5.448 ll 143.578, hillyJungle)
{ val madang: LatLong = -4.85 ll 145.78
  val saidor: LatLong = -5.614 ll 146.473
  val p10: LatLong = -5.918 ll 147.339
  val p15: LatLong = -6.402 ll 147.843
  val p22: LatLong = -6.642 ll 147.856
  val markhamMouth: LatLong = -6.745 ll 146.970
  val deboinMission: LatLong = -8.056 ll 148.123
  val gavida: LatLong = -9.019 ll 149.292
  val east: LatLong = -10.23 ll 150.87

  val hulaBlackSand: LatLong = -10.103 ll 147.726
  val p53: LatLong = -8.067 ll 146.031
  val morigo: LatLong = -7.83 ll 143.98
  val saibai: LatLong = -9.32 ll 142.63

  override val polygonLL: PolygonLL = PolygonLL(GuineaWest.northEast, madang, saidor, p10, p15, p22, markhamMouth, deboinMission, gavida, east,
    hulaBlackSand, p53, morigo, saibai, GuineaWest.southEast
  )
}