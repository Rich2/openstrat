/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package strat
import geom._

object Giza
{
  trait Pyramid
  { def base: PolygonM2 = ??? // Square.dist(baseLength)
    def baseLength: Metre
    def height: Metre
  }

  object Great extends Pyramid
  { val baseLength: Metre = 230.4.metre
    def height: Metre = 146.5.metre
  }

  object Khafre extends Pyramid
  { val baseLength: Metre = 215.5.metre
    def height: Metre = 136.4.metre
  }

  object MenKaure
  { val baseLength: Metre = 108.5.metre
    def height: Metre = 65.5.metre
  }
}