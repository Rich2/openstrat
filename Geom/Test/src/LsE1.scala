/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pgui._

/** E Series lessons deal with games. E1 is a super simple single player turn game.  */
case class LsE1(canv: CanvasPlatform) extends CmdBarGui("Lesson E1")
{
  import e1._
  var state: GameState = GameState.start
  var cmd: TurnCmd = NoMove
  statusText = "Right click to set action to Move. Left to set action to CycleColour. Press Turn button or middle click for next turn."
  
  def cmdDisp: Arr[GraphicElem] = cmd match
  { case Move(v) => Arrow.paint(state.posn, v)//Returns Arr[GraphicElem]
    case CycleColour => Arr(state.drawNextColour)
    case _ => Arr()
  }

  /** frame refers to the screen output. In the same way that a movie is constructed from a number of still frames. So we create the "action" in a
   * graphical application through a series of frames. Unlike in the movies our display may not change for significant periods of time. Where we can
   * it is simpler to create the whole screen out, to create each from a blank slate so to speak rather than just painting the parts of the dsplay
   * that have been modified. */
  def frame(): Unit =
  { reTop(Arr(StdButton.turn(state.turnNum + 1)))
    mainRepaint(state.fillRect %: cmdDisp)
  }
  def newTurn(): Unit = { state = state.turn(cmd); cmd = NoMove; frame() }

  frame()

  topBar.mouseUp = (b, s, v) => s match
  { case Arr1(Turn) => newTurn()
    case _ =>
  }

  mainMouseUp = (b, s, v) => b match
  { case RightButton => { cmd = Move(v); frame() }
    case LeftButton => { cmd = CycleColour; frame() }
    case _ => newTurn()
  }
}