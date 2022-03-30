/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HStep extends TileStep with ElemInt1
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

  def full: HSteps = HSteps(HStepUR, HStepRt, HStepDR, HStepDL, HStepLt, HStepUL)

  implicit val buildEv: ArrInt1sBuilder[HStep, HSteps] = new ArrInt1sBuilder[HStep, HSteps]
  { override type BuffT = HStepBuff
    override def fromIntArray(array: Array[Int]): HSteps = new HSteps(array)
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

/** Hex centre origin and hex step. */
class HCenStep(val r1: Int, val c1: Int, val stepInt: Int)
{ /** The Starting hex centre. */
  def hc1: HCen = HCen(r1, c1)
  def step: HStep = HStep.fromInt(stepInt)
  /** The destination hex centre. */
  def endHC(implicit grider: HGrider): HCen = HCen(r1 + step.r, c1 + step.c)
}

object HCenStep
{ def apply(hCen: HCen, step: HStep): HCenStep = new HCenStep(hCen.r, hCen.c, step.intValue)
  def apply(r: Int, c: Int, step: HStep): HCenStep = new HCenStep(r, c, step.intValue)
}