/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

class ZugGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[ZugTile, ZugSide](xTileMin, xTileMax, yTileMin, yTileMax)
{
 //var urn: List[ZTurn] = Nil
  def placeSquad(polity: Polity, x: Int, y: Int): Squad =
  { val sd = Squad(polity, x, y)     
    val tile = getTile(x, y)
    tile.lunits ::=  sd
    sd
  }
  
  def placeSquads(triples: (Polity, Int, Int) *): Unit = triples.foreach {tr =>
    val x = tr._2
    val y = tr._3
    val sd = Squad(tr._1, x, y)     
    val tile = getTile(x, y)
    tile.lunits ::=  sd 
  }
  
  val fTerrCost: (ZugTile, ZugTile) => OptInt = _.terr.cost + _.terr.cost
  def zPath(startCood: Cood, endCood: Cood): Option[List[Cood]] = findPath(startCood, endCood, fTerrCost)   
  fTilesSetAll(Plain)
  fSidesSetAll(false)   
}

