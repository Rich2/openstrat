/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package psoam
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for far north of the Andes. Dependent on [[pnAmer.Panama]]. */
object AndesFarNorth extends EarthPoly("Andes far north", 5.105 ll -75.212, mtainJungle)
{ val p2: LatLong = 7.923 ll -76.747
  val p5: LatLong = 8.560 ll -76.930
  val p10: LatLong = 9.446 ll -75.941
  val puntaCanoas: LatLong = 10.571 ll -75.512
  val p15: LatLong = 11.108 ll -74.847
  val puntaChegue: LatLong = 11.324 ll -74.160
  val nColumbia: LatLong = 12.458 ll -71.664
  val zapara: LatLong = 10.955 ll -71.530
  val caboSanRoman: LatLong = 12.196 ll -70.020

  val northEast: LatLong = 10.480 ll -68.106
  val p20: LatLong = 9.739 ll -68.148
  val p24: LatLong = 9.533 ll -69.293
  val p30: LatLong = 6.613 ll -71.833
  val p35: LatLong = 2.223 ll -73.791
  val eastEdgeNorth: LinePathLL = LinePathLL(northEast, p20, p24, p30, p35)

  val p40: LatLong = 0.162 ll -77.256
  val ecuadorSE: LatLong = -2.961 ll -77.734
  val eastEdgeSouth: LinePathLL = LinePathLL(p35, p40, ecuadorSE)

  val IslaPunaSE: LatLong = -3.012 ll -80.129
  val peurtoNaranjal: LatLong = -2.666 ll -79.793
  val salinas: LatLong = -2.188 ll -81.001
  val puntaTortuga: LatLong = 0.775 ll -80.089
  val p95: LatLong = 4.258 ll -77.524
  val bahiaSolano10: LatLong = 6.55 ll -77.32

  override val polygonLL: PolygonLL = LinePathLL(p2, p5, p10, puntaCanoas, p15, puntaChegue, nColumbia, zapara, caboSanRoman) ++ eastEdgeNorth +-+
    eastEdgeSouth |++| LinePathLL(peurtoNaranjal, IslaPunaSE, salinas, puntaTortuga, p95, bahiaSolano10, pnAmer.Panama.southEast, pnAmer.Panama.northEast)
}

/** [[polygonLL]] graphical representation for Columbia and Venezuela. Dependent on [[AndesFarNorth]] and [[Guyana]]. */
object ColomVenez extends EarthPoly("Columbia and\nVenezuela", 0 ll -70.0, tropical)
{ val caicara: LatLong = 10.11 ll -64.74
  val margaritaE: LatLong = 10.98 ll -64.41
  val trinidadNE: LatLong = 10.84 ll -60.93
  val p55: LatLong = 0.719 ll -63.146
  val p60: LatLong = 0.257 ll -65.806

  override val polygonLL: PolygonLL = LinePathLL(caicara, margaritaE, trinidadNE, Guyana.northWest, Guyana.southWest, p55, p60) |++<|
    AndesFarNorth.eastEdgeNorth
}

/** [[polygonLL]] graphical representation for Guyana, Suriname and French Guiana. Dependent on nothing. */
object Guyana extends EarthPoly("Guyana", 0 ll -70.0, hillyJungle)
{ val northWest: LatLong = 8.364 ll -59.837
  val courantyneMouth: LatLong = 6.01 ll -57.082
  val p30: LatLong = 4.44 ll -51.520
  val oyaopokMouth: LatLong = 4.242 ll -51.627
  val southEast: LatLong = 2.209 ll -52.885
  val southWest: LatLong = 1.214 ll -58.818
  override val polygonLL: PolygonLL = PolygonLL(northWest, courantyneMouth, p30, oyaopokMouth, southEast, southWest)
}

/** [[polygonLL]] graphical representation for north Andes. Dependent on [[ElSalPanama]]. */
object AndesNearNorth extends EarthPoly("Andes near north", 5.105 ll -75.212, mtainSahel)
{ val p15: LatLong = -13.550 ll -68.636
  val southEast: LatLong = -14.776 ll -67.164

  val chileNW: LatLong = -18.328 ll -70.420
  val p60: LatLong = -13.91 ll -76.39
  val p61: LatLong = -13.461 ll -76.187

  val p68: LatLong = -7.711 ll -79.465
  val p70: LatLong = -6.054 ll -81.115
  val nPeru: LatLong = -5 ll -81
  val west: LatLong = -4.68 ll -81.33

