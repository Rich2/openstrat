package ostrat
package pGrid
import geom._, HexGrid.{coodToVec2 => ctv}

case class VertOffs(up: TVert = VertReg, upRt: BVert = VertReg, dnRt: TVert = VertReg, down: BVert = VertReg, dnLt: TVert = VertReg,
  upLt: BVert = VertReg)

/** Offsets 1 to 4 */
sealed trait HVert
trait TVert extends HVert
trait BVert extends HVert
object VertReg extends TVert with BVert
trait HVertDirn extends HVert { def dirn: Cood }

sealed trait HVertDirn1 extends HVertDirn{ def value: Int }

trait HVertDirn2 extends HVertDirn
{ def ltDirn: Cood
  def ltVal: Int
  def rtDirn: Cood
  def rtVal: Int
  def ltVert(vert: Cood): Vec2 = (ltVal * ctv(vert + ltDirn) + (5 - ltVal) * ctv(vert)) / 5
  def rtVert(vert: Cood): Vec2 = (rtVal * ctv(vert + rtDirn) + (5 - rtVal) * ctv(vert)) / 5

}

sealed trait TVertDirn extends HVertDirn with TVert
trait HVUpRt extends TVertDirn { override def dirn = 2 cc 1 }
case class HVUpRt1(value: Int) extends HVUpRt with HVertDirn1
case class HVUpRt2(ltVal: Int, rtVal: Int) extends HVUpRt with HVertDirn2
{ override def ltDirn: Cood = 0 cc -1
  override def rtDirn: Cood = -2 cc 1
}

trait HVDown extends TVertDirn { override def dirn =  0 cc - 1 }
case class HVDown1(value: Int) extends HVDown with HVertDirn1
case class HVDown2(ltVal: Int, rtVal: Int) extends HVDown with HVertDirn2
{ override def ltDirn: Cood = -2 cc 1
  override def rtDirn: Cood = 2 cc 1
}

trait HVUpLt extends TVertDirn { override def dirn = -2 cc 1 }
case class HVUpLt1(value: Int) extends HVUpLt with HVertDirn1
case class HVUpLt2(ltVal: Int, rtVal: Int) extends HVUpLt with HVertDirn2
{ override def ltDirn: Cood = 2 cc 0
  override def rtDirn: Cood = 0 cc -1
}

sealed trait BVertDirn extends HVertDirn with BVert
trait HVUp extends BVertDirn { override def dirn = 0 cc 1 }
case class HVUp1(value: Int) extends HVUp with HVertDirn1
case class HVUp2(ltVal: Int, rtVal: Int) extends HVUp with HVertDirn2
{ override def ltDirn: Cood = 2 cc -1
  override def rtDirn: Cood = -2 cc -1
}

trait HVDnRt extends BVertDirn { override def dirn = 2 cc -1 }
case class HVDnRt1(value: Int) extends HVDnRt with HVertDirn1
case class HVDnRt2(ltVal: Int, rtVal: Int) extends HVDnRt with HVertDirn2
{ override def ltDirn: Cood = -2 cc -1
  override def rtDirn: Cood = 0 cc 1
}

trait HVDnLt extends BVertDirn  { override def dirn = -2 cc -1 }
case class HVDnLt1(value: Int) extends HVDnLt with HVertDirn1
case class HVDnLt2(ltVal: Int, rtVal: Int) extends HVDnLt with HVertDirn2
{ override def ltDirn: Cood = 0 cc 1
  override def rtDirn: Cood = 2 cc -1
  def sideOtherVert(vs: VertOffs): HVert = ???
  def sidePoly(vert: Cood, vs: VertOffs): Polygon = vs.dnRt match
  {
    case other: HVDnRt2 => Polygon(rtVert(vert), ltVert(vert), other.rtVert(vert.addX(2)), other.ltVert(vert.addX(2)))
  }
}