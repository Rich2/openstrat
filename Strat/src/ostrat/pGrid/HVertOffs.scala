package ostrat
package pGrid
import geom._, HexGrid.{coodToVec2 => ctv}

case class HVertOffs(up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg, down: BVert = HVertReg, dnLt: TVert = HVertReg,
                     upLt: BVert = HVertReg)

trait HVertOffsTr { def vertOffs: HVertOffs}

/** A definition of a Hex Vert with 0, 1 or 2 offset values. Offsets must be Int 1 <= i >= 4 */
sealed trait HVert
trait TVert extends HVert
trait BVert extends HVert

trait HVertSingle extends HVert
{ def vec2(vert: Cood): Vec2
}

object HVertReg extends TVert with BVert with HVertSingle
{ override def vec2(vert: Cood): Vec2 = ctv(vert)
}

trait HVertDirn extends HVert
{ def dirn: Cood
  def nextVert(verts: HVertOffs): HVert
}

sealed trait HVertDirn1 extends HVertDirn with HVertSingle
{ def value: Int
  override def vec2(vert: Cood): Vec2 = (value * ctv(vert + dirn) + (5 - value) * ctv(vert)) / 5
}

trait HVertDirn2 extends HVertDirn
{ def ltDirn: Cood
  def ltVal: Int
  def rtDirn: Cood
  def rtVal: Int
  def ltVec2(vert: Cood): Vec2 = (ltVal * ctv(vert + ltDirn) + (5 - ltVal) * ctv(vert)) / 5
  def rtVec2(vert: Cood): Vec2 = (rtVal * ctv(vert + rtDirn) + (5 - rtVal) * ctv(vert)) / 5
  def sidePoly(vert: Cood, vs: HVertOffs): Polygon = nextVert(vs) match
  { case other: HVertDirn2 => Polygon(rtVec2(vert), ltVec2(vert), other.rtVec2(vert), other.ltVec2(vert))
    case other: HVertSingle => Polygon(rtVec2(vert), ltVec2(vert), other.vec2(vert))
  }
}

sealed trait TVertDirn extends HVertDirn with TVert

trait HVUpRt extends TVertDirn
{ override def dirn = 2 cc 1
  override def nextVert(verts: HVertOffs): HVert = verts.dnRt
}
case class HVUpRt1(value: Int) extends HVUpRt with HVertDirn1
case class HVUpRt2(ltVal: Int, rtVal: Int) extends HVUpRt with HVertDirn2
{ override def ltDirn: Cood = 0 cc -1
  override def rtDirn: Cood = -2 cc 1
}

trait HVDown extends TVertDirn
{ override def dirn =  0 cc - 1
  override def nextVert(verts: HVertOffs): HVert = verts.dnLt
}
case class HVDown1(value: Int) extends HVDown with HVertDirn1
case class HVDown2(ltVal: Int, rtVal: Int) extends HVDown with HVertDirn2
{ override def ltDirn: Cood = -2 cc 1
  override def rtDirn: Cood = 2 cc 1
}

trait HVUpLt extends TVertDirn
{ override def dirn = -2 cc 1
  override def nextVert(verts: HVertOffs): HVert = verts.up
}
case class HVUpLt1(value: Int) extends HVUpLt with HVertDirn1
case class HVUpLt2(ltVal: Int, rtVal: Int) extends HVUpLt with HVertDirn2
{ override def ltDirn: Cood = 2 cc 0
  override def rtDirn: Cood = 0 cc -1
}

sealed trait BVertDirn extends HVertDirn with BVert
trait HVUp extends BVertDirn
{ override def dirn = 0 cc 1
  override def nextVert(verts: HVertOffs): HVert = verts.upRt
}
case class HVUp1(value: Int) extends HVUp with HVertDirn1
case class HVUp2(ltVal: Int, rtVal: Int) extends HVUp with HVertDirn2
{ override def ltDirn: Cood = 2 cc -1
  override def rtDirn: Cood = -2 cc -1
}

trait HVDnRt extends BVertDirn
{ override def dirn = 2 cc -1
  override def nextVert(verts: HVertOffs): HVert = verts.down
}
case class HVDnRt1(value: Int) extends HVDnRt with HVertDirn1
case class HVDnRt2(ltVal: Int, rtVal: Int) extends HVDnRt with HVertDirn2
{ override def ltDirn: Cood = -2 cc -1
  override def rtDirn: Cood = 0 cc 1
}

trait HVDnLt extends BVertDirn
{ override def dirn = -2 cc -1
  override def nextVert(verts: HVertOffs): HVert = verts.upLt
}
case class HVDnLt1(value: Int) extends HVDnLt with HVertDirn1
case class HVDnLt2(ltVal: Int, rtVal: Int) extends HVDnLt with HVertDirn2
{ override def ltDirn: Cood = 0 cc 1
  override def rtDirn: Cood = 2 cc -1

}