/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black, pWeb._

/** A mathematical triangle. */
trait Triangle extends Polygon
{	override def vertsNum: Int = 3
	def x1: Double
	def y1: Double
	def v1: Vec2 = x1 vv y1
	def x2: Double
	def y2: Double
	def v2: Vec2
	def x3: Double
	def y3: Double
	def v3: Vec2 = x3 vv y3

	override def apply(index: Int): Vec2 = index match
	{	case 1 => v1
		case 2 => v2
		case 3 => v3
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}

	override def ptsArray: Array[Double] = Array(x1, y1, x2, y2, x3, y3)

	override def xVertsArray: Array[Double] = Array(x1, x2, x3)
	override def yVertsArray: Array[Double] = Array(y1, y2, y3)
	override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); () }
	override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); () }
	override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x2, y2); f(x3, y3); () }


	/** Translate geometric transformation on a Shape returns a Shape. */
	override def slate(offset: Vec2): Triangle = ???

	/** Translate geometric transformation. */
	override def slate(xOffset: Double, yOffset: Double): Triangle = ???

	/** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
	 * Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): Triangle = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def negY: Triangle = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def negX: Triangle = ???

	override def prolign(matrix: ProlignMatrix): Triangle = ???

	override def reflect(lineLike: LineLike): Triangle = ???

	//override def reflect(line: LineSeg): Triangle = ???

	override def xyScale(xOperand: Double, yOperand: Double): Triangle = ???

	override def xShear(operand: Double): Triangle = ???
	override def yShear(operand: Double): Triangle = ???
	override def slateTo(newCen: Vec2): Triangle = ???
	override def xVertGet(index: Int): Double = ???

	override def yVertGet(index: Int): Double = ???

	def xCen: Double = (x1 + x2 + x3) / 3
	def yCen: Double = (y1 + y2 + y3) / 3
}

final case class TriangleClass(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double) extends Triangle //with AffinePreserve
{ type ThisT = TriangleClass
	override def v2: Vec2 = ???
	override def attribs: Arr[XANumeric] = ???
	override def fTrans(f: Vec2 => Vec2): TriangleClass = ???

	override def rotate(angle: Angle): TriangleClass = ???
	
}

object Triangle
{ //def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double): Triangle = ???
	//def apply(v0: Vec2, v1: Vec2, v2: Vec2): Triangle = ??? // new Triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
	def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolygonFill = PolygonFill(PolygonImp(p1, p2, p3), colour)
}