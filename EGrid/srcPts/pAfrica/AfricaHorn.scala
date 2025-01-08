/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom.*, pglobe.*, egrid.*, WTiles.*

/** [[PolygonLL]] graphic for the south of east Africa. Depends on [[WestAfricaSouth]] [[SaharaCentral]] and [[pMed.SaharaEast]]. */
object AfricaHorn extends EarthPoly("Horn of Africa", 10 ll 32, sahel)
{ val p0 = 15.514 ll 39.484
  val dankalia: LatLong = 14 ll 41.66// eAfricaN
  val p33: LatLong = 12.89 ll 42.99
  val berbera: LatLong = 10 ll 44
  val hornAfrica: LatLong = 12 ll 51
  val iskushuban1: LatLong = 10.44 ll 51.41
  val iskushuban2: LatLong = 10.31 ll 50.90
  val rasMacbar: LatLong = 9.47 ll 50.85
  val p50: LatLong = 4.53 ll 48
  val merca: LatLong = 1.716 ll 44.776

  //val lakeTurkanaSouth: LatLong = 2.406 ll 36.550
  //val lakeTurkanaNW: LatLong = 4.483 ll 35.960
  val lakeChamoSouth: LatLong = 5.708 ll 37.486
  val tekeze: LatLong = 13.808 ll 38.317

  override val polygonLL: PolygonLL = PolygonLL(p0, dankalia, p33, berbera, hornAfrica, iskushuban1, iskushuban2, rasMacbar, p50, merca,
    lakeChamoSouth, tekeze)
}

/** [[PolygonLL]] graphic object for Lake Turkana. Depends on nothing. */
object LakeTurkana extends LakePoly("Lake\nTurkana", 3.725 ll 36.043, Lake)
{ override val area: Kilares = 6405.kilares

  val northEast: LatLong = 4.490 ll 36.182
  val p25: LatLong = 3.329 ll 36.216
  val p40: LatLong = 2.887 ll 36.683
  val south: LatLong = 2.407 ll 36.549
  val eastCoast: LinePathLL = LinePathLL(northEast, p25, p40, south)

  val p70: LatLong = 3.209 ll 36.137
  val p85: LatLong = 3.613 ll 35.833
  val northWest: LatLong = 4.480 ll 35.958
  val westCoast = LinePathLL(south, p70, p85, northWest)

  override val polygonLL: PolygonLL = eastCoast |++| westCoast
}

/** [[PolygonLL]] graphic for the south of east Africa. Depends on [[WestAfricaSouth]] [[SaharaCentral]] and [[pMed.SaharaEast]]. */
object Kenya extends EarthPoly("Kenya", 10 ll 32, savannah)
{
  val equatorEast: LatLong = 0.0 ll 42.4

  override val polygonLL: PolygonLL = LinePathLL(AfricaHorn.lakeChamoSouth, AfricaHorn.merca, equatorEast) ++< LakeVictoria.eastCoast |++<|
    LakeTurkana.eastCoast
}