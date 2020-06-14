/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._, Colour.Black, pXml._

trait EllipseGraphic extends ShapeGraphic
{ type GraphicT <: EllipseGraphic
  override def shape: Ellipse
  def fTrans(newEllipse: Ellipse): GraphicT
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */  
  override def mirrorX: GraphicT = fTrans(shape.mirrorX)
  
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): GraphicT = fTrans(shape.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): GraphicT = fTrans(shape.slate(xOffset, yOffset))
}

final case class EllipseFill(shape: Ellipse, fillColour: Colour) extends EllipseGraphic with ShapeFill
{ type GraphicT = EllipseFill

  override def fTrans(newEllipse: Ellipse): EllipseFill = EllipseFill(newEllipse, fillColour)
  
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(this)  

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): GraphicElem = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def mirrorYOffset(xOffset: Double): GraphicElem = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def mirrorXOffset(yOffset: Double): GraphicElem = ???

  

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def mirrorY: GraphicElem = ???

  override def prolign(matrix: ProlignMatrix): GraphicElem = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: GraphicElem = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: GraphicElem = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: DisplayElem = ???

  override def rotateRadians(radians: Double): GraphicElem = ???

  override def mirror(line: Line2): GraphicElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): GraphicElem = ???

  override def attribs: Arr[Attrib] = ???
}