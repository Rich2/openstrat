/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class CircleDisplay(shape: Circle, facets: Arr[ShapeFacet], children: Arr[ShapeDisplay]) extends ShapeDisplay// with SimilarPreserve
{ /*override type ThisT = CircleDisplay

  override def fTrans(f: Vec2 => Vec2): ThisT =
  {
    val newMems = members.map
    { case sf: ShapeFacet => sf
      case sd: ShapeDisplay => sd
    }
    CircleDisplay(shape.fTrans(f), newMems)
  }*/

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): CircleDisplay = CircleDisplay(shape.slate(offset), facets, children.map(_.slate(offset)))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): ShapeDisplay = ???

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeDisplay = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYOffset(xOffset: Double): ShapeDisplay = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXOffset(yOffset: Double): ShapeDisplay = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: ShapeDisplay = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: ShapeDisplay = ???

  override def prolign(matrix: ProlignMatrix): ShapeDisplay = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: ShapeDisplay = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: ShapeDisplay = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: ShapeDisplay = ???

  override def rotateRadians(radians: Double): ShapeDisplay = ???

  override def reflect(line: Line): ShapeDisplay = ???

  override def reflect(line: Sline): TransElem = ???

  override def svgStr: String = ???

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeDisplay = ???

  override def shearX(operand: Double): ShapeDisplay = ???

  override def shearY(operand: Double): ShapeDisplay = ???
}
