/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

object Germania extends EArea2("Germania", 50.50 ll 11.11, plain)
{
   val eBelgianCoast = 51.36 ll 3.37
   val wMiddleburg = 51.53 ll 3.43
   val eemshaven = 53.44 ll 6.82      
   val neNetherlands = 53.24 ll 7.2
   
   val cuxhaven = 53.88 ll 8.66   
   val swinoujscie = 53.92 ll 14.24
      
   val polygonLL = PolygonLL(Frankia.belgianCoast, eBelgianCoast, wMiddleburg, eemshaven, neNetherlands, cuxhaven,
         Jutland.swJutland, Jutland.lubeck, swinoujscie, Alpsland.vienna, Alpsland.steyr, Frankia.basel)

   val berlin: LocationLL = LocationLL("Berlin", 52.52, 13.41, 1)

   override val places: LocationLLArr = LocationLLArr(berlin)
}

object Alpsland extends EArea2("Alpsland", 45.93 ll 10.49, mtain)
{
   val steyr = 48.04 ll 14.14
   val vienna = 48.22 ll 16.41
   val zagreb = 45.78 ll 15.97
   
   val monfalcone = 45.78 ll 13.56
   val portoDiFalconera = 45.61 ll 12.91
   
   val petraLigure = 44.14 ll 8.28
   val imperia = 43.89 ll 8.06  
      
   val polygonLL = PolygonLL(FranceSouth.stRaphael, FranceSouth.orangeCrossing, FranceSouth.montelimar, Frankia.southEast, Frankia.bourgeEnBresse,
      Frankia.basel, steyr, vienna, zagreb, monfalcone, portoDiFalconera, Italy.venice, Italy.voltri, petraLigure, imperia
   )
}

object Italy extends EArea2("Italy", 42.25 ll 13.39, plain)
{  
   val venice = 45.42 ll 12.21
   val ven1 = 44.96 ll 12.55
   val cervia = 44.25 ll 12.35
   val ancona = 43.62 ll 13.51
   val guilianova = 42.76 ll 13.96   
   val vasto = 42.10 ll 14.71
   val campomarina = 41.92 ll 15.13
   val vieste = 41.88 ll 16.17
   val siponto = 41.60 ll 15.89
   val barletta = 41.32 ll 16.28   
   val otranto = 40.12 ll 18.45
   
   val leuca = 39.79 ll 18.34
   val taranto = 40.52 ll 17.09
   val bruscata = 39.76 ll 16.48   
   val capoColonna = 39.02 ll 17.20
   val palizzi = 37.95 ll 16.03
   val riaciCapo = 37.95 ll 15.67
   val giovanni = 38.23 ll 15.63
   
   val bagnaraCalabra = 38.28 ll 15.79
   
   val lamezia = 38.89 ll 16.22
   val licosa = 40.25 ll 14.91  
   val diProcida = 40.79 ll 14.04
   val gaeta = 41.20 ll 13.57   
   val santaMarinella = 42.03 ll 11.83  
   val puntaAla = 42.80 ll 10.73
   val livorno = 43.54 ll 10.29
   val forteDeiMarmi = 43.96 ll 10.16
   val palmaria = 44.03 ll 9.84
   val recco = 44.35 ll 9.14
   val genoa = 44.39 ll 8.94
   val voltri = 44.42 ll 8.75
   
   val polygonLL = PolygonLL(venice, ven1, cervia, ancona, guilianova, vasto, campomarina, vieste, siponto, barletta, otranto,
                leuca, taranto, bruscata, capoColonna, palizzi, riaciCapo, giovanni,
               bagnaraCalabra, lamezia, licosa, diProcida, gaeta, santaMarinella, puntaAla,
               livorno, forteDeiMarmi, palmaria, recco, genoa, voltri)   
}

object Corsica extends EArea2("Corsica", 42.18 ll 9.17, hills)
{   
   val nCorsica = 43.00 ll 9.42
   val bastia = 42.70 ll 9.45
   val olmuccia = 41.69 ll 9.40
   val sCorsica = 41.37 ll 9.21
   val swCorsica = 41.56 ll 8.79
   val scandola = 42.37 ll 8.54
   val nwCalvi = 42.57 ll 8.71
   val pointeMignola = 42.73 ll 9.16
   val fromontica = 42.67 ll 9.29
   
   val polygonLL = PolygonLL(nCorsica, bastia, olmuccia, sCorsica, swCorsica, scandola, nwCalvi, pointeMignola, fromontica)
}

object Sardina extends EArea2("Sardina", 40.12 ll 9.07, hills)
{
   val calaCaterina = 39.10 ll 9.51
   val perdaLonga = 38.87 ll 8.84
   val capoTeulada = 38.86 ll 8.64
   val portscuso = 39.21 ll 8.36
   val capoFalcone = 40.97 ll 8.20
   val platamona = 40.81 ll 8.46
   val north = 41.25 ll 9.23   
   val east = 40.52 ll 9.82
   
   val polygonLL = PolygonLL(calaCaterina, perdaLonga, capoTeulada, portscuso, capoFalcone, platamona, north, east)
}