/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, ColourArr._

package e1
{
  /** GameState. The game world state between turns. This is an ultra simple single player turn game, with only two actions, move and change colour. It
   *  is not intended as interesting game, but merely to illustrate the common features of a functionally implemented game. */
  case class GameState(turnNum: Int, posn: Pt2, rainbowCycle: RainbowCycle)
  {
    /** Move to a new posn if no greater than 150 pixel distant */
    def turn(cmd: TurnCmd): GameState = cmd match
    {
      case Move(toPosn) =>
      { val len = posn.distTo(toPosn)
        val max = 150
        val newPosn = ife(len > max, posn.slate(toPosn.scale(max / len)), toPosn)
        GameState(turnNum + 1, newPosn, rainbowCycle)
      }   
      case CycleColour => GameState(turnNum + 1, posn, rainbowCycle.next)
      case _ => copy(turnNum + 1)
    }
    def colour: Colour = rainbowCycle()
    def nextColour: Colour = rainbowCycle.nextValue
    def rect: ShapeGenOld = Rectangle.curvedCorners(80, 50, 15, posn)
    def drawNextColour = rect.draw(nextColour, 8)
    def fillRect = rect.fill(colour)
  }
  
  object GameState
  { val start = GameState(0, 0 pp 0, rainbowStart)
  }

  /** A turned Command is a sealed trait */
  sealed trait TurnCmd
  case object NoMove extends TurnCmd
  case object CycleColour extends TurnCmd
  case class Move(toPosn: Pt2) extends TurnCmd
  
  case class Game(curr: GameState, start: GameState, cmds: List[GameState])
  object Game
  {
    def newGame: Game = Game(GameState.start, GameState.start, Nil)
  }
}