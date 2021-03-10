/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An optional hex tile step. Represents the relative move from a hex tile to one of its six neighbours or the non move value. It can be one of the 6
 *  HCStep values or the HCStepNone value. */
sealed trait HCenStepOpt
{ def r: Int
  def c: Int
  def hcen: HCen = HCen(r, c)
}
case object HcenStepNone extends HCenStepOpt { def r: Int = 0; def c: Int = 0 }

/** A Hex tile Step can take 6 values */
sealed trait HcenStep extends HCenStepOpt

case object HCStepUR extends HcenStep{ def r: Int = 2; def c: Int = 2 }
case object HCStepRt extends HcenStep{ def r: Int = 0; def c: Int = 4 }
case object HCStepDR extends HcenStep{ def r: Int = -2; def c: Int = 2 }
case object HCStepDL extends HcenStep{ def r: Int = -2; def c: Int = -2 }
case object HCStepLt extends HcenStep{ def r: Int = 0; def c: Int = -4 }
case object HCStepUL extends HcenStep{ def r: Int = 2; def c: Int = -2 }

/** Hex centre coordinate and hex step. */
case class HCAndStep(r1: Int, c1: Int, step: HcenStep)
{ def hc1: HCen = HCen(r1, c1)
  def hc2: HCen = HCen(r1 + step.r, c1 + step.c)
}