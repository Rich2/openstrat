/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMalay
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Luzon and nearby smaller islands. Depends on nothing. */
object Luzon extends EArea2("Philippines North", 16.779 ll 121.353, hillyJungle)
{ val north: LatLong = 18.650 ll 120.844
  val northEast: LatLong = 18.522 ll 122.225
  val p10: LatLong = 17.141 ll 122.510
  val p15: LatLong = 15.324 ll 121.37
  val jomaigEast: LatLong = 14.703 ll 122.439
  val northWest: LatLong = 16.359 ll 119.815
  val calintaanSouth: LatLong = 12.524 ll 124.091
  val puntaTumaquip = 12.870 ll 123.65
  val llinSouth = 12.157 ll 121.118
  val southWest: LatLong = 13.768 ll 120.658

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, p10, jomaigEast, calintaanSouth, puntaTumaquip, llinSouth, southWest, northWest)
}

/** [[polygonLL]] graphical representation of central Philippines islands. Depends on nothing. */
object Visayas extends EArea2("Visayas Islands", 13.259 ll 122.615, hillyJungle)
{ val negrosS: LatLong = 9.043 ll 123.013
  val panayNW = 11.897 ll 121.887
  val ticaoN: LatLong = 12.691 ll 123.621
  val cebuN: LatLong = 11.281 ll 124.057
  val lapinigN: LatLong = 10.152 ll 124.615

  override val polygonLL: PolygonLL = PolygonLL(negrosS, panayNW, ticaoN, cebuN, lapinigN)
}

/** [[polygonLL]] graphical representation of Palawan and nearby smaller islands. Depends on nothing. */
object Palawan extends EArea2("Palawan Island", 9.478 ll 118.360, mtain)
{ val busangaNW: LatLong = 12.327 ll 119.980
  val busangaNE: LatLong = 12.064 ll 120.338
  val dumaranE: LatLong = 10.568 ll 119.997
  val bugsukSE: LatLong = 8.195 ll 117.332
  val balabacSE: LatLong = 7.805 ll 117.023
  val balabacSW = 7.814 ll 116.993
  val balabacWest = 7.936 ll 116.932
  val p80 = 10.854 ll 119.211

  override val polygonLL: PolygonLL = PolygonLL(busangaNW, busangaNE, dumaranE, bugsukSE, balabacSE, balabacSW, balabacWest, p80)
}

/** [[polygonLL]] graphical representation of Samar and nearby smaller islands. Depends on nothing. */
object Samar extends EArea2("Samar Island", 11.536 ll 125.121, mtain)
{ val batagNE: LatLong = 12.685 ll 125.063
  val samarSE: LatLong = 10.939 ll 125.837
  val panonSouth: LatLong = 9.911 ll 125.282
  val higatanganNW: LatLong = 11.577 ll 124.254
  val northWest: LatLong = 12.549 ll 124.261

  override val polygonLL: PolygonLL = PolygonLL(batagNE, samarSE, panonSouth, higatanganNW, northWest)
}

object Mindano extends EArea2("Mindano", 7.729 ll 125.011, hillyJungle)
{ val north: LatLong = 9.824 ll 125.441
  val capeSanAgustin: LatLong = 6.269 ll 126.193
  val basilianSouth: LatLong = 6.412 ll 121.959
  val p70: LatLong = 7.953 ll 122.222

  override val polygonLL: PolygonLL = PolygonLL(north, capeSanAgustin, basilianSouth, p70)
}