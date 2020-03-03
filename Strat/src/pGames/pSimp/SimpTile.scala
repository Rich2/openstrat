/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._

/** A very Simple Tile for Simplicissima. */
case class UTileOld(x: Int, y: Int, oPlayer: Option[MPlayer] = None) extends TileOld
{
  type FromT = Option[MPlayer]
  def fromT = oPlayer
  override def toString: String = UTileOld.persistImplicit.show(this)
}

object UTileOld
{
  implicit def make: (Int, Int, Option[MPlayer]) => UTileOld = UTileOld.apply

  implicit object SimpTileIsType extends IsType[UTileOld]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[UTileOld]
    override def asType(obj: AnyRef): UTileOld = obj.asInstanceOf[UTileOld]
  }

  implicit val persistImplicit: Persist[UTileOld] = Persist3[Int, Int, Option[MPlayer], UTileOld]("UTile", "x", _.x, "y", _.y,
    "oPlayer", _.oPlayer, apply, Some(None))
}

case class UTileInter(x: Int, y: Int, oPlayer: Option[MPlayer], var potentialPlayers: List[Player] = Nil)

case class Move(mPlayer: MPlayer, cood: Cood)
{
  def sCood = mPlayer.cood
}