/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A mathematical triangle. The Triangle trait is implemented for its general case by [[Triangle.TriangleImp]]. */
trait Triangle extends Polygon3Plus
{ type ThisT <: Triangle
	override def typeStr: String = "Triangle"
	override def vertsNum: Int = 3

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

	def xCen: Double = (v0x + v1x + v2x) / 3
	def yCen: Double = (v0y + v1y + v2y) / 3

	override def fill(colour: Colour): TriangleFill = TriangleFill(this, colour)
	override def fillInt(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

/** Companion object for [[Triangle]] trait. Contains apply factory methods and [[TriangleImp]] implementation for non specialised triangles. */
object Triangle
{ def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleImp(x1, y1, x2, y2, x3, y3)
	def apply(v1: Pt2, v2: Pt2, v3: Pt2): Triangle = TriangleImp(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	final class TriangleImp(val unsafeArray: Array[Double]) extends Triangle with AffinePreserve
	{ override type ThisT = TriangleImp
		override def fromArray(array: Array[Double]): TriangleImp = new TriangleImp(unsafeArray)

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

		def apply(v0: Pt2, v1: Pt2, v2: Pt2): TriangleImp = new TriangleImp(Array[Double](v0.x, v0.y, v1.x, v1.y, v2.x, v2.y))
	}
}