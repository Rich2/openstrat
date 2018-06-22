/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
package pEuropeWest
import geom._
import Terrain._

object Frankia extends Area2('Frankia, 47.28 ll 1.93, plain)
{
   val swFrance = 43 ll -2    
   
   val sLAmelie = 45.47 ll -1.15
   val royan = 45.61 ll -1.04
   val laCoubre = 45.69 ll -1.23
   val laRochelle = 46.15 ll -1.22
   val niortaise = 46.30 ll -1.13
   val sablesdOlonne = 46.49 ll -1.80
   val penmarch = 47.80 ll -4.37
   val vilaineMouth = 47.49 ll -2.44   
   val brest = 48.42 ll - 4.73   
   val landunvez = 48.56 ll -4.72
   val pleubian = 48.86 ll -3.10   
   val yffiniac = 48.49 ll -2.68
   val capFrehel = 48.68 ll -2.31
   val pointeDuGrouin = 48.71 ll -1.84
   val vildeLaMarine = 48.61 ll -1.84
   val avranches = 48.66 ll -1.45
   val cabaneVauban = 48.74 ll -1.57
   val auderville = 49.73 ll -1.94
   val gatteville = 49.69 ll -1.26
   val carentan = 49.36 ll -1.16
   val cabourg = 49.29 ll -0.18
   val villierville = 49.40 ll 0.13
   val seineMouth = 49.43 ll 0.23
   val wLeHavre = 49.51 ll 0.06
   val capAntifer = 49.69 ll 0.16
   val cayeux = 50.18 ll 1.49
   val capGrisNez = 50.87 ll 1.58
   val calais = 50.93 ll 1.74
   val belgianCoast = 51.09 ll 2.54
   
   //val f1 = 49.42 ll 6.54  
   val basel = 47.56 ll 7.58   
   val bourgeEnBresse = 46.20 ll 5.22
   val montelimar = 44.55 ll 4.71
   val orangeCrossing = 44.07 ll 4.76   
   
   val stRaphael = 43.42 ll 6.76
   val frejus = 43.42 ll 6.74
   val laBastideBlanche = 43.15 ll 6.62
   val capBenat = 43.08 ll 6.36
   val laSeyneSurMer = 43.04 ll 5.85
   val fosSurMer = 43.42 ll 4.94  
   val laGrandeMotte = 43.55 ll 4.05
   val narbonne = 43.14 ll 3.08
   
   val seFrance = 42.43 ll 3.17  
    
   val latLongs = LatLongs(swFrance, sLAmelie, royan, laCoubre, laRochelle, niortaise, sablesdOlonne, vilaineMouth, penmarch, brest,
                landunvez, pleubian, yffiniac, capFrehel, pointeDuGrouin, vildeLaMarine, avranches,
                cabaneVauban, auderville, gatteville, carentan, cabourg,villierville, seineMouth,
                wLeHavre, capAntifer, cayeux, capGrisNez, calais, belgianCoast,
                basel, bourgeEnBresse, montelimar, orangeCrossing,               
                stRaphael, frejus,laBastideBlanche, capBenat, laSeyneSurMer,  fosSurMer, laGrandeMotte, narbonne, seFrance)
}

object Iberia extends Area2('Iberia, 41 ll -3.5, hills)
{
   val southWest = 40 ll -8.91
   val espinho = 41.02 ll -8.64
   
   val escaselas = 42.92 ll -9.29
   val malipica = 43.34 ll -8.83   
   val carino = 43.76 ll -7.86
   val santander = 43.49 ll -3.81
   
   
   val neSpain = 42.18 ll 3.06
   val begur = 41.95 ll 3.22
   val barcelona = 41.31 ll 2.12
   val southEast = 40 ll 0.03
   
  
   val gibralter = 36 ll -5.28
   val chipiona = 36.3 ll -6.19
   val heulva = 37 ll -7
   val swPortugal = 37.06 ll -8.34
   val estoril = 38.71 ll -9.48
   
   val valencia = 39.45 ll -0.32
   val xabia = 38.74 ll 0.22
   val almeriaEast = 36.28 ll -2.06
   
      
   val latLongs = LatLongs(gibralter, chipiona, heulva, swPortugal, estoril, espinho, escaselas, malipica, carino, santander, Frankia.swFrance,
         Frankia.seFrance, neSpain, begur, barcelona, valencia, xabia, almeriaEast)
}