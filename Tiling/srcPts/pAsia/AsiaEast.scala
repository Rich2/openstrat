/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._, pPts.RusNorth, pPts.AsiaWestPts

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EArea1("Asia", 60 ll 100)
{ import AsiaEastPts._
  //override val gridMaker = E80Empty
  override val a2Arr = Arr(seAsia, ceAsia, neAsia, feAsia, sakhalin, japan)
}

object AsiaEastPts
{ val binhai = AsiaWestPts.persiaN ll 117.66
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
  val seAsiaNE = AsiaWestPts.mianiLat * neVietnamLong

  val seAsia: EArea2 = EArea2("SEAsia", 26.0 ll 104.0, plain, binhai, rongcheng, haitzhou, putuo, longhai, hongKong, xuwen, yingzaiMouth, neVietnam,
    dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth, pathein, chittagong, AsiaWestPts.magdhara,
    AsiaWestPts.indiaNE,  AsiaWestPts.cAsiaSE)
     
  val khabarovsk = 55 ll 134.73
  val udaMouth = 54.72 ll 135.28
  val khab10 = 54.64 ll 136.81
  val khab20 = 53.29 ll 141.42
  val khab30 = 48.46 ll 140.16
  val nakhodka = 42.69 ll 133.14
  val vladivostok = 43.17 ll 132.00
  val chongpyong = degs(39.74, 127.46)
  val busan = degs(35.19, 129.19)
  val jindo = degs(34.39, 126.14)
  val ryongyon = degs(38.12, 124.78)
  val jinzhou = degs(40.93, 121.22)

  val ceAsia: EArea2 = EArea2("CEAsia", degs(47, 115), plain, khabarovsk, udaMouth, khab10, khab20, khab30, nakhodka, vladivostok,
    chongpyong, busan, jindo, ryongyon, jinzhou, binhai, AsiaWestPts.cAsiaSE,  RusNorth.cAsiaNE)
   
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

  val sundrunMouth = 70.81 ll 152.56
  val sakha50 = 69.82 ll 159.7
  val iultinsky10 = 67.38 ll -174.97
  val eSiberia = 66.07 ll  -170.24

  val seProvidensky = 64.29 ll -173.08
  val iultinsky20 = 66.18 ll -179.61
  val sKamchatka = degs(51.20, 156.89)
  val wKamchatka = degs(55.97, 155.67)
  val penzhinsky = degs(62.76, 164.60)
  val okhotsky2 = degs(59.42, 142.17)

  val feAsia: EArea2 = EArea2("FEAsia", degs(66.22,159.68), taiga, ustYansky, sundrunMouth, sakha50, iultinsky10, eSiberia, seProvidensky, iultinsky20, sKamchatka,
     wKamchatka, penzhinsky, okhotsky2, okhotsky)

  val sakhalinN = 54.38 ll 142.73
  val sakhalinW = 49.07 ll 144.37
  val poronayask = 49.21 ll 143.09
  val sakhalinS = 45.89 ll 142.08
  val pogibi = 52.22 ll 141.64
  val sakhalinNE = 53.39 ll 141.70

  val sakhalin: EArea2 = EArea2("Sakhalin", 50.94 ll 142.90, taiga, sakhalinN, sakhalinW, poronayask, sakhalinS, pogibi, sakhalinNE)

  val sKyshu = degs(31.08, 130.75)
  val neKyushu = degs(33.34, 129.45)
  val kashiwazaki = degs(37.37, 138.55)
  val nHokaido = degs(45.5, 141.93)
  val eHokaido = degs(43.36, 145.74)
  val choshi = degs(35.71, 140.85)

  val japan: EArea2 = EArea2("Japan", degs(36.28, 138.71), plain, sKyshu, neKyushu, kashiwazaki, nHokaido, eHokaido, choshi)
}