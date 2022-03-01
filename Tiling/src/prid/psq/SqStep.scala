/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** A square tile step can take 8 values */
sealed trait SqStep extends TileStep
{ def sqCen: SqCen = SqCen(r, c)
  def reverse: SqStep
  def isNear: Boolean
  def isDiag: Boolean
}

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepNear extends SqStep
{ override def isNear: Boolean = true
  override def isDiag: Boolean = false
}

/** An upward step / move addition of one square tile in a square tile grid. Increases the row coordinate by 2. */
case object SqStepUp extends SqStepNear
{ def r: Int = 2
  def c: Int = 0
  override def reverse: SqStep = SqStepDn
}

/** An rightward step / move / addition of one square tile in a square tile grid. Increases the column coordinate by 2 */
case object SqStepRt extends SqStepNear
{ def r: Int = 0
  def c: Int = 2
  override def reverse: SqStep = SqStepLt
}

/** An downward step / move / addition of one square tile in a square tile grid. */
case object SqStepDn extends SqStepNear
{ def r: Int = -2
  def c: Int = 0
  override def reverse: SqStep = SqStepUp
}

/** An upward of one square tile in a square tile grid. */
case object SqStepLt extends SqStepNear
{ def r: Int = 0
  def c: Int = -2
  override def reverse: SqStep = SqStepRt
}

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqStepDiag extends SqStep
{ override def isNear: Boolean = false
  override def isDiag: Boolean = true
}

/** Up Right square tile step. */
case object SqStepUR extends SqStepDiag
{ def r: Int = 2
  def c: Int = 2
  override def reverse: SqStep = SqStepDR
}

/** Down Right square tile step. */
case object SqStepDR extends SqStepDiag
{ def r: Int = -2
  def c: Int = 2
  override def reverse: SqStep = SqStepUL
}

/** Down Left square tile step. */
case object SqStepDL extends SqStepDiag
{ def r: Int = -2
  def c: Int = -2
  override def reverse: SqStep = SqStepUR
}

/** Up Left square tile step. */
case object SqStepUL extends SqStepDiag
{ def r: Int = 2
  def c: Int = -2
  override def reverse: SqStep = SqStepDR
}

case class SqAndStep(r1: Int, c1: Int, step: SqStep)
{ def sc1: SqCen = SqCen(r1, c1)
  def sc2: SqCen = SqCen(r1 + step.r, c1 + step.c)
}