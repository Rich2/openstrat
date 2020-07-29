/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._, pCanv._

final case class SquareFill(shape: Square, fillColour: Colour) extends ShapeFill
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = {}

  override def reflectXOffset(yOffset: Double): SquareFill = SquareFill(shape.reflectXOffset(yOffset), fillColour)
  @inline def reflectX: SquareFill = reflectXOffset(0)
  override def reflectYOffset(xOffset: Double): SquareFill = SquareFill(shape.reflectYOffset(xOffset), fillColour)
  @inline def reflectY: SquareFill = reflectYOffset(0)

  /** Translate 2d geometric vector transformation. Square properties are maintained under a trasnlate transformation. */
  override def slate(offset: Vec2): SquareFill = SquareFill(shape.slate(offset), fillColour)

  /** Translate geometric transformation. */
  @inline def slate(xOffset: Double, yOffset: Double): SquareFill = SquareFill(shape.slate(xOffset, yOffset), fillColour)

  override def scale(operand: Double): SquareFill = SquareFill(shape.scale(operand), fillColour)

  override def prolign(matrix: ProlignMatrix): SquareFill = SquareFill(shape.prolign(matrix), fillColour)

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: SquareFill = SquareFill(shape.rotate90, fillColour)

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: SquareFill = SquareFill(shape.rotate180, fillColour)

  override def rotate270: SquareFill = SquareFill(shape.rotate270, fillColour)

  override def rotateRadians(radians: Double): SquareFill = ???

  override def reflect(line: Line): SquareFill = SquareFill(shape.reflect(line), fillColour)
  override def reflect(line: Sline): SquareFill = SquareFill(shape.reflect(line), fillColour)

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGraphic = ???

  override def shearX(operand: Double): TransElem = ???
  override def shearY(operand: Double): TransElem = ???

  override def attribs: Arr[Attrib] = ???
  override def svgStr: String = ???
}
