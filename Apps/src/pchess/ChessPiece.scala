/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import geom.*, Colour.*

/** Chess piece. */
sealed trait Piece extends PolygonMemIcon

/** Symmetrical Chess piece */
trait MirrorPiece extends Piece, UnScaledPolygonYMirror
{ def rtLine100: LinePath = LinePath.dbls(20,80, 20,20, 40,20, 40,0)
}

object Pawn extends MirrorPiece
{ override def rtLine100: LinePath = LinePath.dbls(20,80, 20,20, 40,20, 40,0)
}

object Rook extends MirrorPiece
{ override def rtLine100: LinePath = LinePath.dbls(5,100, 5,95, 10,95, 10,100, 25,100, 25,80, 20,80, 20,75, 15,75, 20,15, 30,10, 30,0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)

}
object Knight extends MirrorPiece

/** Chess Bishop 2D outline. */
object Bishop extends MirrorPiece
{ override def rtLine100: LinePath = LinePath.dbls(0,100, 15,75, 20,75, 20,70, 15,70, 20,30, 30,10, 30,0)
}

/** Chess Queen 2D outline. */
object Queen extends MirrorPiece
{ override def rtLine100: LinePath = LinePath.dbls(0,100, 10,90, 25,100, 15,75, 20,75, 20,70, 15,70, 20,30, 30,10, 30,0)
  //override def scaled = rtLine.yMirrorClose.slateY(-50).scale(0.01)
}

/** Chess King 2D outline. */
object King extends MirrorPiece
{ override def rtLine100: LinePath = LinePath.dbls(0,100, 25,100, 15,75, 20,75, 20,70, 15,70, 20,30, 30,10, 30,0)
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