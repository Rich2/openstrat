/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pnAmer
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation for most of Mexico. Dependent on [[UsaSouthWest]], [[UsaNorthEast]] and [[Honduras]]. */
object Mexico extends EarthPoly("Mexico", 24 ll -102.4, hillySavannah)
{ val brownsville: LatLong = 25.98 ll -97.26
  val puntaJerez: LatLong = 22.89 ll -97.77
  val caboRojo: LatLong = 21.57 ll -97.33
  val p40: LatLong = 18.686 ll -95.956
  val puntaRockoPartido: LatLong = 18.708 ll -95.188

  val pochutala: LatLong = 15.76 ll -96.50
  val maldonado: LatLong = 16.325 ll -98.568
  val papagayoMouth: LatLong = 16.684 ll -99.607
  val p70: LatLong = 18.328 ll -103.429
  val manzanillo: LatLong = 19.15 ll -104
  val puntaDelMario: LatLong = 20.41 ll -105.69
  val grandeDeSantiagoMouth: LatLong = 21.64 ll -105.45
  val cAmericaNW: LatLong = 22.8 ll -105.97
  val p90: LatLong = 25.952 ll -109.445

  override def polygonLL: PolygonLL = PolygonLL(UsaPrairiesSouth.galveston, brownsville, puntaJerez, caboRojo, p40, puntaRockoPartido,
    MexicoEast.coatzacoalcosMouth, MexicoEast.tehuantepecMouth, pochutala, maldonado, papagayoMouth, p70, manzanillo, puntaDelMario, grandeDeSantiagoMouth,
    cAmericaNW, p90, UsaSouthWest.rockyPoint, UsaSouthWest.southEast)
}

/** [[polygonLL]] graphical representation for most of Mexico. Dependent on nothing. */
object MexicoEast extends EarthPoly("MexicoEast", 17 ll -91, hillyJungle)
{ val yucatanNE: LatLong = 21.48 ll -86.97
  val cozumelNorth: LatLong = 20.590 ll -86.724
  val cozumelSouth: LatLong = 20.272 ll -86.988
  val laExoeranza: LatLong = 20.328 ll -87.354
  val pajaros: LatLong = 19.598 ll -87.410
  val belizeCity = 17.495 ll -88.181
  val p20: LatLong = 16.4 ll -88.23
  val seBelize: LatLong = 15.88 ll -88.91

  val elSalvadoreW: LatLong = 13.75 ll -90.13
  val swGuatemala: LatLong = 14.55 ll -92.21
  val tehuantepecMouth: LatLong = 16.19 ll -95.15
  val coatzacoalcosMouth: LatLong = 18.16 ll -94.41
  val p65: LatLong = 18.897 ll -91.383
  val champeton: LatLong = 19.36 ll -90.71
  val nwYucatan: LatLong = 21.01 ll -90.3
  val p95: LatLong = 21.62 ll -88.14

  override def polygonLL: PolygonLL = PolygonLL(yucatanNE, cozumelNorth, cozumelSouth, laExoeranza, pajaros,belizeCity, p20, seBelize, elSalvadoreW,
    swGuatemala, tehuantepecMouth, coatzacoalcosMouth, p65, champeton, nwYucatan, p95)
}

/** [[polygonLL]] graphical representation for Honduras and El Salvador. Depends on [[MexicoEast]] and [[Nicaragua]]. */
object Honduras extends EarthPoly("Honduras", 14.219 ll -85.860, mtainJungle)
{ val p5: LatLong = 15.743 ll -88.594
  val puntaManabique: LatLong = 15.964 ll -88.622
  val p10: LatLong = 15.681 ll -88.147
  val p15: LatLong = 15.920 ll -87.730
  val p20: LatLong = 15.968 ll -85.040
  val peurtoCastilla: LatLong = 16.017 ll -85.942
  val barraPatuca: LatLong = 15.917 ll -84.305

  val tigerIsland: LatLong = 13.251 ll -87.644
  val p80: LatLong = 13.158 ll -87.896

