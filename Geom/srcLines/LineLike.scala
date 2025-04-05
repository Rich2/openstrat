/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Common base trait for [[Line]], [[LineSeg]] and [[Ray]]. */
trait LineLike extends Geom2Elem

/** Geometrical Ray. */
class Ray private(x0: Double, y0: Double, angleSecs: Double) extends LineLike
{
  def p0: Pt2 = Pt2(x0, y0)

  override def slate(operand: VecPt2): Ray = ???
  override def slate(xOperand: Double, yOperand: Double): Ray = Ray.v0Secs(p0.slate(xOperand, yOperand), angleSecs)
  override def slateX(xOperand: Double): Ray = Ray.v0Secs(p0.slateX(xOperand), angleSecs)
  override def slateY(yOperand: Double): Ray = Ray.v0Secs(p0.slateY(yOperand), angleSecs)
  override def scale(operand: Double): Ray = ???
  override def negY: Ray = ???
  override def negX: Ray = ???
  override def prolign(matrix: ProlignMatrix): Ray = ???
  override def rotate90: Ray = ???
  override def rotate180: Ray = ???
  override def rotate270: Ray = ???
  override def rotate(angle: AngleVec): Ray = ???
  override def reflect(lineLike: LineLike): Ray = ???
  override def scaleXY(xOperand: Double, yOperand: Double): Ray = ???
  override def shearX(operand: Double): Ray = ???
  override def shearY(operand: Double): Ray = ???
}

/** Companion object for the Ray class, contains apply factory methods. */
object Ray
{ def apply(v0: Pt2, angle: Angle): Ray = new Ray(v0.x, v0.y, angle.secs)
  def apply(x0: Double, y0: Double, angle: Angle): Ray = new Ray(x0, y0, angle.secs)
  def v0Secs(v0: Pt2, angleSecs: Double): Ray = new Ray(v0.x, v0.y, angleSecs)
}