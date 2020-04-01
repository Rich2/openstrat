/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** This package works with hexagonal and Square tile grids. The tile objects themselves will not in the general case the contain grid coordinates, although
 * it may be necessary to include this data for complex Tile values interacting with their wider environment. Its fundamental components are the grid data itself.
 * This is just a linear array of tile data. Compile-time typed grid data. So for example a chess board can be represented by a 64 element Arr, its context
 * determines that it is to be interpreted as an 8 by 8 square grid. Grid descriptions that describe the grid representation in the Array and GridFunctions
 * which implement Cood to T. The grid and grid-gui hierarchies currently contain a mix of new and old systems.
 *
 * The package name pGrid was chosen to allow you to use the name "grid" in your code. */
package object pGrid
{
  val Cood00 = Cood(0, 0)

  /** Gives a Coods Seq of Cood along a horisonatal line */
  def hexSidesHorr(y: Int, cStart: Int, cEnd : Int): Roords =
  { val cs = if (cStart > cEnd) cStart.roundDownToOdd to cEnd.roundUpToOdd by -2 else cStart.roundUpToOdd to cEnd.roundDownToOdd by 2
    cs.pMap(c => Roord(y, c))
  }

  /** Gives a Coods Seq of Cood along a horisonatal line */
  @deprecated def hexSidesHorrOld(y: Int, xStart: Int, xEnd : Int): Coods =
  { val xs = if (xStart > xEnd) xStart.roundDownToOdd to xEnd.roundUpToOdd by -2 else xStart.roundUpToOdd to xEnd.roundDownToOdd by 2
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
  { /** Syntax for succinct  Cood notation. */
    @deprecated def cc (y: Int): Cood = Cood(thisInt, y)
    def rr (c: Int): Roord = Roord(thisInt, c)
  }

  implicit class OptRefImplicit[A <: AnyRef](arr: OptRefs[A])
  {
    def gridSetSome(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = arr.setSome(grid.index(y, c), value)
    def gridSetSomes(triples: (Int, Int, A)*)(implicit grid: TileGrid): Unit = triples.foreach(t => arr.setSome(grid.index(t._1, t._2), t._3))

    def gridMapSomes[B, ArrT <: ArrImut[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrBuild[B, ArrT]): ArrT =
    { val buff = build.buffNew()
      grid.foreach { r =>
        arr.apply(grid.index(r)).foreach{a =>
          val newVal = f(r, a)
          build.buffGrow(buff, newVal)
        }
      }
      build.buffToArr(buff)
    }

    /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
    def gridElemGet(roord: Roord)(implicit grid: TileGrid): A = arr.unsafeArray(grid.index(roord))
  }

  val htStepSomes: Refs[HTStep] = Refs(HTStepUR, HTStepRt, HTStepDR, HTStepDL, HTStepLt, HTStepUL)
  val htSteps: Refs[HTStepLike] = HTStepNone +: htStepSomes
}