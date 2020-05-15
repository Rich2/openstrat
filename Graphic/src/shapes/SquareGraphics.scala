/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._

final case class SquareFill(square: Square, fillColour: Colour) extends FillElem
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = {}

  override def fTrans(f: Vec2 => Vec2): SquareFill = ???

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
}
