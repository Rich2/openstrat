/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** A square tile direction can take 8 values. This can be used for square grid steps or quantums. */
sealed trait SqDirn extends TDirn
{ /** The SqCen that this step would point to if it departed from SqCen(0, 0). */
  def sqCen: SqCen = SqCen(tr, tc)

  /** the step that foes in the opposite direct to this step. */
  def reverse: SqDirn

  /** Is perpendicular or non diagonal an Up / Right / Down / Left square tile direction or step */
  def isPerp: Boolean

  /** Is an UpRight / DownRight / DownLeft / UPLeft step. */
  def isDiag: Boolean
}

/** A perpendicular or non-diagonal square tile direction or step can take 4 values. */
sealed trait SqDirnPerp extends SqDirn with TDirnSided
{ override def isPerp: Boolean = true
  override def isDiag: Boolean = false
}

/** An upward step / move addition of one square tile in a square tile grid. Increases the row coordinate by 2. */
case object SqUp extends SqDirnPerp
{ def sr: Int = 1
  def sc: Int = 0
  override def reverse: SqDirn = SqDn
}

/** An rightward step / move / addition of one square tile in a square tile grid. Increases the column coordinate by 2 */
case object SqRt extends SqDirnPerp
{ def sr: Int = 0
  def sc: Int = 1
  override def reverse: SqDirn = SqLt
}

/** An downward step / move / addition of one square tile in a square tile grid. */
case object SqDn extends SqDirnPerp
{ def sr: Int = -1
  def sc: Int = 0
  override def reverse: SqDirn = SqUp
}

/** An upward of one square tile in a square tile grid. */
case object SqLt extends SqDirnPerp
{ def sr: Int = 0
  def sc: Int = -1
  override def reverse: SqDirn = SqRt
}

/** A non-diagonal square tile Step can take 4 values. */
sealed trait SqDirnDiag extends SqDirn
{ override def isPerp: Boolean = false
  override def isDiag: Boolean = true
}

/** Up Right square tile step. */
case object SqUR extends SqDirnDiag
{ def tr: Int = 2
  def tc: Int = 2
  override def reverse: SqDirn = SqDR
}

/** Down Right square tile step. */
case object SqDR extends SqDirnDiag
{ def tr: Int = -2
  def tc: Int = 2
  override def reverse: SqDirn = SqUL
}

/** Down Left square tile step. */
case object SqDL extends SqDirnDiag
{ def tr: Int = -2
  def tc: Int = -2
  override def reverse: SqDirn = SqUR
}

/** Up Left square tile step. */
case object SqUL extends SqDirnDiag
{ def tr: Int = 2
  def tc: Int = -2
  override def reverse: SqDirn = SqDR
}

case class SqAndStep(r1: Int, c1: Int, step: SqDirn)
{ def sc1: SqCen = SqCen(r1, c1)
  def sc2: SqCen = SqCen(r1 + step.tr, c1 + step.tc)
}