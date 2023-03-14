/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._

object India extends EArea2("India", 20.85 ll 78.68, Jungles)
{
  val mianiLat: Latitude = 25.39.north
  val wAsiaE: Longitude = 66.52.east

  val indiaE: Longitude = 91.5.east
  val himilayasE: Longitude = 83.75.east
  val mianiHor: LatLong = India.mianiLat * wAsiaE
  val magdhara: LatLong = 22.41.north * indiaE

  //val chinaSW = AsiaWestPts.mianiLat * indiaE
  val indiaNE: LatLong = mianiLat * indiaE

  //val himilayasSE = AsiaWestPts.mianiLat * himilayasE
  val balasore: LatLong = 21.41 ll 86.97
  val ongale: LatLong = 15.46 ll 80.18
  val kattupali: LatLong = 13.29 ll 80.34
  val manapad: LatLong = 8.37 ll 78.06
  val sIndia: LatLong = 8.11 ll 77.50
  val kovalam: LatLong = 8.39 ll 76.97
  val karnataka1: LatLong = 14.75 ll 74.23
  val karnataka2: LatLong = 14.80 ll 74.09
  val tarapur: LatLong = 19.83 ll 72.65
  val khambat: LatLong = 22.26 ll 72.48
  val girSomnath: LatLong = 20.69 ll 70.83
  val varvala = 22.30 ll 68.93
  val kutchMouth = 22.92 ll 70.37
  val bhada = 22.83 ll 69.19
  val karachi = degs(25.38, 66.70)

  override val polygonLL: PolygonLL = PolygonLL(mianiHor, indiaNE, indiaNE, magdhara, balasore, ongale, kattupali,
  manapad, sIndia, kovalam, karnataka1, karnataka2, tarapur, khambat, girSomnath, varvala, kutchMouth, bhada, karachi)
}

object SriLanka extends EArea2("SriLanka", 7.47 ll 80.78, Jungles)
{ val dodanduwa = 6.10 ll 80.12
  val wSriLanka = 8.2 ll 79.69
  val nwSrilanka = 9.75 ll 79.86
  val neSrilanka = 9.83 ll 80.24
  val oiluvil = 7.29 ll 81.86
  val dondra = 5.92 ll 80.59

  override val polygonLL: PolygonLL = PolygonLL(dodanduwa, wSriLanka, nwSrilanka, neSrilanka, oiluvil, dondra)
}