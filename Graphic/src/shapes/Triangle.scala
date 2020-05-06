package ostrat
package geom
import Colour.Black

case class Triangle(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double)

object Triangle
{ def apply(v1: Vec2, v2: Vec2, v3: Vec3): Triangle = new Triangle(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)
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

