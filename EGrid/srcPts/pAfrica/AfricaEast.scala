/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for the south of east Africa. Depends on [[WestAfricaSouth]] [[SaharaCentral]] and [[pMed.SaharaEast]]. */
object EastAfricaSouth extends EArea2("East Africa\nsouth", 10 ll 32, savannah)
{ val dankalia: LatLong = 14 ll 41.66// eAfricaN
  val berbera: LatLong = 10 ll 44
  val p33: LatLong = 12.89 ll 42.99
  val hornAfrica: LatLong = 12 ll 51
  val iskushuban1: LatLong = 10.44 ll 51.41
  val iskushuban2: LatLong = 10.31 ll 50.90
  val rasMacbar: LatLong = 9.47 ll 50.85

  val southEast: LatLong = WestAfricaSouth.cAfricaN * 48.east
  val cAfricaNE: LatLong = WestAfricaSouth.cAfricaN * 32.east
    
  val polygonLL: PolygonLL = PolygonLL(WestAfricaSouth.westAfricaPtSE, pMed.SaharaCentral.southEast, pMed.SaharaEast.southEast, dankalia, p33, berbera, hornAfrica, iskushuban1,
    iskushuban2, rasMacbar, southEast, cAfricaNE)
}