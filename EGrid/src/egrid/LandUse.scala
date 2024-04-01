/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Currently this describes forrestion and land use. */
trait LandUse extends TellSimple
{ override def typeStr: String = "LandUse"
}

object LandUse
{ /** Implicit [[Show]] type class instance / evidence for [[LandUse]]. */
  implicit lazy val showEv: ShowTell[LandUse] = ShowTellSimple[LandUse]("LandUse")

  /** Implicit [[Unshow]] type class instance / evidence for [[LandUse]]. */
  implicit lazy val unshowEv: UnshowSingletons[LandUse] = UnshowSingletons[LandUse]("LandUse", CivMix, Forest, LandFree)

  given CanEqual[LandUse, LandUse] = CanEqual.derived
}

case object CivMix extends LandUse
{ override def str: String = "MixedUse"
}

case object LandFree extends LandUse
{
  override def str: String = "No Civilisation"
}

/** forest that is not taiga or rain forest. */
case object Forest extends LandUse
{ def str = "Forest"
  def colour: Colour = Colour.ForestGreen
  def taigaColour: Colour = DarkCyan
  def jungleColour: Colour = DarkGreen
}