  override def polygonLL: PolygonLL = LinePathLL(MexicoEast.seBelize, p5, puntaManabique, p10, p15, p20, peurtoCastilla, barraPatuca, Nicaragua.islaSanPio)|++|
    LinePathLL(Nicaragua.northWest, tigerIsland, p80, MexicoEast.elSalvadoreW)
}

/** [[polygonLL]] graphical representation for  Nicaragua. Depends on [[CostaRico]] and [[LakeCocibolca]]. */
object Nicaragua extends EarthPoly("Nicaragua", 13.050 ll -85.102, hillyJungle)
{ val islaSanPio: LatLong = 14.995 ll -83.123
  val losCordoba: LatLong = 12.392 ll -83.492

  val puntaCosiguina: LatLong = 12.912 ll -87.689
  val northWest = 12.988 ll -87.308

  override def polygonLL: PolygonLL = LinePathLL(islaSanPio, losCordoba, CostaRico.northEast) ++ LakeCocibolca.southCoast |++| LinePathLL(CostaRico.northWest,
    puntaCosiguina, northWest)
}

/** [[polygonLL]] graphical representation for Lake Cocibolca. Depends on nothing. */
object LakeCocibolca extends LakePoly("Lake Cocibolca", 11.552 ll -85.390, lake)
{ override def area: Kilares = 8264.kilares

  val sapoa: LatLong = 11.248 ll -85.624
  val granada: LatLong = 11.940 ll -85.945
  val north: LatLong = 12.122 ll -85.785
  val northEast: LatLong = 12.051 ll -85.585
  val p40: LatLong = 11.109 ll -84.768
  val mainCoast: LinePathLL = LinePathLL(sapoa, granada, north, northEast, p40)

  val south: LatLong = 11.027 ll -84.906
  val southCoast: LinePathLL = LinePathLL(p40, south, sapoa)

  override def polygonLL: PolygonLL = mainCoast |-++-| southCoast
}

/** [[polygonLL]] graphical representation for Costa Rico. Depends on [[Panama]] and [[LakeCocibolca]]. */
object CostaRico extends EarthPoly("Costa Rica", 9.837 ll -83.676, hillyJungle)
{ val northEast: LatLong = 11.120 ll -83.829

  val puntaLiorona: LatLong = 8.58 ll -83.72
  val puntaConejo: LatLong = 9.651 ll -84.680
  val caboBlanco: LatLong = 9.562 ll -85.112
  val p90: LatLong = 10.356 ll -85.874
  val p95: LatLong = 10.894 ll -85.943
  val northWest: LatLong = 11.197 ll -85.829

  override val polygonLL: PolygonLL = LinePathLL(northEast, Panama.peutoViejo, Panama.puntaBurica,  puntaLiorona, puntaConejo, caboBlanco, p90, p95,
    northWest) |++<| LakeCocibolca.southCoast
}

/** [[polygonLL]] graphical representation for Panama. Depends on nothing. */
object Panama extends EarthPoly("Panama", 9.184 ll -79.644, mtainJungle)
{ val peutoViejo: LatLong = 9.655 ll -82.764
  val kusapin: LatLong = 8.79 ll -81.38
  val stIsabel: LatLong = 9.53 ll -79.25
  val stIgnacio: LatLong = 9.26 ll -78.12
  val northEast: LatLong = 7.939 ll -76.932

  val southEast: LatLong = 7.26 ll -77.9
  val p55: LatLong = 8.083 ll -78.442
  val flamencoIsland: LatLong = 8.907 ll -79.516
  val rioEsteroSaladoMouth: LatLong = 8.181 ll -80.486
  val puntaMala: LatLong = 7.466 ll -79.999
  val mariato: LatLong = 7.22 ll -80.88
  val puntaBurica: LatLong = 8.033 ll -82.874

  override val polygonLL: PolygonLL = PolygonLL(peutoViejo, kusapin, stIsabel, stIgnacio, northEast, southEast, p55, flamencoIsland, rioEsteroSaladoMouth,
    puntaMala, mariato, puntaBurica)
}