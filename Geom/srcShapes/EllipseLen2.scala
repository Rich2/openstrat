/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math.Pi, Colour.Black

trait EllipseLen2 extends ShapeLen2, FillableLen2
{ override def slate(operand: VecPtLen2): EllipseLen2
  override def slate(xOperand: Length, yOperand: Length): EllipseLen2
  override def slateX(xOperand: Length): EllipseLen2
  override def slateY(yOperand: Length): EllipseLen2
  override def scale(operand: Double): EllipseLen2
}

class EllipseLen2Gen
{

}
