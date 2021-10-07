/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pGrid._

case class TerrOnly(x: Int, y: Int, terr: WTile) extends ETileAncient
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

case class SideOnlyAncient(x: Int, y: Int) extends TileSideAncient

object SideOnlyAncient
{
   implicit object TerrOnlyIsType extends IsType[SideOnlyAncient]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideOnlyAncient]
      override def asType(obj: AnyRef): SideOnlyAncient = obj.asInstanceOf[SideOnlyAncient]
   }
}

class EGridAncientOnly(name: String, cenLong: Longitude, scale: Metres, xOffset: Int, yOffset: Int, xTileMin: Int, xTileMax: Int,
                       yTileMin: Int, yTileMax: Int, turnNum: Int) extends EGridAncient[TerrOnly, SideOnlyAncient](new Array[Int](0), name, cenLong, scale, xOffset, yOffset,
    xTileMin, xTileMax, yTileMin, yTileMax, turnNum)