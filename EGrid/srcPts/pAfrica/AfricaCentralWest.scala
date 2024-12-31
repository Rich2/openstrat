/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for the island of Bioko / FernandoPo. Depends on nothing. */
object FernandoPo extends EarthPoly("Congo", -7 ll 24, jungle)
{ val north: LatLong = 3.788 ll 8.713
  val northEast: LatLong = 3.761 ll 8.988
  val southEast: LatLong = 3.209 ll 8.675
  val southWest: LatLong = 3.276 ll 8.444
  val p60: LatLong = 3.465 ll 8.458

  override def polygonLL: PolygonLL = PolygonLL(north, northEast,southEast, southWest, p60)
}

/** [[PolygonLL]] graphic object for the Congo. Depends on [[SouthAfricaEast]], [[WestAfricaSouth]], [[LakeTanganyika]] and [[LakeVictoria]]. */
object Congo extends EarthPoly("Congo", -7 ll 24, jungle)
{ val bambou: LatLong = -4.661 ll 11.783
  val gabonSouth: LatLong = -3.957 ll 11.153

  val wAfricaEquator: LatLong = 0.0 ll 9.13
  val caboSanJuan: LatLong = 1.172 ll 9.341
  val bouemba: LatLong = 2.09 ll 9.76
  val londgi: LatLong = 3.076 ll 9.965

  override def polygonLL: PolygonLL = LinePathLL(
    WestAfricaSouth.westAfricaPtSE, AfricaCentral.cAfricaNE, AfricaHorn.lakeTurkanaNW, AfricaHorn.lakeTurkanaSouth, LakeVictoria.kisuma,
    LakeVictoria.north, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++ LakeTanganyika.westCoast |++|
    LinePathLL(Zambia.wantipaNW, LakeMweru.northEast, LakeMweru.north, LakeMweru.west, LakeMweru.southWest, Angola.p10, Angola.katende, Angola.benjoMouth, bambou, gabonSouth,
      wAfricaEquator, caboSanJuan, bouemba, londgi, WestAfricaSouth.cAfricaNW)
}

/** [[PolygonLL]] graphic object for the west of Angola - Zambia. Depends on [[SouthAfricaEast]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 * [[LakeVictoria]]. */
object Angola extends EarthPoly("Angola", -11.950 ll 18.220, jungle)
{
  val katende: LatLong = -6.316 ll 22.384
  val p10: LatLong = -10.128 ll 22.217
  val p30: LatLong = -18.106 ll 11.845
  val namibiaNW: LatLong = -17.252 ll 11.751
  val baiaFarta: LatLong = -12.81 ll 13.01
  val p75: LatLong = -9.083 ll 12.992
  val benjoMouth: LatLong = -8.752 ll 13.393

  override def polygonLL: PolygonLL = PolygonLL(katende, p10, NamibiaBotswana.omatako, NamibiaBotswana.omaruruMouth, p30, namibiaNW, baiaFarta, p75, benjoMouth)
}

/** [[PolygonLL]] graphic object for the west of Angola - Zambia. Depends on [[SouthAfricaEast]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 * [[LakeVictoria]]. */
object Zambia extends EarthPoly("Zambia", -13.629 ll 28.778, savannah)
{ val wantipaNW = -8.538 ll 29.670

  override def polygonLL: PolygonLL = PolygonLL(LakeMweru.southWest, LakeMweru.southEast, LakeMweru.p20, LakeMweru.northEast, wantipaNW,
    LakeTanganyika.southWest, LakeTanganyika.south, LakeMalawi.north, LakeMalawi.west, LakeMalawi.southWest, Mozambique.tete, Zimbabwe.luangwa,
    Zimbabwe.kazungula, NamibiaBotswana.omatako, Angola.p10)
}