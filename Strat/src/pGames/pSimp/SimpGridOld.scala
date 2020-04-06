/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pSimp
import pGrid._ 

class SimpGridOld(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends HexGridRegOld[UTileOld, SideOldBare](xTileMin, xTileMax,
    yTileMin, yTileMax, turnNum)
{
  def getMoves: ArrOld[Move] = tilesMapOptionAll(t => t.oPlayer.flatMap(p => p.move.map(m => Move(p, m))))
  def baseCopy: SimpGridOld = new SimpGridOld(xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
  
  def copy: SimpGridOld =
  {
    val ng = baseCopy
    foreachTilesXYAll{(x, y) => ng.setTile(x, y, getTile(x, y))}
    ng
  }
  
  def resolveTurn(moves: ArrOld[Move]): SimpGridOld =
  {    
    val medGrid = new Array[UTileInter](arrLen)
    foreachTilesXYAll{(x, y) =>
      val t = getTile(x, y)
      medGrid(xyToInd(x, y)) = new UTileInter(t.x, t.y, t.oPlayer)
    }
    moves.foreach { m =>
      if (this.isTileCoodAdjTileCood(m.sCood, m.cood)) medGrid(coodToInd(m.cood)).potentialPlayers ::= m.mPlayer.player
    }
    val newGrid = new SimpGridOld(xTileMin, xTileMax, yTileMin, yTileMax, turnNum + 1)
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
  //def toTuple5: (Int, Int, Int, Int, Arr[TileRow[UTile#FromT]]) = (xTileMin, xTileMax, yTileMin, yTileMax, tilesToMultiAll)
}

object SimpGridOld {
  def start(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int): SimpGridOld = new SimpGridOld(xTileMin, xTileMax, yTileMin, yTileMax, 0)

  implicit val showUnusGrid: Show[SimpGridOld] =
    Show5[Int, Int, Int, Int, ArrOld[TileRowOld[UTileOld#FromT]], SimpGridOld]("SimpGrid", "xTilemin", _.xTileMin, "xTilemax", _.xTileMax, "yTileMin", _.yTileMin,
    "yTileMax", _.yTileMax, "tilesToMultiAll", ??? )
}

