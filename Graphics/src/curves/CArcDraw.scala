/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw
{
  /** Radius of the the circular arc. */
  def radius: Double = curveSeg.radius

  /** Translate 2D geometric transformation on this CArcDraw, returns a CArcDraw. */
  override def slate(xOffset: Double, yOffset: Double): CArcDraw = CArcDraw(curveSeg.slate(xOffset, yOffset), colour, lineWidth)

  /** Translate 2D geometric transformation on this CArcDraw, returns a CArcDraw. This overload might be removeable in Scala 3, but is necessary for the
   *  time being due to type inference problems. */
  override def slate(offset: Vec2Like): CArcDraw = CArcDraw(curveSeg.slate(offset), colour, lineWidth)

  /** Uniform scaling 2D geometric transformation on this CArcDraw, returns a CArcDraw. Use the xyScale method for differential scaling on the X and Y
   *  axes. */
  override def scale(operand: Double): CArcDraw = CArcDraw(curveSeg.scale(operand), colour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on this CArcDraw, returns a CArcDraw. */
  override def negY: CArcDraw = CArcDraw(curveSeg.negY, colour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on this CArcDraw, returns a CArcDraw. */
  override def negX: CArcDraw = CArcDraw(curveSeg.negX, colour, lineWidth)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): CArcDraw = CArcDraw(curveSeg.prolign(matrix), colour, lineWidth)

  /** Rotation 2D geometric transformation on a EArcDraw taking the rotation as a scalar measured in radians, returns a EArcDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: Angle): CArcDraw = CArcDraw(curveSeg.rotate(angle), colour, lineWidth)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArcDraw, returns a EArcDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): CArcDraw = CArcDraw(curveSeg.reflect(lineLike), colour, lineWidth)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = ??? //cp.cArcDraw3(this)
}
