/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnorm
import prid.phex._, Colour._

trait Tile extends Coloured
{

}

object Island extends Tile with HInner6
{ override def colour: Colour = LightGreen
}

object Sea extends Tile
{ override def colour: Colour = Blue
}

object Plain extends Tile
{ override def colour: Colour = LightGreen
}

case class Head1Land(outSideNum: Int) extends Tile with HInner5
{
  override def colour: Colour = Pink
}