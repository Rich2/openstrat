/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A trait for classes of line paths specified by [[[HCen]] hex grid tile centre coordinates. Can't remember why this is a trait. */
class LinePathHC(val unsafeArray: Array[Int]) extends AnyVal with HCoordSeqDef with LinePathInt2s[HCoord]
{ override type ThisT = LinePathHC
  override def typeStr: String = "LinePathHC"
  override def fromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
}


/*trait HCenPathTr extends Any
{
  def unsafeArray: Array[Int]
  @inline final def startR: Int = unsafeArray(0)
  @inline final def startC: Int = unsafeArray(1)
  def start: HCen = HCen(startR, startC)
  def segNum: Int = unsafeArray.length - 2

  def segsForeach[U](f: LineSeg => U)(implicit grider: HGriderFlat): Unit =
  { var count = 0
    //var hc1: HCen = start
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0
    while (count < segNum)
    { unsafeArray(count + 2) match
      { case 1 => { r2 = r1 + 2; c2 = c1 + 2 }
        case 2 => { r2 = r1; c2 = c1 + 4 }
        case 3 => { r2 = r1 - 2; c2 = c1 + 2 }
        case 4 => { r2 = r1 - 2; c2 = c1 - 2 }
        case 5 => { r2 = r1; c2 = c1 - 4 }
        case 6 => { r2 = r1 + 2; c2 = c1 - 2 }
        case n => excep(s"$n unexpected integer value.")
      }
      val hls = LineSegHC(r1, c1, r2, c2)
      f(hls.lineSeg)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segsMap[B, ArrB <: SeqImut[B]](f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB], grider: HGriderFlat): ArrB =
  { val res = build.newArr(segNum)
    var count = 0
    segsForeach{ s =>
      res.unsafeSetElem(count, f(s))
      count += 1
    }
    res
  }
}*/

/*trait HCenPathCompanion[T <: HCenPathTr]
{
  def fromArray(array: Array[Int]): T
  def apply(start: HCen, steps: HStep*): T = apply(start.r, start.c, steps :_*)

  def apply(startRow: Int, startColumn: Int, steps: HStep*): T =
  { val array = new Array[Int](2 + steps.length)
    array(0) = startRow
    array(1) = startColumn
    steps.iForeach{(i, step) => array(i + 2) = step.intValue }
    fromArray(array)
  }
}*/

/** A line path specified in hex grid centre coordinates. */
/*
class LinePathHC(val unsafeArray: Array[Int]) extends AnyVal with HCenPathTr with LinePathInt2s[HCen]
{ override type ThisT = LinePathHC
  override def typeStr: String = "HCenPath"
  override def dataElem(i1: Int, i2: Int): HCen = HCen(i1, i2)
  override def unsafeFromArray(array: Array[Int]): LinePathHC = new LinePathHC(array)
  override def fElemStr: HCen => String = _.toString
}*/
