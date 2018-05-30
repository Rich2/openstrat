/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth
import Terrain._

object AtlanticNorth extends Area1('NAtlantic, 60 ll -30)
{ 
  // override val gridMaker = E80Empty   
   override val a2Seq: List[Area2] = List(Iceland)
}

object Iceland extends Area2('Iceland, 64.78 ll -18.07, taiga)
{   
   val reykjavik = 64.17 ll -21.75
   val w1 = 64.75 ll -22.30
   val wIceland = 64.87 ll -24.04
   val kleifar = 65.45 ll -21.68
   val breidavik = 65.50 ll -24.52
   val homstrandir = 66.46 ll -22.93
   val n1 = 65.99 ll -21.31
   val hrutafjodur = 65.15 ll -21.07
   val n2 = 66.08 ll -20.41
   val nIceland = 66.53 ll -16.15
   val n3 = 65.55 ll -13.67
   val eIceland = 65.07 ll -13.49
   val vattames = 64.92 ll -13.68
   val sIceland = 63.40 ll -18.73
   val swIceland = 63.8 ll -22.70
   val latLongs = LatLongs(reykjavik, w1, wIceland, kleifar, breidavik, homstrandir, n1, hrutafjodur, n2, nIceland,
         n3, eIceland, vattames, sIceland, swIceland)
}