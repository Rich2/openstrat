/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of north Siberia.Depends on nothing. */
object SiberiaNorth extends EArea2("SiberiaNorth", 70 ll 95, tundra)
{ val krasnoyarsk: LatLong = 77.43 ll 103.99
  val kras10: LatLong = 76.62 ll 112.46
  val kras20 = 75.38 ll 113.69
  val khatangaMouth = 73.21 ll 106.23
  val khat10 = 74.02 ll 110.26
  val sakha10 = 74.00 ll 112.83
  val anabarMouth: LatLong = 73.534 ll 113.478
  val anabarHead: LatLong = 70.774 ll 113.337
  val southEast: LatLong = 66.877 ll 114.084
  val p50: LatLong = 66.344 ll 99.119

  val yenisei70: LatLong = 66.775 ll 86.738
  val yeniseiMouth: LatLong = 71.831 ll 82.730
  val p90: LatLong = 75.64 ll 91.5

  override val polygonLL: PolygonLL = PolygonLL(krasnoyarsk, kras10, kras20, khatangaMouth, khat10, sakha10, anabarMouth, anabarHead, southEast, p50,
    yenisei70, yeniseiMouth, p90
  )
}

/** [[polygonLL]] graphical representation of south Siberia.Depends on [[SiberiaNorth]] and [[LakeBaikal]]. */
object SiberiaSouth extends EArea2("SiberiaSouth", 60 ll 92, taiga)
{ val lensk: LatLong = 60.709 ll 114.92
  val khuvsgulLakeN: LatLong = 51.616 ll 100.542
  val southWest = 49.208 ll 82.144

  override val polygonLL = LinePathLL(SiberiaNorth.southEast, lensk) ++ LakeBaikal.westCoast.reverse |++| LinePathLL(khuvsgulLakeN, Mongolia.west, southWest,
    SiberiaNorth.yenisei70, SiberiaNorth.p50)
}

/** [[polygonLL]] graphical representation of Korea.Depends on nothing. */
object LakeBaikal extends EArea2("Lake Baikal", 53.463 ll 108.157, lake)
{ val north: LatLong = 55.872 ll 109.742
  val northEast: LatLong = 55.670 ll 109.963
  val east: LatLong = 54.157 ll 109.556
  val mamai: LatLong = 51.456 ll 104.768

  /** The east coast of Lake Baikal in clockwise direction. Shares both north and south [[LatLong]] points with the westCoast. */
  val eastCoast = LinePathLL(north, northEast, east, mamai)

  val west: LatLong = 51.697 ll 103.699

  /** The east coast of Lake Baikal in clockwise direction. Shares both north and south [[LatLong]] points with the westCoast. */
  val southCoast = LinePathLL(mamai, west)
  val angaraMouth: LatLong = 51.870 ll 104.82
  val p80: LatLong = 53.927 ll 108.197

  /** The west coast of Lake Baikal in clockwise direction. Shares both south and north [[LatLong]] points with the eastCoast. */
  val westCoast: LinePathLL = LinePathLL(west, angaraMouth, p80, north)

  override val polygonLL: PolygonLL = PolygonLL(north, northEast, east, mamai, west, angaraMouth, p80)
}

/** [[PolygonLL]] graphic for Yakutz area depends on [[FEAsia]], [[LakeBaikal]], [[SiberiaSouth]] and [[SiberiaNorth]]. */
object Yakutia extends EArea2("Yakutia", 64 ll 115, taiga)
{ val bulunsky10: LatLong = 72.85 ll 122.47
  val dunayNorth = 73.93 ll 124.68
  val bulunsky20 = 73.08 ll 129.27

  val bukhta: LatLong = 70.70 ll 131.07
  val sakha20 = 71.94 ll 132.77
  val sakha30 = 71.36 ll 134.38
  val sakha40 = 71.96 ll 138.71
  val khabarovsk: LatLong = 55 ll 134.73

  override val polygonLL: PolygonLL = PolygonLL(FeAsia.ustYansky, FeAsia.okhotsky, khabarovsk, LakeBaikal.north, SiberiaSouth.lensk, SiberiaNorth.southEast, SiberiaNorth.anabarHead, SiberiaNorth.anabarMouth,
    bulunsky10, dunayNorth, bulunsky20, bukhta, sakha20, sakha30, sakha40,
  )
}

/** [[polygonLL]] graphical representation of far east Asia. Depends on nothing. */
object FeAsia extends EArea2("FEAsia", 66.22 ll 159.68, hillyTundra)
{ val farAsiaW = 141.6.east

  val ustYansky: LatLong = 72.81.north * farAsiaW
  val sakha43 = 72.11 ll 149.64
  val sundrunMouth = 70.81 ll 152.56
  val sakha45 = 71.07 ll 157.19
  val sakha48 = 70.26 ll 160.11
  val sakha50 = 69.82 ll 159.7
  val chukotka10 = 69.47 ll 166.93
  val chukotka20 = 70.03 ll 168.49
  val chukatka21 = 69.87 ll 169.42
  val chukatka24 = 69.21 ll 168.31
  val chukatka27 = 69.04 ll 171.05
  val chukatka29 = 70.09 ll 170.61
  val chukotka30 = 69.86 ll 176.12
  val iultinsky10 = 67.38 ll -174.97
  val eSiberia = 66.07 ll -170.24

  val seProvidensky = 64.29 ll -173.08
  val iultinsky20 = 66.18 ll -179.61
  val anadyrMouth = 64.74 ll 177.58
  val anadyrsky10 = 62.29 ll 179.11
  val anadyrsky20 = 62.44 ll 176.63
  val kamchatka10 = 59.96 ll 170.31
  val kamchatka20 = 60.05 ll 163.82
  val nachikiMouth = 57.84 ll 162.14
  val kamchatka30 = 57.79 ll 163.25
  val sKamchatka = 51.20 ll 156.89
  val wKamchatka = 55.97 ll 155.67
  val kamchatka40 = 60.42 ll 161.95
  val kamchatka50 = 60.91 ll 163.78

  val penzhinsky = 62.76 ll 164.60
  val kamchatka55 = 62.55 ll 163.26
  val magadan5 = 60.60 ll 160.17
  val magadan7 = 61.94 ll 160.37
  val magadan10 = 61.81 ll 157.48
  val magadan15 = 59.88 ll 154.19
  val magadan20 = 59.19 ll 155.17
  val magadan25 = 58.86 ll 151.39
  val magadan30 = 59.61 ll 151.37
  val magadan40 = 59.24 ll 148.93
  val okhotaMouth = 59.33 ll 143.07
  val okhotsky2 = 59.42 ll 142.17
  val okhotsky: LatLong = 58.73.north * farAsiaW

  override val polygonLL = PolygonLL(ustYansky, sakha43, sundrunMouth, sakha45, sakha48, sakha50, chukotka10,
  chukotka20, chukatka21, chukatka24, chukatka27, chukatka29, chukotka30, iultinsky10, eSiberia,
  seProvidensky, iultinsky20, anadyrMouth, anadyrsky10, anadyrsky20, kamchatka10, kamchatka20, nachikiMouth, kamchatka30, sKamchatka,
  wKamchatka, kamchatka40, kamchatka50, penzhinsky, kamchatka55, magadan5, magadan7, magadan10, magadan15, magadan20, magadan25, magadan30, magadan40, okhotaMouth, okhotsky2, okhotsky)
}
