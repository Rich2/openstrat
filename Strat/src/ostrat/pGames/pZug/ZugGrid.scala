/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

class ZugGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[ZugTile, ZugSide](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
  protected[this] var idCounter: Int = 100
  def getID: Int = {idCounter += 1; idCounter }
 //var urn: List[ZTurn] = Nil
  def placeSquad(polity: Polity, x: Int, y: Int): Squad =
  { val sd = Squad(polity, x, y, getID)     
    val tile = getTile(x, y)
    setTile(x, y,tile.copy(lunits = sd :: tile.lunits))
    sd
  }
  
  def placeSquads(triples: (Polity, Int, Int) *): Unit = triples.foreach {tr =>
    val x = tr._2
    val y = tr._3
    val sd = Squad(tr._1, x, y, getID)     
    val tile = getTile(x, y)
    setTile(x, y,tile.copy(lunits = sd :: tile.lunits))
  }
  
  val fTerrCost: (ZugTile, ZugTile) => OptInt = _.terr.cost + _.terr.cost
  def zPath(startCood: Cood, endCood: Cood): Option[List[Cood]] = findPath(startCood, endCood, fTerrCost)   
  setAllTiles(Plain)
  setAllSides(false)
  
  def minCopy: ZugGrid = new ZugGrid(xTileMin, xTileMax, yTileMin, yTileMax)
  def squads(player: Polity, actions: List[SquadTurn]): Unit = {}
  
}

