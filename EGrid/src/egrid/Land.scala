/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land tile. Describes topology, climate-biome and land use. */
case class Land(elev: Lelev, climate: Climate, landUse: LandUse) extends WTile with Tell3[Lelev, Climate, LandUse]
{ override def typeStr: String = "Land"
  override def name1: String = "elev"
  override def name2: String = "climate"
  override def name3: String = "landUse"
  override def tell1: Lelev = elev
  override def tell2: Climate = climate
  override def tell3: LandUse = landUse
  override def show1: Show[Lelev] = Lelev.showEv
  override def show2: Show[Climate] = Climate.showEv
  override def show3: Show[LandUse] = LandUse.showEv
  override def opt1: Option[Lelev] = None
  override def opt2: Option[Climate] = None
  override def opt3: Option[LandUse] = Some(CivMix)
  override def tellDepth: Int = 2

  override def isLand: Boolean = true

  override def colour: Colour = elev match
  { case _: MountainsLike if climate == IceCap => Mountains.colour.average(White)
    case _: MountainsLike if climate == Boreal && landUse == Forest => Mountains.colour.average(Forest.taigaColour)
    case _: MountainsLike if climate == Tropical && landUse == Forest => Mountains.colour.average(Forest.jungleColour)
    case _: MountainsLike if landUse == Forest => Mountains.colour.average(Forest.colour)
    case Mountains => Mountains.colour
    case MountLakes => MountLakes.colour
    case _: HillyLike if climate == Boreal => Forest.taigaColour.aver2To1(Hilly.colour)
    case _: HillyLike if climate == Tropical => Forest.jungleColour.aver2To1(Chocolate)
    case _: HillyLike if landUse == Forest => Chocolate.average(Forest.colour)
    case Hilly | HillyLakes => Chocolate.average(climate.colour)
    case _: PlainLike if climate == Boreal && landUse == Forest => Forest.taigaColour
    case _: PlainLike if climate == Tropical && landUse == Forest => Forest.jungleColour
    case _: PlainLike if landUse == Forest => Forest.colour
    case PlainLakes => PlainLakes.colour.average(climate.colour)
    case PlainLakes => PlainLakes.colour
    case _ => climate.colour
  }
}

/** Companion object for [[Land]], contains factory apply method and type class instance for [[Show]], [[Unshow]] and [[EqT]], */
object Land
{ /** Factory apply method for [[Land]] objects. */
  def apply(elev: Lelev, climate: Climate, landUse: LandUse = CivMix): Land = new Land(elev, climate, landUse)

  /** Implicit [[Show]] and [[Unshow]] type class instances / evidence for [[Land]]. */
  implicit lazy val persistEv: Persist3Both[Lelev, Climate, LandUse, Land] =
    Persist3Both.shorts[Lelev, Climate, LandUse, Land]("Land", "elev", _.elev, "climate", _.climate, "use", _.landUse, WTiles.landWords, apply, Some(CivMix))

  /** Implicit [[EqT]] type class instance / evidence for [[Land]]. */
  implicit val eqEv: EqT[Land] = (a1, a2) => a1.elev == a2.elev && a1.climate == a1.climate && a1.landUse == a2.landUse
}