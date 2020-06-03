/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black


final case class CircleFill(circle: Circle, fillColour: Colour) extends FillElem
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)

  override def mirrorXOffset(yOffset: Double): CircleFill = copy(circle.mirrorXOffset(yOffset))
  override def mirrorX: CircleFill = copy(circle.mirrorX)
  override def mirrorYOffset(xOffset: Double): CircleFill = CircleFill(circle.mirrorYOffset(xOffset), fillColour)
  override def mirrorY: CircleFill = copy(circle.mirrorY)

  override def slate(offset: Vec2): CircleFill = copy(circle.slate(offset))
  
  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): CircleFill = copy(circle.slate(xOffset, yOffset))

  override def scale(operand: Double): CircleFill = CircleFill(circle.scale(operand), fillColour)

  override def prolign(matrix: ProlignMatrix): CircleFill = CircleFill(circle.prolign(matrix), fillColour)

  override def rotate90: CircleFill = copy(circle.rotate90)

  override def rotate180: CircleFill = copy(circle.rotate180)

  override def rotate270: CircleFill = copy(circle.rotate270)

  override def rotateRadians(radians: Double): CircleFill = copy(circle.rotateRadians(radians))

  override def mirror(line: Line2): CircleFill = copy(circle.mirror(line))
}
