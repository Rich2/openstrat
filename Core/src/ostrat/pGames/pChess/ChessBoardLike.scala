/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pChess
import pGrid._

abstract class ChessBoardLike[TileT <: Tile](implicit evTile: IsType[TileT]) extends SquareGrid[TileT](2, 16, 2, 16)
{
   def fSetSquare(x: Int, y: Int, fSquare: (Int, Int) => TileT): Unit = fSetTile(2 * x, 2 * y, fSquare)
   
}

sealed trait Player
object Player1 extends Player
object Player2 extends Player
