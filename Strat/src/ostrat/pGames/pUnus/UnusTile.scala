/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._

/** A very Simple Tile for UnusGame. */
case class UTile(x: Int, y: Int, oPlayer: Option[MPlayer]) extends Tile

object UTile
{
  implicit def make: (Int, Int, Option[MPlayer]) => UTile = UTile.apply
  implicit object SimpTileIsType extends IsType[UTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[UTile]
    override def asType(obj: AnyRef): UTile = obj.asInstanceOf[UTile]   
  }
}

case class UTileInter(x: Int, y: Int, oPlayer: Option[MPlayer], var potentialPlayers: List[Player] = Nil)

case class Move(mPlayer: MPlayer, cood: Cood)
{
  def sCood = mPlayer.cood
}