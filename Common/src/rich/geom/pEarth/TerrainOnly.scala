/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pEarth
//import pGrid._

case class TerrOnly(x: Int, y: Int, terr: Terrain) extends ETile
object TerrOnly
{
   implicit val tileMaker: (Int, Int, Terrain) => TerrOnly = apply
   implicit object W2IsType extends IsType[TerrOnly]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[TerrOnly]
      override def asType(obj: AnyRef): TerrOnly = obj.asInstanceOf[TerrOnly]
   }
}

class EGridOnly(name: String, cenLong: Longitude, scale: Dist, xOffset: Int, yOffset: Int,  xTileMin: Int, xTileMax: Int,
      yTileMin: Int, yTileMax: Int) extends EGrid[TerrOnly](new Array[Int](0), name, cenLong, scale, xOffset, yOffset, xTileMin, xTileMax,
      yTileMin, yTileMax: Int)
          