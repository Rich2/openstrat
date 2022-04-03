/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HStep extends TStep with ElemInt1
{ /** The delta [[HCen]] of this step inside a hex grid. */
  def hCenDelta: HCen = HCen(r, c)
  def intValue: Int
  def reverse: HStep
  def canEqual(a: Any): Boolean = a.isInstanceOf[HStep]
}

object HStep
{
  def fromInt(inp: Int): HStep = inp match {
    case 1 => HStepUR
    case 2 => HStepRt
    case 3 => HStepDR
    case 4 => HStepDL
    case 5 => HStepLt
    case 6 => HStepUL
    case n => excep(s"$n is not a valid HStep")
  }

  def full: HStepArr = HStepArr(HStepUR, HStepRt, HStepDR, HStepDL, HStepLt, HStepUL)

  implicit val buildEv: Int1ArrBuilder[HStep, HStepArr] = new Int1ArrBuilder[HStep, HStepArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HStepArr = new HStepArr(array)
    override def fromIntBuffer(buffer: Buff[Int]): HStepBuff = new HStepBuff(buffer)
  }
}

/** A step upright on a hex tile grid [[HGrid]]. */
case object HStepUR extends HStep
{ def r: Int = 2
  def c: Int = 2
  def intValue = 1
  override def reverse: HStep = HStepDL
}

/** A step right on a hex tile grid [[HGrid]]. */
case object HStepRt extends HStep
{ def r: Int = 0
  def c: Int = 4
  def intValue = 2
  override def reverse: HStep = HStepLt
}

/** A step downright on a hex tile grid [[HGrid]]. */
case object HStepDR extends HStep
{ def r: Int = -2
  def c: Int = 2
  def intValue = 3
  override def reverse: HStep = HStepUL
}

/** A step downleft on a hex tile grid [[HGrid]]. */
case object HStepDL extends HStep
{ def r: Int = -2
  def c: Int = -2
  def intValue = 4
  override def reverse: HStep = HStepUR
}

/** A step left on a hex tile grid [[HGrid]]. */
case object HStepLt extends HStep
{ def r: Int = 0
  def c: Int = -4
  def intValue = 5
  override def reverse: HStep = HStepRt
}

/** A step upleft on a hex tile grid [[HGrid]]. */
case object HStepUL extends HStep
{ def r: Int = 2
  def c: Int = -2
  def intValue = 6
  override def reverse: HStep = HStepDR
}

class HStepArr(val unsafeArray: Array[Int]) extends AnyVal with Int1Arr[HStep]
{ override type ThisT = HStepArr
  override def typeStr: String = "HSteps"
  override def dataElem(intValue: Int): HStep = HStep.fromInt(intValue)
  override def fromArray(array: Array[Int]): HStepArr = new HStepArr(array)
  override def fElemStr: HStep => String = _.toString

  def segsNum: Int = unsafeArray.length

  def segsForeach[U](start: HCen)(f: LineSeg => U)(implicit grider: HGriderFlat): Unit = segsForeach(start.r, start.c)(f)

  def segsForeach[U](startR: Int, startC: Int)(f: LineSeg => U)(implicit grider: HGriderFlat): Unit =
  { var count = 0
    var r1 = startR
    var c1 = startC
    var r2: Int = 0
    var c2: Int = 0

    while (count < segsNum)
    { val step =  HStep.fromInt(unsafeArray(count))
      r2 = r1 + step.r
      c2 = c1 + step.c
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

  def pathHC(startHC: HCen)(implicit grider: HGrider): LinePathHC = {
    val buff: HCoordBuff = HCoordBuff(startHC)
    var i = 0
    /*while(i < length)
    foreach{ step =>
      currHC = grider.unsafeStepEnd(currHC, step)
      res.unsafeSetElem(i, currHC)
      i +=1
    }
    res*/
    LinePathHC.fromBuffer(buff.unsafeBuffer)
  }
}

object HStepArr extends Int1SeqDefCompanion[HStep, HStepArr]
{ override def fromArray(array: Array[Int]): HStepArr = new HStepArr(array)

  implicit val flatBuilder: ArrFlatBuilder[HStepArr] = new Int1ArrFlatBuilder[HStep, HStepArr]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HStepArr = new HStepArr(array)
    override def fromIntBuffer(buffer: Buff[Int]): HStepBuff = new HStepBuff(buffer)
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