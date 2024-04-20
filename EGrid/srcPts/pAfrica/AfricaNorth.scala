/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for the west of the Sahara depends on [[pMed.Maghreb]]. */
object SaharaWest extends EarthArea("SaharaWest", 22 ll -5.50, deshot)
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

/** [[PolygonLL]] graphic for the south of west Africa, south of the Sahara depends on [[SaharaWest]]. */
object WestAfricaSouth extends EarthArea("West Africa\nsouth", 11 ll 0, savannah)
{ val cAfricaN: Latitude = 4.53.north
  val cAfricaNW: LatLong = cAfricaN * 8.89.east
  val walkersIsland: LatLong = 4.881 ll 5.149
  val sangana: LatLong = 4.31 ll 5.99
  val aiyetoro: LatLong = 6.20 ll 4.66
  val denu: LatLong = 6.093 ll 1.154
  val ketaLagoon: LatLong = 5.789 ll 0.925
  val capeThreePoints: LatLong = 4.73 ll -2.09
  val liberia: LatLong = 4.18 ll -7.22
  val mambaPoint: LatLong = 6.313 ll -10.817
  val mania: LatLong = 7.635 ll -13.065
  val sierraLeone: LatLong = 8.11 ll -13.11
  val conakry: LatLong = 9.508 ll -13.718
  val iihaDeOrango: LatLong = 11.089 ll -16.247
  val diembereng: LatLong = 12.482 ll -16.792
  val dakar: LatLong = 14.30 ll -17.2
  val keurMassene: LatLong = 16.7 ll -16.38

  /** The south east corner of West Africa. */
  val westAfricaPtSE: LatLong = 4.53 ll 16.75

  val westAfricaSouthCoast: LinePathLL = LinePathLL(walkersIsland, sangana, aiyetoro, denu, ketaLagoon, capeThreePoints, liberia)

  override val polygonLL: PolygonLL = PolygonLL(cAfricaNW, sangana, aiyetoro, capeThreePoints, liberia, mambaPoint, mania, sierraLeone, conakry, iihaDeOrango,
    diembereng, dakar, keurMassene, SaharaWest.southWest, pMed.SaharaCentral.southWest, pMed.SaharaCentral.southEast, westAfricaPtSE, cAfricaNW)
}

/** [[PolygonLL]] graphic for the south of east Africa. Depends on [[WestAfricaSouth]] [[SaharaCentral]] and [[pMed.SaharaEast]]. */
object AfricaCentral extends EarthArea("Central Africa", 10 ll 32, savannah)
{

  val cAfricaNE: LatLong = WestAfricaSouth.cAfricaN * 32.east

  val polygonLL: PolygonLL = PolygonLL(WestAfricaSouth.westAfricaPtSE, pMed.SaharaCentral.southEast, AfricaHorn.tekeze, AfricaHorn.lakeChamoSouth,
    AfricaHorn.lakeTurkanaNW, cAfricaNE)
}

/** [[PolygonLL]] graphic for the south of east Africa. Depends on [[WestAfricaSouth]] [[SaharaCentral]] and [[pMed.SaharaEast]]. */
object AfricaHorn extends EarthArea("Horn of Africa", 10 ll 32, sahel)
{
  val p0 = 15.514 ll 39.484
  val dankalia: LatLong = 14 ll 41.66// eAfricaN
  val p33: LatLong = 12.89 ll 42.99
  val berbera: LatLong = 10 ll 44
  val hornAfrica: LatLong = 12 ll 51
  val iskushuban1: LatLong = 10.44 ll 51.41
  val iskushuban2: LatLong = 10.31 ll 50.90
  val rasMacbar: LatLong = 9.47 ll 50.85
  val p50: LatLong = WestAfricaSouth.cAfricaN * 48.east
  val equatorEast: LatLong = 0.0 ll 42.4

  val lakeTurkanaSouth: LatLong = 2.406 ll 36.550
  val lakeTurkanaNW: LatLong = 4.483 ll 35.960
  val lakeChamoSouth: LatLong = 5.708 ll 37.486
  val tekeze: LatLong = 13.808 ll 38.317

  val polygonLL: PolygonLL = PolygonLL(p0, dankalia, p33, berbera, hornAfrica, iskushuban1, iskushuban2, rasMacbar, p50, equatorEast,
    LakeVictoria.kusa, LakeVictoria.kisuma, lakeTurkanaSouth, lakeTurkanaNW, lakeChamoSouth, tekeze)
}