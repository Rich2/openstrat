/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

trait Triangle extends Polygon
{	override def length: Int = 3
	def x0: Double
	def y0: Double
	def v0: Vec2 = x0 vv y0
	def x1: Double
	def y1: Double
	def v1: Vec2
	def x2: Double
	def y2: Double
	def v2: Vec2 = x2 vv y2
	
	override def apply(index: Int): Vec2 = index match
	{	case 0 => v0
		case 1 => v1
		case 2 => v2
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}
	
	override def elem1sArray: Array[Double] = Array(x0, x1, x2)
	override def elem2sArray: Array[Double] = Array(y0, y1, y2)
	override def foreach[U](f: Vec2 => U): Unit = { f(v0); f(v1); f(v2); () }
	override def foreachTail[U](f: Vec2 => U): Unit = { f(v1); f(v2); () }
	override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x1, y1); f(x2, y2); () }
	override def shearX(operand: Double): Triangle = ???
	override def shearY(operand: Double): Triangle = ???

	override def xGet(index: Int): Double = ???

	override def yGet(index: Int): Double = ???
}

final case class TriangleClass(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double) extends Triangle with AffinePreserve
{ override type ThisT = TriangleClass
	override def v1: Vec2 = ???
	override def shapeAttribs: Arr[XANumeric] = ???
	override def fTrans(f: Vec2 => Vec2): TriangleClass = ???

	override def rotateRadians(radians: Double): TriangleClass = ???

	override def fillOld(fillColour: Colour): ShapeFillOld = ???

	override def drawOld(lineWidth: Double, lineColour: Colour): ShapeDraw = ???

	override def fillDrawOld(fillColour: Colour, lineWidth: Double, lineColour: Colour): ShapeFillDraw = ???
}

object Triangle
{ //def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double): Triangle = ???
	//def apply(v0: Vec2, v1: Vec2, v2: Vec2): Triangle = ??? // new Triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
	def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolygonFillOld = PolygonFillOld(PolygonGen(p1, p2, p3), colour)
}
