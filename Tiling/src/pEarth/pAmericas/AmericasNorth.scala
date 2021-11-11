/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._


object LakeSuperior extends EarthLevel2("Lake Superior", 47.66 ll -90, lake)
{
  override def polygonLL: PolygonLL = ???
}

object AmericasNorth extends EarthLevel1("AmericasNorth", 49 ll -100)
{ val w49th = degs(49, -125.66)
  val yakut = degs(59.93, -141.03)
  val swAlaska = degs(60.50, -164.55)
  val nwAlaska = degs(70.11, -161.87)
  val wCanadaE = 110.west
  val wCanadaEN = 72.97.north * wCanadaE
  val wCanadaES = 49.north * wCanadaE
   
  val wCanada: A2Type = EarthLevel2("WCanada", degs(64.051, -129.98), taiga, w49th, yakut, swAlaska, nwAlaska, wCanadaEN, wCanadaES)

  val nwPass = 69.5 ll -82.82
  val eggIsland = degs(59.91, -94.85)
  val jamesBayNW = degs(55.07, -82.31)
  val jamesBayS = degs(51.14, -79.79)
  val huronNE = degs(45.90, -80.76)

  val h49th80 = degs(49, -80)

  val cCanada: A2Type = EarthLevel2("CCanada", degs(64.051, -100.0), taiga, wCanadaES, wCanadaEN, nwPass, eggIsland, jamesBayNW, jamesBayS, huronNE)

  val hudsonBayMouthE = degs(62.57, -77.99)
  val ungavaW = degs(61.04, -69.56)
  val ungavaS = degs(58.26, -67.45)
  /** Camden County Georgia USA */
  val NAtlanticSW = 31 ll  -81.47
  val ungavaE = degs(60.49, -64.74)
  val labradorE = degs(52.42, -56.05)
  val newFoundlandE = degs(47.52, -52.64)
  //val e49th = deg(49, -64.41) Removed as Newfoundland is the close
  val maineE = degs(44.87, -66.93)
  val eNovaScotia = 46.16 ll -59.86
   
  val eCanadaCoast = LinePathLL(ungavaE, labradorE, newFoundlandE, eNovaScotia)
   
  val eCanada: A2Type = new EarthLevel2("ECanada", degs(53.71, -70), taiga)
  {
    override val polygonLL: PolygonLL = {
      val lPath = LinePathLL(huronNE, jamesBayS, hudsonBayMouthE, ungavaW, ungavaS) ++ eCanadaCoast
      lPath.close(maineE, h49th80)
    }
  }
         
  val cAmericaN =  22.8.north
  val cAmericaNW= cAmericaN ll -105.97
  val sanDiego = degs(32.57, -117.11)
  val humboldt = 40.44 ll -124.40
  val ensenada = 31.74 ll -116.73
  val seFlorida = degs(25.34, -80.39)
  val swFlorida = degs(25.19, -81.13)
  val nwFlorida = degs(30.14, -84.06)
  val galveston = 29.31 ll -94.77
  val rockyPoint = 31.16 ll -113.02
   
  val montague = 31.70 ll -114.71
   
  lazy val usa = EarthLevel2("UnitedStates", degs(39.8, -98.6), plain, /*cAmericaNW, */ sanDiego, humboldt, w49th, wCanadaES, huronNE, h49th80, maineE,
     NAtlanticSW, seFlorida, swFlorida, nwFlorida, galveston, rockyPoint, montague)
   
  val cabotPulmo = 23.37 ll -109.44
  val sanLucas = 22.87 ll -109.91
  val wBaja = 27.84 ll -115.07
  val baja = EarthLevel2("Baja", 27.80 ll -113.31, plain, sanDiego, montague, cabotPulmo, sanLucas, wBaja)
   
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

  val cAmerica: A2Type = EarthLevel2("CAmerica", degs(17.31, -94.16), jungle, sePanama, mariato, quebrada, swGuatemala, pochutala, manzanillo,
     cAmericaNW, rockyPoint, galveston, brownsville, coatz, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin, stIsabel, stIgnacio,
     nePanama)
   
  val wCuba = 21.86 ll -84.95
  val havana = 23.14 ll -82.39
  val eCuba = 20.22 ll -74.13
  val cabotCruz = 19.84 ll -77.73
  val yara = 20.45 ll -77.07
  val surgidero = 22.68 ll -82.29
  val cuba = EarthLevel2("Cuba", 21.97 ll -78.96, jungle, wCuba, havana, eCuba, cabotCruz, yara, surgidero)
         
  type A2Type = EarthLevel2
   
  override val a2Arr: Arr[A2Type] = Arr(usa, wCanada, cCanada, eCanada, baja, cAmerica, cuba)
}