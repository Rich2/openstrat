/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Dubious trait. Only known sub trait is TileGrid, used by pGrid.RectGui */
trait RectMap
{
   val scale: Dist
   
   def left: Dist
   def bottom: Dist
   def right: Dist
   def top: Dist
   
   def xLen: Dist = right - left   
   lazy val xCen: Dist = (left + right) / 2
   def yLen: Dist = right - left
   lazy val yCen: Dist = (left + right)  / 2
   lazy val cen: Dist2 = Dist2(xCen, yCen)   
   lazy val diagLen: Dist = Dist2(xLen, yLen).magnitude
//   def xLenDist: Dist = rightDist - leftDist   
//   def xCenDist: Dist = (leftDist + rightDist) / 2
}

trait RectMapToDist extends RectMap
{
   def useless = "blah"
   
}
