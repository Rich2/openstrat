/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

class ZugGridOld(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends HexGridRegOld[ZugTileOld, ZugSideOld](xTileMin, xTileMax,
    yTileMin, yTileMax, turnNum)
{
  protected[this] var idCounter: Int = 100
  def getID: Int = {idCounter += 1; idCounter }
 //var urn: List[ZTurn] = Nil
  def placeSquad(polity: Polity, x: Int, y: Int): SquadOld =
  { val sd = SquadOld(polity, x, y, getID)
    val tile = getTile(x, y)
    setTile(x, y,tile.copy(lunits = sd +: tile.lunits))
    sd
  }
  
  def placeSquads(triples: (Polity, Int, Int) *): Unit = triples.foreach {tr =>
    val x = tr._2
    val y = tr._3
    val sd = SquadOld(tr._1, x, y, getID)
    val tile = getTile(x, y)
    setTile(x, y,tile.copy(lunits = sd +: tile.lunits))
  }
  
  val fTerrCost: (ZugTileOld, ZugTileOld) => OptInt = (o1, o2) => o1.terr.cost |+| o2.terr.cost
  def zPath(startCood: Cood, endCood: Cood): Option[List[Cood]] = findPath(startCood, endCood, fTerrCost)
  setTilesAll(Plain)
  setSidesAll(false)
  
  def minCopy: ZugGridOld = new ZugGridOld(xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
  def squads(player: Polity, actions: List[SquadTurn]): Unit = {}
}