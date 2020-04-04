/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import Colour._, pGrid._

sealed trait DungTerr extends AnyRef with PersistSingleton
{ def colour: Colour
}

object DungTerr { implicit val toDTile: (Int, Int, DungTerr) => DTileOld = DTileOld.apply }

object Open extends DungTerr
{ override def colour: Colour = Violet
  val str: String = "Open"
}

object Wall extends DungTerr
{ override def colour: Colour = fromInts(80, 80, 80)
  val str: String = "Wall"
}
