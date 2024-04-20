/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for the island of Bioko / FernandoPo. Depends on nothing. */
object FernandoPo extends EarthArea("Congo", -7 ll 24, jungle)
{ val north: LatLong = 3.788 ll 8.713
  val northEast: LatLong = 3.761 ll 8.988
  val southEast: LatLong = 3.209 ll 8.675
  val southWest: LatLong = 3.276 ll 8.444
  val p60: LatLong = 3.465 ll 8.458

  override def polygonLL: PolygonLL = PolygonLL(north, northEast,southEast, southWest, p60)
}

/** [[PolygonLL]] graphic object for the west of cnetral Africa. Depends on [[SouthAfrica]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 *  [[LakeVictoria]]. */
object Congo extends EarthArea("Congo", -7 ll 24, jungle)
{ val bambou: LatLong = -4.661 ll 11.783
  val gabonSouth: LatLong = -3.957 ll 11.153

  val wAfricaEquator: LatLong = 0.0 ll 9.13
  val caboSanJuan: LatLong = 1.172 ll 9.341
  val bouemba: LatLong = 2.09 ll 9.76
  val londgi: LatLong = 3.076 ll 9.965

  override def polygonLL: PolygonLL = LinePathLL(
    WestAfricaSouth.westAfricaPtSE, AfricaCentral.cAfricaNE, AfricaHorn.lakeTurkanaNW, AfricaHorn.lakeTurkanaSouth, LakeVictoria.kisuma,
    LakeVictoria.north, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++ LakeTanganyika.westCoast |++|
    LinePathLL(AngloaZambia.wantipaNW, LakeMweru.northEast, LakeMweru.north, LakeMweru.west, LakeMweru.southWest, AngloaZambia.benjoMouth, bambou, gabonSouth,
      wAfricaEquator, caboSanJuan, bouemba, londgi, WestAfricaSouth.cAfricaNW)
}

/** [[PolygonLL]] graphic object for the west of Angola - Zambia. Depends on [[SouthAfrica]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 *  [[LakeVictoria]]. */
object AngloaZambia extends EarthArea("AngolaZambia", -7 ll 24, savannah)
{
  val wantipaNW = -8.538 ll 29.670
  val baiaFarta: LatLong = -12.81 ll 13.01
  val p75: LatLong = -9.083 ll 12.992
  val benjoMouth = -8.752 ll 13.393
  override def polygonLL: PolygonLL = PolygonLL(LakeMweru.southWest, LakeMweru.southEast, LakeMweru.p20, LakeMweru.northEast, wantipaNW,
    LakeTanganyika.southWest, LakeTanganyika.south, LakeMalawi.north, LakeMalawi.west, LakeMalawi.southWest, SouthAfrica.p95, SouthAfrica.sAfricaNW, baiaFarta, p75, benjoMouth,
  )
}