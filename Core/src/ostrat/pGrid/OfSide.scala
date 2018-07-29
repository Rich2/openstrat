/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

trait OfSide[TileT <: GridElem, SideT <: GridElem, GridT <: TileGrid[TileT, SideT]] extends OfGridElem[TileT, SideT, GridT]
{
   def side: SideT    
   final def cood: Cood = side.cood
   def sideCen: Vec2
   def coodsLine: CoodLine = grid.vertCoodLineOfSide(cood)
   def vertLine: Line2 = coodsLine.toLine2(coodToDispVec2)
   def ifTiles[A](f: (TileT, TileT) => Boolean, fA: (TileT, TileT) => A): List[A] = grid.optSidesTiles(cood) match
   {
      case (Some(t1), Some(t2)) => if (f(t1, t2)) fA(t1, t2) :: Nil else Nil
      case _ => Nil
   }
}