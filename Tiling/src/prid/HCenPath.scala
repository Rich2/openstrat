/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

trait HCenPathTr extends Any
{
  def arrayUnsafe: Array[Int]
  @inline final def startR: Int = arrayUnsafe(0)
  @inline final def startC: Int = arrayUnsafe(1)
  def start: HCen = HCen(startR, startC)
  def segNum: Int = arrayUnsafe.length - 2

  def segsForeach[U](f: LineSeg => U): Unit =
  { var count = 0
    //var hc1: HCen = start
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0
    while (count < segNum)
    { arrayUnsafe(count + 2) match
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

  def segsMap[B, ArrB <: ArrBase[B]](f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(segNum)
    var count = 0
    segsForeach{ s =>
      res.unsafeSetElem(count, f(s))
      count += 1
    }
    res
  }
}

trait HCenPathCompanion[T <: HCenPathTr]
{
  def fromArray(array: Array[Int]): T
  def apply(start: HCen, steps: HStep*): T = apply(start.r, start.c, steps :_*)

  def apply(startRow: Int, startColumn: Int, steps: HStep*): T =
  { val array = new Array[Int](2 + steps.length)
    array(0) = startRow
    array(1) = startColumn
    steps.iForeach{(step, i) => array(i + 2) = step.intValue }
    fromArray(array)
  }
}

class HCenPath(val arrayUnsafe: Array[Int]) extends AnyVal with HCenPathTr
{

}
