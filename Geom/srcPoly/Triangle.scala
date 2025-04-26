/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A mathematical triangle. The Triangle trait is implemented for its general case by [[Triangle.TriangleGen]]. */
trait Triangle extends Polygon3Plus
{ type ThisT <: Triangle
	override def typeStr: String = "Triangle"
	final override def numVerts: Int = 3
	final override def numElems: Int = 3
	final override def arrayUnsafe: Array[Double] = Array(v0x, v0y, v1x, v1y, v2x, v2y)
	final override def xVertsArray: Array[Double] = Array(v0x, v1x, v2x)
	final override def yVertsArray: Array[Double] = Array(v0y, v1y, v2y)

	/** 2D geometric transformation on a triangle returns a triangle. The method takes a function from a [[Pt2]] 2D Vector or point to a [[Pt2]]. */
	def vertsTrans(f: Pt2 => Pt2): Triangle = Triangle(f(v0), f(v1), f(v2))

	override def slate(operand: VecPt2): Triangle = vertsTrans(_.slate(operand))
	override def slate(xOperand: Double, yOperand: Double): Triangle = vertsTrans(_.slate(xOperand, yOperand))
	override def slateX(xOperand: Double): Triangle = vertsTrans(_.slateX(xOperand))
	override def slateY(yOperand: Double): Triangle = vertsTrans(_.slateY(yOperand))
	override def scale(operand: Double): Triangle = vertsTrans(_.scale(operand))
	override def negX: Triangle = vertsTrans(_.negX)
	override def negY: Triangle = vertsTrans(_.negY)
	override def rotate90: Triangle = vertsTrans(_.rotate90)
	override def rotate180: Triangle = vertsTrans(_.rotate180)
	override def rotate270: Triangle = vertsTrans(_.rotate180)
	override def prolign(matrix: AxlignMatrix): Triangle = vertsTrans(_.prolign(matrix))
	override def reflect(lineLike: LineLike): Triangle = vertsTrans(_.reflect(lineLike))
	override def rotate(rotation: AngleVec): Triangle = vertsTrans(_.rotate(rotation))
	override def scaleXY(xOperand: Double, yOperand: Double): Triangle = vertsTrans(_.xyScale(xOperand, yOperand))
	override def shearX(operand: Double): Triangle = vertsTrans(_.xShear(operand))
	override def shearY(operand: Double): Triangle = vertsTrans(_.yShear(operand))

	final override def v0: Pt2 = Pt2(v0x, v0y)
	def xCen: Double = (v0x + v1x + v2x) / 3
	def yCen: Double = (v0y + v1y + v2y) / 3

	final override def vLastX: Double = v2x
	final override def vLastY: Double = v2y
	final override def vLast: Pt2 = Pt2(v2x, v2y)

	final override def sides: LineSegArr = ??? // new LineSegArr(Array(v0x, v0y,))

	final override def setElemUnsafe(index: Int, newElem: Pt2): Unit = ???

	final override def elem(index: Int): Pt2 = index %% 3 match
	{ case 0 => v0
		case 1 => v1
		case _ => v2
	}
	
	final override def vertX(index: Int): Double = index %% 3 match
	{ case 0 => v0x
		case 1 => v1x
		case _ => v2x
	}

	final override def vertY(index: Int): Double = index %% 3 match
	{ case 0 => v0y
		case 1 => v1y
		case _ => v2y
	}

	override def fill(fillfacet: FillFacet): TriangleFill = TriangleFill(this, fillfacet)
	override def fillInt(intValue: Int): TriangleFill = TriangleFill(this, Colour(intValue))
}

/** Companion object for [[Triangle]] trait. Contains apply factory methods and [[TriangleGen]] implementation for non-specialised triangles. */
object Triangle
{ /** Factory apply method for [[Trinagle]]. There is an apply name overload that takes the [[Pt2]]s as parameters. */
	def apply(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Triangle = TriangleGen(x1, y1, x2, y2, x3, y3)

	/** Factory apply method for [[Trinagle]]. There is an apply name overload that takes [[Double]]s as parameters. */
	def apply(v1: Pt2, v2: Pt2, v3: Pt2): Triangle = TriangleGen(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

	/** Implementation for the general case of triangle */
	final class TriangleGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double) extends Triangle,
		AffinePreserve//, PolygonLikeDbl2[Pt2], Pt2SeqSpec
	{ override type ThisT = TriangleGen
		//override def fromArray(array: Array[Double]): TriangleGen = new TriangleGen(arrayUnsafe)

		override def vertsTrans(f: Pt2 => Pt2): TriangleGen = TriangleGen(f(v0), f(v1), f(v2))

		/** A method to perform all the [[AffinePreserve]] transformations with a function from PT2 => PT2. This is delegated to the VertsTrans method as a
		 * TriangleImp is specified by its vertices. This is not the case for all Polygons. */
		override def ptsTrans(f: Pt2 => Pt2): TriangleGen = vertsTrans(f)

		override def side0: LSeg = LSeg(v0x, v0y, vertX(1), vertY(1))
		override def sd0CenX: Double = v0x \/ vertX(1)
		override def sd0CenY: Double = v0y \/ vertY(1)
		override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
	}

	object TriangleGen
	{
		def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double): TriangleGen = new TriangleGen(v0x, v0y, v1x, v1y, v2x, v2y)
		/*{ val array = new Array[Double](6)
		  array(0) = v0x; array(1) = v0y; array(2) = v1x; array(3) = v1y; array(4) = v2x; array(5) = v2y
		  new TriangleGen(array)
	  }*/

		def apply(v0: Pt2, v1: Pt2, v2: Pt2): TriangleGen = new TriangleGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
	}
}