/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pMed
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for Thasos island. Depends on nothing. */
object Thasos extends IslandPoly("Thasos", 40.686 ll 24.659, mtainSubForest)
{ override def area: Kilares = 380.kilares

  val north: LatLong = 40.801 ll 24.650
  val northEast: LatLong = 40.736 ll 24.784
  val southEast: LatLong = 40.606 ll 24.774
  val south: LatLong = 40.567 ll 24.644
  val southWest: LatLong = 40.602 ll 24.530

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, southWest)
}

object LesbosChios extends IslandPolyGroup("LesbosChios")
{ override def elements: RArr[IslandPolyLike] = RArr(Lesbos, Chios)
  val psara = 45.5.kilares
  override val area: Kilares = super.area + psara
}

/** [[PolygonLL]] graphic for Lesbos. Depends on nothing. */
object Lesbos extends IslandPoly("Lesbos", 39.19 ll 26.30, hillyOce)
{ override val area: Kilares = 1633.kilares
  override def oGroup: Some[LesbosChios.type] = Some(LesbosChios)

  val north: LatLong = 39.39 ll 26.34
  val northEast: LatLong = 39.34 ll 26.42
  val southEast: LatLong = 39.02 ll 26.61
  val south: LatLong = 38.96 ll 26.40
  val p70: LatLong = 39.19 ll 25.83
  val p75: LatLong = 39.26 ll 25.86
  val poseidonsLungs: LatLong = 39.37 ll 26.17

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, p70, p75, poseidonsLungs)
}

/** [[PolygonLL]] graphic for Chios. Depends on nothing. */
object Chios extends IslandPoly("Chios", 38.19 ll 26.30, mtainSavannah)
{ override val area: Kilares = 842.3.kilares
  override def oGroup: Some[LesbosChios.type] = Some(LesbosChios)

  val north: LatLong = 38.60 ll 26.00
  val northEast: LatLong = 38.55 ll 26.16
  val p20: LatLong = 38.33 ll 26.16
  val south: LatLong = 38.15 ll 26.01
  val southEast: LatLong = 38.24 ll 25.86
  val northWest: LatLong = 38.58 ll 25.85

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, p20, south, southEast, northWest)
}

/** Island group for [[Samos]] , Ikaria and islands in between. */
object SamosIkaria extends IslandPolyGroup("SamosIkaria")
{ override def elements: RArr[IslandPolyLike] = RArr(Samos)
  val ikaria: Kilares = 255.3.kilares
  override val area: Kilares = Samos.area + ikaria + 5.kilares
}

/** [[PolygonLL]] graphic for Samos. Depends on nothing. */
object Samos extends IslandPoly("Samos", 37.748 ll 26.829, mtainSavannah)
{ override val area: Kilares = 477.4.kilares
  override def oGroup: Option[IslandPolyGroup] = Some(SamosIkaria)

  val north: LatLong = 37.813 ll 26.741
  val northEast: LatLong = 37.779 ll 27.066
  val east: LatLong = 37.712 ll 27.066
  val p35: LatLong = 37.687 ll 26.952
  val southEast: LatLong = 37.640 ll 26.878
  val west: LatLong = 37.731 ll 26.566

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, east, p35, southEast, west)
}

/** The Cyclades, east of the [[Peloponnese]], north of [[Crete]], one of the six island groups of Greece. */
object Cyclades extends IslandPolyGroup("Cyclades")
{ override val elements: RArr[IslandPolyLike] = RArr(Naxos)
  override val area: Kilares = 2572.kilares
}

/** [[PolygonLL]] graphic for Greek, Cyclades islands of Andros 380km² + Tinos 194.5km² = km². Depends on nothing. */
object Andros extends IslandPoly("Andros", 37.852 ll 24.869, mtainSub)
{ override val oGroup: Some[IslandPolyGroup] = Some(Cyclades)
  val andros0: Kilares = 380.kilares
  val tinos: Kilares = 194.5.kilares
  override val area: Kilares = andros0 + tinos

  val north: LatLong = 37.998 ll 24.789
  val northEast: LatLong = 37.898 ll 24.956
  val tinosNE: LatLong = 37.608 ll 25.251
  val tinosSouth: LatLong = 37.524 ll 25.189
  val northWest: LatLong = 37.960 ll 24.688

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, tinosNE, tinosSouth, northWest)
}

/** [[PolygonLL]] graphic for Greek, Cyclades islands of Naxos and Paros 526.3km². Depends on nothing. */
object Naxos extends IslandPoly("Naxos", 37.058 ll 25.493, mtainSub)
{ val naxos0: Kilares = 430.kilares
  val paros: Kilares = 196.3.kilares
  override val area: Kilares = naxos0 + paros
  override val oGroup: Some[Cyclades.type] = Some(Cyclades)

  val north: LatLong = 37.203 ll 25.536
  val northEast: LatLong = 37.110 ll 25.597
  val southEast: LatLong = 36.958 ll 25.549
  val south: LatLong = 36.917 ll 25.448
  val parosSouth: LatLong = 36.980 ll 25.180
  val chainSW: LatLong = 36.948 ll 24.957
  val parosNW: LatLong = 37.155 ll 25.225

  val west: LatLong = 37.078 ll 25.337

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, southEast, south, parosSouth, chainSW, parosNW)
}

/** [[PolygonLL]] graphic for Greek Island of kythira. Depends on nothing. */
object Kythira extends IslandPoly("Kythira", 36.243 ll 22.989, mtainSavannah)
{ override val area: Kilares = 300.kilares
  val antikythera = 20.43.kilares
  val area2 = area + antikythera
  
  val north: LatLong = 36.385 ll 22.950
  val east: LatLong = 36.249 ll 23.099
  val southEast: LatLong = 36.130 ll 23.047
  val southWest: LatLong = 36.200 ll 22.908

  override val polygonLL: PolygonLL = PolygonLL(north, east, southEast, southWest)
}

/** The Dodecanese island group. */
object Dodecanese extends IslandPolyGroup("Dodecanese")
{ override val elements: RArr[IslandPolyLike] = RArr(DodecaneseWest, Rhodes)
  override def area: Kilares = 2714.kilares
}

/** West [[Dodecanese]] 1200km². */
object DodecaneseWest extends IslandPolyGroup("DodecaneseWest")
{ override val elements: RArr[IslandPolyLike] = RArr()
  override def area: Kilares = Dodecanese.area - Rhodes.area - 100.kilares
}

/** [[PolygonLL]] graphic for Rhodes. Depends on nothing. */
object Rhodes extends IslandPoly("Rhodes", 36.22 ll 27.95, hillyOce)
{ override val area: Kilares = 1400.68.kilares
  override val oGroup: Some[Dodecanese.type] = Some(Dodecanese)

  val north: LatLong = 36.46 ll 28.22
  val akraLindos: LatLong = 36.05 ll 28.09
  val p40: LatLong = 35.93 ll 27.86
  val south: LatLong = 35.88 ll 27.76
  val p50: LatLong = 35.94 ll 27.72
  val p65: LatLong = 36.15 ll 27.69
  val p70: LatLong = 36.27 ll 27.81

  override val polygonLL: PolygonLL = PolygonLL(north, akraLindos, p40, south, p50, p65, p70)
}