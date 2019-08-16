package ostrat
package pGrid

case class VertOffs(upA: Int = 0, upB: Int = 0, upRtA: Int = 0, upRtB: Int = 0, dnRtA: Int = 0, dnRtB: Int = 0, downA: Int = 0, downB: Int = 0,
               dnLtA: Int = 0, dnLtB: Int = 0, upLtA: Int = 0, upLtB: Int = 0)

/** Offsets 1 to 4 */
sealed trait HVertDirn
{ def value: Int
  def dirn: Cood
}

sealed trait TVertDirn extends HVertDirn
case class HVUpRt(value: Int) extends TVertDirn { override def dirn = 2 cc 1 }
case class HVDown(value: Int) extends TVertDirn { override def dirn =  0 cc - 1 }
case class HVupRt(value: Int) extends TVertDirn { override def dirn = -2 cc 1 }

sealed trait BVertDirn extends HVertDirn
case class HVUp(value: Int) extends BVertDirn { override def dirn = 0 cc 1 }
case class HVDnRt(value: Int) extends BVertDirn { override def dirn = 2 cc -1 }
case class HVDnLt(value: Int) extends BVertDirn { override def dirn = -2 cc -1 }

sealed trait HVertOff
case class TVertOff(off1: TVertDirn, off2: TVertDirn) extends HVertOff
case class BVertOff(off1: BVertDirn, off2: BVertDirn) extends HVertOff