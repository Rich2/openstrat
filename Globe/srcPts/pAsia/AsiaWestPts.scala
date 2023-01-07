/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._, pEurope.{Anatolia, Baltland, Caucasus, Ukraine}

object RusNorth extends EArea2("NRus", 61 ll 54, taiga)
{ val wAsiaE = 66.52.east
  val indiaE = 91.5.east
   
  /** North Coast */
  val chizhaSouth = 66.90 ll 44.52
  val chizhaWest = 67.19 ll 43.77
  val shoynaNorth1 = 68.30 ll 44.21
  val shoynaNorth2 = 68.66 ll 42.28
  val shoynaNorth3 = 68.53 ll 44.07
  val shoynaNorth4 = 68.47 ll 45.78
  val shoynaEast1 = 68.11 ll 46.52
  val shoynaEast2 = 67.81 ll 46.65
  val kiyaEast1 = 67.69 ll 45.32
  val chizhaEast1 = 67.33 ll 44.90
  val vizhas = 66.81 ll 45.96
  val amderma = 69.76 ll 61.68
  val nRusNE = 75.64.north * indiaE
   
  val cAsiaNE = 55.north * indiaE
  val cEuropeE = 51.36.east
  //val nRusSW = 55.north * cEuropeE
  val kazakNE = 55.north * wAsiaE

  val polygonLL: PolygonLL = PolygonLL(Baltland.mezenMouth, chizhaSouth, chizhaWest, shoynaNorth1, shoynaNorth2, shoynaNorth3, shoynaNorth4, shoynaEast1,
     shoynaEast2, kiyaEast1, chizhaEast1, vizhas, amderma, nRusNE, cAsiaNE, kazakNE, Baltland.southEast)
}

object AsiaWestPts
{ /** South West Turkey */
  val salwa = 24.71 ll 50.77
  val nQatar = 26.15 ll 51.26
  val doha = 25.25 ll 51.61
  val alGharbia = degs(23.97, 51.94)
  val icad = 24.30 ll 54.44
  val kumzar = 26.36 ll 56.38
  val alKhaburah = 23.98 ll 57.10
  val eOman = degs(22.48, 59.72)
  val mirbat = 16.94 ll 54.80
  val dhahawnMouth = 16.20 ll 52.23
  val haswayn = 15.63 ll 52.22
  val sYemen = degs(12.65, 43.98)
  val sharmas = degs(28.03, 35.23)
  val alFaw = degs(29.93, 48.47)
      
  val arabia: EArea2 = EArea2("Arabia", degs(25, 45.0), desert, Anatolia.damascus, alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar,
     alKhaburah, eOman, mirbat, dhahawnMouth, haswayn, sYemen, sharmas, Anatolia.eilat)
   
  val mahshahr = 30.22.north * Caucasus.asiaMinorE

  val iraq: EArea2 = EArea2("Iraq", 34.0 ll 44.5, desert, Anatolia.damascus, Anatolia.cizre, Caucasus.caspianSW, mahshahr, alFaw)
   
  val mianiLat: Latitude = 25.39.north

  /** 38.86N */
  val persiaN = 38.86.north

  val caspianSE = degs(36.92, 54.03)
  val persiaCaspianN = persiaN ll 53.99
  val persiaNE = persiaN * RusNorth.wAsiaE
  val mianiHor = mianiLat * RusNorth.wAsiaE
  val seIran = degs(25.37, 61.67)
  val kuhmobarak = 25.80 ll 57.30
  val nHormuz = 27.17 ll 56.47
  val nwHormuz = 26.49 ll 54.79
  val zeydan = 27.88 ll 51.50

  val persia: EArea2 = EArea2("Persia", degs(32.4, 60), hills, mahshahr, Caucasus.caspianSW, caspianSE, persiaCaspianN, persiaNE,
     mianiHor, seIran, kuhmobarak, nHormuz, nwHormuz, zeydan)
 
   
  val caspianN = 47.05.north * RusNorth.cEuropeE
  val caspianNE = degs(46.66, 53.03)
  val bautino = degs(44.53, 50.24)
  val kendirliBay = degs(42.73, 52.74)
  val mangystau = degs(45.48, 52.78)
  val volodarsky = degs(46.39, 49.03)

  val kazak: EArea2 = EArea2("Kazak", degs(47, 60), plain, RusNorth.kazakNE, persiaNE, persiaCaspianN, kendirliBay, bautino, mangystau,
     caspianNE, caspianN, volodarsky, Ukraine.caspianW, Baltland.southEast)
  
  val caspianSea: EArea2 = EArea2("CaspianSea", degs(42.10, 50.64), sea, caspianN, caspianNE, persiaCaspianN, caspianSE,
     Caucasus.caspianSW)
    
  val magdhara = 22.41.north * RusNorth.indiaE
       
  //val chinaSW = AsiaWestPts.mianiLat * indiaE
  val indiaNE = AsiaWestPts.mianiLat * RusNorth.indiaE
  val himilayasE = 83.75.east
  //val himilayasSE = AsiaWestPts.mianiLat * himilayasE
  val balasore = degs(21.41, 86.97)
  val ongale = 15.46 ll 80.18
  val kattupali = 13.29 ll 80.34
  val manapad = 8.37 ll 78.06
  val sIndia = degs(8.11, 77.50)
  val kovalam = 8.39 ll 76.97
  val karnataka1 = 14.75 ll 74.23
  val karnataka2 = 14.80 ll 74.09
  val tarapur = 19.83 ll 72.65
  val khambat = 22.26 ll 72.48
  val girSomnath = 20.69 ll 70.83
  val varvala = 22.30 ll 68.93
  val kutchMouth = 22.92 ll 70.37
  val bhada = 22.83 ll 69.19
  val karachi = degs(25.38, 66.70)

  val india: EArea2 = EArea2("India", degs(20.85, 78.68), jungle, mianiHor, indiaNE, indiaNE, magdhara, balasore, ongale, kattupali,
     manapad, sIndia, kovalam, karnataka1, karnataka2, tarapur, khambat, girSomnath, varvala, kutchMouth, bhada, karachi)
               
  val dodanduwa = 6.10 ll 80.12
  val wSriLanka = 8.2 ll 79.69
  val nwSrilanka = 9.75 ll 79.86
  val neSrilanka = 9.83 ll 80.24
  val oiluvil = 7.29 ll 81.86
  val dondra = 5.92 ll 80.59

  val sriLanka: EArea2 = EArea2("SriLanka", 7.47 ll 80.78, jungle, dodanduwa, wSriLanka, nwSrilanka, neSrilanka, oiluvil, dondra)
   
  val cAsiaSE = AsiaWestPts.persiaN * RusNorth.indiaE

   val himalayas: EArea2 = EArea2("Himalayas", degs(32, 75), mtain, cAsiaSE, AsiaWestPts.persiaNE, AsiaWestPts.mianiHor,
      AsiaWestPts.indiaNE)
       
   val centralAsia = EArea2("CAsia", degs(47, 76), plain, RusNorth.cAsiaNE, RusNorth.kazakNE, persiaNE, cAsiaSE)
        
   val wSeverny = 71.81 ll 51.49
   val severny1 = 75.37 ll 57.03
   val severnyN = 76.99 ll 67.91
   val severny2 = 72.28 ll 55.36
   val eSeverny = 70.71 ll 57.59
   val severny = EArea2("Severny", 74.38 ll 57.29, ice, wSeverny, severny1, severnyN, severny2, eSeverny)
}