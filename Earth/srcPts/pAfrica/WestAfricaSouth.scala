/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._

object SaharaWest extends EArea2("Sahara\nwest", 25 ll 1, Deserts)
{ val southLine = 17.north
  val eastLine = 16.75.east
  val northEast = 31.2.north *  eastLine
   
  val southWest = southLine * 16.27.west
  val nouakchott = 18.078 ll -16.02
  val nouadhibouBay = 21.28 ll -16.90
  val nouadhibou = 20.77 ll -17.05
  val nou2 = 21.27 ll -17.04
  val boujdour = 26.13 ll -14.50
  val agadir = 30.16 ll -9.24
  val rabat = 34.03 ll -6.83
  val tangierW = 35.79 ll -5.92
  val ceuta = 35.88 ll -5.31
  val alHoceima = 35.15 ll -4.38
  val biharaPlage = 35.07 ll -2.01
  val tunis = 37.08 ll 10.20
  val neTunis = 37.07 ll 11.04
  val sTunis = 33.30 ll 10.08
  val misrata = 32.37 ll 15.03
  val southEast = southLine * eastLine
   
  val polygonLL: PolygonLL = PolygonLL(southWest, nouakchott,nouadhibouBay, nouadhibou, nou2, boujdour, agadir, rabat, tangierW, ceuta, alHoceima,
    biharaPlage, tunis, neTunis, sTunis, misrata, northEast, southEast)
}

object WestAfricaSouth extends EArea2("West Africa\nsouth", 11 ll 0, Plain)
{ val cAfricaN = 4.53.north
  val cAfricaNW = cAfricaN * 8.89.east
  val sangana = 4.31 ll 5.99
  val aiyetoro = 6.20 ll 4.66
  val capeThreePoints = 4.73 ll -2.09
  val liberia = 4.18 ll -7.22
  val sierraLeone = 8.11 ll -13.11
  val dakar = 14.30 ll -17.2
  val keurMassene = 16.7 ll -16.38

  /** The south east corner of West Africa. */
  val westAfricaPtSE =  cAfricaN * SaharaWest.eastLine

  val westAfricaSouthCoast = LinePathLL(sangana, aiyetoro, capeThreePoints, liberia)

  override val polygonLL: PolygonLL = PolygonLL(cAfricaNW, sangana, aiyetoro, capeThreePoints, liberia, sierraLeone, dakar, keurMassene, SaharaWest.southWest,
    SaharaWest.southEast, westAfricaPtSE, cAfricaNW)
}