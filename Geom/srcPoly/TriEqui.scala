/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** Equilateral triangle. */
trait TriEqui extends TriIsos
{
  override def slate(operand: VecPt2): TriEqui = TriEqui.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))
  //override def slate(xOperand: Double, yOperand: Double): Triangle = super.slate(xOperand, yOperand)
  override def rotate(rotation: AngleVec): TriEqui = TriEqui.verts(v0.rotate(rotation), v1.rotate(rotation), v2.rotate(rotation))
}

object TriEqui
{ /** Factory apply method for equilateral triangle. */
  def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriEqui = new TriEquiGen(v0x, v0y, v1x, v1y, v2x, v2y)

  /** Constructs an equilateral triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is
   * not an apply method. */
  def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriEqui = new TriEquiGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)

  /** Equilateral triangle. will become a trait. */
  final class TriEquiGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriEqui, AxisFree
  { type ThisT = TriEquiGen

    override def typeStr: String = "TriangleEqui"

    override def height: Double = ???

    override def attribs: RArr[XAtt] = ???

    override def vertsTrans(f: Pt2 => Pt2): TriEqui = ???

    override def slate(operand: VecPt2): TriEquiGen = TriEquiGen.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

    override def rotate(rotation: AngleVec): TriEquiGen = TriEquiGen.verts(v0.rotate(rotation), v1.rotate(rotation), v2.rotate(rotation))
    override def reflect(lineLike: LineLike): TriEquiGen = ???
  }

  object TriEquiGen
  { /** Constructs an equilateral triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is
     * not an apply method. */
    def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriEquiGen = new TriEquiGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
  }
}

/** An equilateral triangle, with one side, aligned to the X axis. */
final class TriEquiXlign(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriEqui, TriIsosXlign
{ override type ThisT = TriEquiXlign
  override def slate(operand: VecPt2): TriEquiXlign = TriEquiXlign.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): Triangle =
    new TriEquiXlign(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)
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
  def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriEquiXlign = new TriEquiXlign(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
}