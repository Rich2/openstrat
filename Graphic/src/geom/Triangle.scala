package ostrat
package geom
import Colour.Black

object Triangle
{ def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolyFill = PolyFill(Polygon(p1, p2, p3), colour)
}

object Equilateral
{ def draw(sideLength: Double = 1, lineWidth: Double = 1, colour: Colour = Black): PolyDraw =
 PolyDraw(
 	Polygon((0 vv sideLength * math.sqrt(3) / 3),
					(sideLength / 2 vv -sideLength * math.sqrt(3) / 6),
					(-sideLength / 2 vv -sideLength * math.sqrt(3) / 6)
				), lineWidth, colour)
}

