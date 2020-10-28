/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An optional hex tile step. Represents the relative move from a hex tile to one of its six neighbours or the non move value. It can be one of the 6
 *  HCStep values or the HCStepNone value. */
sealed class HCStepOpt(val r: Int, val c: Int)
{ def hCen: HCen = HCen(r, c)
}
case object HCStepNone extends HCStepOpt(0, 0)

/** A Hex tile Step can take 6 values */
sealed class HCStep(rIn: Int, cIn: Int) extends HCStepOpt(rIn, cIn)

case object HCStepUR extends HCStep(2, 2)
case object HCStepRt extends HCStep(0, 4)
case object HCStepDR extends HCStep(-2, 2)
case object HCStepDL extends HCStep(-2, -2)
case object HCStepLt extends HCStep(0, -4)
case object HCStepUL extends HCStep(2, -2)

case class HCAndStep(r1: Int, c1: Int, step: HCStep)
{ def hc1: HCen = HCen(r1, c1)
  def hc2: HCen = HCen(r1 + step.r, c1 + step.c)
}