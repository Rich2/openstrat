/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A shape based graphic. Will probably change the name back to ShapeGraphic. */
trait ShapeGraphic extends DisplayElem
{ def shape: Shape
  def facets: Arr[ShapeFacet]

  /** The [[ShapeGraphic]] type will be widened at a later point. */
  def children: Arr[ShapeGraphic]
  //def attribs: Arr[Attrib]
  def svgStr: String

  /*def fMems(f: ShapeDisplay => ShapeDisplay): Arr[ShapeMember] = members.map{
    case sd: ShapeDisplay => f(sd)
    case sf: ShapeFacet => sf
  }*/

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): ShapeGraphic

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): ShapeGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeGraphic

  override def prolign(matrix: ProlignMatrix): ShapeGraphic

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: ShapeGraphic

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: ShapeGraphic

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: ShapeGraphic

  override def rotateRadians(radians: Double): ShapeGraphic

  override def reflect(line: Line): ShapeGraphic

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphic
  override def shearX(operand: Double): ShapeGraphic

  override def shearY(operand: Double): ShapeGraphic
}
