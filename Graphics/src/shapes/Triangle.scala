/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

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

	override def ptsArray: Array[Double] = Array(xCen, yCen, x1, y1, x2, y2, x3, y3)

	override def xVertsArray: Array[Double] = Array(x1, x2, x3)
	override def yVertsArray: Array[Double] = Array(y1, y2, y3)
	override def foreachVert(f: Vec2 => Unit): Unit = { f(v1); f(v2); f(v3); () }
	override def foreachVertTail[U](f: Vec2 => U): Unit = { f(v2); f(v3); () }
	override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x2, y2); f(x3, y3); () }

	/** 2D geometric transformation on a triangle returns a triangle. The method takes a function from a [[Vec2]] 2D Vector or point to a [[Vec2]]. */
	override def fTrans(f: Vec2 => Vec2): Triangle = Triangle(f(v1), f(v2), f(v3))

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slate(offset: Vec2): Triangle = fTrans(_ + offset)

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slate(xOffset: Double, yOffset: Double): Triangle = fTrans(_.addXY(xOffset, yOffset))

	/** Uniform scaling 2D geometric transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
	 *  Circles and Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): Triangle = fTrans(_ * operand)

	/** Mirror, reflection transformation across the Y axis on a triangle returns a triangle.The return type is narrowed in sub classes. */
	override def negY: Triangle = fTrans(_.negY)

	/** Mirror, reflection transformation across the X axis on a triangle, returns a triangle. The rturn type to be narrowed sub classes. */
	override def negX: Triangle = fTrans(_.negX)

	override def prolign(matrix: ProlignMatrix): Triangle = fTrans(_.prolign(matrix))

	override def reflect(lineLike: LineLike): Triangle = fTrans(_.reflect(lineLike))

	override def xyScale(xOperand: Double, yOperand: Double): Triangle = fTrans(_.xyScale(xOperand, yOperand))

	override def xShear(operand: Double): Triangle = fTrans(_.xShear(operand))
	override def yShear(operand: Double): Triangle = fTrans(_.yShear(operand))
	override def slateTo(newCen: Vec2): Triangle = fTrans(_ + newCen - cen)

	override def xVertGet(index: Int): Double = index match
	{	case 1 => x1
		case 2 => x2
		case 3 => x3
		case n => excep(n.str + " is out of range for a triangle.")
	}

	override def yVertGet(index: Int): Double = index match
	{	case 1 => y1
		case 2 => y2
		case 3 => y3
		case n => excep(n.str + " is out of range for a triangle.")
	}

	def xCen: Double = (x1 + x2 + x3) / 3
	def yCen: Double = (y1 + y2 + y3) / 3

	override def fill(colour: Colour): TriangleFill = TriangleFill(this, colour)
	override def fillHex(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

object Triangle
{ def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleImp(x1, y1, x2, y2, x3, y3)
	def apply(v1: Vec2, v2: Vec2, v3: Vec2): Triangle = TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	final case class TriangleImp(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double) extends Triangle with AffinePreserve
	{ type ThisT = TriangleImp
		override def v2: Vec2 = Vec2(x2, y2)

		override def fTrans(f: Vec2 => Vec2): TriangleImp = TriangleImp(f(v1), f(v2), f(v3))

	}

	object TriangleImp
	{		def apply(v1: Vec2, v2: Vec2, v3: Vec2): TriangleImp = new TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)
	}
}