/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

import scala.collection.mutable.ArrayBuffer

/** A trait for [[HStep]]s. The purpose of the trait rather than a class is to allow the consumer to mix in their own traits. Its not clear whether
 *  this is useful in Scala 3 or its bettter to use union types with the [[HSteps]] class. */
trait HStepsTr extends Any
{ def unsafeArray: Array[Int]
  def segsNum: Int = unsafeArray.length

  def segsForeach[U](start: HCen)(f: LineSeg => U)(implicit grider: HGriderFlat): Unit = segsForeach(start.r, start.c)(f)

  def segsForeach[U](startR: Int, startC: Int)(f: LineSeg => U)(implicit grider: HGriderFlat): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0
    while (count < segsNum)
    { unsafeArray(count) match
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

  def segsMap[B, ArrB <: SeqImut[B]](start: HCen)(f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB], grider: HGriderFlat): ArrB =
    segsMap(start.r, start.c)(f)(build, grider)

  def segsMap[B, ArrB <: SeqImut[B]](startR: Int, startC: Int)(f: LineSeg => B)(implicit build: ArrBuilder[B, ArrB], grider: HGriderFlat): ArrB =
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
    steps.iForeach{(i, step) => array(i) = step.intValue }
    fromArray(array)
  }
}

class HSteps(val unsafeArray: Array[Int]) extends AnyVal with ArrInt1s[HStep] with HStepsTr
{ override type ThisT = HSteps
  override def typeStr: String = "HSteps"
  override def dataElem(intValue: Int): HStep = HStep.fromInt(intValue)
  override def unsafeFromArray(array: Array[Int]): HSteps = new HSteps(array)
  override def fElemStr: HStep => String = _.toString
}

object HSteps extends HStepsCompanion[HSteps]
{ override def fromArray(array: Array[Int]): HSteps = new HSteps(array)

  implicit val flatBuilder: ArrFlatBuilder[HSteps] = new ArrInt1sFlatBuilder[HStep, HSteps]
  { override type BuffT = HStepBuff
    override def fromIntArray(inp: Array[Int]): HSteps = new HSteps(inp)
    override def fromIntBuffer(inp: Buff[Int]): HStepBuff = new HStepBuff(inp)
  }
}

/** ArrayBuffer based buffer class for Colours. */
class HStepBuff(val unsafeBuffer: ArrayBuffer[Int]) extends AnyVal with Int1Buff[HStep]
{ override def typeStr: String = "HStepBuff"
  def intToT(i1: Int): HStep = HStep.fromInt(i1)
}

object HStepBuff
{ def apply(initLen: Int = 4): HStepBuff = new HStepBuff(new Buff[Int](initLen))
}