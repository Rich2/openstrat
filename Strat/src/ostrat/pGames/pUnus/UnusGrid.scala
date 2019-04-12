/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._ 

class UnusGrid (xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, val turn: Int) extends HexGridReg[UTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
  def getMoves: List[Move] = this.tilesMapOptionListAll(t => t.oPlayer.flatMap(p => p.move.map(m => Move(p, m))))
  
  def resolveTurn(moves: List[Move]): UnusGrid = 
  {    
    val medGrid = new Array[UTileInter](arrLen)
    this.foreachTilesXYAll{(x, y) =>
      val t = getTile(x, y)
      medGrid(xyToInd(x, y)) = new UTileInter(t.x, t.y, t.oPlayer)
    }
    moves.foreach{ m =>
      if (this.isTileCoodAdjTileCood(m.sCood, m.cood)) medGrid(coodToInd(m.cood)).potentialPlayers ::= m.mPlayer.player
    }
    val newGrid = new UnusGrid(xTileMin, xTileMax, yTileMin, yTileMax, turn)
    newGrid.setTilesAll(None)    
    this.foreachTileAll(tile => tile.oPlayer.foreach(mp => moves.find(_.mPlayer == mp) match
      {
        case None => newGrid.fSetTile(tile.cood, Some(mp))
     
        case Some(myMove) =>
        {
          val targ = medGrid(coodToInd(myMove.cood))          
          if  (targ.oPlayer.nonEmpty | targ.potentialPlayers.length > 1) newGrid.fSetTile(tile.cood, Some(mp))            
          else newGrid.fSetTile(myMove.cood, Some(MPlayer(mp.player, myMove.cood)))         
        }
      }))
    newGrid    
  }
}

object UnusGrid
{
  def start(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int): UnusGrid = new UnusGrid(xTileMin, xTileMax, yTileMin, yTileMax, 0)
}

