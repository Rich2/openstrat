/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._ 

/** A Player has a very simple token with a letter and colour for recognition." */
case class MPlayer(player: Player, x: Int, y: Int, move: Option[Cood] = None) extends WithColour
{  
  def cood: Cood = Cood(x, y)
  override def toString = player.toString
  override def colour = player.colour
}

case class Player(val char: Char, val colour: Colour)
{
  override def toString = "Player " + char
}

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

case class UTileInter(x: Int, y: Int, oPlayer: Option[MPlayer], var potentialPlayer: Boolean = false)

case class Move(mPlayer: MPlayer, cood: Cood)
{
  def sCood = mPlayer.cood
}

class UnusGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[UTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
  def getMoves: List[Move] = ???// this.tilesMapOptionListAll(t => t.oPlayer.flatMap(p => p.move.map(m => Move(p, m))))
  
  def resolveTurn(moves: List[Move]): UnusGrid = 
  {
    val medGrid = new Array[UTileInter](arrLen)
    this.foreachTilesXYAll{(x, y) =>
      val t = getTile(x, y)
      medGrid(xyToInd(x, y)) = new UTileInter(t.x, t.y, t.oPlayer)
    }
    moves.foreach{ m =>
      if (this.isTileCoodAdjTileCood(m.sCood, m.cood)) medGrid(coodToInd(m.cood)).potentialPlayer = true
    }
    val newGrid = new UnusGrid(xTileMin, xTileMax, yTileMin, yTileMax)
    newGrid.setTilesAll(None)
    this.foreachTileAll(tile => tile.oPlayer.foreach(mp => moves.find(_.mPlayer == mp) match
      {
        case None => newGrid.fSetTile(tile.cood, Some(mp))
     
        case Some(myMove) =>
        {
          val targ = medGrid(coodToInd(myMove.cood))
          if (targ.oPlayer.nonEmpty | targ.potentialPlayer) newGrid.fSetTile(tile.cood, Some(mp))
          else newGrid.fSetTile(myMove.cood, Some(MPlayer(mp.player, myMove.cood.x, myMove.cood.y))) 
        }
      }))
    newGrid    
  }
}

