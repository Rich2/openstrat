/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._

/** A very Simple Tile for Simplicissima. */
case class UTile(x: Int, y: Int, oPlayer: Option[MPlayer] = None) extends Tile
{
  type FromT = Option[MPlayer]
  def fromT = oPlayer
  override def toString: String = UTile.persistImplicit.show(this)
}

object UTile
{
  implicit def make: (Int, Int, Option[MPlayer]) => UTile = UTile.apply

  implicit object SimpTileIsType extends IsType[UTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[UTile]
    override def asType(obj: AnyRef): UTile = obj.asInstanceOf[UTile]   
  }

  implicit val persistImplicit: Persist[UTile] = Persist3[Int, Int, Option[MPlayer], UTile]("UTile", u => (u.x, u.y, u.oPlayer), apply, Some(None))
}

case class UTileInter(x: Int, y: Int, oPlayer: Option[MPlayer], var potentialPlayers: List[Player] = Nil)

case class Move(mPlayer: MPlayer, cood: Cood)
{
  def sCood = mPlayer.cood
}