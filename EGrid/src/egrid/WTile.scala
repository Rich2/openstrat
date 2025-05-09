/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour.*

trait WTileHelper

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world 320km hex
 * 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it and other land
 * hexs. */
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
  implicit val showTEv: ShowTell[WTile] = ShowTell[WTile]("WTile")

  /** [[Unshow]] type class instance / evidence for [[WTile]]. */
  implicit val unshowEv: UnshowSum[WTile] = UnshowSum[WTile]("WTile", Land.persistEv, Water.unshowEv)

  implicit val eqEV: EqT[WTile] = (a1, a2) => if(a1 == a2) true else (a1, a2) match{
    case (l1: Land, l2: Land) => Land.eqEv.eqT(l1, l2)
    case _ => false
  }
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

object TerrainNone extends WTile, TellSimple
{ override def str = "NoTerrain"
  override def colour = Gray
  override def isLand: Boolean = false
}

/** Winter sea ice. */
object SeaIceWinter extends Water, TellSimple
{ override def str = "SeaIceWinter"
  override def colour = LightSkyBlue
}

/** All year round sea ice sheet. */
case object SeaIcePerm extends WTile, Water, TellSimple
{ override def str = "SeaIce"
  override def colour = White
  override def isLand: Boolean = false
}

/** Winter lake ice. */
object LakeIceWinter extends Water, TellSimple
{ override def str = "SeaIceWinter"
  override def colour = Lake.colour
}