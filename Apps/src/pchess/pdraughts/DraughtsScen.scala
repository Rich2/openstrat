/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import prid._, psq._

trait DraughtsScen extends ChessLikeScen
{ def segNum: Int
  def turn: Int = segNum / 2
  def draughts: SqCenOptLayer[Draught]
  def resolve(move: SqCenArr): ExcEither[DraughtsScen] = move match
  { case _ if move.empty => LeftExc("Empty Command")
    case _ if move.length == 1 => LeftExc("No move given.")
    case _ => LeftExc("Default error.")
  }
}

object DraughtsStart extends DraughtsScen
{ val segNum:Int = 0
  val draughts: SqCenOptLayer[Draught] = gridSys.newSCenOptDGrider[Draught]
  def rowCens(y: Int): SqCenArr = iToMap(ife(y.div4Rem2, 2, 4), 16, 4){c => SqCen(y, c) }
  iToForeach(2, 6, 2){y => rowCens(y).foreach{r => draughts.unsafeSetSome(r, LightMan) } }
  iToForeach(16, 12, -2){y => rowCens(y).foreach{r => draughts.unsafeSetSome(r, DarkMan) } }
}