/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._

/** Will be split up. Dependant on [[UsaWest]] and [[UsaEast]]. */
object CentralAmerica extends EArea2("CAmerica", degs (17.31, - 94.16), jungle)
{ val brownsville = degs(25.98, -97.26)
  val coatz = degs(18.13, -94.5)
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
  val quebrada = degs(8.04, -82.88)
  val swGuatemala = degs(14.55, -92.21)
  val pochutala = degs(15.76, -96.50)
  val manzanillo = degs(19.15, -104)
  val cAmericaNW= 22.8 ll -105.97

  override def polygonLL: PolygonLL = PolygonLL(UsaWest.galveston, brownsville, coatz, champeton, nwYucatan, neYucatan, seBelize, eHonduras, kusapin,
    stIsabel, stIgnacio, nePanama, sePanama, mariato, quebrada, swGuatemala, pochutala, manzanillo, cAmericaNW, UsaWest.rockyPoint)
}

object Baja extends EArea2("Baja", 27.80 ll -113.31, plain){
  val cabotPulmo = 23.37 ll -109.44
  val sanLucas = 22.87 ll -109.91
  val wBaja = 27.84 ll -115.07

  override def polygonLL: PolygonLL = PolygonLL(UsaWest.sanDiego, UsaWest.montague, cabotPulmo, sanLucas, wBaja)
}

object Cuba extends EArea2("Cuba", 21.97 ll -78.96, jungle){
  val wCuba = 21.86 ll -84.95
  val havana = 23.14 ll -82.39
  val eCuba = 20.22 ll -74.13
  val cabotCruz = 19.84 ll -77.73
  val yara = 20.45 ll -77.07
  val surgidero = 22.68 ll -82.29
  override def polygonLL: PolygonLL = PolygonLL(wCuba, havana, eCuba, cabotCruz, yara, surgidero)
}