/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package strat
import geom._

object Giza
{
  trait Pyramid
  { def base: PolygonM2 = ??? // Square.dist(baseLength)
    def baseLength: Metres
    def height: Metres
  }

  object Great extends Pyramid
  { val baseLength: Metres = 230.4.metres
    def height: Metres = 146.5.metres
  }

  object Khafre extends Pyramid
  { val baseLength: Metres = 215.5.metres
    def height: Metres = 136.4.metres
  }

  object MenKaure
  { val baseLength: Metres = 108.5.metres
    def height: Metres = 65.5.metres
  }
}