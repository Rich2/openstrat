/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFx

object TestApp extends App
{
   import geom._
   val sPt = Vec2(0, -1)
   val endPt = Vec2(-1, 0)   
   val a = ArcSeg(endPt, Vec2Z)   
   println(a.controlPt(sPt))
}