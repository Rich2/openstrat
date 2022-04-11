/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** A square tile direction can take 8 values. This can be used for square grid steps or quantums. */
sealed trait SqDirn extends TDirn
{ /** The SqCen that this step would point to if it departed from SqCen(0, 0). */
  def sqCen: SqCen = SqCen(r, c)

  /** the step that foes in the opposite direct to this step. */
  def reverse: SqDirn

  /** Is an Up / Right / Down / Left step. */
  def isNear: Boolean

  /** Is an UpRight / DownRight / DownLeft / UPLeft step. */
  def isDiag: Boolean
}

/** A perpendicular or non-diagonal square tile direction or step can take 4 values. */
sealed trait SqDirnPerp extends SqDirn
{ override def isNear: Boolean = true
  override def isDiag: Boolean = false
}

/** An upward step / move addition of one square tile in a square tile grid. Increases the row coordinate by 2. */
case object SqUp extends SqDirnPerp
{ def r: Int = 2
  def c: Int = 0
  override def reverse: SqDirn = SqDn
}

/** An rightward step / move / addition of one square tile in a square tile grid. Increases the column coordinate by 2 */
case object SqRt extends SqDirnPerp
{ def r: Int = 0
  def c: Int = 2
  override def reverse: SqDirn = SqLt
}

/** An downward step / move / addition of one square tile in a square tile grid. */
case object SqDn extends SqDirnPerp
{ def r: Int = -2
  def c: Int = 0
  override def reverse: SqDirn = SqUp
}

/** An upward of one square tile in a square tile grid. */
case object SqLt extends SqDirnPerp
{ def r: Int = 0
  def c: Int = -2
  override def reverse: SqDirn = SqRt
}

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqDirnDiag extends SqDirn
{ override def isNear: Boolean = false
  override def isDiag: Boolean = true
}

/** Up Right square tile step. */
case object SqUR extends SqDirnDiag
{ def r: Int = 2
  def c: Int = 2
  override def reverse: SqDirn = SqDR
}

/** Down Right square tile step. */
case object SqDR extends SqDirnDiag
{ def r: Int = -2
  def c: Int = 2
  override def reverse: SqDirn = SqUL
}

/** Down Left square tile step. */
case object SqDL extends SqDirnDiag
{ def r: Int = -2
  def c: Int = -2
  override def reverse: SqDirn = SqUR
}

/** Up Left square tile step. */
case object SqUL extends SqDirnDiag
{ def r: Int = 2
  def c: Int = -2
  override def reverse: SqDirn = SqDR
}

case class SqAndStep(r1: Int, c1: Int, step: SqDirn)
{ def sc1: SqCen = SqCen(r1, c1)
  def sc2: SqCen = SqCen(r1 + step.r, c1 + step.c)
}