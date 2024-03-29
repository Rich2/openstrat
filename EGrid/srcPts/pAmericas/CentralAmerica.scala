/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on [[UsaSouthWest]], [[UsaNorthEast]] and [[ElSalPanama]]. */
object Mexico extends EArea2("Mexico", 24 ll -102.4, hillyOce)
{ val brownsville: LatLong = 25.98 ll -97.26
  val puntaJerez: LatLong = 22.89 ll -97.77
  val caboRojo: LatLong = 21.57 ll -97.33
  val pochutala: LatLong = 15.76 ll -96.50
  val manzanillo: LatLong = 19.15 ll -104
  val puntaDelMario = 20.41 ll -105.69
  val grandeDeSantiagoMouth = 21.64 ll -105.45
  val cAmericaNW: LatLong = 22.8 ll -105.97

  override def polygonLL: PolygonLL = PolygonLL(UsaPrariesSouth.galveston, brownsville, puntaJerez, caboRojo, MexicoEast.coatzacoalcosMouth,
    MexicoEast.tehuantepecMouth, pochutala, manzanillo, puntaDelMario, grandeDeSantiagoMouth, cAmericaNW, UsaSouthWest.rockyPoint, UsaSouthWest.southEast)
}

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on [[UsaSouthWest]], [[UsaNorthEast]] and [[ElSalPanama]]. */
object MexicoEast extends EArea2("MexicoEast", 17 ll -91, hillyJungle)
{ val coatzacoalcosMouth: LatLong = 18.16 ll -94.41
  val champeton: LatLong = 19.36 ll -90.71
  val nwYucatan: LatLong = 21.01 ll -90.3
  val p30 = 21.62 ll -88.14
  val neYucatan: LatLong = 21.48 ll -86.97
  val p40 = 16.4 ll -88.23
  val seBelize: LatLong = 15.88 ll -88.91
  val elSalvadoreW: LatLong = 13.75 ll -90.13
  val swGuatemala: LatLong = 14.55 ll -92.21
  val tehuantepecMouth: LatLong = 16.19 ll -95.15

  override def polygonLL: PolygonLL = PolygonLL(coatzacoalcosMouth, champeton, nwYucatan, p30, neYucatan, p40, seBelize, elSalvadoreW, swGuatemala,
    tehuantepecMouth)
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