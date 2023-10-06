/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import Colour._

trait ZugTerrHelper

/** ZugFuhrer hex terrain. Currently a simple form of terrain. */
trait ZugTerr extends ZugTerrHelper with TellSimple with Coloured
{
  override def typeStr: String = "ZugTerr"
  def colour: Colour
  def cost: OptInt = SomeInt(1)
  def conceal: Boolean = false
}

case object Plain extends ZugTerr
{ override def str: String = "Plain"
  override def colour = LightGreen
}

case object WheatField extends ZugTerr
{ override def str: String = "WheatField"
  override def colour = Wheat
}

case object Hill extends ZugTerr
{ override def str: String = "Hill"
  override def colour = Brown
}

trait Building extends ZugTerr
{ override def conceal = true
}

case object StoneBuilding extends Building
{ override def str: String = "Stone"
  override def colour = Gray
  override def cost: OptInt = SomeInt(3)
}

case object WoodBuilding extends Building
{ override def str: String = "Wooden"
  override def colour = Brown
}

case object Lake extends ZugTerr
{ override def str: String = "Lake"
  override def colour = Blue
  override def cost: OptInt = NoInt
}