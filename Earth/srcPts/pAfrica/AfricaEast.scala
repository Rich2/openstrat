/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._

object EastAfricaSouth extends EArea2("East Africa\nsouth", 10 ll 32, Plain)
{ val dankalia = 14 ll 41.66// eAfricaN
  val berbera = 10 ll 44
  val hornAfrica = 12 ll 51
  val iskushuban1 = 10.44 ll 51.41
  val iskushuban2 = 10.31 ll 50.90
  val rasMacbar = 9.47 ll 50.85

  val southEast = WestAfricaSouth.cAfricaN * 48.east
  val cAfricaNE = WestAfricaSouth.cAfricaN * 32.east
    
  val polygonLL: PolygonLL = PolygonLL(WestAfricaSouth.westAfricaPtSE, pMed.SaharaCentral.southEast, pMed.SaharaEast.southEast, dankalia, berbera, hornAfrica, iskushuban1,
    iskushuban2, rasMacbar, southEast, cAfricaNE)
}