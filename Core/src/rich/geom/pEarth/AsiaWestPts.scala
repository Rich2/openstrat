/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth
import LatLong._
import Terrain._

object AsiaWestPts
{
   /** South West Turkey */       
   
   val salwa = 24.71 ll 50.77
   val nQatar = 26.15 ll 51.26
   val doha = 25.25 ll 51.61
   val alGharbia = deg(23.97, 51.94)
   val icad = 24.30 ll 54.44
   val kumzar = 26.36 ll 56.38
   val alKhaburah = 23.98 ll 57.10
   val eOman = deg(22.48, 59.72)
   val mirbat = 16.94 ll 54.80
   val dhahawnMouth = 16.20 ll 52.23
   val haswayn = 15.63 ll 52.22
   val sYemen = deg(12.65, 43.98)
   val sharmas = deg(28.03, 35.23)
   val alFaw = deg(29.93, 48.47)
      
   val arabia = Area2('Arabia, deg(25, 45.0), desert,
         EuropePts.damascus, alFaw, salwa, nQatar, doha, alGharbia, icad, kumzar, alKhaburah, eOman,
               mirbat, dhahawnMouth, haswayn, sYemen, sharmas, EuropePts.eilat)     
   
   val mahshahr = 30.22.north * EuropePts.asiaMinorE
   
   val iraq = Area2('Iraq, 34.0 ll 44.5, desert, EuropePts.damascus, EuropePts.cizre, EuropePts.caspianSW, mahshahr, alFaw)
   
   val mianiLat: Latitude = 25.39.north
   val wAsiaE = 66.52.east 
   val persiaN = 38.86.north    
   val caspianSE = deg(36.92, 54.03)
   val persiaCaspianN = persiaN ll 53.99 
   val persiaNE = persiaN * wAsiaE
    val mianiHor = mianiLat * wAsiaE
   val seIran = deg(25.37, 61.67)
   val kuhmobarak = 25.80 ll 57.30
   val nHormuz = 27.17 ll 56.47
   val nwHormuz = 26.49 ll 54.79
   val zeydan = 27.88 ll 51.50
   val persia = Area2('Persia, deg(32.4, 60), hills,
         mahshahr, EuropePts.caspianSW, caspianSE, persiaCaspianN, persiaNE, mianiHor, seIran, kuhmobarak, nHormuz, nwHormuz, zeydan)                       
   
   val cAsiaN = 54.89.north
   val kazakNE = cAsiaN * wAsiaE  
   val cEuropeE = 51.36.east
    
   val nRusSW = cAsiaN * cEuropeE
   val caspianN = 47.05.north * cEuropeE   
   val caspianNE = deg(46.66, 53.03)
   val bautino = deg(44.53, 50.24)
   val kendirliBay = deg(42.73, 52.74)
   val mangystau = deg(45.48, 52.78)
   val volodarsky = deg(46.39, 49.03) 
   val kazak = Area2('Kazak, deg(47, 60), plain,
         kazakNE, persiaNE, persiaCaspianN, kendirliBay, bautino, mangystau, caspianNE, caspianN, volodarsky, EuropePts.caspianW, EuropePts.nRusSW)         
   
   val indiaE = 91.5.east
   val amderma = 69.76 ll 61.68
   val nRusNE = 75.64.north * indiaE  
   val cAsiaNE = cAsiaN * indiaE
   val nRus = Area2('NRus, deg(61, 54), taiga,
         amderma, nRusNE, cAsiaNE, kazakNE, nRusSW, EuropePts.nRusSW, EuropePts.onezhsky)   
  
   val caspianSea = Area2('CaspianSea, deg(42.10, 50.64), sea, caspianN, caspianNE, persiaCaspianN, caspianSE, EuropePts.caspianSW) 
         
    
   val magdhara = 22.41.north * indiaE
       
   //val chinaSW = AsiaWestPts.mianiLat * indiaE
   val indiaNE = AsiaWestPts.mianiLat * indiaE
   val himilayasE = 83.75.east   
   //val himilayasSE = AsiaWestPts.mianiLat * himilayasE
   val balasore = deg(21.41, 86.97)
   val ongale = 15.46 ll 80.18
   val kattupali = 13.29 ll 80.34
   val manapad = 8.37 ll 78.06
   val sIndia = deg(8.11, 77.50)
   val kovalam = 8.39 ll 76.97
   val karnataka1 = 14.75 ll 74.23
   val karnataka2 = 14.80 ll 74.09
   val tarapur = 19.83 ll 72.65
   val khambat = 22.26 ll 72.48
   val girSomnath = 20.69 ll 70.83
   val varvala = 22.30 ll 68.93
   val kutchMouth = 22.92 ll 70.37
   val bhada = 22.83 ll 69.19
   val karachi = deg(25.38, 66.70)   
   val india = Area2('India, deg(20.85, 78.68), jungle,
         mianiHor, indiaNE, indiaNE, magdhara, balasore, ongale, kattupali, manapad, sIndia,
               kovalam, karnataka1, karnataka2, tarapur, khambat, girSomnath, varvala, kutchMouth, bhada, karachi) 
               
   val dodanduwa = 6.10 ll 80.12
   val wSriLanka = 8.2 ll 79.69
   val nwSrilanka = 9.75 ll 79.86
   val neSrilanka = 9.83 ll 80.24
   val oiluvil = 7.29 ll 81.86
   val dondra = 5.92 ll 80.59
   val sriLanka = Area2('SriLanka, 7.47 ll 80.78, jungle, dodanduwa, wSriLanka, nwSrilanka, neSrilanka, oiluvil, dondra)
   
   val cAsiaSE = AsiaWestPts.persiaN * AsiaWestPts.indiaE            
   val himalayas = Area2('Himalayas, deg(32, 75), mtain,
         cAsiaSE, AsiaWestPts.persiaNE, AsiaWestPts.mianiHor, AsiaWestPts.indiaNE)   
       
    val centralAsia = Area2('CAsia, deg(47, 76), plain, cAsiaNE, kazakNE, persiaNE, cAsiaSE)
    
    
    val wSeverny = 71.81 ll 51.49
    val severny1 = 75.37 ll 57.03
    val severnyN = 76.99 ll 67.91
    val severny2 = 72.28 ll 55.36
    val eSeverny = 70.71 ll 57.59
    val severny = Area2('Severny, 74.38 ll 57.29, ice, wSeverny, severny1, severnyN, severny2, eSeverny)
}
