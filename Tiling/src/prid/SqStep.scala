/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** A tile step, move or addition, or no move. */
trait TileStepOpt
{/** Row coordinate delta. */
  def r: Int

  /** Column coordinate delta */
  def c: Int
}

/** An optional square tile step. Can take 9 values. Represents the relative move from a square tile to one of its eight neighbours or the non move value. It
 *  can be one of the 6 [[HStep]] values or the [[HStepNone]] value. */
sealed trait SqStepOpt extends TileStepOpt

/** An optional square tile step of None. */
case object SqStepNone extends SqStepNearOpt { def r: Int = 0; def c: Int = 0 }

/** A square tile step can take 8 values */
sealed trait SqStep extends SqStepOpt

/** An non-diagonal optional square tile step. Can take 5 values. Represents the relative move from a square tile to one of its non-diagonal 4
 *  neighbours or the non move [[SqStepNone]] value. */
sealed trait SqStepNearOpt extends SqStepOpt

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepNear extends SqStepNearOpt with SqStep

/** An upward step / move addition of one square tile in a square tile grid. Increases the row coordinate by 2. */
case object SqStepUp extends SqStepNear { def r: Int = 2; def c: Int = 0 }

/** An rightward step / move / addition of one square tile in a square tile grid. Increases the column coordinate by 2 */
case object SqStepRight extends SqStepNear { def r: Int = 0; def c: Int = 2 }

/** An dwonward step / move / addition of one square tile in a square tile grid. */
case object SqStepDown extends SqStepNear { def r: Int = -2; def c: Int = 0 }

/** An upward of one square tile in a square tile grid. */
case object SqStepLeft extends SqStepNear { def r: Int = 0; def c: Int = -2 }

/** An diagonal optional square tile step. Can take 5 values. Represents the relative move from a square tile to one of its diagonal 4 neighbours or
 *  the non move [[SqStepNone]] value. */
sealed trait SqStepDiagOpt extends SqStepOpt

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepDiag extends SqStepDiagOpt with SqStep

case object SqStepUR extends SqStepDiag { def r: Int = 2; def c: Int = 2 }
case object SqStepDR extends SqStepDiag { def r: Int = 2; def c: Int = -2 }
case object SqStepDL extends SqStepDiag{ def r: Int = -2; def c: Int = -2 }
case object SqStepUL extends SqStepDiag{ def r: Int = 2; def c: Int = 2 }

case class SqAndStep(r1: Int, c1: Int, step: SqStep)
{ def sc1: SqCen = SqCen(r1, c1)
  def sc2: SqCen = SqCen(r1 + step.r, c1 + step.c)
}