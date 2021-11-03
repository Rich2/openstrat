/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Common base trait for [[Line]], [[LineSeg]] and [[Ray]]. */
trait LineLike extends GeomElem

/** Geometrical Ray. */
class Ray private(x0: Double, y0: Double, angleSecs: Double) extends LineLike
{
  def p0: Pt2 = Pt2(x0, y0)

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slateXY(xDelta: Double, yDelta: Double): Ray = Ray.v0Secs(p0.addXY(xDelta, yDelta), angleSecs)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): Ray = ???

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: Ray = ???

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: Ray = ???

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): Ray = ???

  override def rotate90: Ray = ???
  override def rotate180: Ray = ???
  override def rotate270: Ray = ???

  /** Rotation 2D geometric transformation on a Ray. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): Ray = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a Ray. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): Ray = ???

  /** XY scaling 2D geometric transformation on a Ray. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): Ray = ???

  /** Shear 2D geometric transformation along the X Axis on a Ray. The return type will be narrowed in sub classes and traits. */
  override def shearX(operand: Double): Ray = ???

  /** Shear 2D geometric transformation along the Y Axis on a Ray. The return type will be narrowed in sub classes and traits. */
  override def shearY(operand: Double): Ray = ???
}

/** Companion object for the Ray class, contains apply factory methods. */
object Ray
{ def apply(v0: Pt2, angle: Angle): Ray = new Ray(v0.x, v0.y, angle.secs)
  def apply(x0: Double, y0: Double, angle: Angle): Ray = new Ray(x0, y0, angle.secs)
  def v0Secs(v0: Pt2, angleSecs: Double): Ray = new Ray(v0.x, v0.y, angleSecs)
}