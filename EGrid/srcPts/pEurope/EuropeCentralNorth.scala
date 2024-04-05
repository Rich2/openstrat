/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** Displays Netherlands depends on [[BelgLux]]. */
object Netherlands extends EArea2("Netherlands", 52.31 ll 5.91, oceanic)
{ val rootunmerroog = 53.539 ll 6.604
  val eemshaven: LatLong = 53.439 ll 6.881

  val northEast: LatLong = 53.24 ll 7.2
  val p30: LatLong = 51.897 ll 6.722
  val p35: LatLong = 51.835 ll 5.961
  val p40: LatLong = 51.361 ll 6.224
  val wMiddleburg: LatLong = 51.53 ll 3.43
  val nooderhaaksEast: LatLong = 52.973 ll 4.657
  val p80: LatLong = 53.39 ll 5.20

  val westerwodenseeMouth = 53.24 ll 7.21

  override val polygonLL: PolygonLL = PolygonLL(northEast, p30, p35, p40, BelgLux.aachen, BelgLux.north, BelgLux.coastEast, wMiddleburg,
    nooderhaaksEast, p80, rootunmerroog, eemshaven, westerwodenseeMouth)
}

/** Displays roughly Germany on map. Depends on [[Alsace]], [[FranceNorth]] and [[BelgLux]]. */
object Germania extends EArea2("Germania", 50.50 ll 11.11, oceanic)
{ val p80: LatLong = 53.506 ll 7.048
  val borkum = 53.594 ll 6.641
  val cuxhaven: LatLong = 53.88 ll 8.66
  val swinoujscie: LatLong = 53.92 ll 14.24

  override val polygonLL = PolygonLL(Netherlands.northEast, p80, borkum, cuxhaven, Jutland.swJutland, Jutland.lubeck, swinoujscie, Alpsland.vienna,
    Alpsland.steyr, Alsace.basel, Alsace.east, Alsace.luxSE, BelgLux.aachen, Netherlands.p40, Netherlands.p35, Netherlands.p30)

  val berlin: LocationLL = LocationLL("Berlin", 52.52, 13.41, 1)
  val dresden = LocationLL("Dresden", 51.05, 13.74, 2)

  override val places: LocationLLArr = LocationLLArr(berlin)
}

/** Displays the Alps, depends on [[Alsace]], [[FranceNorth]] and [[ItalyNorth]]. */
object Alpsland extends EArea2("Alpsland", 45.93 ll 10.49, mtainDepr)
{ val steyr: LatLong = 48.04 ll 14.14
  val vienna: LatLong = 48.22 ll 16.41
  val zagreb: LatLong = 45.78 ll 15.97
   
  val monfalcone: LatLong = 45.78 ll 13.56
  val portoDiFalconera: LatLong = 45.61 ll 12.91

  val petraLigure: LatLong = 44.14 ll 8.28
  val imperia: LatLong = 43.89 ll 8.06
      
  val polygonLL: PolygonLL = PolygonLL(FranceSouth.stRaphael, FranceSouth.orangeCrossing, FranceSouth.montelimar, FranceNorth.southEast,
    FranceNorth.bourgeEnBresse, Alsace.southWest, Alsace.basel, steyr, vienna, zagreb, monfalcone, portoDiFalconera, ItalyNorth.venice, ItalyNorth.voltri,
    petraLigure, imperia)
}
