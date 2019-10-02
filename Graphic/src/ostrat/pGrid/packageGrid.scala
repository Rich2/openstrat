/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This package works with hexagonal and Square tile grids. The Tile objects themsleves will not in the general case the contain grid coordinates, although
 * it may be necesary to iclude this data for complex Tile values interacting with their wider environment. Its fundamental components are the grid data itself.
 * This is just a linear array of tile data. The grid and grid-gui hierarchies currently contain a mix of new and old systems. */
package object pGrid
{
  val Cood00 = Cood(0, 0)
  
  /** Gives a Coods Seq of Cood along a horisonatal line */
  def hSidesHorr(y: Int, xStart: Int, xEnd : Int): Coods = 
  {
    val xs = if (xStart > xEnd) xStart.roundDownToOdd to xEnd.roundUpToOdd by -2 else xStart.roundUpToOdd to xEnd.roundDownToOdd by 2     
    xs.pMap(x => Cood(x, y))     
  }
  
  /** Not sure about this method */
  def rectHCoods(xDim: Int,  yDim: Int, xOff: Int = 0, yOff: Int = 0): Set[Cood] =
  { val res1 = for
    { x <- xOff until xDim * 2 + xOff by 2
      y <- yOff until yDim * 2 + yOff by 2
	    if x % 4 == y % 4	              
    }  
	  yield Cood(x + 2, y + 2)
    res1.toSet
  }
  
  implicit class IntGridImplicit(thisInt: Int)
  {
     def cc (y: Int): Cood = Cood(thisInt, y)     
  }
}