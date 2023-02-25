/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless

case class Army(nation: Nation, num: Int) extends Coloured
{
  override def colour: Colour = nation.colour
}
