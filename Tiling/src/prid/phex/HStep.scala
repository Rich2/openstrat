/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HStep extends TileStep with ElemInt1
{ def hCen: HCen = HCen(r, c)
  def intValue: Int
  def reverse: HStep
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

/** Hex centre coordinate and hex step. */
case class HexAndStep(r1: Int, c1: Int, step: HStep)
{ /** The Starting hex centre. */
  def hc1: HCen = HCen(r1, c1)

  /** The destination hex centre. */
  def hc2: HCen = HCen(r1 + step.r, c1 + step.c)
}

object HexAndStep
{
  def apply(hCen: HCen, step: HStep): HexAndStep = new HexAndStep(hCen.r, hCen.c, step)
}