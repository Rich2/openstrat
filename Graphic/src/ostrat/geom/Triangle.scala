package ostrat
package geom
import Colour.Black

object Triangle
{
  def fill(p1: Vec2, p2: Vec2, p3: Vec2, colour: Colour = Black): PolyFill = { debb(); PolyFill(Polygon(p1, p2, p3), colour) }
}

object Equilateral
{

}


