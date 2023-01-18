/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAsia
import geom._, pglobe._, LatLong._, WTile._

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EArea1("Asia", 60 ll 100)
{ import AsiaEastPts._
  //override val gridMaker = E80Empty
  override val a2Arr = RArr(seAsia, Korea, ceAsia, neAsia, feAsia, sakhalin, Hokkaido, japan, Taiwan)
}

/** Polygon display for Korea. */
object Korea extends EArea2("Korea", 37.77 ll 127.55, hills)
{ val northEast = 41.49 ll 129.65
  val kaima = 40.84 ll 129.71
  val chongpyong = 39.74 ll 127.46
  val koreaE = 37.06 ll 129.40
  val busan = degs(35.19, 129.19)
  val jindo = degs(34.39, 126.14)
  val ryongyon = degs(38.12, 124.78)
  val taeryongMouth = 39.49 ll 125.31
  val dalianSouth = 38.76 ll 121.16
  val p10 = 39.53 ll 121.23
  val xianshuiMouth = 40.48 ll 122.28
  val liaoheMouth = 40.95 ll 121.82

  override val polygonLL = PolygonLL(northEast, kaima, chongpyong, koreaE, busan, jindo, ryongyon, taeryongMouth, dalianSouth, p10, xianshuiMouth, liaoheMouth)
}

object AsiaEastPts
{ val binhai = Persia.persiaN ll 117.66
  val rongcheng = degs(37.39, 122.69)
  val haitzhou = degs(34.95, 119.20)
  val putuo = degs(29.9, 122.34)
  val longhai = degs(24.26, 118.14)
  val hongKong = degs(22.44, 114.16)
  val xuwen = degs(20.24, 110.18)
  val yingzaiMouth = degs(21.45, 109.90)
         
  val neVietnamLong = 108.east
  val neVietnam = 21.54.north * neVietnamLong
   
  val eVietnam = degs(12.93, 109.37)
  val dienChau = degs(18.99, 105.56)
  val sVietnam = degs(8.68, 104.92)
  val bankok = degs(13.59, 100.39)
  val seMalaysia = degs(1.39, 104.25)
  val swMalaysia = degs(1.32, 103.47)
  val selekoh = degs(3.89, 100.73)
  val neMalayPen = degs(13.48, 98.45)
  val sittangMouth = degs(17.36, 96.89)
  val pathein = degs(16.17, 94.31)
    
  val chittagong = degs(22.74, 91.54)
  val seAsiaNE = India.mianiLat * neVietnamLong

  val seAsia: EArea2 = EArea2("SEAsia", 26.0 ll 104.0, plain, binhai, rongcheng, haitzhou, putuo, longhai, hongKong, xuwen, yingzaiMouth, neVietnam,
    dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth, pathein, chittagong, India.magdhara,
    India.indiaNE,  CentralAsia.southEast)
     
  val khabarovsk = 55 ll 134.73
  val udaMouth = 54.72 ll 135.28
  val khab10 = 54.64 ll 136.81
  val khab15 = 54.28 ll 139.75
  val khab20 = 53.29 ll 141.42
  val khab30 = 48.46 ll 140.16
  val primorsky10 = 45.82 ll 137.68
  val nakhodka = 42.69 ll 133.14
  val vladivostok = 43.17 ll 132.00

  val jinzhou = degs(40.93, 121.22)
  val qinhuangdao = 39.92 ll 119.61
  val luanheMouth = 39.43 ll 119.30
  val huituo = 39.19 ll 118.98

  val ceAsia: EArea2 = EArea2("CEAsia", degs(47, 115), plain, khabarovsk, udaMouth, khab10, khab15, khab20, khab30, primorsky10, nakhodka,
    vladivostok, Korea.northEast,  Korea.liaoheMouth, jinzhou, qinhuangdao, luanheMouth, huituo, binhai, CentralAsia.southEast,  RusNorth.cAsiaNE)
   
  val krasnoyarsk = 77.43 ll 103.99
  val kras10 = 76.62 ll 112.46
  val kras20 = 75.38 ll 113.69
  val khatangaMouth = 73.21 ll 106.23
  val khat10 = 74.02 ll 110.26
  val sakha10 = 74.00 ll 112.83
  val anabarMouth = 73.55 ll 113.51
  val bulunsky10 = 72.85 ll 122.47
  val dunayNorth = 73.93 ll 124.68
  val bulunsky20 = 73.08 ll 129.27

  val farAsiaW = 141.6.east
  val bukhta = 70.70 ll 131.07
  val sakha20 = 71.94 ll 132.77
  val sakha30 = 71.36 ll 134.38
  val sakha40 = 71.96 ll 138.71
  val ustYansky = 72.81.north * farAsiaW
  val okhotsky = 58.73.north * farAsiaW

  val neAsia: EArea2 = EArea2("NEAsia", degs(64, 113), taiga, krasnoyarsk, kras10, kras20, khatangaMouth, khat10, sakha10, anabarMouth,
    bulunsky10, dunayNorth, bulunsky20, bukhta, sakha20, sakha30, sakha40, ustYansky, okhotsky, khabarovsk, RusNorth.cAsiaNE, RusNorth.nRusNE)

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
  val eSiberia = 66.07 ll  -170.24

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

  val feAsia: EArea2 = EArea2("FEAsia", degs(66.22,159.68), taiga, ustYansky, sakha43, sundrunMouth, sakha45, sakha48, sakha50, chukotka10,
    chukotka20, chukatka21, chukatka24, chukatka27, chukatka29, chukotka30, iultinsky10, eSiberia,
    seProvidensky, iultinsky20, anadyrMouth, anadyrsky10, anadyrsky20, kamchatka10, kamchatka20, nachikiMouth, kamchatka30, sKamchatka,
    wKamchatka, kamchatka40, kamchatka50, penzhinsky, kamchatka55, magadan5, magadan7, magadan10, magadan15, magadan20, magadan25, magadan30, magadan40, okhotaMouth, okhotsky2, okhotsky)

  val sakhalinN = 54.38 ll 142.73
  val sakhalinW = 49.07 ll 144.37
  val poronayask = 49.21 ll 143.09
  val sakhalinS = 45.89 ll 142.08
  val pogibi = 52.22 ll 141.64
  val sakhalinNE = 53.39 ll 141.70

  val sakhalin: EArea2 = EArea2("Sakhalin", 50.94 ll 142.90, taiga, sakhalinN, sakhalinW, poronayask, sakhalinS, pogibi, sakhalinNE)
}