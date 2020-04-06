/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import geom._

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

    def gridForeachSome(f: (Roord, A) => Unit)(implicit grid: TileGrid): Unit =
    { grid.foreach { r =>
        arr.apply(grid.index(r)).foreach{a =>f(r, a) }
      }
    }

    def gridMapSomes[B, ArrT <: Arr[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrBuild[B, ArrT]): ArrT =
    { val buff = build.newBuff()
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

  implicit class ArrImplicit[A](val arr: Arr[A])
  {
    /** Set tile row from the Cood. */
    final def setRow(roord: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord = setRow(roord.y, roord.c, tileValues: _*)(grid)

    /** Note set Row starts with the y (row) parameter. */
    final def setRow(yRow: Int, cStart: Int, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
    {
      val tiles: List[A] = tileValues.toSingles
      tiles.iForeach { (e, i) =>
        val c = cStart + i * grid.cStep
        arr.unsafeSetElem(grid.index(yRow, c), e)
      }
      Roord(yRow, cStart + (tiles.length - 1) * grid.cStep)
    }

    def gridForeach(f: (Roord, A) => Unit)(implicit grid: TileGrid): Unit = grid.foreach{ r => f(r, arr(grid.index(r))) }
  }

  implicit def refsTileGridImplicit[A <: AnyRef](thisRefs: Refs[A]) = new RefsGridExtensions[A](thisRefs)


  implicit class GridTransExtension[T](value: T)(implicit grid: TileGrid, ev: Trans[T])
  {
    def gridTrans(scale: Double, offset: Vec2 = Vec2Z): T = value.trans(orig => (orig - offset - grid.cen) * scale)
  }

  @deprecated implicit class RefsListImplicit[A](thisRefs: Refs[List[A]])
  { def gridPrepend(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = gridPrepend(Roord(y, c), value)
    def gridPrepend(roord: Roord, value: A)(implicit grid: TileGrid): Unit = thisRefs.unsafeArr(grid.index(roord)) ::= value
    def gridPrepends(value : A, roords: Roord*)(implicit grid: TileGrid): Unit = roords.foreach{r =>  thisRefs.unsafeArr(grid.index(r)) ::= value }

    def gridHeadsMap[B, BB <: Arr[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrBuild[B, BB]): BB =
    {
      val buff = build.newBuff()
      grid.foreach { r => thisRefs(grid.index(r)) match
        {
          case h :: _ => build.buffGrow(buff, f(r, h))
          case _ =>
        }
      }
      build.buffToArr(buff)
    }
  }

  val htStepSomes: Refs[HTStep] = Refs(HTStepUR, HTStepRt, HTStepDR, HTStepDL, HTStepLt, HTStepUL)
  val htSteps: Refs[HTStepLike] = HTStepNone +: htStepSomes
}