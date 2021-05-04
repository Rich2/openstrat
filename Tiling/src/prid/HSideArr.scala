/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

class HSideArr[A <: AnyRef](val unsafeArr: Array[A])
{

}

class HSideBooleans(val unsafeArr: Array[Boolean]) extends AnyVal
{
  def setTrues(hSides: HSide*)(implicit grid: HGrid): Unit = hSides.foreach(r => unsafeArr(grid.sideArrIndex(r)) = true)
}