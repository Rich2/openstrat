/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, LatLong._, Terrain._, pPts.RusNorth, pPts.AsiaWestPts

object AsiaEastPts
{
   val binhai = AsiaWestPts.persiaN ll 117.66
   val rongcheng = deg(37.39, 122.69)
   val haitzhou = deg(34.95, 119.20)
   val putuo = deg(29.9, 122.34)
   val longhai = deg(24.26, 118.14)
   val hongKong = deg(22.44, 114.16)
   val xuwen = deg(20.24, 110.18)
   val yingzaiMouth = deg(21.45, 109.90)            
         
   val neVietnamLong = 108.east      
   val neVietnam = 21.54.north * neVietnamLong
   
   val eVietnam = deg(12.93, 109.37)
   val dienChau = deg(18.99, 105.56)
   val sVietnam = deg(8.68, 104.92)
   val bankok = deg(13.59, 100.39)   
   val seMalaysia = deg(1.39, 104.25)
   val swMalaysia = deg(1.32, 103.47)
   val selekoh = deg(3.89, 100.73)
   val neMalayPen = deg(13.48, 98.45)
   val sittangMouth = deg(17.36, 96.89)
   val pathein = deg(16.17, 94.31)
    
   val chittagong = deg(22.74, 91.54)
   val seAsiaNE = AsiaWestPts.mianiLat * neVietnamLong   
   val seAsia = Area2("SEAsia", deg(20.0, 104.0), plain,
         binhai, rongcheng, haitzhou, putuo, longhai, hongKong, xuwen, yingzaiMouth,
               neVietnam, dienChau, eVietnam, sVietnam, bankok, seMalaysia, swMalaysia, selekoh, neMalayPen, sittangMouth, pathein,
               chittagong, AsiaWestPts.magdhara, AsiaWestPts.indiaNE,  AsiaWestPts.cAsiaSE) 
                  
     
   val khabarovsk = 55 ll 134.73   
   val chongpyong = deg(39.74, 127.46)
   val busan = deg(35.19, 129.19)
   val jindo = deg(34.39, 126.14)
   val ryongyon = deg(38.12, 124.78)
   val jinzhou = deg(40.93, 121.22)   
   val ceAsia = Area2("CEAsia", deg(47, 115), plain,
         khabarovsk, chongpyong, busan, jindo, ryongyon, jinzhou, binhai, AsiaWestPts.cAsiaSE,  RusNorth.cAsiaNE)      
   
   val krasnoyarsk = deg(77.43, 103.99)         
   val farAsiaW = 141.6.east
   val bukhta = deg(70.70, 131.07)
   val ustYansky = 72.81.north * farAsiaW
   val okhotsky = 58.73.north * farAsiaW   
   val neAsia = Area2("NEAsia", deg(64, 113), taiga,
         krasnoyarsk, bukhta, ustYansky, okhotsky, khabarovsk, RusNorth.cAsiaNE/*, AsiaWestPts.kazakNE*/, RusNorth.nRusNE)
   
   val sakha = deg(69.82, 159.7)
   val iultinsky = deg(67.38, -174.97)
   val eSiberia = deg(66.07, -170.24)
   val seProvidensky = deg(64.29, -173.08)
   val sKamchatka = deg(51.20, 156.89)
   val wKamchatka = deg(55.97, 155.67)
   val penzhinsky = deg(62.76, 164.60)
   val okhotsky2 = deg(59.42, 142.17)
   val feAsia = Area2("FEAsia", deg(66.22,159.68), taiga,
         ustYansky, sakha, iultinsky, eSiberia, seProvidensky, sKamchatka, wKamchatka, penzhinsky, okhotsky2, okhotsky)      
  
   val sKyshu = deg(31.08, 130.75)
   val neKyushu = deg(33.34, 129.45)
   val kashiwazaki = deg(37.37, 138.55)
   val nHokaido = deg(45.5, 141.93)
   val eHokaido = deg(43.36, 145.74)   
   val choshi = deg(35.71, 140.85)
   val japan = Area2("Japan", deg(36.28, 138.71), plain, sKyshu, neKyushu, kashiwazaki, nHokaido, eHokaido, choshi)         
}