  override val polygonLL: PolygonLL = LinePathLL(AndesFarNorth.ecuadorSE, p15, southEast) ++< LakeTiticaca.mainCoast |++| LinePathLL(chileNW, p60, p61, p68,
    p70, nPeru, west, AndesFarNorth.peurtoNaranjal)
}

/** [[polygonLL]] graphical representation for the west of Amazon basin. Dependent on [[AndesFarNorth]], [[ColomVenez]] and [[AndesNearNorth]]. */
object AmazonWest extends EarthPoly("Amazon west", -20 ll -70.0, jungle)
{ override val polygonLL: PolygonLL = LinePathLL(ColomVenez.p60, ColomVenez.p55, AmazonEast.southWest, AndesNearNorth.p15) |++<| AndesFarNorth.eastEdgeSouth
}

/** [[polygonLL]] graphical representation for the east of the Amazon basin. Dependent on [[ColomVenez]] [[Guyana]]. */
object AmazonEast extends EarthPoly("Amazon east", -20 ll -70.0, jungle)
{ val nAmapa: LatLong = 4.39 ll -51.51
  val amazonMouthS: LatLong = -0.18 ll -49.3
  val marajoMouth: LatLong = -0.430 ll -48.136
  val mearimMouth: LatLong = -2.419 ll -44.330

  val acailandia: LatLong = -4.943 ll -47.497
  val p50: LatLong = -9.983 ll -54.897
  val ariel: LatLong = -9.858 ll -58.208
  val southWest: LatLong = -10.040 ll -61.941

  override val polygonLL: PolygonLL = PolygonLL(Guyana.oyaopokMouth, nAmapa, amazonMouthS, marajoMouth, mearimMouth, acailandia, p50, ariel, southWest,
    ColomVenez.p55, Guyana.southWest, Guyana.southEast)
}

/** [[polygonLL]] graphical representation for Lake Titicaca. Dependent on nothing. */
object LakeTiticaca extends LakePoly("Lake Titicaca", -15.846 ll -69.338, lake)
{ override val area: Kilares = 8372.kilares

  val south: LatLong = -16.591 ll -68.863
  val puno: LatLong = -15.832 ll -70.019
  val north: LatLong = -15.235 ll -69.693
  val p30: LatLong = -15.944 ll -68.714
  val p40: LatLong = -16.250 ll -68.567
  val mainCoast: LinePathLL = LinePathLL(south, puno, north, p30, p40)

  val p45: LatLong = -16.436 ll -68.934
  val seCoast: LinePathLL = LinePathLL(p40, p45, south)


  override val polygonLL: PolygonLL = mainCoast |-++-| seCoast
}

/** [[polygonLL]] graphical representation for the centre of South America. Dependent on [[AmazonEast]], [[SouthAmericaEast]], [[SouthAmericaMiddle]],
 * [[AndesMiddle]], [[AndesNearNorth]] and [[AmazonWest]]. */
object SouthAmericaCentral extends EarthPoly("South America\ncentral", -10.04 ll -45.81, tropical)
{
  override val polygonLL: PolygonLL = PolygonLL(AmazonEast.southWest, AmazonEast.ariel, AmazonEast.p50, SouthAmericaMiddle.nwSAmericaES, AndesMiddle.northEast,
    AndesNearNorth.southEast, AndesNearNorth.p15)
}

/** [[polygonLL]] graphical representation for the east of South America. Dependent on [[AmazonEast]], [[SouthAmericaMiddle]] and [[AmazonWest]]. */
object SouthAmericaEast extends EarthPoly("South America\neast", -10.04 ll -45.81, tropical)
{ val p10: LatLong = -2.34 ll -43.32
  val pedraFurada: LatLong = -2.784 ll -40.500
  val p25: LatLong = -5.152 ll -35.496
  val caboBranco: LatLong = -7.146 ll -34.796
  val paulista: LatLong = -7.918 ll -34.820
  val p50 = -9.078 ll -35.245
  val p70: LatLong = -13.23 ll -38.94
  val jequitinhonhaMouth: LatLong = -15.84 ll -38.85

  override val polygonLL: PolygonLL = PolygonLL(AmazonEast.mearimMouth, p10, pedraFurada, p25, caboBranco, paulista, p50, p70, jequitinhonhaMouth,
    SouthAmericaMiddle.sSAmericaNE, SouthAmericaMiddle.nwSAmericaES, AmazonEast.p50, AmazonEast.acailandia)
}