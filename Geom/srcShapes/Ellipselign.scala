/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** An ellipse whose axes are aligned to the X and Y axes. This is a trait as both [[Circle]] and [[Ellipselign.EllipselignGen]] classes implement this
 * interface. */
trait Ellipselign extends Ellipse
{ /** The radius of the axis of the ellipse aligned to the X axis. */
  def xRadius: Double

  /** The radius of the axis of the ellipse aligned to the Y axis. */
  def yRadius: Double

  /** The alignment angle of axis 1 in an [[Ellipselign]] including the special case of [[Circle]] is by definition 0°. */
  override final def alignAngle: Angle = Angle(0)

  override def slate(operand: VecPt2): Ellipselign =
    new EllipselignGen(p0X + operand.x, p0Y + operand.y, p1X + operand.x, p1Y + operand.y, p3X + operand.x, p3Y + operand.y)

  override def slate(xOperand: Double, yOperand: Double): Ellipselign =
    new EllipselignGen(p0X + xOperand, p0Y + yOperand, p1X + xOperand, p1Y + yOperand, p3X + xOperand, p3Y + yOperand)

  override def slateX(xOperand: Double): Ellipselign = new EllipselignGen(p0X + xOperand, p0Y, p1X + xOperand, p1Y, p3X + xOperand, p3Y)
  override def slateY(yOperand: Double): Ellipselign = new EllipselignGen(p0X, p0Y + yOperand, p1X, p1Y + yOperand, p3X, p3Y + yOperand)
  override def scale(operand: Double): Ellipselign = new EllipselignGen(p0X * operand, p0Y * operand, p1X * operand, p1Y * operand, p3X * operand, p3Y * operand)
  override def negX: Ellipselign = ???// Ellipselign(xRadius, yRadius, -cenX, cenY)
  override def negY: Ellipselign = ???//Ellipselign(xRadius, yRadius, cenX, -cenY)
  override def rotate90: Ellipselign = ???
  override def rotate180: Ellipselign = ???
  override def rotate270: Ellipselign = ???
}

object Ellipselign
{
  def apply(xRadius: Double, yRadius: Double, cen: Pt2 = Pt2Z): Ellipselign =
    new EllipselignGen(cen.x, cen.y + yRadius, cen.x + xRadius, cen.y, cen.x - xRadius, cen.y)
  
  def apply(xRadius: Double, yRadius: Double, cenX: Double, cenY: Double): Ellipselign =
    new EllipselignGen(cenX, cenY + yRadius, cenX + xRadius, cenY, cenX - xRadius, cenY)
}

final class EllipselignGen(val p0X: Double, val p0Y: Double, val p1X: Double, val p1Y: Double, val p3X: Double, val p3Y: Double) extends Ellipselign
{ def xRadius: Double = ???
  def yRadius: Double = ???

  /** The h value of this ellipse. */
  override def h: Double = ???

  override def area: Double = ???

  override def boundingRect: Rect = Rect(xRadius * 2, yRadius * 2, cenX, cenY)

  /** Determines if the parameter point lies inside this [[Circle]]. */
  override def ptInside(pt: Pt2): Boolean = boundingRect.ptInside(pt) &&
    { val t1 = (pt.x - cenX).squared * yRadius.squared
      val t2 = (pt.y - cenY).squared * xRadius.squared
      val t3 = xRadius.squared * yRadius.squared
      t1 + t2 <= t3
    }
}