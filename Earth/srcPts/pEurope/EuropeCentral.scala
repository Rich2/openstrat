/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** Displays Netherlands depends on [[BelgLux]]. */
object Netherlands extends EArea2("Netherlands", 52.31 ll 5.91, plain)
{  val wMiddleburg = 51.53 ll 3.43
   val denHeider = 52.95 ll 4.72
   val p10 = 53.39 ll 5.20
   val eemshaven = 53.46 ll 6.82
   val westerwodenseeMouth = 53.24 ll 7.21
   val neNetherlands = 53.24 ll 7.2

   override val polygonLL = PolygonLL(BelgLux.coastEast, wMiddleburg, denHeider, p10, eemshaven, westerwodenseeMouth, neNetherlands, BelgLux.aachen,
      BelgLux.north)
}

/** Displays roughly Germany on map. Depends on [[Alsace]], [[Frankia]] and [[BelgLux]]. */
object Germania extends EArea2("Germania", 50.50 ll 11.11, plain)
{  val cuxhaven = 53.88 ll 8.66
   val swinoujscie = 53.92 ll 14.24
      
   override val polygonLL = PolygonLL(Netherlands.neNetherlands, cuxhaven, Jutland.swJutland, Jutland.lubeck, swinoujscie, Alpsland.vienna,
      Alpsland.steyr, Alsace.basel, Alsace.east, Alsace.luxSE, BelgLux.aachen)

   val berlin: LocationLL = LocationLL("Berlin", 52.52, 13.41, 1)

   override val places: LocationLLArr = LocationLLArr(berlin)
}

/** Displays the Alps, depends on [[Alsace]], [[Frankia]] and [[Italy]]. */
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
      Alsace.southWest, Alsace.basel, steyr, vienna, zagreb, monfalcone, portoDiFalconera, Italy.venice, Italy.voltri, petraLigure, imperia
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