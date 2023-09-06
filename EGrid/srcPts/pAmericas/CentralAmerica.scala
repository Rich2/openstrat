/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, egrid._, WTile._

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on [[UsaWest]], [[UsaEast]] and [[AmericasCentral]]. */
object Mexico extends EArea2("Mexico", 24 ll -102.4, hills)
{ val brownsville = 25.98 ll -97.26
  val pochutala = 15.76 ll -96.50
  val manzanillo = 19.15 ll -104
  val cAmericaNW= 22.8 ll -105.97

  override def polygonLL: PolygonLL = PolygonLL(UsaMid.galveston, brownsville, AmericasCentral.coatzacoalcosMouth, AmericasCentral.tehuantepecMouth,
    pochutala, manzanillo, cAmericaNW, UsaWest.rockyPoint, UsaWest.southEast)
}

/** [[polygonLL]] graphical representation for central America not including most of Mexico. Depends on nothing. */
object AmericasCentral extends EArea2("CAmerica", 14.62 ll -86.84, jungle)
{ val coatzacoalcosMouth: LatLong = 18.16 ll -94.41
  val champeton: LatLong = 19.36 ll -90.71
  val nwYucatan: LatLong = 21.01 ll -90.3
  val neYucatan: LatLong = 21.48 ll -86.97
  val seBelize: LatLong = 15.88 ll -88.91
  val eHonduras: LatLong = 15.0 ll -83.17
  val kusapin: LatLong = 8.79 ll -81.38
  val stIsabel: LatLong = 9.53 ll -79.25
  val stIgnacio: LatLong = 9.26 ll -78.12
  val nePanama: LatLong = 8.43 ll -77.26

  val sePanama: LatLong = 7.26 ll -77.9
  val mariato: LatLong = 7.22 ll -80.88
  val swGuatemala: LatLong = 14.55 ll -92.21
  val tehuantepecMouth: LatLong = 16.19 ll -95.15

  override def polygonLL: PolygonLL = PolygonLL(coatzacoalcosMouth, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin, stIsabel,
    stIgnacio, nePanama, sePanama, mariato, swGuatemala, tehuantepecMouth)
}

/** [[polygonLL]] graphical representation for Cuba. Depends on nothing. */
object Cuba extends EArea2("Cuba", 21.97 ll -78.96, jungle)
{ val wCuba: LatLong = 21.86 ll -84.95
  val havana: LatLong = 23.14 ll -82.39
  val eCuba: LatLong = 20.22 ll -74.13
  val cabotCruz: LatLong = 19.84 ll -77.73
  val yara: LatLong = 20.45 ll -77.07
  val surgidero: LatLong = 22.68 ll -82.29

  override def polygonLL: PolygonLL = PolygonLL(wCuba, havana, eCuba, cabotCruz, yara, surgidero)
}