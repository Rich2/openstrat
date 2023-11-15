/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

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
   val dresden = LocationLL("Dresden", 51.05, 13.74, 2)
   override val places: LocationLLArr = LocationLLArr(berlin)
}

/** Displays the Alps, depends on [[Alsace]], [[Frankia]] and [[Italy]]. */
object Alpsland extends EArea2("Alpsland", 45.93 ll 10.49, mtain)
{ val steyr = 48.04 ll 14.14
  val vienna = 48.22 ll 16.41
  val zagreb = 45.78 ll 15.97
   
  val monfalcone = 45.78 ll 13.56
  val portoDiFalconera = 45.61 ll 12.91
   
  val petraLigure = 44.14 ll 8.28
  val imperia = 43.89 ll 8.06
      
  val polygonLL = PolygonLL(FranceSouth.stRaphael, FranceSouth.orangeCrossing, FranceSouth.montelimar, Frankia.southEast, Frankia.bourgeEnBresse,
     Alsace.southWest, Alsace.basel, steyr, vienna, zagreb, monfalcone, portoDiFalconera, Italy.venice, Italy.voltri, petraLigure, imperia)
}
