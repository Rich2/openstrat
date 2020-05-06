package ostrat
package geom
import Colour.Black

object Triangle
{ def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolygonFill = PolygonFill(PolygonGen(p1, p2, p3), colour)
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

