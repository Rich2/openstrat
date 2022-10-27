/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._

object LakeMichigan extends EArea2("Lake Michigan", 43.82 ll -87.1, lake)
{ val mouthNorth: LatLong = 45.84 ll -84.75
  val north: LatLong = 46.10 ll -85.42
  val northWest: LatLong = 45.91 ll -86.97
  val west: LatLong = 43.04 ll -87.89
  val south: LatLong = 41.62 ll -87.25
  val pointBetsie: LatLong = 44.69 ll -86.26
  val mouthSouth: LatLong = 45.78 ll -84.75

  val coast: LinePathLL = LinePathLL(mouthNorth, north, northWest, west, south, pointBetsie, mouthSouth)

  override def polygonLL: PolygonLL = coast.reverseClose
}

object UsaEast extends EArea2("United States\neast", degs(39.8, -85.0), plain)
{ import AmericasNorth._

  val stumpyPoint = 35.69 ll -75.73

  override def polygonLL: PolygonLL = LinePathLL(galveston, SouthWestCanada.wUsaNE) ++ LakeSuperior.usCoast ++ LakeHuron.pineMouth ++ LakeMichigan.coast ++
    LakeHuron.usCoastSouth  ++ LakeErie.usCoast ++ LakeOntario.usCoast ++!
    (EastCanada.maineE, stumpyPoint, NAtlanticSW, seFlorida, swFlorida, nwFlorida, galveston, rockyPoint)
}

object UsaWest extends EArea2 ("United States\nwest", 40.0 ll - 108.0, desert)
{ import AmericasNorth._

  val sanDiego = 32.57 ll -117.11
  val humboldt = 40.44 ll -124.40
  override def polygonLL: PolygonLL = PolygonLL(sanDiego, humboldt, SouthWestCanada.w49th, SouthWestCanada.wUsaNE, galveston, rockyPoint, montague)

  val lasVegas = LocationLL("Las Vegas", 36.17, -115.14, 2)
  val denver = LocationLL("Denver", 39.74, -105, 2)
  val losAngeles = LocationLL("Los Angeles", 34.05, -118.24, 1)

  override val locations: LocationLLArr = LocationLLArr(lasVegas, denver, losAngeles)
}

object AmericasNorth extends EArea1("North America", 49 ll -100)
{ /** Camden County Georgia USA */
  val NAtlanticSW = 31 ll  -81.47

  val cAmericaN =  22.8.north
  val cAmericaNW= cAmericaN ll -105.97


  val ensenada = 31.74 ll -116.73
  val seFlorida = degs(25.34, -80.39)
  val swFlorida = degs(25.19, -81.13)
  val nwFlorida = degs(30.14, -84.06)
  val galveston = 29.31 ll -94.77
  val rockyPoint = 31.16 ll -113.02

  val montague = 31.70 ll -114.71

  val cabotPulmo = 23.37 ll -109.44
  val sanLucas = 22.87 ll -109.91
  val wBaja = 27.84 ll -115.07
  val baja = EArea2("Baja", 27.80 ll -113.31, plain, UsaWest.sanDiego, montague, cabotPulmo, sanLucas, wBaja)

  val mariato = degs(7.22, -80.88)
  val quebrada = degs(8.04, -82.88)
  val swGuatemala = degs(14.55, -92.21)
  val pochutala = degs(15.76, -96.50)
  val manzanillo = degs(19.15, -104)

  val brownsville = degs(25.98, -97.26)
  // val cAmericaNE= cAmericaN ll -97.79

  val sePanama = degs(7.26, -77.9)
  val coatz = degs(18.13, -94.5)
  val champeton = degs(19.36, -90.71)
  val nwYucatan =degs(21.01, -90.3)
  val neYucatan = degs(21.48, -86.97)
  val seBelize = degs(15.88, -88.91)
  val eHonduras = degs(15.0, -83.17)
  val kusapin = degs(8.79, -81.38)
  val stIsabel = degs(9.53, -79.25)
  val stIgnacio = degs(9.26, -78.12)
  val nePanama = degs(8.43, -77.26)

  val cAmerica: EArea2 = EArea2("CAmerica", degs(17.31, -94.16), jungle, sePanama, mariato, quebrada, swGuatemala, pochutala,
    manzanillo, cAmericaNW, rockyPoint, galveston, brownsville, coatz, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin, stIsabel,
    stIgnacio, nePanama)

  val wCuba = 21.86 ll -84.95
  val havana = 23.14 ll -82.39
  val eCuba = 20.22 ll -74.13
  val cabotCruz = 19.84 ll -77.73
  val yara = 20.45 ll -77.07
  val surgidero = 22.68 ll -82.29
  val cuba = EArea2("Cuba", 21.97 ll -78.96, jungle, wCuba, havana, eCuba, cabotCruz, yara, surgidero)

  val lakes = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)
  override val a2Arr: RArr[EArea2] = lakes ++
    RArr(UsaWest, UsaEast, Alaska, NorthWestCanada, SouthWestCanada, CentralCanada, BanksIsland, VictoriaIsland, SouthamptonIsland, EastCanada, BaffinIsland,
    NewFoundland, baja, cAmerica, cuba)
}