/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HStep extends TileStep
{ def hCen: HCen = HCen(r, c)
  def code: Int
  def reverse: HStep
}

/** A step upright on a hex tile grid [[HGrid]]. */
case object HStepUR extends HStep
{ def r: Int = 2
  def c: Int = 2
  def code = 1
  override def reverse: HStep = HStepDL
}

/** A step right on a hex tile grid [[HGrid]]. */
case object HStepRt extends HStep
{ def r: Int = 0
  def c: Int = 4
  def code = 2
  override def reverse: HStep = HStepLt
}

/** A step downright on a hex tile grid [[HGrid]]. */
case object HStepDR extends HStep
{ def r: Int = -2
  def c: Int = 2
  def code = 3
  override def reverse: HStep = HStepUL
}

/** A step downleft on a hex tile grid [[HGrid]]. */
case object HStepDL extends HStep
{ def r: Int = -2
  def c: Int = -2
  def code = 4
  override def reverse: HStep = HStepUR
}

/** A step left on a hex tile grid [[HGrid]]. */
case object HStepLt extends HStep
{ def r: Int = 0
  def c: Int = -4
  def code = 5
  override def reverse: HStep = HStepRt
}

/** A step upleft on a hex tile grid [[HGrid]]. */
case object HStepUL extends HStep
{ def r: Int = 2
  def c: Int = -2
  def code = 6
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