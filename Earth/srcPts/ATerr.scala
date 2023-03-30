/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import Colour._

trait ATerr extends Coloured

object Plain extends ATerr
{ override def colour: Colour = LightGreen
}

object Desert extends ATerr
{ override def colour: Colour = LemonChiffon
}

object Taiga extends ATerr
{ override def colour: Colour = DarkCyan
}

object Tundra extends ATerr
{ override def colour: Colour = LightCyan
}

object Hilly extends ATerr
{ override def colour: Colour = Chocolate
}

object Jungles extends ATerr
{ override def colour: Colour = DarkGreen
}

object Mtains extends ATerr
{ override def colour: Colour = Gray
}

object Seas extends ATerr
{ override def colour: Colour = DarkBlue
}

object Lake extends ATerr
{ override def colour: Colour = Blue
}

object Icy extends ATerr
{ override def colour: Colour = White
}