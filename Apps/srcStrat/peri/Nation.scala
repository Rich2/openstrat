/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import Colour._

class Nation(val colour: Colour) extends Coloured

object NoNation extends Nation(White)
object NRed extends Nation(Red)
object NViolet extends Nation(Violet)
case class Army(nation: Nation, num: Int) extends Coloured
{  override def colour: Colour = nation.colour
}