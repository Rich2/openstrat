/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package psoam
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for the near south of South America. Depends on [[SouthAmericaFS]]. */
object SouthAmericaNS extends EarthPoly("South America\nnear south", -36.495 ll -66.0, savannah)
{ val northEast: LatLong = -34.165 ll -58.232  
  val islaSarandi: LatLong = -34.449 ll -58.505
  val puntaPiedras: LatLong = -35.426 ll -57.131
  val p18: LatLong = -36.331 ll -56.747
  val marDelPlata: LatLong = -38.076 ll -57.542
  val trinidadIsland = -39.226 ll -61.876
  val p45: LatLong = -40.584 ll -62.175
  val p48: LatLong = -41.136 ll -63.046
  val puntaLavapie: LatLong = -37.15 ll -73.59
  val northWest: LatLong = -32.608 ll -71.467
  val mendoza: LatLong = -32.826 ll -68.868

  override val polygonLL: PolygonLL = PolygonLL(northEast, islaSarandi, puntaPiedras, p18, marDelPlata, trinidadIsland, p45, p48, SouthAmericaFS.northEast,
    SouthAmericaFS.northWest, puntaLavapie, northWest, mendoza)
}

/** [[polygonLL]] graphical representation for south Argentine and south Chile. Depends on nothing. */
object SouthAmericaFS extends EarthPoly("South America\nfar south", -27.0 ll -70.22, savannah)
{ val northEast: LatLong = -40.76 ll -65.02
  val puntaNorte: LatLong = -42.075 ll -63.759
  val puntaDelgada: LatLong = -42.764 ll -63.634
  val p16: LatLong = -45.061 ll -65.585
  val p20: LatLong = -46.048 ll -67.620
  val p21: LatLong = -46.573 ll -67.415
  val p22: LatLong = -47.034 ll -66.704
  val cabotBlanco: LatLong = -47.201 ll -65.735
  val puntaMedanosa: LatLong = -48.115 ll -65.923
  val p25: LatLong = -49.914 ll -67.817
  val p29: LatLong = -50.331 ll -68.876
  val southEast: LatLong = -52.331 ll -68.355
  val p40: LatLong = -52.386 ll -69.486
  val p45: LatLong = -52.834 ll -70.627
  val furia: LatLong = -55.470 ll -72.312
  val islaCarlos: LatLong = -54.076 ll -73.473
  val p60: LatLong = -53.597 ll -73.854
  val p70: LatLong = -52.741 ll -74.712
  val p74: LatLong = -50.660 ll -75.520
  val covadonga: LatLong = -49.001 ll -75.685
  val islaEsmeralda : LatLong= -48.86 ll -75.62
  val northWest: LatLong = -39.97 ll -73.67

  override val polygonLL: PolygonLL = PolygonLL(northEast, puntaNorte, puntaDelgada, p16, p20, p21, p22, cabotBlanco, puntaMedanosa, p25, p29, southEast, p40,
    p45, furia, islaCarlos, p60, p70, p74, covadonga, islaEsmeralda, northWest)
}

/** [[polygonLL]] graphical representation for the Falkland Islands 12173km². Depends on nothing. */
object DelFuego extends EarthPoly("Tierra del Fuego", -51.781 ll -59.211, hillySteppe)
{ val north: LatLong = -52.451 ll -69.413
  val east: LatLong = -54.657 ll -65.125
  val hornos: LatLong = -55.977 ll -67.273
  val west: LatLong = -54.644 ll -72.078
  val p65: LatLong = -54.437 ll -70.922

  override def polygonLL: PolygonLL = PolygonLL(north, east, hornos, west, p65)
}

/** [[polygonLL]] graphical representation for the Falkland Islands 12173km². Depends on nothing. */
object Falklands extends IslandPoly("Falkland Islands", -51.781 ll -59.211, hillySteppe)
{ override val area: Kilares = 12173.kilares

  val eiNorthWest: LatLong = -51.233 ll -58.970
  val capePembroke: LatLong = -51.682 ll -57.715
  val barren: LatLong = -52.394 ll -59.724
  val beaver: LatLong = -51.834 ll -61.346
  val westPoint: LatLong = -51.335 ll -60.734

  override def polygonLL: PolygonLL = PolygonLL(eiNorthWest, capePembroke, barren, beaver, westPoint)
}