/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, WTile._

object India extends EArea2 ("India", 20.85 ll 78.68, jungle)
{
  val magdhara = 22.41.north * RusNorth.indiaE

  //val chinaSW = AsiaWestPts.mianiLat * indiaE
  val indiaNE = AsiaWestPts.mianiLat * RusNorth.indiaE
  val himilayasE = 83.75.east
  //val himilayasSE = AsiaWestPts.mianiLat * himilayasE
  val balasore = degs(21.41, 86.97)
  val ongale = 15.46 ll 80.18
  val kattupali = 13.29 ll 80.34
  val manapad = 8.37 ll 78.06
  val sIndia = degs(8.11, 77.50)
  val kovalam = 8.39 ll 76.97
  val karnataka1 = 14.75 ll 74.23
  val karnataka2 = 14.80 ll 74.09
  val tarapur = 19.83 ll 72.65
  val khambat = 22.26 ll 72.48
  val girSomnath = 20.69 ll 70.83
  val varvala = 22.30 ll 68.93
  val kutchMouth = 22.92 ll 70.37
  val bhada = 22.83 ll 69.19
  val karachi = degs(25.38, 66.70)

  override def polygonLL: PolygonLL = PolygonLL(AsiaWestPts.mianiHor, indiaNE, indiaNE, magdhara, balasore, ongale, kattupali,
  manapad, sIndia, kovalam, karnataka1, karnataka2, tarapur, khambat, girSomnath, varvala, kutchMouth, bhada, karachi)
}


