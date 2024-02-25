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

  /** [[Show]] type class instance / evidence for [[WTile]]. */
  implicit val showTEv: ShowTellSum[WTile] = ShowTellSum[WTile]("WTile")

  /** [[Unshow]] type class instance / evidence for [[WTile]]. */
  implicit val unshowEv: UnshowSum[WTile] = UnshowSum[WTile]("WTile", Land.persistEv, Water.unshowEv)

  implicit val eqEV: EqT[WTile] = (a1, a2) => if(a1 == a2) true else (a1, a2) match{
    case (l1: Land, l2: Land) => Land.eqEv.eqT(l1, l2)
    case _ => false
  }
}

/** Object to provide short names for various [[WTile]] values. */
object WTiles
{
  val lakesTundra: Land = Land(PlainLakes, Tundra, LandFree)
  val lakesTaiga: Land = Land(PlainLakes, Boreal, Forest)

  /** Shorthand for [[IceCap]]. */
  val ice: Land = Land(Plain, IceCap, LandFree)

  val tundra: Land = Land(Plain, Tundra, LandFree)
  val taiga: Land = Land(Plain, Boreal, Forest)
  val plain: Land = Land(Plain, Temperate, CivMix)
  val forest: Land = Land(Plain, Temperate, Forest)
  val savannah: Land = Land(Plain, Savannah, CivMix)
  val sahel: Land = Land(Plain, Sahel, CivMix)
  val desert: Land = Land(Plain, Desert, LandFree)
  val jungle: Land = Land(Plain, Tropical, Forest)

  val hillyTundra: Land = Land(Hilly, Tundra, LandFree)
  val hillyTaiga: Land = Land(Hilly, Boreal, Forest)
  val hilly: Land = Land(Hilly, Temperate, CivMix)
  val hillyForest: Land = Land(Hilly, Temperate, Forest)
  val hillyDesert: Land = Land(Hilly, Desert, LandFree)
  val hillySavannah: Land = Land(Hilly, Savannah, CivMix)
  val hillySahel: Land = Land(Hilly, Sahel, CivMix)
  val hillySubForest: Land = Land(Hilly, Subtropical, Forest)
  val hillyJungle: Land = Land(Hilly, Tropical, Forest)

  val hillyLakesTaiga: Land = Land(HillyLakes, Boreal, Forest)

  /** deprecated use one of the specialised shorthands instead.  */
  val mtainOld: Land = Land(Mountains, Boreal, Forest)

  val mtainIce: Land = Land(Mountains, IceCap, LandFree)
  val mtainTundra: Land = Land(Mountains, Tundra, LandFree)
  val mtainTaiga: Land = Land(Mountains, Boreal, Forest)
  val mtainSub: Land = Land(Mountains, Subtropical, Forest)
  val mtainDesert: Land = Land(Mountains, Desert, LandFree)
  val mtainJungle: Land = Land(Mountains, Tropical, Forest)
  val mtainLakesTaiga: Land = Land(MountLakes, Boreal, Forest)

  /** Sequence of short hand words for [[Land]]. */
  lazy val landWordTuples: Seq[(String, Land)] = identStrs[Land](lakesTundra, lakesTaiga,
    ice, tundra, taiga, plain, forest, savannah, sahel, desert, jungle,
    hillyTundra, hillyTaiga, hilly, hillyForest, hillySavannah, hillySahel, hillyDesert, hillyJungle, hillyLakesTaiga,
    mtainOld, mtainIce, mtainTundra, mtainTaiga, mtainSub, mtainDesert, mtainJungle)

  /** Sequence of short hand words for [[Land]]. */
  lazy val landWords: ArrPairStr[Land] = landWordTuples.mapPairArr(_._1, _._2)

  /** Short hand for [[SeaIceWinter]]. */
  val wice: Water = SeaIceWinter

  val sea: Water = Sea
  val lake: Water = Lake

  /** Sequence of short hand words for [[Water]]. */
  lazy val waterWords: ArrPairStr[Water] = ArrPairStr[Water](("sea", sea), ("lake", lake), ("wice,", wice))
}

/** A common trait for Ocean and Lake. */
trait Water extends WTile with WSepSome
{ override def isLand: Boolean = false
}

/** Companion object for [[Water]] trait contains type class instances for [[Show]], [[Unshow]] and [[EqT]]. */
object Water
{ /** [[Show]] type class instance / evidence for [[Water]]. */
  implicit val showEv: ShowTellSimple[Water] = ShowTellSimple[Water]("Water")

  /** [[Unshow]] type class instance / evidence for [[Water]]. */
  implicit val unshowEv: UnshowSingletons[Water] = UnshowSingletons.shorts[Water]("Water", WTiles.waterWords, Sea, Lake)

  /** [[EqT]] type class instance / evidence for [[Water]]. */
  implicit val eqEv: EqT[Water] = (w1, w2) => w1 == w2
}

/** Sea tile. This is an object as currently has no other variables such as depth, current or climate. */
case object Sea extends Water
{ override def str: String = "Sea"
  override def colour: Colour = DarkBlue
}

/** Lake tile. This is an object as currently has no other variables such as depth, current or climate. */
case object Lake extends Water
{ override def str: String = "Lake"
  override def colour: Colour = Blue
}

object TerrainNone extends WTile with TellSimple
{ override def str = "NoTerrain"
  override def colour = Gray
  override def isLand: Boolean = false
}

/** Winter sea ice. */
object SeaIceWinter extends Water with TellSimple
{ override def str = "SeaIceWinter"
  override def colour = LightSkyBlue//.average(White).average(White)
}

/** All year round sea ice sheet. */
case object SeaIcePerm extends WTile with TellSimple
{ override def str = "SeaIce"
  override def colour = White
  override def isLand: Boolean = false
}