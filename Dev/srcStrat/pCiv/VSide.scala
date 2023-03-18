/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSide extends Coloured

object Straits extends VSide with Coloured
{ override def colour: Colour = DarkBlue
}

object River extends VSide
{ override def colour: Colour = Blue
}