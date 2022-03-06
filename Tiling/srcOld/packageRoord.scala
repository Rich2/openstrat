/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, pGrid._

package object proord
{
  implicit class GridSlateScaleExtension[T](value: T)(implicit grid: TileGridOld, evSlate: Slate[T], evScale: Scale[T]) {
    /** Translates Vec2s relative to Grid centre and then scales. */
    def gridScale(scale: Double): T =
    { val v = - grid.cenVec
      val a = evSlate.SlateXYT(value, v.x, v.y)
      evScale.scaleT(a, scale)
    }

    implicit class GridTransSimExtension[T](value: T)(implicit grid: TileGridOld, ev: TransSim[T])
    {
      def gridTrans(offset: Vec2, scale: Double): T =
      { val a = ev.slate(value, -offset - grid.cenVec)
        ev.scale(a, scale)
      }
    }

    /** Translates Vec2s relative to focus and then scales. */
    def gridRoordScale(focus: Roord, scale: Double): T =
    { val v = - focus.gridVec
      val a = evSlate.SlateXYT(value, v.x, v.y)
      evScale.scaleT(a, scale)
    }
  }

  /** Gives a Coods Seq of Cood along a horizontal line */
  def hexSidesHorr(y: Int, cStart: Int, cEnd : Int): Roords =
  { val cs: Range = if (cStart > cEnd) cStart.roundDownToOdd to cEnd.roundUpToOdd by -2 else cStart.roundUpToOdd to cEnd.roundDownToOdd by 2
    cs.pMap(c => Roord(y, c))
  }

  implicit class IntGridImplicit(thisInt: Int)
  { /** Syntax for succinct  [[Roord]] notation. */
    //def rr (c: Int): Roord = Roord(thisInt, c)
  }


  /** Not sure about the use of List in this class. */
  implicit class TilesListImplicit[A](thisRefs: TilesArr[List[A]])
  { def prepend(y: Int, c: Int, value: A)(implicit grid: TileGridOld): Unit = prepend(Roord(y, c), value)
    def prepend(roord: Roord, value: A)(implicit grid: TileGridOld): Unit = thisRefs.unsafeArr(grid.arrIndex(roord)) ::= value
    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }
  }
}
