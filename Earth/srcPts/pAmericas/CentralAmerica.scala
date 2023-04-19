/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAmericas
import geom._, pglobe._, LatLong._

/** [[polygonLL]] graphical representation for most of Mexico. Dependant on [[UsaWest]], [[UsaEast]] and [[AmericasCentral]]. */
object Mexico extends EArea2("Mexico", 24 ll -102.4, Hilly)
{ val brownsville = 25.98 ll -97.26
  val pochutala = 15.76 ll -96.50
  val manzanillo = 19.15 ll -104
  val cAmericaNW= 22.8 ll -105.97

  override def polygonLL: PolygonLL = PolygonLL(UsaWest.galveston, brownsville, AmericasCentral.coatzacoalcosMouth, AmericasCentral.tehuantepecMouth,
    pochutala, manzanillo, cAmericaNW, UsaWest.rockyPoint)
}

/** [[polygonLL]] graphical representation for central America not including most of Mexico. Depends on nothing. */
object AmericasCentral extends EArea2("CAmerica", 14.62 ll -86.84, Jungles)
{ val coatzacoalcosMouth: LatLong = 18.16 ll -94.41
  val champeton = degs(19.36, -90.71)
  val nwYucatan = degs(21.01, -90.3)
  val neYucatan = degs(21.48, -86.97)
  val seBelize = degs(15.88, -88.91)
  val eHonduras = degs(15.0, -83.17)
  val kusapin = degs(8.79, -81.38)
  val stIsabel = degs(9.53, -79.25)
  val stIgnacio = degs(9.26, -78.12)
  val nePanama = degs(8.43, -77.26)

  val sePanama = degs(7.26, -77.9)
  val mariato = degs(7.22, -80.88)
  val swGuatemala = degs(14.55, -92.21)
  val tehuantepecMouth: LatLong = 16.19 ll -95.15

  override def polygonLL: PolygonLL = PolygonLL(coatzacoalcosMouth, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin, stIsabel,
    stIgnacio, nePanama, sePanama, mariato, swGuatemala, tehuantepecMouth)
}

/** [[polygonLL]] graphical representation for Cuba. Depends on nothing. */
object Cuba extends EArea2("Cuba", 21.97 ll -78.96, Jungles)
{ val wCuba = 21.86 ll -84.95
  val havana = 23.14 ll -82.39
  val eCuba = 20.22 ll -74.13
  val cabotCruz = 19.84 ll -77.73
  val yara = 20.45 ll -77.07
  val surgidero = 22.68 ll -82.29
  override def polygonLL: PolygonLL = PolygonLL(wCuba, havana, eCuba, cabotCruz, yara, surgidero)
}