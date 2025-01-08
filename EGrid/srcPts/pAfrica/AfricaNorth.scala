/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom.*, pglobe.*, egrid.*, WTiles.*

/** [[PolygonLL]] graphic for Canaries depends on nothing. */
object Canarias extends IslandPoly("Canarias", 27.96 ll -15.60, oceanic)
{ override val area: Kilares = 7493.kilares

  val elHierro: LatLong = 27.72 ll -18.15
  val laPalma: LatLong = 28.85 ll -17.92
  val lanzarote: LatLong = 29.24 ll -13.47
  val fuerteventura: LatLong = 28.24 ll -13.94
  val granCanaria: LatLong = 27.74 ll -15.60

  val polygonLL: PolygonLL = PolygonLL(elHierro, laPalma, lanzarote, fuerteventura, granCanaria)
}

/** [[PolygonLL]] graphic for the west of the Sahara depends on [[pMed.MaghrebEast]]. */
object SaharaWest extends EarthPoly("SaharaWest", 22 ll -5.50, deshot)
{ val southWest: LatLong = 17 ll -16.27
  val nouakchott: LatLong = 18.078 ll -16.02
  val nouadhibouBay: LatLong = 21.28 ll -16.90
  val nouadhibou: LatLong = 20.77 ll -17.05
  val nou2: LatLong = 21.27 ll -17.04
  val boujdour: LatLong = 26.13 ll -14.50
  val p80: LatLong = 28.741 ll -11.074
  val p90: LatLong = 29.235 ll -10.328

  override val polygonLL: PolygonLL = PolygonLL(pMed.MaghrebEast.southEast, pMed.SaharaCentral.southWest, southWest, nouakchott, nouadhibouBay,
    nouadhibou, nou2, boujdour, p80, p90, pMed.MaghrebWest.agadir, pMed.MaghrebWest.southEast)
}

/** [[PolygonLL]] graphic for the south of west Africa, south of the Sahara depends on [[SaharaWest]]. */
object WestAfricaSouth extends EarthPoly("West Africa\nsouth", 11 ll 0, savannah)
{
  val cAfricaNW: LatLong = 4.53 ll 8.89
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
object AfricaCentral extends EarthPoly("Central Africa", 10 ll 32, savannah)
{
  val polygonLL: PolygonLL = PolygonLL(WestAfricaSouth.westAfricaPtSE, pMed.SaharaCentral.southEast, AfricaHorn.tekeze, AfricaHorn.lakeChamoSouth,
    LakeTurkana.northEast, LakeTurkana.northWest, Uganda.northWest, CongoWest.northEast)
}