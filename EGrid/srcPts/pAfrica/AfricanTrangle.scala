/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for Lake Victoria. Depends on nothing. */
object LakeVictoria extends EArea2("Lake\nVictoria", -1 ll 32.83, Lake)
{ val southEast: LatLong = -2.23 ll 33.84
  val katongaMouth: LatLong =  -0.14 ll 31.94
  val east: LatLong = -0.39 ll 34.26
  val north: LatLong = 0.34 ll 33.34
  val southWest: LatLong = -2.64 ll 31.76
  val southEastAfrica: LinePathLL = LinePathLL(southWest, southEast, east, north, katongaMouth)

  override def polygonLL: PolygonLL = southEastAfrica.reverse.toPolygon
}

/** [[PolygonLL]] graphic object for Lake Tanganyika depends on nothing. */
object LakeTanganyika extends EArea2("Lake\nTanganyika", -6.25 ll 29.57, Lake)
{ val northEast: LatLong = -3.36 ll 29.34
  val northWest: LatLong = -3.36 ll 29.16
  val kalemie: LatLong = -5.91 ll 29.20
  val moba: LatLong = -7.04 ll 29.78
  val south: LatLong = -8.81 ll 31.03
  val westCoast: LinePathLL = LinePathLL(northEast, northWest, kalemie, moba, south)

  val mahaleSE: LatLong = -6.45 ll 30.14
  val muhela: LatLong = -6.24 ll 29.72
  val mahaleNE: LatLong = -5.91 ll 29.95

  val eastCoast: LinePathLL = LinePathLL(south, mahaleSE, muhela, mahaleNE, northEast)

  override def polygonLL: PolygonLL = westCoast.reverse |++<| eastCoast.inner
}

/** [[PolygonLL]] graphic object for the west of cnetral Africa. Depends on [[SouthAfrica]], [[WestAfricaSouth]], [[LakeTanganyika]] and
 *  [[LakeVictoria]]. */
object CentralAfricaWest extends EArea2("Central Africa\nwest", -7 ll 24, jungle)
{ val wAfricaEquator: LatLong = 0.0 ll 9.13
  val baiaFarta: LatLong = -12.81 ll 13.01
  val luanda: LatLong = -8.35 ll 13.15
  val bouemba: LatLong = 2.09 ll 9.76

  override def polygonLL: PolygonLL = LinePathLL(SouthAfrica.sAfricaNW, baiaFarta, luanda, wAfricaEquator, bouemba, WestAfricaSouth.cAfricaNW,
    WestAfricaSouth.westAfricaPtSE, EastAfricaSouth.cAfricaNE, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++
    LakeTanganyika.westCoast |+| SouthAfrica.cAfricaSE
}

/** [[PolygonLL]] graphic object for the east of central Africa. Depends on [[SouthAfrica]], [[LakeTanganyika]] and  [[LakeVictoria]]. */
object centralAfricaEast extends EArea2("Central Africa\neast", -2.17 ll 36.64, land)
{ val seNacala: LatLong = -14.4 ll 40.3
  val eAfricaEquator: LatLong = 0.0 ll 42.4
  val mombassa: LatLong = -4.03 ll 39.28

  override def polygonLL: PolygonLL = SouthAfrica.cAfricaSE %: LakeTanganyika.eastCoast ++ LakeVictoria.southEastAfrica |++|
    LinePathLL(EastAfricaSouth.cAfricaNE, EastAfricaSouth.southEast, eAfricaEquator, mombassa, seNacala, SouthAfrica.sAfricaNE)
}

/** [[PolygonLL]] graphic object for Madagascar depends on nothing. */
object Madagascar extends EArea2("Madagascar", -19.42 ll 46.57, land)
{ val north: LatLong = -11.95 ll 49.26
  val east: LatLong = -15.33 ll 50.48
  val southEast: LatLong = -25.03 ll 46.99
  val south: LatLong = -25.60 ll 45.16
  val tambohorano: LatLong = -17.51 ll 43.93

  override def polygonLL: PolygonLL = PolygonLL(north, east, southEast, south, tambohorano)
}

/** [[PolygonLL]] graphic object for southern Africa depends on nothing. */
object SouthAfrica extends EArea2("South Africa", -25 ll 24, land)
{ val sAfricaN: Latitude = 17.south

  val sAfricaNW: LatLong = - 17 ll 11.76
  val cAfricaSE: LatLong = sAfricaN * 31.east
  val sAfricaNE: LatLong = -17 ll 39.06
  val agulhas: LatLong = -34.83 ll 20.00
  val capeTown: LatLong = -34 ll 19
  val nNamibia: LatLong = -17.12 ll 11.3
  val beira: LatLong = -19.35 ll 34.3
  val inhambane: LatLong = -23.38 ll 35.2
  val maputo: LatLong = -25.4 ll 32.2
  val richardsBay: LatLong = -29 ll 32
  val portLiz: LatLong = -34 ll 26

  override def polygonLL: PolygonLL = PolygonLL(agulhas, capeTown, nNamibia, sAfricaNW, cAfricaSE, sAfricaNE, beira, inhambane, maputo, richardsBay,
    portLiz)
}