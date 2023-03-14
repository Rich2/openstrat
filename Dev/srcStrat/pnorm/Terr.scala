/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import Colour._

trait Terr extends Coloured
{

}

object Sea extends Terr
{ override def colour: Colour = Blue
}

object Plain extends Terr
{ override def colour: Colour = LightGreen
}