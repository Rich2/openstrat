/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Common base trait for [[Line]], [[LineSeg]] and [[Ray]]. */
trait LineLike extends GeomElem

/** Geometrical Ray. */
class Ray private(x0: Double, y0: Double, angleSecs: Double) extends LineLike
{
  def p0: Pt2 = Pt2(x0, y0)

  /** Translate 2D geometric transformation on this GeomElem. The Return type will be narrowed in sub traits and  classes. */
  //override def slate(offset: Vec2Like): GeomElem = Ray.v0Secs(p0.slate(offset), angleSecs)

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def xySlate(xOffset: Double, yOffset: Double): GeomElem = Ray.v0Secs(p0.addXY(xOffset, yOffset), angleSecs)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): GeomElem = ???

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: GeomElem = ???

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: GeomElem = ???

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): GeomElem = ???

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): GeomElem = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): GeomElem = ???

  /** XY scaling 2D geometric transformation on a GeomElem. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): GeomElem = ???

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): GeomElem = ???

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): GeomElem = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

/** Companion object for the Ray class, contains apply factory methods. */
object Ray
{ def apply(v0: Pt2, angle: Angle): Ray = new Ray(v0.x, v0.y, angle.secs)
  def apply(x0: Double, y0: Double, angle: Angle): Ray = new Ray(x0, y0, angle.secs)
  def v0Secs(v0: Pt2, angleSecs: Double): Ray = new Ray(v0.x, v0.y, angleSecs)
}