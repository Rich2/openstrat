/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriEqui]] and the general case [[TriIsos]]. */
trait TriIsos extends Triangle
{ type ThisT <: TriIsos
  override def typeStr: String = "TriangleIsos"
  override def slate(operand: VecPt2): TriIsos = TriIsos.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): TriIsos =
    TriIsos.dbls(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

  override def scale(operand: Double): TriIsos = TriIsos.dbls(v0x * operand, v0y * operand, v1x * operand, v1y * operand, v2x * operand, v2y * operand)

  /** The height of this isosceles triangle from the bisection of the third side to the vertex where the 2 equal length sides meet. */
  def height: Double = ???
}

object TriIsos
{
  /** Constructs an isosceles triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is not
   * an apply method. */
  def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriIsos = new TriIsosGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)

  /** Factory method to create isosceles triangle from the 6 vertex [[Double]] values.  */
  def dbls(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriIsos = new TriIsosGen(v0x, v0y, v1x, v1y, v2x, v2y)

  /** The general case of an isosceles triangle. */
  final class TriIsosGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriIsos, AxisFree
  { type ThisT = TriIsosGen

    //override def fromArray(array: Array[Double]): TriangleIsosGen = new TriangleIsosGen(array)
    override def vertsTrans(f: Pt2 => Pt2): TriIsosGen = ???

    override def attribs: RArr[XAtt] = ???

    override def slate(operand: VecPt2): TriIsosGen = TriIsosGen.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

    override def slate(xOperand: Double, yOperand: Double): TriIsosGen =
      new TriIsosGen(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

    override def scale(operand: Double): TriIsosGen = new TriIsosGen(v0x * operand, v0y * operand,v1x * operand, v1y * operand, v2x * operand, v2y * operand)

    override def rotate(rotation: AngleVec): TriIsosGen = ???

    override def reflect(lineLike: LineLike): TriIsosGen = ???
  }

  object TriIsosGen
  {  /** Constructs an isosceles triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is not
     * an apply method. */
    def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriIsosGen = new TriIsosGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
  }
}

/** An isosceles triangle with its base parallel to the X axis. */
trait TriIsosXlign extends TriIsos
{ override def slate(operand: VecPt2): TriIsosXlign = TriIsosXlign.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): TriIsosXlign =
    TriIsosXlign.dbls(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

  override def scale(operand: Double): TriIsosXlign =
    TriIsosXlign.dbls(v0x * operand, v0y * operand, v1x * operand, v1y * operand, v2x * operand, v2y * operand)
}

object TriIsosXlign
{
  /** Factory apply method to construct an isosceles triangle with its base aligned to the X axis, from the base y component, the base's left and right and the
   * Y component of the apex.*/
  def apply(baseY: Double, left: Double, right: Double, apexY: Double): TriIsosXlign =
  { val apexX = left \/ right
    val v1x = ife(apexY >= baseY, left, apexY)
    val v1Y = ife(apexY >= baseY, baseY, apexY)
    new TriIsosXlignGen(right, baseY, v1x, v1Y, ife(apexY >= baseY, apexX, left), ife(apexY >= baseY, apexY, baseY))
  }

  /** Factory apply method for isosolese triangle with its base aligned to the X axis. */
  def dbls(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriIsosXlign = new TriIsosXlignGen(v0x, v0y, v1x, v1y, v2x, v2y)

  /** Constructs an isosceles triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is not
   * an apply method. */
  def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriIsosXlign = new TriIsosXlignGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)

  /** The general case of an isosceles triangle aligned to the X axis. */
  final class TriIsosXlignGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriIsosXlign
  { override type ThisT = TriIsosXlignGen
    override def slate(operand: VecPt2): TriIsosXlignGen = TriIsosXlignGen.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))

    override def slate(xOperand: Double, yOperand: Double): TriIsosXlignGen =
      new TriIsosXlign.TriIsosXlignGen(v0x + xOperand, v0y + yOperand, v1x + xOperand, v1y + yOperand, v2x + xOperand, v2y + yOperand)

    override def scale(operand: Double): TriIsosXlignGen = new TriIsosXlignGen(v0x * operand, v0y * operand,v1x * operand, v1y * operand, v2x * operand, v2y * operand)
  }

  object TriIsosXlignGen
  { /** Constructs an isosceles triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is not
     * an apply method. */
    def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriIsosXlignGen = new TriIsosXlignGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
  }
}