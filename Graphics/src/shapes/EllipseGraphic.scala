/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait EllipseGraphic extends ShapeGraphic
{ override def shape: Ellipse

  /** Translate geometric transformation. Translates this Ellipse Graphic into a modified EllipseGraphic. */
  override def slate(offset: Vec2): EllipseGraphic

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): EllipseGraphic

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): EllipseGraphic

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): EllipseGraphic

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): EllipseGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: EllipseGraphic

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: EllipseGraphic

  override def prolign(matrix: ProlignMatrix): EllipseGraphic

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: EllipseGraphic

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: EllipseGraphic

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: EllipseGraphic

  override def rotateRadians(radians: Double): EllipseGraphic

  override def reflect(line: Line): EllipseGraphic

  override def scaleXY(xOperand: Double, yOperand: Double): EllipseGraphic

  override def shearX(operand: Double): EllipseGraphic

  override def shearY(operand: Double): EllipseGraphic

  override def reflect(line: Sline): EllipseGraphic
}
