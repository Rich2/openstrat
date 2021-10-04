/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

trait HStepsTr extends Any
{ def arrayUnsafe: Array[Int]
  def segsNum: Int = arrayUnsafe.length

  def segsForeach[U](start: HCen)(f: LineSeg => U): Unit = segsForeach(start.r, start.c)(f)

  def segsForeach[U](startR: Int, startC: Int)(f: LineSeg => U): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0
    while (count < segsNum)
    { arrayUnsafe(count) match
    { case 1 => { r2 = r1 + 2; c2 = c1 + 2 }
      case 2 => { r2 = r1; c2 = c1 + 4 }
      case 3 => { r2 = r1 - 2; c2 = c1 + 2 }
      case 4 => { r2 = r1 - 2; c2 = c1 - 2 }
      case 5 => { r2 = r1; c2 = c1 - 4 }
      case 6 => { r2 = r1 + 2; c2 = c1 - 2 }
      case n => excep(s"$n unexpected integer value.")
    }
      val hls = HCoordLineSeg(r1, c1, r2, c2)
      f(hls.lineSeg)
      count += 1
      r1 = r2
      c1 = c2
    }
  }

  def segsMap[B, ArrB <: ArrBase[B]](start: HCen)(f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB]): ArrB = segsMap(start.r, start.c)(f)(build)

  def segsMap[B, ArrB <: ArrBase[B]](startR: Int, startC: Int)(f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB]): ArrB =
  { val res = build.newArr(segsNum)
    var count = 0
    segsForeach(startR, startC){ s =>
      res.unsafeSetElem(count, f(s))
      count += 1
    }
    res
  }
}

trait HStepsCompanion[T <: HStepsTr]
{
  def fromArray(array: Array[Int]): T

  def apply(steps: HStep*): T =
  { val array = new Array[Int](steps.length)
    steps.iForeach{(step, i) => array(i) = step.code }
    fromArray(array)
  }
}

class HSteps(val arrayUnsafe: Array[Int]) extends AnyVal with HStepsTr

object HSteps extends HStepsCompanion[HSteps]
{
  override def fromArray(array: Array[Int]): HSteps = new HSteps(array)
}