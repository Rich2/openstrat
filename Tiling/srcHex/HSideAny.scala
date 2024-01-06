/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

trait HSideLayerAny[A]
{
  def unsafeArray: Array[A]

  def set(hs: HSep, value: A)(implicit grid: HGridSys): Unit =
  { val i = grid.sideLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(r: Int, c: Int, value: A)(implicit grid: HGridSys): Unit =
  { val i = grid.sideLayerArrayIndex(r, c)
    if (i >= unsafeArray.length) deb(s"$r, $c")
    unsafeArray(i) = value
  }

  def set(grid: HGridSys, hs: HSep, value: A): Unit =
  { val i = grid.sideLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def setIf(hs: HSep, value: A)(implicit grid: HGrid): Unit =
  { if(grid.sepExists(hs))
    { val i = grid.sideLayerArrayIndex(hs)
      unsafeArray(i) = value
    }
  }

  def setIf(r: Int, c: Int, value: A)(implicit grid: HGrid): Unit =
  { if(grid.sepExists(r, c))
    { val i = grid.sideLayerArrayIndex(r, c)
       unsafeArray(i) = value
    }
  }

  /** Swts the [[HSep]]s specified by their Int parameters to rhe given value. */
  def setSomeInts(value: A, hSideInts: Int*)(implicit grid: HGridSys): Unit =
  {  val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2) { i =>
      val r = hSideInts(i)
      val c = hSideInts(i + 1)
      r %% 4 match {
        case 1 | 3 if c.div4Rem1 | c.div4Rem3 =>
        case 0 if c.div4Rem2 =>
        case 2 if c.div4Rem0 =>
        case _ => excep(s"$r, $c is not a valid hex side tile coordinate.")
      }
      val index = grid.sideLayerArrayIndex(r, c)
      unsafeArray(index) = value
    }
  }
}