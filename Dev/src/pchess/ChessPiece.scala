/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import geom._, Colour._

/** Chess piece. */
sealed trait Piece extends PolygonMemIcon

/** Symmetrical Chess piece */
trait MirrorPiece extends Piece with UnScaledPolygonYMirror
{ def rtLine100: LinePath = LinePath(20 pp 80, 20 pp 20, 40 pp 20, 40 pp 0)
}

object Pawn extends MirrorPiece
{ override def rtLine100: LinePath = LinePath(20 pp 80, 20 pp 20, 40 pp 20, 40 pp 0)
}

object Rook extends MirrorPiece
{ override def rtLine100: LinePath = LinePath(5 pp 100, 5 pp 95, 10 pp 95, 10 pp 100, 25 pp 100, 25 pp 80, 20 pp 80, 20 pp 75, 15 pp 75, 20 pp 15, 30 pp 10, 30 pp 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)

}
object Knight extends MirrorPiece

/** Chess Bishop 2D outline. */
object Bishop extends MirrorPiece
{ override def rtLine100: LinePath = LinePath(0 pp 100, 15 pp 75, 20 pp 75, 20 pp 70, 15 pp 70, 20 pp 30, 30 pp 10, 30 pp 0)
}

/** Chess Queen 2D outline. */
object Queen extends MirrorPiece
{ override def rtLine100: LinePath = LinePath(0 pp 100, 10 pp 90, 25 pp 100, 15 pp 75, 20 pp 75, 20 pp 70, 15 pp 70, 20 pp 30, 30 pp 10, 30 pp 0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)
}

/** Chess King 2D outline. */
object King extends MirrorPiece
{ override def rtLine100: LinePath = LinePath(0 pp 100, 25 pp 100, 15 pp 75, 20 pp 75, 20 pp 70, 15 pp 70, 20 pp 30, 30 pp 10, 30 pp 0)
}

sealed trait Player extends Coloured

object PWhite extends Player
{ def colour = White
}

object PBlack extends Player
{ def colour = Black
}

/** Player Piece */
case class PPiece(player: Player, piece: Piece)
