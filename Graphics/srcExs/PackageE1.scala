/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._, Colours._

package e1
{
  /** GameState. The game world state between turns. This is an ultra simple single player turn game, with only two actions, move and change colour. It
   *  is not intended as interesting game, but merely to illustrate the common features of a functionally implemented game. */
  case class GameState(turnNum: Int, posn: Vec2, rainbowCycle: RainbowCycle)
  {
    /** Move to a new posn if no greater than 150 pixel distant */
    def turn(cmd: TurnCmd): GameState = cmd match
    {
      case Move(toPosn) =>
      { val diff = toPosn  - posn
        val len = diff.magnitude
        val max = 150
        val newPosn = ife(len > max, posn + toPosn * max / len, toPosn)
        GameState(turnNum + 1, newPosn, rainbowCycle)
      }   
      case CycleColour => GameState(turnNum + 1, posn, rainbowCycle.next)
      case _ => copy(turnNum + 1)
    }
    def colour: Colour = rainbowCycle()
    def nextColour: Colour = rainbowCycle.nextValue
    def rect: PolyCurve = Rectangle.curvedCorners(80, 50, 15, posn)
    def drawNextColour = rect.draw(8, nextColour)
    def fillRect = rect.fill(colour)
  }
  
  object GameState
  {
    val start = GameState(0, 0 vv 0, rainbowStart)
  }

  /** A turned Command is a sealed trait */
  sealed trait TurnCmd
  case object NoMove extends TurnCmd
  case object CycleColour extends TurnCmd
  case class Move(toPosn: Vec2) extends TurnCmd
  
  case class Game(curr: GameState, start: GameState, cmds: List[GameState])
  object Game
  {
    def newGame: Game = Game(GameState.start, GameState.start, Nil)
  }
}
