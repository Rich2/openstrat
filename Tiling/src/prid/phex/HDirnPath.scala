/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._
//import collection.mutable.ArrayBuffer

class HDirnPath(val unsafeArray: Array[Int]) extends ArrayIntBacked
{
  def startR: Int = unsafeArray(0)
  def startC: Int = unsafeArray(1)
  def startCen = HCen(unsafeArray(0), unsafeArray(1))
  def segsNum: Int = unsafeArray.length - 2

  def segHCsForeach(f: LineSegHC => Unit): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step = HDirn.fromInt(unsafeArray(count + 2))
      r2 = r1 + step.tr
      c2 = c1 + step.tc
      val hls = LineSegHC(r1, c1, r2, c2)
      f(hls)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segHCsMap[B, ArrB <: Arr[B]](f: LineSegHC => B)(implicit build: ArrMapBuilder[B, ArrB], grider: HGridSys): ArrB =
  { val res = build.uninitialised(segsNum)
    var count = 0
    segHCsForeach{ s => res.unsafeSetElem(count, f(s)); count += 1 }
    res
  }

  def projLineSegs(implicit proj: HSysProjection): LineSegArr =
  { val res = LineSegArr.uninitialised(segsNum)
    var count = 0
    segHCsForeach{ lh =>
      val ols = proj.transOptLineSeg(lh)
      ols.foreach(res.unsafeSetElem(count, _))
      count += 1
    }
    res
  }
}