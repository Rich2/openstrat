/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._, pGrid._

case class TerrOnly(x: Int, y: Int, terr: WTile) extends ETileOld
{
  type FromT = WTile
  def fromT = terr
}
object TerrOnly
{
   implicit val tileMaker: (Int, Int, WTile) => TerrOnly = apply
   implicit object TerrOnlyIsType extends IsType[TerrOnly]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[TerrOnly]
      override def asType(obj: AnyRef): TerrOnly = obj.asInstanceOf[TerrOnly]
   }
}

case class SideOldOnly(x: Int, y: Int) extends TileSideOld

object SideOldOnly
{
   implicit object TerrOnlyIsType extends IsType[SideOldOnly]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideOldOnly]
      override def asType(obj: AnyRef): SideOldOnly = obj.asInstanceOf[SideOldOnly]
   }
}

class EGridOldOnly(name: String, cenLong: Longitude, scale: Dist, xOffset: Int, yOffset: Int, xTileMin: Int, xTileMax: Int,
                   yTileMin: Int, yTileMax: Int, turnNum: Int) extends EGridOld[TerrOnly, SideOldOnly](new Array[Int](0), name, cenLong, scale, xOffset, yOffset,
    xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
          