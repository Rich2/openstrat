/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

case class CircleDisplay(shape: Circle, members: Arr[ShapeMember]) extends ShapeDisplay  with SimilarPreserve
{ override type ThisT = CircleDisplay

  override def fTrans(f: Vec2 => Vec2): ThisT =
  {
    val newMems = members.map
    { case sf: ShapeFacet => sf
      case sd: ShapeDisplay => sd
    }
    CircleDisplay(shape.fTrans(f), newMems)
  }
  override def svgStr: String = ???

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeDisplay = ???

  override def shearX(operand: Double): ShapeDisplay = ???

  override def shearY(operand: Double): ShapeDisplay = ???
}
