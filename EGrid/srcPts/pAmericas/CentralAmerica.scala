/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on [[UsaSouthWest]], [[UsaNorthEast]] and [[ElSalPanama]]. */
object Mexico extends EArea2("Mexico", 24 ll -102.4, hillyOce)
{ val brownsville: LatLong = 25.98 ll -97.26
  val puntaJerez: LatLong = 22.89 ll -97.77
  val caboRojo: LatLong = 21.57 ll -97.33
  val p40: LatLong = 18.686 ll -95.956
  val puntaRockoPartido: LatLong = 18.708 ll -95.188

  val pochutala: LatLong = 15.76 ll -96.50
  val maldonado: LatLong = 16.325 ll -98.568
  val papagayoMouth: LatLong = 16.684 ll -99.607
  val p70: LatLong = 18.328 ll -103.429
  val manzanillo: LatLong = 19.15 ll -104
  val puntaDelMario: LatLong = 20.41 ll -105.69
  val grandeDeSantiagoMouth: LatLong = 21.64 ll -105.45
  val cAmericaNW: LatLong = 22.8 ll -105.97
  val p90: LatLong = 25.952 ll -109.445

  override def polygonLL: PolygonLL = PolygonLL(UsaPrariesSouth.galveston, brownsville, puntaJerez, caboRojo, p40, puntaRockoPartido, MexicoEast.coatzacoalcosMouth,
    MexicoEast.tehuantepecMouth, pochutala, maldonado, papagayoMouth, p70, manzanillo, puntaDelMario, grandeDeSantiagoMouth, cAmericaNW, p90,
    UsaSouthWest.rockyPoint, UsaSouthWest.southEast)
}

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on nothing. */
object MexicoEast extends EArea2("MexicoEast", 17 ll -91, hillyJungle)
{ val yucatanNE: LatLong = 21.48 ll -86.97
  val cozumelNorth: LatLong = 20.590 ll -86.724
  val cozumelSouth: LatLong = 20.272 ll -86.988
  val laExoeranza: LatLong = 20.328 ll -87.354
  val pajaros: LatLong = 19.598 ll -87.410
  val belizeCity = 17.495 ll -88.181
  val p20: LatLong = 16.4 ll -88.23
  val seBelize: LatLong = 15.88 ll -88.91

  val elSalvadoreW: LatLong = 13.75 ll -90.13
  val swGuatemala: LatLong = 14.55 ll -92.21
  val tehuantepecMouth: LatLong = 16.19 ll -95.15
  val coatzacoalcosMouth: LatLong = 18.16 ll -94.41
  val p65: LatLong = 18.897 ll -91.383
  val champeton: LatLong = 19.36 ll -90.71
  val nwYucatan: LatLong = 21.01 ll -90.3
  val p95: LatLong = 21.62 ll -88.14

  override def polygonLL: PolygonLL = PolygonLL(yucatanNE, cozumelNorth, cozumelSouth, laExoeranza, pajaros,belizeCity, p20, seBelize, elSalvadoreW,
    swGuatemala, tehuantepecMouth, coatzacoalcosMouth, p65, champeton, nwYucatan, p95)
}

/** [[polygonLL]] graphical representation for central America from El Salvadore to Panama. Depends on [[MexicoEast]]. */
object ElSalPanama extends EArea2("CAmerica", 14.62 ll -86.84, jungle)
{ val p10 = 15.968 ll -85.040
  val eHonduras: LatLong = 15.0 ll -83.17

  /** The mouth of the San Juuan de Nicaragua river, the Caribbean border between Nicaragua and Costa Rica. */
  val sanJuanMouth: LatLong = 10.94 ll -83.70

  val kusapin: LatLong = 8.79 ll -81.38
  val stIsabel: LatLong = 9.53 ll -79.25
  val stIgnacio: LatLong = 9.26 ll -78.12
  val nePanama: LatLong = 8.43 ll -77.26
  val sePanama: LatLong = 7.26 ll -77.9
  val rioEsteroSaladoMouth = 8.181 ll -80.486
  val puntaMala = 7.466 ll -79.999
  val mariato: LatLong = 7.22 ll -80.88
  val puntaLiorona = 8.58 ll -83.72
  val puntaCosiguina: LatLong = 12.91 ll -87.69

  override def polygonLL: PolygonLL = PolygonLL(MexicoEast.seBelize, p10, eHonduras, sanJuanMouth, kusapin, stIsabel, stIgnacio, nePanama, sePanama,
    rioEsteroSaladoMouth, puntaMala, mariato, puntaLiorona, puntaCosiguina, MexicoEast.elSalvadoreW)
}