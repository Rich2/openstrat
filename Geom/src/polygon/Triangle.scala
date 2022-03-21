/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A mathematical triangle. The Triangle trait is implemented for its general case by [[Triangle.TriangleImp]]. */
trait Triangle extends Polygon3Plus
{ def unsafeArray: Array[Double]
	override def vertsNum: Int = 3

	def v0x: Double = unsafeArray(0)
	def v0y: Double = unsafeArray(1)
	override def v0: Pt2 = v0x pp v0y
	def v1x: Double = unsafeArray(2)
	def v1y: Double = unsafeArray(3)
	inline override def v1: Pt2 = v1x pp v1y
	def v2x: Double = unsafeArray(4)
	def v2y: Double = unsafeArray(5)
	override def v2: Pt2 = v2x pp v2y

	/** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def sd0CenX: Double = ???

	/** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
	 * be thought of as vertex 0.5. */
	override def sd0CenY: Double = ???

	override def sd0Cen: Pt2 = v2 midPt v0

	override def sd1CenX: Double = ???

	override def sd1CenY: Double = ???

	override def sd1Cen: Pt2 = v0 midPt v1


	/** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def sd2CenX: Double = ???

	/** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
	 * thought of as vertex 2.5. */
	override def sd2CenY: Double = ???

	override def sd2Cen: Pt2 = v1 midPt v2

	override def unsafeVert(index: Int): Pt2 = index match
	{	case 1 => v0
		case 2 => v1
		case 3 => v2
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}

	override def vertsArray: Array[Double] = Array(xCen, yCen, v0x, v0y, v1x, v1y, v2x, v2y)

	override def vertsArrayX: Array[Double] = Array(v0x, v1x, v2x)
	override def vertsArrayY: Array[Double] = Array(v0y, v1y, v2y)
	override def vertsForeach[U](f: Pt2 => U): Unit = { f(v0); f(v1); f(v2); () }
	override def vertsTailForeach[U](f: Pt2 => U): Unit = { f(v1); f(v2); () }
	override def vertPairsTailForeach[U](f: (Double, Double) => U): Unit = { f(v1x, v1y); f(v2x, v2y); () }

	/** 2D geometric transformation on a triangle returns a triangle. The method takes a function from a [[Pt2]] 2D Vector or point to a [[Pt2]]. */
	override def vertsTrans(f: Pt2 => Pt2): Triangle = Triangle(f(v0), f(v1), f(v2))

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
	{	case 1 => v0x
		case 2 => v1x
		case 3 => v2x
		case n => excep(n.str + " is out of range for a triangle.")
	}

	override def yVert(index: Int): Double = index match
	{	case 1 => v0y
		case 2 => v1y
		case 3 => v2y
		case n => excep(n.str + " is out of range for a triangle.")
	}

	def xCen: Double = (v0x + v1x + v2x) / 3
	def yCen: Double = (v0y + v1y + v2y) / 3

	override def fill(colour: Colour): TriangleFill = TriangleFill(this, colour)
	override def fillInt(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

object Triangle
{ def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleImp(x1, y1, x2, y2, x3, y3)
	def apply(v1: Pt2, v2: Pt2, v3: Pt2): Triangle = TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	final class TriangleImp(val unsafeArray: Array[Double]) extends Triangle with AffinePreserve
	{ override type ThisT = TriangleImp
		//override def v1: Pt2 = Pt2(v1x, v1y)
		override def vertsTrans(f: Pt2 => Pt2): TriangleImp = TriangleImp(f(v0), f(v1), f(v2))

		/** A method to perform all the [[AffinePreserve]] transformations with a function from PT2 => PT2. This is delegated to the VertsTrans method as
		 * a TriangleImp is specified by its vertices. This is not the case for all Polygons. */
		override def ptsTrans(f: Pt2 => Pt2): TriangleImp = vertsTrans(f)
	}

	object TriangleImp
	{
		def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriangleImp = {
		  val array = new Array[Double](6)
		  array(0) = v0x; array(1) = v0y; array(2) = v1x; array(3) = v1y; array(4) = v2x; array(5) = v2y
		  new TriangleImp(array)
	  }

		def apply(v0: Pt2, v1: Pt2, v2: Pt2): TriangleImp =  {
			val array = new Array[Double](6)
			array(0) = v0.x; array(1) = v0.y; array(2) = v1.x; array(3) = v1.y; array(4) = v2.x; array(5) = v2.y
			new TriangleImp(array)
		}
	}
}