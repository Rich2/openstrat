/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Elliptical Arc. I think its important not to encode unnecessary data, not because of space concerns but because this may allow contradictory data.
 I've replaced  3 scalars and 2 booleans in the JavaFx encoding with 4 scalars. */
final case class EArc(xStart: Double, yStart: Double, xCen: Double, yCen: Double, x1: Double, y1: Double, xEnd: Double, yEnd: Double) extends
  TransElem
{ //override type SimerT = EArc

  //override def fTrans(f: Vec2 => Vec2): EArc = ???
  //override def rotate(angle: Angle): EArc = ???
  override def slate(offset: Vec2): EArc = ???
  override def rotateRadians(radians: Double): EArc = ???
  override def scale(operand: Double): EArc = ???
  //override def shear(xScale: Double, yScale: Double): EArc = ???

  override def mirror(line: Line2): EArc = ???

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def mirrorYOffset(xOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def mirrorXOffset(yOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def mirrorX: TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def mirrorY: TransElem = ???

  override def prolign(matrix: ProlignMatrix): TransElem = ???

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  override def rotate90: TransElem = ???

  /** Rotates 180 degrees or Pi radians. */
  override def rotate180: TransElem = ???

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  override def rotate270: TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???
}

object EArc
{
  //def apply(): EArc = new EArc
}

