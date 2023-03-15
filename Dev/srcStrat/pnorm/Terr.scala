/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex._, Colour._

trait Terr extends Coloured
{

}

object Sea extends Terr
{ override def colour: Colour = Blue
}

object Plain extends Terr
{ override def colour: Colour = LightGreen
}

object Island extends Terr with HIslandColoured[Sea.type, Plain.type]
{
  override def outer: Sea.type = Sea

  override def inner: Plain.type = Plain
}