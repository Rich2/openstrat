/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Cubic bezier curve */
class Bezier (xStart: Double, yStart: Double, xCtl1: Double, yCtl1: Double, xCtl2: Double, yCtl2: Double, xEnd: Double, yEnd: Double)
{
   def start = Vec2(xStart, yStart)
   def ctl1 = Vec2(xCtl1, yCtl1)
   def ctl2 = Vec2(xCtl2, yCtl2)
   def endPt = Vec2(xEnd, yEnd)
}