/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A mathematical triangle. The Triangle trait is implemented for its general case by [[Triangle.TriangleImp]]. */
trait Triangle extends Polygon3Plus
{	override def vertsNum: Int = 3
	override def v1: Pt2 = v1x pp v1y
	override def v3: Pt2 = x3 pp y3

	/** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def xSd1Cen: Double = ???

	/** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def ySd1Cen: Double = ???

	override def sd1Cen: Pt2 = v3 midPt v1

	override def xSd2Cen: Double = ???

	override def ySd2Cen: Double = ???

	override def sd2Cen: Pt2 = v1 midPt v2


	/** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def xSd3Cen: Double = ???

	/** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def ySd3Cen: Double = ???

	override def sd3Cen: Pt2 = v2 midPt v3

	override def vert(index: Int): Pt2 = index match
	{	case 1 => v1
		case 2 => v2
		case 3 => v3
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}

	override def vertsArray: Array[Double] = Array(xCen, yCen, v1x, v1y, x2, y2, x3, y3)

	override def vertsArrayX: Array[Double] = Array(v1x, x2, x3)
	override def vertsArrayY: Array[Double] = Array(v1y, y2, y3)
	override def foreachVert[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); () }
	override def foreachVertTail[U](f: Pt2 => U): Unit = { f(v2); f(v3); () }
	override def foreachPairTail[U](f: (Double, Double) => U): Unit = { f(x2, y2); f(x3, y3); () }

	/** 2D geometric transformation on a triangle returns a triangle. The method takes a function from a [[Pt2]] 2D Vector or point to a [[Pt2]]. */
	override def fTrans(f: Pt2 => Pt2): Triangle = Triangle(f(v1), f(v2), f(v3))

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slate(offset: Vec2Like): Triangle = fTrans(_.slate(offset))

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slateXY(xOffset: Double, yOffset: Double): Triangle = fTrans(_.addXY(xOffset, yOffset))

	/** Uniform scaling 2D geometric transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
	 *  Circles and Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): Triangle = fTrans(_.scale(operand))

	/** Mirror, reflection transformation across the Y axis on a triangle returns a triangle.The return type is narrowed in sub classes. */
	override def negY: Triangle = fTrans(_.negY)

	/** Mirror, reflection transformation across the X axis on a triangle, returns a triangle. The rturn type to be narrowed sub classes. */
	override def negX: Triangle = fTrans(_.negX)

	override def prolign(matrix: ProlignMatrix): Triangle = fTrans(_.prolign(matrix))

	override def reflect(lineLike: LineLike): Triangle = fTrans(_.reflect(lineLike))

	override def rotate90: Triangle = fTrans(_.rotate90)
	override def rotate180: Triangle = fTrans(_.rotate180)
	override def rotate270: Triangle = fTrans(_.rotate180)

	override def scaleXY(xOperand: Double, yOperand: Double): Triangle = fTrans(_.xyScale(xOperand, yOperand))

	override def shearX(operand: Double): Triangle = fTrans(_.xShear(operand))
	override def shearY(operand: Double): Triangle = fTrans(_.yShear(operand))


	override def xVert(index: Int): Double = index match
	{	case 1 => v1x
		case 2 => x2
		case 3 => x3
		case n => excep(n.str + " is out of range for a triangle.")
	}

	override def yVert(index: Int): Double = index match
	{	case 1 => v1y
		case 2 => y2
		case 3 => y3
		case n => excep(n.str + " is out of range for a triangle.")
	}

	def xCen: Double = (v1x + x2 + x3) / 3
	def yCen: Double = (v1y + y2 + y3) / 3

	override def fill(colour: Colour): TriangleFill = TriangleFill(this, colour)
	override def fillHex(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

object Triangle
{ def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleImp(x1, y1, x2, y2, x3, y3)
	def apply(v1: Pt2, v2: Pt2, v3: Pt2): Triangle = TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	final case class TriangleImp(v1x: Double, v1y: Double, x2: Double, y2: Double, x3: Double, y3: Double) extends Triangle with AffinePreserve
	{ type ThisT = TriangleImp
		override def v2: Pt2 = Pt2(x2, y2)

		override def fTrans(f: Pt2 => Pt2): TriangleImp = TriangleImp(f(v1), f(v2), f(v3))

	}

	object TriangleImp
	{		def apply(v1: Pt2, v2: Pt2, v3: Pt2): TriangleImp = new TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)
	}
}