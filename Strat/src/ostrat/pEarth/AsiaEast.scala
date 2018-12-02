/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends Area1('Asia, 60 ll 100)    
{   
   //type A2Type = Area2
   override def fill = true//false
   import AsiaEastPts._
   //override val gridMaker = E80Empty
   override val a2Seq = List(seAsia, ceAsia, neAsia, feAsia, japan) 
}
