/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, LatLong._

/** Lake Victoria. */
object LakeVictoria extends EArea2("Lake\nVictoria", -1 ll 32.83, Lake)
{ val southEast = -2.23 ll 33.84
  val katongaMouth =  -0.14 ll 31.94
  val east = -0.39 ll 34.26
  val north = 0.34 ll 33.34
  val southWest = -2.64 ll 31.76
  val southEastAfrica = LinePathLL(southWest, southEast, east, north, katongaMouth )
  override def polygonLL: PolygonLL = southEastAfrica.reverseClose
}

/** Lake Tanganyika. */
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

  override def polygonLL: PolygonLL = westCoast.reverse +/--! eastCoast
}

/** [[PolygonLL]] graphic object. */
object CentralAfricaWest extends EArea2("Central Africa\nwest", -7 ll 24, Jungles)
{  val wAfricaEquator = 0.0 ll 9.13
  val baiaFarta = -12.81 ll 13.01
  val luanda = -8.35 ll 13.15
  val bouemba = 2.09 ll 9.76

  override def polygonLL: PolygonLL = LinePathLL(SouthAfrica.sAfricaNW, baiaFarta, luanda, wAfricaEquator, bouemba, WestAfricaSouth.cAfricaNW,
    WestAfricaSouth.westAfricaPtSE, EastAfricaSouth.cAfricaNE, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++
    LakeTanganyika.westCoast |+| SouthAfrica.cAfricaSE
}

object centralAfricaEast extends EArea2("Central Africa\neast", -2.17 ll 36.64, Plain)
{ val seNacala = -14.4 ll 40.3
  val eAfricaEquator = 0.0 ll 42.4
  val mombassa = -4.03 ll 39.28

  override def polygonLL: PolygonLL = SouthAfrica.cAfricaSE %: LakeTanganyika.eastCoast ++ LakeVictoria.southEastAfrica |++|
    LinePathLL(EastAfricaSouth.cAfricaNE, EastAfricaSouth.southEast, eAfricaEquator, mombassa, seNacala, SouthAfrica.sAfricaNE)
}

object Madagascar extends EArea2("Madagascar", -19.42 ll 46.57, Plain)
{ val madagascarN = degs(-11.95, 49.26)
  val madagascarE = degs(-15.33, 50.48)
  val madagascarSE = degs(-25.03, 46.99)
  val madagascarS = degs(-25.60, 45.16)
  val tambohorano = degs(-17.51, 43.93)

  override def polygonLL: PolygonLL = PolygonLL(madagascarN, madagascarE, madagascarSE, madagascarS, tambohorano)
}

object SouthAfrica extends EArea2("South Africa", -25 ll 24, Plain)
{ val sAfricaN = 17.south

  val sAfricaNW = - 17 ll 11.76
  val cAfricaSE = sAfricaN * 31.east
  val sAfricaNE = -17 ll 39.06
  val agulhas = degs(-34.83, 20.00)
  val capeTown = degs(-34, 19)
  val nNamibia = -17.12 ll 11.3
  val beira = -19.35 ll 34.3
  val inhambane = -23.38 ll 35.2
  val maputo = -25.4 ll 32.2
  val richardsBay = degs(-29, 32)
  val portLiz = degs(-34, 26)

  override def polygonLL: PolygonLL = PolygonLL(agulhas, capeTown, nNamibia, sAfricaNW, cAfricaSE, sAfricaNE, beira, inhambane, maputo, richardsBay,
    portLiz)
}