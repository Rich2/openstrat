/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriEqui]] and the general case [[TriIsos]]. */
trait TriIsos extends Triangle
{ type ThisT <: TriIsos
  override def typeStr: String = "TriangleIsos"

  /** The height of this isosceles triangle from the bisection of the third side to the vertex where the 2 equal length sides meet. */
  def height: Double = ???
}

/** The general case of an isosceles triangle. */
final class TriIsosGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriIsos,
AxisFree//, PolygonLikeDbl2[Pt2], Pt2SeqSpec
{	type ThisT = TriIsosGen

  //override def fromArray(array: Array[Double]): TriangleIsosGen = new TriangleIsosGen(array)
  override def vertsTrans(f: Pt2 => Pt2): TriIsosGen = ???

  override def attribs: RArr[XAtt] = ???

  override def rotate(rotation: AngleVec): TriIsosGen = ???

  override def reflect(lineLike: LineLike): TriIsosGen = ???
}

/** An isosceles triangle with its base parallel to the X axis. */
trait TriIsosXlign extends TriIsos
{
  
}

object TriIsosXlign
{
  def apply(baseY: Double, left: Double, right: Double, apexY: Double): TriIsosXlign =
  { val apexX = left \/ right
    val v1x = ife(apexY >= baseY, left, apexY)
    val v1Y = ife(apexY >= baseY, baseY, apexY)
    new TriIsosXlignGen(right, baseY, v1x, v1Y, ife(apexY >= baseY, apexX, left), ife(apexY >= baseY, apexY, baseY))
  }

  /** The general case of an isosceles triangle aligned to the X axis. */
  final class TriIsosXlignGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends TriIsosXlign
  { override type ThisT = TriIsosXlignGen

    override def slate(operand: VecPt2): TriIsosXlignGen = TriIsosXlignGen.verts(v0.slate(operand), v1.slate(operand), v2.slate(operand))
  }

  object TriIsosXlignGen
  { /** Constructs an isosceles triangle from its vertices. These are not checked. It is up to the user to supply valid values for the class, hence this is not
     * an apply method. */
    def verts(v0: Pt2, v1: Pt2, v2: Pt2): TriIsosXlignGen = new TriIsosXlignGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
  }
}