package ostrat
package geom
import Colour.Black

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
}

case class TriangleClass(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double) extends Triangle
{
	override def v1: Vec2 = ???	

	/** Translate geometric transformation. */
	override def slate(offset: Vec2): TransElem = ???

	/** Translate geometric transformation. */
	override def slate(xOffset: Double, yOffset: Double): TransElem = ???

	/** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
	 * Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): TransElem = ???

	/** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
	override def mirrorYOffset(xOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
	override def mirrorXOffset(yOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorX: TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorY: TransElem = ???

	override def prolign(matrix: ProlignMatrix): TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians anticlockwise. */
	override def rotate90: TransElem = ???

	/** Rotates 180 degrees or Pi radians. */
	override def rotate180: TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians clockwise. */
	override def rotate270: TransElem = ???

	override def rotateRadians(radians: Double): TransElem = ???

	override def reflect(line: Line): TransElem = ???
	override def reflect(line: LineSeg): TransElem = ???

	override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

	override def shearX(operand: Double): TransElem = ???

	override def fill(colour: Colour): ShapeFill = ???

	override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

object Triangle
{ //def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double): Triangle = ???
	//def apply(v0: Vec2, v1: Vec2, v2: Vec2): Triangle = ??? // new Triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
	def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolygonFill = PolygonFill(PolygonClass(p1, p2, p3), colour)
}

trait IsosTriangle extends Triangle
{	def height: Double
	def x1: Double = ???
	def y1: Double = ???
	override def v1: Vec2 = ???
}

case class IsosTriangleClass(x0: Double, y0: Double, x2: Double, y2: Double, height: Double) extends IsosTriangle
{
	override def v1: Vec2 = ???

	override def foreach[U](f: Vec2 => U): Unit = ???

	/** Translate geometric transformation. */
	override def slate(offset: Vec2): TransElem = ???

	/** Translate geometric transformation. */
	override def slate(xOffset: Double, yOffset: Double): TransElem = ???

	/** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
	 * Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): TransElem = ???

	/** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
	override def mirrorYOffset(xOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
	override def mirrorXOffset(yOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorX: TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorY: TransElem = ???

	override def prolign(matrix: ProlignMatrix): TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians anticlockwise. */
	override def rotate90: TransElem = ???

	/** Rotates 180 degrees or Pi radians. */
	override def rotate180: TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians clockwise. */
	override def rotate270: TransElem = ???

	override def rotateRadians(radians: Double): TransElem = ???

	override def reflect(line: Line): TransElem = ???
	override def reflect(line: LineSeg): TransElem = ???

	override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

	override def shearX(operand: Double): TransElem = ???

	override def fill(colour: Colour): ShapeFill = ???

	override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

case class EquiTriangle(x0: Double, y0: Double, x2: Double, y2: Double) extends IsosTriangle
{	
	override def height: Double = ???
	override def foreach[U](f: Vec2 => U): Unit = ???

	/** Translate geometric transformation. */
	override def slate(offset: Vec2): TransElem = ???

	/** Translate geometric transformation. */
	override def slate(xOffset: Double, yOffset: Double): TransElem = ???

	/** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
	 * Squares. Use the xyScale method for differential scaling. */
	override def scale(operand: Double): TransElem = ???

	/** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
	override def mirrorYOffset(xOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
	override def mirrorXOffset(yOffset: Double): TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorX: TransElem = ???

	/** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
	 * in sub classes. */
	override def mirrorY: TransElem = ???

	override def prolign(matrix: ProlignMatrix): TransElem = ???

	override def shearX(operand: Double): TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians anticlockwise. */
	override def rotate90: TransElem = ???

	/** Rotates 180 degrees or Pi radians. */
	override def rotate180: TransElem = ???

	/** Rotates 90 degrees or Pi/2 radians clockwise. */
	override def rotate270: TransElem = ???

	override def rotateRadians(radians: Double): TransElem = ???

	override def reflect(line: Line): TransElem = ???

	override def reflect(line: LineSeg): TransElem = ???

	override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???

	override def fill(colour: Colour): ShapeFill = ???

	override def draw(lineWidth: Double, lineColour: Colour): ShapeDraw = ???
}

object Equilateral
{ def draw(sideLength: Double = 1, lineWidth: Double = 1, colour: Colour = Black): PolygonDraw =
  PolygonDraw(
	PolygonClass((0 vv sideLength * math.sqrt(3) / 3),
		(sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
		(-sideLength / 2 vv -sideLength * math.sqrt(3) / 6)
		), lineWidth, colour)

  def fill(sideLength: Double = 1, colour: Colour = Black): PolygonFill =
    PolygonFill(
 	    PolygonClass((0 vv sideLength * math.sqrt(3) / 3),
		    (sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
		    (-sideLength / 2 vv -sideLength * math.sqrt(3) / 6)
		   ), colour)
}