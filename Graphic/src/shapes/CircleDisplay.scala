/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class CircleDisplay(shape: Circle, members: Arr[ShapeMember]) extends ShapeDisp {
  override def svgStr: String = ???

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): DisplayElem = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): DisplayElem = ???

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): DisplayElem = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): DisplayElem = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): DisplayElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: DisplayElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: DisplayElem = ???

  override def prolign(matrix: ProlignMatrix): DisplayElem = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: DisplayElem = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: DisplayElem = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: DisplayElem = ???

  override def rotateRadians(radians: Double): DisplayElem = ???

  override def reflect(line: Line): DisplayElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem = ???

  override def reflect(line: Sline): TransElem = ???

  override def shearX(operand: Double): TransElem = ???

  override def shearY(operand: Double): TransElem = ???
}
