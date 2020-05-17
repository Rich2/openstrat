package ostrat
package geom
import Colour.Black

trait Poly3Plus extends Any
{
	/*def x0: Double
	def y0: Double
	def v0: Vec2 = x0 vv y0
	def x1: Double
	def y1: Double
	def v1: Vec2 = x1 vv y1
	def x2: Double
	def y2: Double
	def v2: Vec2 = x2 vv y2*/
}

final case class Triangle(val array: Array[Double]) extends Polygon with Poly3Plus
{ type AlignT = Triangle
	type ThisT = Triangle
	def typeStr = "Triangle"
	//override def length: Int = 3

	//override def fTrans(f: Vec2 => Vec2): AlignT = Triangle(f(v0), f(v1), f(v2))
  /*override def apply(index: Int): Vec2 = index match
	{	case 0 => v0
		case 1 => v1
		case 2 => v2
		case n => excep("index: " + n.toString + "out of range. There are only 3 vertices in a triangle.")
	}*/
	def unsafeFromArray(array: Array[Double]): Triangle = new Triangle(array)
	//override def foreach[U](f: Vec2 => U): Unit = { f(v0); f(v1); f(v2)	}
	def fTrans(f: Vec2 => Vec2): Triangle = ??? //new Triangle(arrTrans(f))
	override def apply(index: Int): Vec2 = ???

	override def foreach[U](f: Vec2 => U): Unit = ???

	override def length: Int = ???
}

object Triangle
{ def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double): Triangle = ???
	def apply(v0: Vec2, v1: Vec2, v2: Vec2): Triangle = ??? // new Triangle(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y)
	def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolygonFill = PolygonFill(PolygonGen(p1, p2, p3), colour)
}

object Equilateral
{ def draw(sideLength: Double = 1, lineWidth: Double = 1, colour: Colour = Black): PolygonDraw =
  PolygonDraw(
	PolygonGen((0 vv sideLength * math.sqrt(3) / 3),
		(sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
		(-sideLength / 2 vv -sideLength * math.sqrt(3) / 6)
		), lineWidth, colour)

  def fill(sideLength: Double = 1, colour: Colour = Black): PolygonFill =
    PolygonFill(
 	    PolygonGen((0 vv sideLength * math.sqrt(3) / 3),
		    (sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
		    (-sideLength / 2 vv -sideLength * math.sqrt(3) / 6)
		   ), colour)
}

