/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._

/** [[PolygonLL]] graphic for the south of west Africa, south of the Sahara depends on [[SaharaWest]]. */
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
  val westAfricaPtSE = 4.53 ll 16.75

  val westAfricaSouthCoast = LinePathLL(sangana, aiyetoro, capeThreePoints, liberia)

  override val polygonLL: PolygonLL = PolygonLL(cAfricaNW, sangana, aiyetoro, capeThreePoints, liberia, sierraLeone, dakar, keurMassene,
    SaharaWest.southWest, pMed.SaharaCentral.southWest, pMed.SaharaCentral.southEast, westAfricaPtSE, cAfricaNW)
}

/** [[PolygonLL]] graphic for the west of the Sahara depends on [[pMed.Maghreb]]. */
object SaharaWest extends EArea2("SaharaWest", 22 ll -5.50, Deserts)
{ val southWest: LatLong = 17 ll -16.27
  val nouakchott: LatLong = 18.078 ll -16.02
  val nouadhibouBay: LatLong = 21.28 ll -16.90
  val nouadhibou: LatLong = 20.77 ll -17.05
  val nou2: LatLong = 21.27 ll -17.04
  val boujdour: LatLong = 26.13 ll -14.50

  override val polygonLL: PolygonLL = PolygonLL(pMed.Maghreb.southEast, pMed.SaharaCentral.southWest, southWest, nouakchott, nouadhibouBay,
    nouadhibou, nou2, boujdour, pMed.Maghreb.agadir)
}