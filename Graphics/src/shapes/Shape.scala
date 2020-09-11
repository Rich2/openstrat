/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. */
trait Shape extends TransElem
{ def fillOld(fillColour: Colour): ShapeFill
  def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw
  def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw
  def shapeAttribs: Arr[XANumeric]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }
  def boundingRect: BoundingRect

  /** Translate geometric transformation on a Shape returns a Shape. */
  def slate(offset: Vec2): Shape

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): Shape

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): Shape

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def reflectYOffset(xOffset: Double): Shape

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def reflectXOffset(yOffset: Double): Shape

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectX: Shape

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def reflectY: Shape

  def prolign(matrix: ProlignMatrix): Shape

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: Shape

  /** Rotates 180 degrees or Pi radians. */
  def rotate180: Shape

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: Shape

  def rotateRadians(radians: Double): Shape

  def reflect(line: Line): Shape
  def reflect(line: Sline): Shape
  def scaleXY(xOperand: Double, yOperand: Double): Shape

  def shearX(operand: Double): TransElem
  def shearY(operand: Double): TransElem
}