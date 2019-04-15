/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package learn
import ostrat._, geom._

package e1
{
  /** GameState. The game world state between turns. This is an ultra simple single player turn game, with only two actions, move and change colour. It
   *  is not intended as interesting game, but merely to illustrate the common features of a functionally implemented game. */
  case class GState(turnNum: Int, posn: Vec2, rainbowCycle: RainbowCycle)
  {
    /** Move to a new posn if no greater than 150 pixel distant */
    def turn(cmd: Option[TurnCmd]): GState = cmd match
    {
      case Some(Move(toPosn)) =>
      { val diff = toPosn  - posn
        val len = diff.magnitude
        val max = 150
        val newPosn = ife(len > max, posn + toPosn * max / len, toPosn)
        GState(turnNum + 1, newPosn, rainbowCycle)
      }   
      case Some(CycleColour) => GState(turnNum + 1, posn, rainbowCycle.next)
      case _ => copy(turnNum + 1)
    }
    def colour: Colour = rainbowCycle()
    def nextColour: Colour = rainbowCycle.nextValue
    def rect = Rectangle.curvedCorners(80, 50, 15, posn)
    def drawNextColour = rect.draw(4, nextColour)
    def fillRect = rect.fill(colour)
  }
  
  object GState
  {
    val start = GState(0, 0 vv 0, RainbowCycle.start)
  }
  
  sealed trait TurnCmd
  case object CycleColour extends TurnCmd
  case class Move(toPosn: Vec2) extends TurnCmd
  
  case class Game(curr: GState, start: GState, cmds: List[GState])
  object Game
  {
    def newGame: Game = Game(GState.start, GState.start, Nil)
  }
}
