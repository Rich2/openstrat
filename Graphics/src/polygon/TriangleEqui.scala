/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** Equilateral triangle. will become a trait. */
final case class TriangleEqui(x1: Double, y1: Double, x3: Double, y3: Double) extends TriangleIsos
{
  type ThisT = TriangleEqui
  override def height: Double = ???
  override def attribs: Arr[XANumeric] = ???
  override def fTrans(f: Vec2 => Vec2): ThisT = ???

  override def rotate(angle: Angle): Triangle = ???
  override def negY: ThisT = fTrans(_.negY)
  override def negX: ThisT = fTrans(_.negX)

  override def reflect(lineLike: LineLike): Triangle = ???

  override def xyScale(xOperand: Double, yOperand: Double): Triangle = ???

  override def slateTo(newCen: Vec2): TriangleEqui = ???

}