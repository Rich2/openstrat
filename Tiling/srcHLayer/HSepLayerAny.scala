/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HSepLayerAny[A]
{
  def unsafeArray: Array[A]

  /** Sets the value in the;ayer for the given [[HSep]]. Throws exception if [[HSep]] does not exist within the [[HGrid]]. */
  def set(hs: HSep, value: A)(implicit grid: HGridSys): Unit = if(grid.hSepExists(hs)) unsafeArray(grid.sepLayerArrayIndex(hs)) = value
    else excep(s"$hs doesn't exist in this grid")

  /** Sets the value in the;ayer for the given [[HSep]]. Throws exception if [[HSep]] does not exist within the [[HGrid]]. */
  def set(r: Int, c: Int, value: A)(implicit grid: HGridSys): Unit = if (grid.hSepExists(r, c)) unsafeArray(grid.sepLayerArrayIndex(r, c)) = value
    else excep(s"$r, $c doesn't exist in this grid")

  /** Sets the value in the;ayer for the given [[HSep]]. Throws exception if [[HSep]] does not exist within the [[HGrid]]. */
  def set(grid: HGridSys, hs: HSep, value: A): Unit = if (grid.hSepExists(hs)) unsafeArray(grid.sepLayerArrayIndex(hs)) = value
    else excep(s"$hs doesn't exist in this grid")

  /** Set [[HSep]] if the [[HSep]] exists in the given [[HGrid]]. */
  def setExists(grid: HGrid, hs: HSep, value: A): Unit =
  { if(grid.sepExists(hs))
    { val i = grid.sepLayerArrayIndex(hs)
      unsafeArray(i) = value
    }
  }

  /** Set [[HSep]] if the [[HSep]] exists in the given [[HGrid]]. */
  def setExists(grid: HGrid, r: Int, c: Int, value: A): Unit =
  { if(grid.hSepExists(r, c))
    { val i = grid.sepLayerArrayIndex(r, c)
      unsafeArray(i) = value
    }
  }

  /** Sets multiple [[HSep]]s specified by their r and c [[Int]] components parameters to the same given value. */
  def setSomeRC(value: A, hSeps: Int*)(implicit grid: HGridSys): Unit =
  { val len = hSeps.length / 2
    iUntilForeach(0, len * 2, 2) { i =>
      val r = hSeps(i)
      val c = hSeps(i + 1)
      r %% 4 match
      { case 1 | 3 if c.div4Rem1 | c.div4Rem3 =>
        case 0 if c.div4Rem2 =>
        case 2 if c.div4Rem0 =>
        case _ => excep(s"$r, $c is not a valid hex side tile coordinate.")
      }
      val index = grid.sepLayerArrayIndex(r, c)
      unsafeArray(index) = value
    }
  }
}