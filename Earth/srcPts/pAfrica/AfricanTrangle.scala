/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._

/** The southern part of Africa below approximately 5 degrees north, roughly triangular in shape. */
object AfricanTrangle extends EArea1("African Triangle", -16.14 ll 24.36)
{ type A2Type = EArea2
  import AfricaSouthernPts._
  override val a2Arr: RArr[EArea2] = RArr(LakeVictoria, LakeTanganyika, southernAfrica, CentralAfricaWest, centralAfricaEast, madagascar)
}

/** Lake Victoria. */
object LakeVictoria extends EArea2("Lake\nVictoria", -1 ll 32.83, lake)
{ val southEast = -2.23 ll 33.84
  val katongaMouth =  -0.14 ll 31.94
  val east = -0.39 ll 34.26
  val north = 0.34 ll 33.34
  val southWest = -2.64 ll 31.76
  val southEastAfrica = LinePathLL(southWest, southEast, east, north, katongaMouth )
  override def polygonLL: PolygonLL = southEastAfrica.reverseClose
}

/** Lake Tanganyika. */
object LakeTanganyika extends EArea2("Lake\nTanganyika", -6.25 ll 29.57, lake)
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

object CentralAfricaWest extends EArea2("Central Africa\nwest", -7 ll 24, jungle)
{
  import AfricaSouthernPts._
  /** A quasi polygon on the earths surface defined in [[LatLong]]s. */
  override def polygonLL: PolygonLL = LinePathLL(sAfricaNW, baiaFarta, luanda, wAfricaEquator, bouemba, AfricaWestPts.cAfricaNW,
    AfricaWestPts.westAfricaPtSE, EastAfricaSouth.cAfricaNE, LakeVictoria.katongaMouth, LakeVictoria.southWest) ++
    LakeTanganyika.westCoast +! cAfricaSE
}

object centralAfricaEast extends EArea2("Central Africa\neast", -2.17 ll 36.64, plain){
  import AfricaSouthernPts._

  override def polygonLL: PolygonLL = cAfricaSE %: LakeTanganyika.eastCoast ++ LakeVictoria.southEastAfrica ++!
    (EastAfricaSouth.cAfricaNE, EastAfricaSouth.southEast, eAfricaEquator, mombassa, seNacala, sAfricaNE)
}

object AfricaSouthernPts
{ val cAfricaN = 4.42.north
  val sAfricaNW = - 17 ll 11.76
  val baiaFarta = -12.81 ll 13.01
  val luanda = -8.35 ll 13.15
  val bouemba = 2.09 ll 9.76
  val wAfricaEquator = 0.0 ll 9.13

  val sAfricaN = 17.south
  val cAfricaSE = sAfricaN * 31.east

  val centralAfrWestCoast = LinePathLL(sAfricaNW, baiaFarta, luanda, wAfricaEquator)

  val eAfricaEquator = 0.0 ll 42.4
  val mombassa = -4.03 ll 39.28
  val seNacala = -14.4 ll 40.3
  val sAfricaNE = -17 ll 39.06


  val agulhas = degs(-34.83, 20.00)
  val capeTown = degs(-34, 19)
  val nNamibia = -17.12 ll 11.3
  val beira = -19.35 ll 34.3
  val inhambane = -23.38 ll 35.2
  val maputo = -25.4 ll 32.2
  val richardsBay = degs(-29, 32)
  val portLiz = degs(-34, 26)

  val southernAfrica: EArea2 = EArea2("Southern Africa", -25 ll 24, plain, agulhas, capeTown, nNamibia, sAfricaNW, cAfricaSE, sAfricaNE, beira, inhambane,
    maputo, richardsBay, portLiz)
         
  val madagascarN = degs(-11.95, 49.26)
  val madagascarE = degs(-15.33, 50.48)
  val madagascarSE = degs(-25.03, 46.99)
  val madagascarS = degs(-25.60, 45.16)
  val tambohorano = degs(-17.51, 43.93)
  val madagascar = EArea2("Madagascar", degs(-19.42, 46.57), plain, madagascarN, madagascarE, madagascarSE, madagascarS, tambohorano)
}