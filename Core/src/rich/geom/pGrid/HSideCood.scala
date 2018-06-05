/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pGrid

//case class HSideCood(val x: Int, val y: Int) extends TileSideCood with HexGridCood
//{    
//   def canEqual(a: Any) = a.isInstanceOf[HSideCood]
//   override def equals(that: Any): Boolean = that match
//   {
//      case HSideCood(x2, y2) if x == x2 && y == y2 => true
//      case _ => false
//   }
//   
//   @inline def fOrientation[A](upRight: => A, rightSide: => A, downRight: => A): A = Unit match
//   {
//      case _ if (y.div4Rem1 && x.div4Rem1) || (y.div4Rem3 && x.div4Rem3) => upRight
//      case _ if (y.isDivBy4 && x.div4Rem2) || (y.div4Rem2 && x.isDivBy4) => rightSide      
//      case _ if (y.div4Rem1 && x.div4Rem3) || (y.div4Rem3 && x.div4Rem1) => downRight
//      case _ => excep("invalid Hex Side coordinate: " - xyStr)
//   }
//   def orientationStr: String = fOrientation("UR", "Rt", "DR")
//   override def toString: String = x.commas(y, orientationStr)
//   def topPt: Vec2 =  fOrientation(
//         Vec2(x - 1, yAdj + yDist / 2),   
//         Vec2(x, yAdj + yDist),         
//         Vec2(x + 1, yAdj + yDist / 2))
//         
//   def bottomPt: Vec2 = fOrientation(
//         Vec2(x + 1, yAdj - yDist / 2),   
//         Vec2(x, yAdj - yDist),         
//         Vec2(x - 1, yAdj - yDist / 2))      
//   
//   def hexSideLine: Line2 = fOrientation(
//         Line2(x - 1, yAdj + yDist / 2, x + 1, yAdj - yDist / 2),   
//         Line2(x, yAdj + yDist, x, yAdj - yDist),         
//         Line2(x - 1, yAdj - yDist / 2, x + 1, yAdj + yDist / 2))
//   def leftTileCood: HexCood = fOrientation(
//         HexCood(x - 1 , y - 1),
//         HexCood(x - 2, y),
//         HexCood(x - 1, y + 1 )) 
//   def rightTileCood: HexCood = fOrientation(   
//          HexCood(x + 1, y + 1),
//          HexCood(x + 2, y),
//          HexCood(x + 1, y - 1))
//   @inline def adjTileCoods: (HexCood, HexCood) = (leftTileCood, rightTileCood)
//   
//   def upTileCood: HexCood = fOrientation(
//         HexCood(x - 3, y + 1),
//         HexCood(x , y + 2),
//         HexCood(x + 3, y + 1 ))
//         
//   def downTileCood: HexCood = fOrientation(
//         HexCood(x + 3, y - 1),
//         HexCood(x , y - 2),
//         HexCood(x - 3, y - 1 ))
//   //def upSidePath      
//   def rectPoly: Seq[Vec2] = Rect.cen(x, yAdj, 0.5, yDist).rotate(fOrientation(60.degs, 0.degs, -60.degs))            
//}
//
//object HSideCood
//{
//   //def apply(x: Int, y: Int): HSideCood = new HSideCood(x, y)
//   //def unapply(hsc: HSideCood): Option[(Int, Int)] = Some((hsc.x, hsc.y))
//   implicit class HSideCoodSeqImplicit(thisSeq: Seq[HSideCood])
//   {
//      def coodContains(x: Int, y: Int): Boolean = thisSeq.contains(HSideCood(x, y))
//   }
//}
   