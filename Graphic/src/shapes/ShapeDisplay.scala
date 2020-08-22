/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._, Colour.Black

/** A ShapeMember, a member of a ShapeDisp can either be a ShapeFacet or a [[ShapeDisplay]]. */
sealed trait ShapeMember

/** A shape based graphic. Will probably change the name back to ShapeGraphic. */
trait ShapeDisplay extends DisplayElem with ShapeMember
{ def shape: Shape
  def members: Arr[ShapeMember]
  //def attribs: Arr[Attrib]
  def svgStr: String

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeDisplay

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeDisplay

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeDisplay

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): ShapeDisplay

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): ShapeDisplay

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeDisplay

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeDisplay

  override def prolign(matrix: ProlignMatrix): ShapeDisplay

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: ShapeDisplay

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: ShapeDisplay

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: ShapeDisplay

  override def rotateRadians(radians: Double): ShapeDisplay

  override def reflect(line: Line): ShapeDisplay

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeDisplay
  override def shearX(operand: Double): ShapeDisplay

  override def shearY(operand: Double): ShapeDisplay
}

trait ShapeFacet extends ShapeMember
{ def attribs: Arr[Attrib]
}

class FillColour(value: Int) extends ShapeFacet
{ def colour: Colour = new Colour(value)
  override def attribs: Arr[Attrib] = Arr(FillAttrib(colour))
  def fillAttrib: FillAttrib = FillAttrib(colour)
}

case class ShapeActive(id: Any) extends ShapeFacet
{ override def attribs: Arr[Attrib] = Arr()
}

trait CurveFacet extends ShapeFacet

case class CurveDraw(width: Double = 2.0, colour: Colour = Black) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: Arr[Attrib] = Arr(strokeWidthAttrib, strokeAttrib)
}