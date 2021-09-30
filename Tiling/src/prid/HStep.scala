/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A step on a hex tile grid [[HGrid]] can take 6 values: upright right, downright, downleft, left and upleft. */
sealed trait HStep extends TileStep
{ def hCen: HCen = HCen(r, c)
  def code: Int
}

/** A step upright on a hex tile grid [[HGrid]]. */
case object HStepUR extends HStep
{ def r: Int = 2
  def c: Int = 2
  def code = 1
}

/** A step right on a hex tile grid [[HGrid]]. */
case object HStepRt extends HStep
{ def r: Int = 0
  def c: Int = 4
  def code = 2
}

/** A step downright on a hex tile grid [[HGrid]]. */
case object HexStepDR extends HStep
{ def r: Int = -2
  def c: Int = 2
  def code = 3
}

/** A step downleft on a hex tile grid [[HGrid]]. */
case object HStepDL extends HStep
{ def r: Int = -2
  def c: Int = -2
  def code = 4
}

/** A step left on a hex tile grid [[HGrid]]. */
case object HStepLt extends HStep
{ def r: Int = 0
  def c: Int = -4
  def code = 5
}

/** A step upleft on a hex tile grid [[HGrid]]. */
case object HStepUL extends HStep
{ def r: Int = 2
  def c: Int = -2
  def code = 6
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