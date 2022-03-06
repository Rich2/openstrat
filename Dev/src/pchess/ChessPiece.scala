/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import geom._, pGrid._, Colour._

sealed trait Piece extends UnScaledPolygon
{
  def apply(): PolygonGen = ??? //Square.apply
}
object Pawn extends Piece with UnScaledPolygonYMirror
{
  def rtLine100: Pt2s = Pt2s(20 pp 80, 20 pp 20, 40 pp 20, 40 pp 0)
}

object Rook extends Piece with UnScaledPolygonYMirror
{ def rtLine100: Pt2s = Pt2s(5 pp 100, 5 pp 95, 10 pp 95, 10 pp 100, 25 pp 100, 25 pp 80, 20 pp 80, 20 pp 75, 15 pp 75, 20 pp 15, 30 pp 10, 30 pp 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)

}
object Knight extends Piece
object Bishop extends Piece

object Queen extends Piece with UnScaledPolygonYMirror
{ def rtLine100: Pt2s = Pt2s(0 pp 100, 10 pp 90, 25 pp 100, 15 pp 75, 20 pp 75, 20 pp 70, 15 pp 70, 20 pp 30, 30 pp 10, 30 pp 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)
}

object King extends Piece

sealed trait Player extends Coloured

object PWhite extends Player
{ def colour = White
}

object PBlack extends Player
{ def colour = Black
}

/** Player Piece */
case class PPiece(player: Player, piece: Piece)
