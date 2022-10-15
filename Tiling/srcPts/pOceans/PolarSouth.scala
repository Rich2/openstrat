/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._

object PolarSouth extends EArea1("PolarSouth", -89.9 ll 0)
{
  val antartica: EArea2 = EArea2("Antartic", -89.91 ll 0, ice, -70.7 ll 4.77, -66.38 ll 53.46, -71.39 ll 71.22, -66.48 ll 102.33,
    -66.2 ll 130.98, -68.82 ll 153.13, -71.59 ll 169.13, -78.12 ll 166.14, -77.37 ll -156.41, -74.85 ll -127.23, -73.36 ll -98.40, -69.2 ll -65.71,
    -82.6 ll -60.61, -80.31 ll -33.72, -78.61 ll -35.65/* db */, -72.27 ll -10.51)
   
   //override val gridMaker = E80Empty       
   override val a2Arr: RArr[EArea2] = RArr(antartica)
}