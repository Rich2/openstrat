/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour._

case class CArcDraw(curveSeg: CArc, colour: Colour = Black, lineWidth: Double = 2) extends EArcDraw
{
  /** Translate 2D geometric transformation on a CArcDraw, returns a CArcDraw. */
  override def slate(xOffset: Double, yOffset: Double): CArcDraw = CArcDraw(curveSeg.slate(xOffset, yOffset), colour, lineWidth)

  /** Translate 2D geometric transformation on a CArcDraw, returns a CArcDraw. This overload might be removeable in Scala 3, but is necessary for the
   *  time being due to type inference problems. */
  override def slate(offset: Vec2Like): EArcDraw = CArcDraw(curveSeg.slate(offset), colour, lineWidth)

  /** Uniform scaling 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): EArcDraw = super.scale(operand)

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: EArcDraw = super.negY

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: EArcDraw = super.negX

  /** 2D geometric transformation using a [[ProlignMatrix]] on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): EArcDraw = super.prolign(matrix)

  /** Rotation 2D geometric transformation on a EArcDraw taking the rotation as a scalar measured in radians, returns a EArcDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: Angle): EArcDraw = super.rotate(angle)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArcDraw, returns a EArcDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): EArcDraw = super.reflect(lineLike)

  override def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = ??? //cp.cArcDraw3(this)
}
