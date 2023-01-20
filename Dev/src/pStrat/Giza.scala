/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package strat
import geom._

object Giza
{
  trait Pyramid
  { def base: PolygonM2 = ??? // Square.dist(baseLength)
    def baseLength: Length
    def height: Length
  }

  object Great extends Pyramid
  { val baseLength: Length = 230.4.metres
    def height: Length = 146.5.metres
  }

  object Khafre extends Pyramid
  { val baseLength: Length = 215.5.metres
    def height: Length = 136.4.metres
  }

  object MenKaure
  { val baseLength: Length = 108.5.metres
    def height: Length = 65.5.metres
  }
}