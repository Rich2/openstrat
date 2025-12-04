/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** Equilateral triangle. */
trait TriEqui extends TriIsos
{
  //override def slate(xOperand: Double, yOperand: Double): Triangle = super.slate(xOperand, yOperand)
}

object TriEqui
{ /** Factory apply method for equilateral triangle. */
  def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriEqui = new TriEquiGen(v0x, v0y, v1x, v1y, v2x, v2y)
}

/** Equilateral triangle. will become a trait. */
final class TriEquiGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriEqui, AxisFree
{ type ThisT = TriEquiGen
  override def typeStr: String = "TriangleEqui"

  override def height: Double = ???
  override def attribs: RArr[XAtt] = ???
  override def vertsTrans(f: Pt2 => Pt2): TriEqui = ???

  override def rotate(rotation: AngleVec): TriEquiGen = ???

  override def reflect(lineLike: LineLike): TriEquiGen = ???
}

/** An equilateral triangle, with one side, aligned to the X axis. */
final class TriEquiXlign(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriEqui, TriIsosXlign
{ override type ThisT = TriEquiXlign
}

object TriEquiXlign
{
  def apply(baseY: Double, left: Double, right: Double, apexUp: Boolean): TriEquiXlign =
  { val apexX = left \/ right
    val apexY: Double =
    { val r1 = (right - left).abs * 3.sqrt / 2
      ife(apexUp, baseY + r1, baseY -r1)
    }
    val v0x: Double = ife(apexUp, apexX, right)
    val v0y: Double = ife(apexUp, apexY, baseY)
    val v1x: Double = ife(apexUp, right, apexX)
    val v1y: Double = ife(apexUp, baseY, apexY)
    new TriEquiXlign(v0x, v0y, v1x, v1y, left, baseY)
  }
  
  /** Returns an equilateral triangle aligned to the X axis class. The vertices are not tested. */
  def vertices(v0: Pt2, v1: Pt2, v2: Pt2): TriEquiXlign = new TriEquiXlign(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
}