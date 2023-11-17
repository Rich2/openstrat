/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WTileHelper

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world
 * 320km hex 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it
 * and other land hexs.  */
trait WTile extends WTileHelper with Coloured with Tell
{ override def typeStr: String = "WTile"
  def isLand: Boolean
  def isWater: Boolean = !isLand
}

/** Companion object for WTile, contains short factory methods. */
object WTile
{
  implicit object TerainIsType extends IsType[WTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[WTile]
    override def asType(obj: AnyRef): WTile = obj.asInstanceOf[WTile]
  }

  /** This is not correct, but put in as temporary measure. */
  implicit val eqImplicit: EqT[WTile] = (a1, a2) => a1 == a2

  /** [[Show]] type class instance / evidence for [[WTile]]. */
  implicit val showTEv: ShowTellSum[WTile] = ShowTellSum[WTile]("WTile")

  /** [[Unshow]] type class instance / evidence for [[WTile]]. */
  implicit val unshowEv: UnshowSum[WTile] = UnshowSum[WTile]("WTile", Land.unshowEv, Water.unshowEv)
}

object WTiles
{ val land: Land = Land(Level, Temperate, CivMix)
  val hills: Land = Land(Hilly, Temperate, CivMix)
  val forest: Land = Land(Level, Temperate, Forest)
  val hillyForest: Land = Land(Hilly, Temperate, Forest)
  val desert: Land = Land(Level, Desert, LandFree)
  val hillyDesert: Land = Land(Hilly, Desert, LandFree)
  val jungle: Land = Land(Level, Tropical, Forest)
  val hillyJungle: Land = Land(Hilly, Tropical, Forest)
  val taiga: Land = Land(Level, Taiga, Forest)
  val hillyTaiga: Land = Land(Hilly, Taiga, LandFree)
  val tundra: Land = Land(Level, Tundra, LandFree)
  val hillyTundra: Land = Land(Hilly, Tundra, LandFree)
  val savannah: Land = Land(Level, Savannah, CivMix)
  val hillySavannah: Land = Land(Hilly, Savannah, CivMix)
  val sahel: Land = Land(Level, Sahel, CivMix)
  val hillySahel: Land = Land(Hilly, Sahel, CivMix)

  lazy val landPairs: ArrPairStr[Land] = ArrPairStr[Land](("land", land), ("hills", hills))

  val ice: Land = Land(Level, IceCap, LandFree)
  val sice: WTile = SeaIce
  val sea: Water = Sea
  val lake: Water = Lake
  val mtain: Land = Land(Mountains, Taiga, Forest)
}

/** A common trait for Ocean and Lake. */
trait Water extends WTile with WSideSome with TellSimple
{ override def isLand: Boolean = false
}

/** Companion object for [[Water]] trait contains type class instances for [[Show]], [[Unshow]] and [[EqT]]. */
object Water
{ /** [[Show]] type class instance / evidence for [[Water]]. */
  implicit val showEv: ShowTellSimple[Water] = ShowTellSimple[Water]("Water")

  /** [[Unshow]] type class instance / evidence for [[Water]]. */
  implicit val unshowEv: UnshowSingletons[Water] = UnshowSingletons[Water]("Water", Sea, Lake)

  /** [[EqT]] type class instance / evidence for [[Water]]. */
  implicit val eqEv: EqT[Water] = (w1, w2) => w1 == w2
}

/** Sea tile. This is an object as currently has no other variables such as depth, current or climate. */
case object Sea extends Water
{ override def str = "Sea"
  override def colour: Colour = DarkBlue
}

/** Lake tile. This is an object as currently has no other variables such as depth, current or climate. */
case object Lake extends Water
{ override def str = "Lake"
  override def colour: Colour = Blue
}

object TerrainNone extends WTile with TellSimple
{ override def str = "NoTerrain"
  override def colour = Gray
  override def isLand: Boolean = false
  //override def shortDescrip: String = "No terrain"
}

/** Winter sea ice. */
object WSeaIce extends Water with TellSimple
{ override def str = "WSeaIce"
  override def colour = LightSkyBlue.average(White).average(White)
  override def isLand: Boolean = false
  //override def shortDescrip: String = "WTile"
}