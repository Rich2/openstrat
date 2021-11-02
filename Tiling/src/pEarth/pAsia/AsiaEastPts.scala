/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._, pPts.RusNorth, pPts.AsiaWestPts

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

  val seAsia: EarthLevel2 = EarthLevel2("SEAsia", degs(20.0, 104.0), plain, binhai, rongcheng, haitzhou, putuo, longhai, hongKong, xuwen,
     yingzaiMouth, neVietnam, dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth, pathein, chittagong,
     AsiaWestPts.magdhara, AsiaWestPts.indiaNE,  AsiaWestPts.cAsiaSE)
     
  val khabarovsk = 55 ll 134.73
  val chongpyong = degs(39.74, 127.46)
  val busan = degs(35.19, 129.19)
  val jindo = degs(34.39, 126.14)
  val ryongyon = degs(38.12, 124.78)
  val jinzhou = degs(40.93, 121.22)

  val ceAsia: EarthLevel2 = EarthLevel2("CEAsia", degs(47, 115), plain, khabarovsk, chongpyong, busan, jindo, ryongyon, jinzhou, binhai,
     AsiaWestPts.cAsiaSE,  RusNorth.cAsiaNE)
   
  val krasnoyarsk = degs(77.43, 103.99)
  val farAsiaW = 141.6.east
  val bukhta = degs(70.70, 131.07)
  val ustYansky = 72.81.north * farAsiaW
  val okhotsky = 58.73.north * farAsiaW

  val neAsia: EarthLevel2 = EarthLevel2("NEAsia", degs(64, 113), taiga, krasnoyarsk, bukhta, ustYansky, okhotsky, khabarovsk,
     RusNorth.cAsiaNE/*, AsiaWestPts.kazakNE*/, RusNorth.nRusNE)
   
  val sakha = degs(69.82, 159.7)
  val iultinsky = degs(67.38, -174.97)
  val eSiberia = degs(66.07, -170.24)
  val seProvidensky = degs(64.29, -173.08)
  val sKamchatka = degs(51.20, 156.89)
  val wKamchatka = degs(55.97, 155.67)
  val penzhinsky = degs(62.76, 164.60)
  val okhotsky2 = degs(59.42, 142.17)

  val feAsia: EarthLevel2 = EarthLevel2("FEAsia", degs(66.22,159.68), taiga, ustYansky, sakha, iultinsky, eSiberia, seProvidensky, sKamchatka,
     wKamchatka, penzhinsky, okhotsky2, okhotsky)
  
  val sKyshu = degs(31.08, 130.75)
  val neKyushu = degs(33.34, 129.45)
  val kashiwazaki = degs(37.37, 138.55)
  val nHokaido = degs(45.5, 141.93)
  val eHokaido = degs(43.36, 145.74)
  val choshi = degs(35.71, 140.85)

  val japan: EarthLevel2 = EarthLevel2("Japan", degs(36.28, 138.71), plain, sKyshu, neKyushu, kashiwazaki, nHokaido, eHokaido, choshi)
}