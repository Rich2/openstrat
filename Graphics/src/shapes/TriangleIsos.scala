/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** An isosceles triangle. This trait is implemented by the equilateral triangle, [[TriangleEqui]] and the general case [[TriangleIsos]]. */
trait TriangleIsos extends Triangle
{	def height: Double
  def x1: Double = ???
  def y1: Double = ???
  override def v1: Vec2 = ???
}

/** The general case of an isosceles triangle. */
final case class TriangleIsosGen(x0: Double, y0: Double, x2: Double, y2: Double, height: Double) extends TriangleIsos
{	type ThisT = TriangleIsosGen

  override def fTrans(f: Vec2 => Vec2): TriangleIsosGen = ???
  override def v1: Vec2 = ???
  override def attribs: Arr[XANumeric] = ???
  override def foreach[U](f: Vec2 => U): Unit = ???

  override def rotate(angle: Angle): TriangleIsosGen = ???

  override def negY: ThisT = fTrans(_.negY)
  override def negX: ThisT = fTrans(_.negX)
  override def reflect(lineLike: LineLike): Triangle = ???
  //override def reflect(line: LineSeg): Triangle = ???

  override def xyScale(xOperand: Double, yOperand: Double): Triangle = ???

  override def fill(fillColour: Colour): PolygonFill = ???

  //override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}