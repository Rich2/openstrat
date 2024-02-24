/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Describes climate and biome. */
trait Climate extends TellSimple
{ override def typeStr: String = "Climate"
  def colour: Colour
}

object Climate
{ /** Implicit [[Show]] type class instance / evidence for [[Climate]]. */
  implicit lazy val showEv: ShowTell[Climate] = ShowTellSimple[Climate]("Climate")

  /** Implicit [[Unshow]] type class instance / evidence for [[Climate]]. */
  implicit lazy val unshowEv: UnshowSingletons[Climate] = UnshowSingletons[Climate]("Climate", Temperate, Desert, Sahel, Savannah, Boreal, Tundra, Tropical)

  given CanEqual[Climate, Climate] = CanEqual.derived
}

/** Trewartha Fi Ice cap classification. All year round snow. */
case object IceCap extends Climate
{ override def str = "IceCap"
  override def colour = White
}


/** Trewartha Ft Tundra climate classification. Too cold and dry for [[Forest]] */
case object Tundra extends Climate
{ override def str = "Tundra"
  override def colour = Plum.average(Thistle)
}

/** Trewartha E Boreal climate classification. Subartic or Boreal Taiga climate. Normally has [[Forest]]. */
case object Boreal extends Climate
{ override def str = "Boreal"
  override def colour = DarkCyan
}

/** Trewartha Dc Temperate Continental classification. No intense dry season. The coldest monthly mean temperature reaches below 0 °C. */
case object Continental extends Climate
{ def colour: Colour = LightGreen
  override def str = "Continental"
}

/** Trewartha Do Temperate oceanic climate classification. No intense dry season. The coldest monthly mean temperature reaches is not below 0 °C. */
case object Temperate extends Climate
{ def colour: Colour = LightGreen
  override def str = "Temperate"
}

/** Köppen BWk cold desert climate classification. Average annual temperature less than 18°C. */
case object DesertCold extends Climate
{ override def str = "DesertCold"
  override def colour = LemonChiffon
}

/** Köppen BWh hot desert climate classification. Average annual temperature above 18°C. */
case object Desert extends Climate
{ override def str = "Desert"
  override def colour = LemonChiffon
}

/** Semi Desert climate and biome. */
case object Sahel extends Climate
{ override def str = "Sahel"
  override def colour = LemonChiffon.average(YellowGreen)
}

/** Savannah, steppe and prairie biome. */
case object Savannah extends Climate
{ override def str = "Savannah"
  override def colour = YellowGreen
}

/** Tropical climate. High precipitation through out year. */
case object Tropical extends Climate
{ override def str = "Tropical"
  override def colour = YellowGreen
}