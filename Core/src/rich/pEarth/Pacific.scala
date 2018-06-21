/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pEarth
import geom._
import Terrain._

object PacificTop extends Area1('Pacific, 0 ll 175)
{
  // type A2Type = Area2   
   
   val sHawaii = 18.91 ll -155.68
   val nwHawaii = 21.57 ll -158.28
   val nHawaii = 21.71 ll -157.97
   val hana = 20.75 ll -155.98
   val eHawii = 19.51 ll -154.80
   val hawaii = Area2('Hawaii, 20.85 ll -156.92, plain, sHawaii, nwHawaii, nHawaii, hana, eHawii)
 // override val gridMaker = E80Empty 
  override val a2Seq: List[Area2] = List(hawaii) 
}