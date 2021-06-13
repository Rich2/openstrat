package ostrat
package strat
import geom._

object Giza
{
  trait Pyramid
  { def base: PolygonMs = ??? // Square.dist(baseLength)
    def baseLength: Metre
    def height: Metre
  }

  object Great extends Pyramid
  { val baseLength: Metre = 230.4.metres
    def height: Metre = 146.5.metres
  }

  object Khafre extends Pyramid
  { val baseLength: Metre = 215.5.metres
    def height: Metre = 136.4.metres
  }

  object MenKaure
  { val baseLength: Metre = 108.5.metres
    def height: Metre = 65.5.metres
  }
}