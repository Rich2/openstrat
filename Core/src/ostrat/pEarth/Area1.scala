/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._

abstract class Area1(val sym: Symbol, val cen: LatLong) extends GeographicSymbolKey
{   
   def neighbs: List[Area1] = Nil
   def a2Seq: List[Area2]   
   def fill: Boolean = true         
   //def a2r(eg: EarthGui): Disp2 = a2Seq.displayFold(_.display(eg, fill))  
   def disp2(eg: EarthGui): Disp2 = a2Seq.displayFold(_.display(eg, fill))          
}
