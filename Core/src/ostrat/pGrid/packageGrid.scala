/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The grid and grid-gui hierarchies lack clarity */
package object pGrid
{   
   val Cood00 = Cood(0, 0) 
   
   /** Not sure about this method */
   def rectHCoods(xDim: Int,  yDim: Int, xOff: Int = 0, yOff: Int = 0): Set[Cood] =
	      (for (
	            x <- xOff until xDim * 2 + xOff by 2;
	            y <- yOff until yDim * 2 + yOff by 2;
	            if (x % 4 == y % 4))
	         yield Cood(x + 2, y + 2)).toSet
   
}