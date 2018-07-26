/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** I've kept this file because some of the code may be useful */
case class HSideCood(val x: Int, val y: Int)// extends TileSideCood with HexGridCood

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
