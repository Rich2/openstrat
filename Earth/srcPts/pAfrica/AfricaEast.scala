/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._

/** [[PolygonLL]] graphic for north east Africa. Depends on [[SaharaWest]] and [[Sinai]]. */
object SaharaEast extends EArea2("Sahara\neast", 24 ll 25, Deserts)
{ val elAgheila = 30.12 ll 19.08
  val benghazi = 32.12 ll 20.05
  val derna = 32.93 ll 22.15
  val p90 = 30.82 ll 29.09
  val baltim = 31.60 ll 31.01

  val p5 = 29.59 ll 32.34
  val southEast = 17 ll 39.4

  val polygonLL: PolygonLL = PolygonLL(SaharaWest.southEast, SaharaWest.northEast, elAgheila, benghazi, derna, p90, baltim, middleEast.Sinai.portSaid,
    middleEast.Sinai.suez, p5, southEast)
}

object EastAfricaSouth extends EArea2("East Africa\nsouth", 10 ll 32, Plain)
{ val dankalia = 14 ll 41.66// eAfricaN
  val berbera = 10 ll 44
  val hornAfrica = 12 ll 51
  val iskushuban1 = 10.44 ll 51.41
  val iskushuban2 = 10.31 ll 50.90
  val rasMacbar = 9.47 ll 50.85

  val southEast = WestAfricaSouth.cAfricaN * 48.east
  val cAfricaNE = WestAfricaSouth.cAfricaN * 32.east
    
  val polygonLL: PolygonLL = PolygonLL(WestAfricaSouth.westAfricaPtSE, SaharaWest.southEast, SaharaEast.southEast, dankalia, berbera, hornAfrica, iskushuban1,
    iskushuban2, rasMacbar, southEast, cAfricaNE)
}