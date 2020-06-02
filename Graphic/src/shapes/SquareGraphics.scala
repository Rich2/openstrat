/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._

final case class SquareFill(square: Square, fillColour: Colour) extends FillElem
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = {}

  override def mirrorXOffset(yOffset: Double): SquareFill = SquareFill(square.mirrorXOffset(yOffset), fillColour)
  @inline def mirrorX: SquareFill = mirrorXOffset(0)
  override def mirrorYOffset(xOffset: Double): SquareFill = SquareFill(square.mirrorYOffset(xOffset), fillColour)
  @inline def mirrorY: SquareFill = mirrorYOffset(0)

  /** Translate 2d geometric vector transformation. Square properties are maintained under a trasnlate transformation. */
  override def slate(offset: Vec2): SquareFill = SquareFill(square.slate(offset), fillColour)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): SquareFill = SquareFill(square.slate(xOffset, yOffset), fillColour)

  override def scale(operand: Double): SquareFill = SquareFill(square.scale(operand), fillColour)

  override def prolign(matrix: ProlignMatrix): SquareFill = SquareFill(square.prolign(matrix), fillColour)

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: SquareFill = SquareFill(square.rotate90, fillColour)

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: SquareFill = SquareFill(square.rotate180, fillColour)

  override def rotate270: SquareFill = SquareFill(square.rotate270, fillColour)

  override def rotateRadians(radians: Double): SquareFill = ???

  override def mirror(line: Line2): SquareFill = SquareFill(square.mirror(line), fillColour)
}
