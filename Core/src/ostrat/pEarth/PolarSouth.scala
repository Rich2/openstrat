/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import LatLong._
import Terrain._

object PolarSouth extends Area1('PolarSouth, -89.9 ll 0)
{
   //type A2Type = Area2 
   
   val antartica = Area2('Antartic, deg(-89.91, 0), ice, deg(-70.7, 4.77), deg(-66.38, 53.46), deg(-71.39, 71.22), deg(-66.48, 102.33),
         deg(-66.2, 130.98), deg(-68.82, 153.13), deg(-71.59, 169.13), deg(-78.12, 166.14), deg(-77.37, -156.41), deg(-74.85, -127.23),
         deg(-73.36, -98.40), deg(-69.2, -65.71), deg(-82.6, -60.61), deg(-80.31, -33.72), deg(-78.61, -35.65/* db */), deg(-72.27, -10.51))   
   
   //override val gridMaker = E80Empty       
   override val a2Seq = List(antartica) 
}