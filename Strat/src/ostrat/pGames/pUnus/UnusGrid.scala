/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pUnus
import pGrid._ 

/** A Player has a very simple token with a letter and colour for recognition." */
class Player(val char: Char, val colour: Colour) extends WithColour
class PossiblePlayer(val char: Char, val colour: Colour)

case class UTile(x: Int, y: Int, mem: Option[Player]) extends Tile

object UTile
{
  implicit def make: (Int, Int, Option[Player]) => UTile = UTile.apply
  implicit object SimpTileIsType extends IsType[UTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[UTile]
    override def asType(obj: AnyRef): UTile = obj.asInstanceOf[UTile]   
  }
}

case class Move(player: Cood, dirn: HexDirn)

class UnusGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[UTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
  def resolveTurn(moves: List[Move]): UnusGrid =
  {
    val newGrid = new UnusGrid(xTileMin, xTileMax, yTileMin, yTileMax)
    newGrid.foreachTileAll(tile => tile.mem.foreach(p => moves.find(_.player == tile.cood) match
        {
      case None => newGrid.fSetTile(tile.cood, getTile(tile.cood).mem)
      case Some(move) =>  
        }))
    newGrid
  }
}

