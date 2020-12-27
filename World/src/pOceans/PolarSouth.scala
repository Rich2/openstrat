/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, LatLong._, WTile._

object PolarSouth extends WldArea1("PolarSouth", -89.9 ll 0)
{
   //type A2Type = Area2 
   
   val antartica = WldArea2("Antartic", degs(-89.91, 0), ice, degs(-70.7, 4.77), degs(-66.38, 53.46), degs(-71.39, 71.22), degs(-66.48, 102.33),
         degs(-66.2, 130.98), degs(-68.82, 153.13), degs(-71.59, 169.13), degs(-78.12, 166.14), degs(-77.37, -156.41), degs(-74.85, -127.23),
         degs(-73.36, -98.40), degs(-69.2, -65.71), degs(-82.6, -60.61), degs(-80.31, -33.72), degs(-78.61, -35.65/* db */), degs(-72.27, -10.51))
   
   //override val gridMaker = E80Empty       
   override val a2Arr = Arr(antartica)
}