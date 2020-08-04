/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pZug
import Colour._

trait ZugTerr
{ def colour: Colour
  def cost: OptInt = SomeInt(1)
  def conceal: Boolean = false
}

case object Plain extends ZugTerr
{ override def colour = LightGreen
}

case object WheatField extends ZugTerr
{ override def colour = Wheat
}

case object Hill extends ZugTerr
{ override def colour = Brown
}

trait Building extends ZugTerr { override def conceal = true }

case object StoneBuilding extends Building
{ override def colour = Gray
  override def cost: OptInt = SomeInt(3)
}

case object WoodBuilding extends Building
{ override def colour = Brown
}

case object Lake extends ZugTerr
{ override def colour = Blue
  override def cost: OptInt = NoInt
}