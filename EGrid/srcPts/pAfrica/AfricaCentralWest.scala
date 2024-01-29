/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for the west of cnetral Africa. Depends on [[SouthAfrica]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 *  [[LakeVictoria]]. */
object Congo extends EArea2("Congo", -7 ll 24, jungle)
{ val wAfricaEquator: LatLong = 0.0 ll 9.13

  val bouemba: LatLong = 2.09 ll 9.76

  override def polygonLL: PolygonLL = LinePathLL(wAfricaEquator, bouemba, WestAfricaSouth.cAfricaNW,
    WestAfricaSouth.westAfricaPtSE, AfricaCentral.cAfricaNE, AfricaHorn.lakeTurkanaNW, AfricaHorn.lakeTurkanaSouth, LakeVictoria.kisuma,
    LakeVictoria.north, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++ LakeTanganyika.westCoast |++|
    LinePathLL(AngloaZambia.wantipaNW, LakeMweru.northEast, LakeMweru.north, LakeMweru.west, LakeMweru.southWest, AngloaZambia.benjoMouth)
}

/** [[PolygonLL]] graphic object for the west of Angola - Zambia. Depends on [[SouthAfrica]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 *  [[LakeVictoria]]. */
object AngloaZambia extends EArea2("AngolaZambia", -7 ll 24, savannah)
{
  val wantipaNW = -8.538 ll 29.670
  val baiaFarta: LatLong = -12.81 ll 13.01
 // val luanda: LatLong = -8.35 ll 13.15
  val benjoMouth = -8.752 ll 13.393
  override def polygonLL: PolygonLL = PolygonLL(LakeMweru.southWest, LakeMweru.southEast, LakeMweru.p20, LakeMweru.northEast, wantipaNW,
    LakeTanganyika.southWest, LakeTanganyika.south, SouthAfrica.cAfricaSE, SouthAfrica.sAfricaNW, baiaFarta, benjoMouth,
  )
}