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

  override def reflect(line: LineSeg): EArc = ???
  override def reflect(line: Line): EArc = ???
  
  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: TransElem = ???

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: TransElem = ???

  override def prolign(matrix: ProlignMatrix): TransElem = ???

  override def xyScale(xOperand: Double, yOperand: Double): TransElem = ???

  override def xShear(operand: Double): TransElem = ???
  override def yShear(operand: Double): TransElem = ???
}

object EArc
{
  //def apply(): EArc = new EArc
}

