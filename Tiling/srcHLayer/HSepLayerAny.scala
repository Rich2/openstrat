/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

trait HSepLayerAny[A]
{
  def unsafeArray: Array[A]

  def set(hs: HSep, value: A)(implicit grid: HGridSys): Unit =
  { val i = grid.sepLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(r: Int, c: Int, value: A)(implicit grid: HGridSys): Unit =
  { val i = grid.sepLayerArrayIndex(r, c)
    if (i >= unsafeArray.length) deb(s"$r, $c")
    unsafeArray(i) = value
  }

  def set(grid: HGridSys, hs: HSep, value: A): Unit =
  { val i = grid.sepLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def setIf(hs: HSep, value: A)(implicit grid: HGrid): Unit =
  { if(grid.sepExists(hs))
    { val i = grid.sepLayerArrayIndex(hs)
      unsafeArray(i) = value
    }
  }

  def setIf(r: Int, c: Int, value: A)(implicit grid: HGrid): Unit =
  { if(grid.sepExists(r, c))
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