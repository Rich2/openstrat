/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An optional hex tile step. Can take 7 values. Represents the relative move from a hex tile to one of its six neighbours or the non move value. It
 *  can be one of the 6 [[HexStep]] values or the [[HexStepNone]] value. */
sealed trait HexStepOpt
{ def r: Int
  def c: Int
  def hCen: HCen = HCen(r, c)
}

/** An optional hex tile step of None. */
case object HexStepNone extends HexStepOpt { def r: Int = 0; def c: Int = 0 }

/** A hex tile Step can take 6 values */
sealed trait HexStep extends HexStepOpt

/** hex Step up right. */
case object HexStepUR extends HexStep{ def r: Int = 2; def c: Int = 2 }

/** hex Step right. */
case object HexStepRt extends HexStep{ def r: Int = 0; def c: Int = 4 }

/** hex Step down right. */
case object HexStepDR extends HexStep{ def r: Int = -2; def c: Int = 2 }

/** hex Step down left. */
case object HexStepDL extends HexStep{ def r: Int = -2; def c: Int = -2 }

/** Hex step left. */
case object HexStepLt extends HexStep{ def r: Int = 0; def c: Int = -4 }

/** Hex step up left. */
case object HexStepUL extends HexStep{ def r: Int = 2; def c: Int = -2 }

/** Hex centre coordinate and hex step. */
case class HexAndStep(r1: Int, c1: Int, step: HexStep)
{ def hc1: HCen = HCen(r1, c1)
  def hc2: HCen = HCen(r1 + step.r, c1 + step.c)
}