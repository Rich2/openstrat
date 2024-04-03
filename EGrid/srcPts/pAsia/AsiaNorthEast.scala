/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  val dunayNorth: LatLong = 73.93 ll 124.68
  val bulunsky20: LatLong = 73.08 ll 129.27

  val bukhta: LatLong = 70.70 ll 131.07
  val sakha20: LatLong = 71.94 ll 132.77
  val sakha30: LatLong = 71.36 ll 134.38
  val sakha40 : LatLong= 71.96 ll 138.71
  val khabarovsk: LatLong = 55 ll 134.73

  override val polygonLL: PolygonLL = PolygonLL(FeAsia.ustYansky, FeAsia.okhotsky, khabarovsk, LakeBaikal.north, SiberiaSouth.lensk, SiberiaNorth.southEast, SiberiaNorth.anabarHead, SiberiaNorth.anabarMouth,
    bulunsky10, dunayNorth, bulunsky20, bukhta, sakha20, sakha30, sakha40,
  )
}

/** [[polygonLL]] graphical representation of far east Asia. Depends on [[Kamchatka]]. */
object FeAsia extends EArea2("far east\nAsia", 66.22 ll 159.68, hillyTundra)
{ val farAsiaW: Longitude = 141.6.east

  val ustYansky: LatLong = 72.81.north * farAsiaW
  val sakha43: LatLong = 72.11 ll 149.64
  val sundrunMouth: LatLong = 70.81 ll 152.56
  val sakha45: LatLong = 71.07 ll 157.19
  val sakha48: LatLong = 70.26 ll 160.11
  val sakha50: LatLong = 69.82 ll 159.7
  val chukotka10: LatLong = 69.47 ll 166.93
  val chukotka20: LatLong = 70.03 ll 168.49
  val chukatka21: LatLong = 69.87 ll 169.42
  val chukatka24: LatLong = 69.21 ll 168.31
  val chukatka27: LatLong = 69.04 ll 171.05
  val chukatka29: LatLong = 70.09 ll 170.61
  val chukotka30: LatLong = 69.86 ll 176.12
  val iultinsky10: LatLong = 67.38 ll -174.97
  val east: LatLong = 66.123 ll -169.683

  val p40: LatLong = 65.615 ll -170.581
  val p43: LatLong = 64.761 ll -172.071
  val seProvidensky: LatLong = 64.265 ll -173.073
  val enmmelen: LatLong = 65.003 ll -175.891
  val iultinsky20: LatLong = 66.18 ll -179.61
  val p46: LatLong = 65.243 ll -179.549
  val anadyrMouth: LatLong = 64.74 ll 177.58
  val p48: LatLong = 64.324 ll 178.344
  val p51: LatLong = 62.824 ll 179.568
  val anadyrsky10: LatLong = 62.29 ll 179.11
  val anadyrsky20: LatLong = 62.44 ll 176.63

  val penzhinsky: LatLong = 62.76 ll 164.60
  val kamchatka55: LatLong = 62.55 ll 163.26
  val magadan5: LatLong = 60.60 ll 160.17
  val magadan7: LatLong = 61.94 ll 160.37
  val magadan10: LatLong = 61.81 ll 157.48
  val magadan15: LatLong = 59.88 ll 154.19
  val magadan20: LatLong = 59.19 ll 155.17
  val magadan25: LatLong = 58.86 ll 151.39
  val magadan30: LatLong = 59.61 ll 151.37
  val magadan40: LatLong = 59.24 ll 148.93
  val okhotaMouth: LatLong = 59.33 ll 143.07
  val okhotsky2: LatLong = 59.42 ll 142.17
  val okhotsky: LatLong = 58.73.north * farAsiaW

  override val polygonLL: PolygonLL = PolygonLL(ustYansky, sakha43, sundrunMouth, sakha45, sakha48, sakha50, chukotka10, chukotka20, chukatka21, chukatka24,
    chukatka27, chukatka29, chukotka30, iultinsky10, east,
    p40, p43, seProvidensky, enmmelen, iultinsky20, p46, anadyrMouth, p48, p51, anadyrsky10, anadyrsky20, Kamchatka.northEast, Kamchatka.penzhinaMouth,
    penzhinsky, kamchatka55, magadan5, magadan7, magadan10, magadan15, magadan20, magadan25, magadan30, magadan40, okhotaMouth, okhotsky2, okhotsky)
}

/** [[polygonLL]] graphical representation of far east Asia. Depends on nothing. */
object Kamchatka extends EArea2("kamchatka", 56.483 ll 159.556, hillyTaiga)
{ val northEast: LatLong = 62.036 ll 175.268
  val p10: LatLong = 59.96 ll 170.31
  val p16: LatLong = 59.815 ll 166.136
  val p18: LatLong = 59.797 ll 164.833
  val p24: LatLong = 58.887 ll 164.644
  val nachikiMouth: LatLong = 57.84 ll 162.14
  val p30: LatLong = 57.79 ll 163.25
  val p35: LatLong = 56.189 ll 163.337
  val p39: LatLong = 54.852 ll 162.145
  val p45: LatLong = 53.103 ll 160.031
  val south: LatLong = 51.20 ll 156.89
  val p53: LatLong = 55.089 ll 155.579
  val west: LatLong = 55.97 ll 155.67
  val p56: LatLong = 57.733 ll 156.796
  val p67: LatLong = 60.42 ll 161.95
  val p70: LatLong = 60.91 ll 163.78
  val penzhinaMouth: LatLong = 62.467 ll 165.094

  override val polygonLL: PolygonLL = PolygonLL(northEast, p10, p16, p18, p24, nachikiMouth, p30, p35, p39, p45, south, p53, west, p56, p67, p70, penzhinaMouth)
}