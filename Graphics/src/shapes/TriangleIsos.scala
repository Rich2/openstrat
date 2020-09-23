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

  override def rotateRadians(radians: Double): TriangleIsosGen = ???
  override def reflectXOffset(yOffset: Double): ThisT = fTrans(_.reflectXOffset(yOffset))
  override def reflectYOffset(xOffset: Double): ThisT = fTrans(_.reflectYOffset(xOffset))
  override def reflectX: ThisT = fTrans(_.reflectX)
  override def reflectY: ThisT = fTrans(_.reflectY)
  override def reflect(line: Line): Triangle = ???
  override def reflect(line: LineSeg): Triangle = ???

  override def xyScale(xOperand: Double, yOperand: Double): Triangle = ???

  override def fillOld(fillColour: Colour): ShapeFill = ???

  override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}