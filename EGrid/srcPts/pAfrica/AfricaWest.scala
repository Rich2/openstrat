/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for the south of west Africa, south of the Sahara depends on [[SaharaWest]]. */
object WestAfricaSouth extends EArea2("West Africa\nsouth", 11 ll 0, land)
{ val cAfricaN: Latitude = 4.53.north
  val cAfricaNW: LatLong = cAfricaN * 8.89.east
  val sangana: LatLong = 4.31 ll 5.99
  val aiyetoro: LatLong = 6.20 ll 4.66
  val capeThreePoints: LatLong = 4.73 ll -2.09
  val liberia: LatLong = 4.18 ll -7.22
  val sierraLeone: LatLong = 8.11 ll -13.11
  val dakar: LatLong = 14.30 ll -17.2
  val keurMassene: LatLong = 16.7 ll -16.38

  /** The south east corner of West Africa. */
  val westAfricaPtSE: LatLong = 4.53 ll 16.75

  val westAfricaSouthCoast: LinePathLL = LinePathLL(sangana, aiyetoro, capeThreePoints, liberia)

  override val polygonLL: PolygonLL = PolygonLL(cAfricaNW, sangana, aiyetoro, capeThreePoints, liberia, sierraLeone, dakar, keurMassene,
    SaharaWest.southWest, pMed.SaharaCentral.southWest, pMed.SaharaCentral.southEast, westAfricaPtSE, cAfricaNW)
}

/** [[PolygonLL]] graphic for the west of the Sahara depends on [[pMed.Maghreb]]. */
object SaharaWest extends EArea2("SaharaWest", 22 ll -5.50, desert)
{ val southWest: LatLong = 17 ll -16.27
  val nouakchott: LatLong = 18.078 ll -16.02
  val nouadhibouBay: LatLong = 21.28 ll -16.90
  val nouadhibou: LatLong = 20.77 ll -17.05
  val nou2: LatLong = 21.27 ll -17.04
  val boujdour: LatLong = 26.13 ll -14.50
  val p80: LatLong = 28.741 ll -11.074
  val p90: LatLong = 29.235 ll -10.328

  override val polygonLL: PolygonLL = PolygonLL(pMed.Maghreb.southEast, pMed.SaharaCentral.southWest, southWest, nouakchott, nouadhibouBay,
    nouadhibou, nou2, boujdour, p80, p90, pMed.Maghreb.agadir)
}