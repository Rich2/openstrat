/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An optional square tile step. Can take 9 values. Represents the relative move from a square tile to one of its eight neighbours or the non move value. It
 *  can be one of the 6 [[HexStep]] values or the [[HexStepNone]] value. */
sealed trait SqStepOpt
{ def r: Int
  def c: Int
}

/** An optional square tile step of None. */
case object SqStepNone extends SqStepNearOpt { def r: Int = 0; def c: Int = 0 }

/** A square tile step can take 8 values */
sealed trait SqStep extends SqStepOpt

/** An non-diagonal optional square tile step. Can take 5 values. Represents the relative move from a square tile to one of its non-diagonal 4
 *  neighbours or the non move [[SqStepNone]] value. */
sealed trait SqStepNearOpt extends SqStepOpt

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepNear extends SqStepNearOpt with SqStep

case object SqStepUp extends SqStepNear { def r: Int = 0; def c: Int = 2 }
case object SqStepRight extends SqStepNear { def r: Int = 2; def c: Int = 0 }
case object SqStepDown extends SqStepNear { def r: Int = 0; def c: Int = -2 }
case object SqStepLeft extends SqStepNear { def r: Int = -2; def c: Int = 0 }

/** An diagonal optional square tile step. Can take 5 values. Represents the relative move from a square tile to one of its diagonal 4 neighbours or
 *  the non move [[SqStepNone]] value. */
sealed trait SqStepDiagOpt extends SqStepOpt

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepDiag extends SqStepDiagOpt with SqStep

case object SqStepUR extends SqStepDiag { def r: Int = 2; def c: Int = 2 }
case object SqStepDR extends SqStepDiag { def r: Int = 2; def c: Int = -2 }
case object SqStepDL extends SqStepDiag{ def r: Int = -2; def c: Int = -2 }
case object SqStepUL extends SqStepDiag{ def r: Int = 2; def c: Int = 2 }
//
//case class HCAndStep(r1: Int, c1: Int, step: HcenStep)
//{ def hc1: Hcen = Hcen(r1, c1)
//  def hc2: Hcen = Hcen(r1 + step.r, c1 + step.c)
//}