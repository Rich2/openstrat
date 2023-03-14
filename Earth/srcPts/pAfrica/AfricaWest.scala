/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._


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
  val westAfricaPtSE =  cAfricaN * pMed.SaharaWest.eastLine

  val westAfricaSouthCoast = LinePathLL(sangana, aiyetoro, capeThreePoints, liberia)

  override val polygonLL: PolygonLL = PolygonLL(cAfricaNW, sangana, aiyetoro, capeThreePoints, liberia, sierraLeone, dakar, keurMassene,
    pMed.SaharaWest.southWest, pMed.SaharaWest.southEast, westAfricaPtSE, cAfricaNW)
}