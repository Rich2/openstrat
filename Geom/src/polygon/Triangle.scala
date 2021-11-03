/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A mathematical triangle. The Triangle trait is implemented for its general case by [[Triangle.TriangleImp]]. */
trait Triangle extends Polygon3Plus
{	override def vertsNum: Int = 3
	override def v1: Pt2 = v1x pp v1y
	override def v3: Pt2 = v3x pp v3y

	/** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def sd1CenX: Double = ???

	/** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def sd1CenY: Double = ???

	override def sd1Cen: Pt2 = v3 midPt v1

	override def sd2CenX: Double = ???

	override def sd2CenY: Double = ???

	override def sd2Cen: Pt2 = v1 midPt v2


	/** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def sd3CenX: Double = ???

	/** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def sd3CenY: Double = ???

	override def sd3Cen: Pt2 = v2 midPt v3

	override def vert(index: Int): Pt2 = index match
	{	case 1 => v1
		case 2 => v2
		case 3 => v3
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}

	override def vertsArray: Array[Double] = Array(xCen, yCen, v1x, v1y, v2x, v2y, v3x, v3y)

	override def vertsArrayX: Array[Double] = Array(v1x, v2x, v3x)
	override def vertsArrayY: Array[Double] = Array(v1y, v2y, v3y)
	override def vertsForeach[U](f: Pt2 => U): Unit = { f(v1); f(v2); f(v3); () }
	override def foreachVertTail[U](f: Pt2 => U): Unit = { f(v2); f(v3); () }
	override def foreachVertPairTail[U](f: (Double, Double) => U): Unit = { f(v2x, v2y); f(v3x, v3y); () }

	/** 2D geometric transformation on a triangle returns a triangle. The method takes a function from a [[Pt2]] 2D Vector or point to a [[Pt2]]. */
	override def vertsTrans(f: Pt2 => Pt2): Triangle = Triangle(f(v1), f(v2), f(v3))

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slate(offset: Vec2Like): Triangle = vertsTrans(_.slate(offset))

	/** Translate 2D geometric transformation on a Triangle returns a Triangle. The return type is narrowed in sub classes. */
	override def slateXY(xDelta: Double, yDelta: Double): Triangle = vertsTrans(_.addXY(xDelta, yDelta))

	/** Uniform scaling 2D geometric transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
	 *  Circles and Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): Triangle = vertsTrans(_.scale(operand))

	/** Mirror, reflection transformation across the Y axis on a triangle returns a triangle.The return type is narrowed in sub classes. */
	override def negY: Triangle = vertsTrans(_.negY)

	/** Mirror, reflection transformation across the X axis on a triangle, returns a triangle. The rturn type to be narrowed sub classes. */
	override def negX: Triangle = vertsTrans(_.negX)

	override def prolign(matrix: ProlignMatrix): Triangle = vertsTrans(_.prolign(matrix))

	override def reflect(lineLike: LineLike): Triangle = vertsTrans(_.reflect(lineLike))

	override def rotate90: Triangle = vertsTrans(_.rotate90)
	override def rotate180: Triangle = vertsTrans(_.rotate180)
	override def rotate270: Triangle = vertsTrans(_.rotate180)

	override def scaleXY(xOperand: Double, yOperand: Double): Triangle = vertsTrans(_.xyScale(xOperand, yOperand))

	override def shearX(operand: Double): Triangle = vertsTrans(_.xShear(operand))
	override def shearY(operand: Double): Triangle = vertsTrans(_.yShear(operand))


	override def xVert(index: Int): Double = index match
	{	case 1 => v1x
		case 2 => v2x
		case 3 => v3x
		case n => excep(n.str + " is out of range for a triangle.")
	}

	override def yVert(index: Int): Double = index match
	{	case 1 => v1y
		case 2 => v2y
		case 3 => v3y
		case n => excep(n.str + " is out of range for a triangle.")
	}

	def xCen: Double = (v1x + v2x + v3x) / 3
	def yCen: Double = (v1y + v2y + v3y) / 3

	override def fill(colour: Colour): TriangleFill = TriangleFill(this, colour)
	override def fillInt(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

object Triangle
{ def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleImp(x1, y1, x2, y2, x3, y3)
	def apply(v1: Pt2, v2: Pt2, v3: Pt2): Triangle = TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	final case class TriangleImp(v1x: Double, v1y: Double, v2x: Double, v2y: Double, v3x: Double, v3y: Double) extends Triangle with AffinePreserve
	{ override type ThisT = TriangleImp
		override def v2: Pt2 = Pt2(v2x, v2y)
		override def vertsTrans(f: Pt2 => Pt2): TriangleImp = TriangleImp(f(v1), f(v2), f(v3))

		/** A method to perform all the [[AffinePreserve]] transformations with a function from PT2 => PT2. This is delegated to the VertsTrans method as
		 * a TriangleImp is specified by its vertices. This is not the case for all Polygons. */
		override def ptsTrans(f: Pt2 => Pt2): TriangleImp = vertsTrans(f)
	}

	object TriangleImp
	{		def apply(v1: Pt2, v2: Pt2, v3: Pt2): TriangleImp = new TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)
	}
}