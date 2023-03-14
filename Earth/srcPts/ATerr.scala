/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import Colour._

trait ATerr extends Coloured {

}

object Plain extends ATerr
{ override def colour: Colour = LightGreen
}

object Deserts extends ATerr
{ override def colour: Colour = LemonChiffon
}

object Taigas extends ATerr
{ override def colour: Colour = DarkCyan
}

object Tundras extends ATerr
{ override def colour: Colour = LightCyan
}

object Hill extends ATerr
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

object Lakes extends ATerr
{ override def colour: Colour = DarkBlue
}

object Icy extends ATerr
{ override def colour: Colour = White
}