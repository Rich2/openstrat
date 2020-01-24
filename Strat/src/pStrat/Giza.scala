package ostrat
package strat
import geom._

object Giza
{
  trait Pyramid
  { def base: PolygonDist = Square.dist(baseLength)
    def baseLength: Dist
    def height: Dist
  }

  object Great extends Pyramid
  { val baseLength: Dist = 230.4.metres
    def height: Dist = 146.5.metres
  }

  object Khafre extends Pyramid
  { val baseLength: Dist = 215.5.metres
    def height: Dist = 136.4.metres
  }

  object MenKaure
  { val baseLength: Dist = 108.5.metres
    def height: Dist = 65.5.metres
  }
}