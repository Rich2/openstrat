/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

class HexDirn(x: Int, y: Int)
{
   val halfRelCood: Cood = Cood(x, y)
   def relCood: Cood = Cood(x * 2, y * 2)   
   def vec: Pt2 = HexGridAncient.coodToVec2(halfRelCood)
}

object HCen extends HexDirn(0, 0) 
object HUpRight extends HexDirn(1, 1)
object HRight extends HexDirn(2, 0) 
object HDownRight extends HexDirn(1, -1) 
object HDownLeft extends HexDirn(-1, -1)
object HLeft extends HexDirn(-2, 0)
object HUpLeft extends HexDirn(-1, 1)

object HexDirn
{
   def fromNeighbTileCood(relCood: Cood): HexDirn = fromNeighbTileCood(relCood.xi, relCood.yi)
   def fromNeighbTileCood(relX: Int, relY: Int): HexDirn = (relX, relY) match
   {
      case (2, 2) => HUpRight
      case (4, 0) => HRight
      case (2, -2) => HDownRight
      case (-2, -2) => HDownLeft
      case (-4, 0) => HLeft
      case (-2, 2) => HUpLeft
      case _ => throw excep("HexDirn")
   }
   
   def optFromNeighbTileCood(cood1: Cood, cood2: Cood): Option[HexDirn] = (cood2 -cood1) match
   {
      case Cood(2, 2) => Some(HUpRight)
      case Cood(4, 0) => Some(HRight)
      case Cood(2, -2) => Some(HDownRight)
      case Cood(-2, -2) => Some(HDownLeft)
      case Cood(-4, 0) => Some(HLeft)
      case Cood(-2, 2) => Some(HUpLeft)
      case _ => None
   }
   
}
/** A HexCood represents an integer coordinate within a tile grid system. This has current been implemented for Hexs and Squares, while
 *  triangles is the third possible regular tile system. A HexCood represents either a tile centre, a tile side or a tile vertex. This system
 *  allows river and naval units to move along the tile sides. */
//final class HexCood private(val x: Int, val y: Int) extends TileCood// with HexGridCood
//{   
//   import pGrid.{HexCood => hg}
//   
//   def vertCoods: Seq[HexCood] = HexCood.hVertCoods.map(_ + this)   
//   
//   def verts: Vec2s =  HexCood.verts.slate(x, yAdj)// Seq(x + hg.x0 v yAdj + hg.y0)
////         x + hg.x1, yAdj + hg.y1,
////         x + hg.x2, yAdj + hg.y2,
////         x + hg.x3, yAdj + hg.y3,
////         x + hg.x4, yAdj + hg.y4,
////         x + hg.x5, yAdj + hg.y5)
//   
//   def cen: Vec2 = Vec2(x, yAdj)
//   def cenVerts: Vec2s = HexCood.cenVerts.slate(x, yAdj)//Vec2.xy(
////         x, yAdj,
////         x + hg.x0, yAdj + hg.y0,
////         x + hg.x1, yAdj + hg.y1,
////         x + hg.x2, yAdj + hg.y2,
////         x + hg.x3, yAdj + hg.y3,            
////         x + hg.x4, yAdj + hg.y4,            
////         x + hg.x5, yAdj + hg.y5
////      )  
//   
//   @inline def ownedSideCoods: List[HexCood] = List(HexCood(x + 1, y + 1), HexCood(x + 2, y), HexCood(x + 1, y - 1))
//   @inline def nonOwnedSideCoods: List[HexCood] = List(HexCood(x - 1, y - 1), HexCood(x - 2, y), HexCood(x - 1, y + 1))
//   @inline def sideCoods: List[HexCood] = ownedSideCoods ::: nonOwnedSideCoods
//
//   def downLevelHexs: Seq[HexCood] =
//   {
//      val adj = this * 4
//      val cs = Seq((-4, -4), (0, -4), (4, -4),
//            (-6, -2), (-2, -2), (2, -2), (6, -2),
//            (-8, 0), (-4, 0), (0, 0), (4, 0),
//            (-6, 2), (-2, 2), (2, 2), (6, 2),
//            (0, 4))
//      cs.map(c=> adj.addXY(c._1, c._2))      
//   }
//   def hexVertsLatLongU(latLongOffset: LatLong, xyOffset: Dist2): Seq[LatLong] = verts.map(v => hg.latLongU(v, latLongOffset, xyOffset))
//   def hexVertsLatLongV(latLongOffset: LatLong, xyOffset: Dist2): Seq[LatLong] = verts.map(v => hg.latLongV(v, latLongOffset, xyOffset))
//   def hexVertsLatLongW(latLongOffset: LatLong, xyOffset: Dist2): Seq[LatLong] = verts.map(v => hg.latLongW(v, latLongOffset, xyOffset)) 
//   
//   @inline def fOrientation[A](upRight: => A, rightSide: => A, downRight: => A): A = Unit match
//   {
//      case _ if (y.div4Rem1 && x.div4Rem1) || (y.div4Rem3 && x.div4Rem3) => upRight
//      case _ if (y.isDivBy4 && x.div4Rem2) || (y.div4Rem2 && x.isDivBy4) => rightSide      
//      case _ if (y.div4Rem1 && x.div4Rem3) || (y.div4Rem3 && x.div4Rem1) => downRight
//      case _ => excep("invalid Hex Side coordinate: " - xyStr)
//   }
//   def orientationStr: String = fOrientation("UR", "Rt", "DR")
//   import HexCood.yDist
//   def hexSideLine: Line2 = fOrientation(
//         Line2(x - 1, yAdj + yDist / 2, x + 1, yAdj - yDist / 2),   
//         Line2(x, yAdj + yDist, x, yAdj - yDist),         
//         Line2(x - 1, yAdj - yDist / 2, x + 1, yAdj + yDist / 2))
//   
//}


