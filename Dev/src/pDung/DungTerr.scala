/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import geom._, Colour._

sealed trait DungTerr extends AnyRef with ShowSimple
{ override def typeStr: String = "DungTerr"
  def colour: Colour
}

object Open extends DungTerr
{ override def colour: Colour = Violet
  val str: String = "Open"
}

object Wall extends DungTerr
{ override def colour: Colour = fromInts(80, 80, 80)
  val str: String = "Wall"
}
