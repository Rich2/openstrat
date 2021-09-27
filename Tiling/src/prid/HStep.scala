/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An optional hex tile step. Can take 7 values. Represents the relative move from a hex tile to one of its six neighbours or the non move value. It
 *  can be one of the 6 [[HStep]] values or the [[HStepNone]] value. */
sealed trait HStepOpt extends TileStepOpt
{ def hCen: HCen = HCen(r, c)
  def code: Int
}

/** An optional hex tile step of None. */
case object HStepNone extends HStepOpt
 { def r: Int = 0
   def c: Int = 0
   def code = 0
 }

/** A hex tile step can take 6 values. */
sealed trait HStep extends HStepOpt

/** hex Step up right. */
case object HStepUR extends HStep
{ def r: Int = 2
  def c: Int = 2
  def code = 1
}

/** hex Step right. */
case object HStepRt extends HStep
{ def r: Int = 0
  def c: Int = 4
  def code = 2
}

/** hex Step down right. */
case object HexStepDR extends HStep
{ def r: Int = -2
  def c: Int = 2
  def code = 3
}

/** hex Step down left. */
case object HStepDL extends HStep
{ def r: Int = -2
  def c: Int = -2
  def code = 4
}

/** Hex step left. */
case object HStepLt extends HStep
{ def r: Int = 0
  def c: Int = -4
  def code = 5
}

/** Hex step up left. */
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