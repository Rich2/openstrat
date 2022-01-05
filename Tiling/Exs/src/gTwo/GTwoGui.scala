/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import pgui._, prid._, geom._, gOne._

case class GTwoGui(canv: CanvasPlatform, scenStart: TwoScen) extends SquareMapGui("Game Two Gui")
{
  statusText = "Let click on Player to select. Right click on adjacent square to set move."
  var scen = scenStart
  implicit def grid: SqGrid = scen.grid
  debvar(grid.xCen)
  def players: SqCenArrOpt[Player] = scen.oPlayers

  /** The number of pixels / 2 displayed per row height. */
  var cPScale = grid.fullDisplayScale(mainWidth, mainHeight)

  def lunits = players.scSomesMap{ (sc, p) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + sc.strComma, 150, p.charStr + "\n" + sc.strComma, 60, p.charStr)
    Rect(1.2, 0.8, sc.toPt2).fillDrawTextActive(p.colour, p, str, 24, 2.0)  }

  def css = players.cMapNones(hc => TextGraphic(hc.rcStr, 20, hc.toPt2))

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of
   *  those moves. This data is state for the Gui. */
  var moves: SqCenArrOpt[SqStep] = NoMoves

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = moves.scSomesMap { (sc, step) =>
    LineSegSC(sc, sc.step(step)).lineSeg.draw(players.unSafeApply(sc).colour)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn = clickButtonOld("Turn " + (scen.turn + 1).toString, _ => {
        val getOrders = players.some2sMap(moves)((player, step) => (player, step))//moves.mapSomes(rs => rs)
        scen = scen.endTurn(getOrders)
        moves = NoMoves
        repaint()
        thisTop()
  })

  /** Draws the tiles sides (or edges). */
  val sidesDraw = grid.sidesDraw()

  /** There are mo moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: SqCenArrOpt[SqStep] = grid.newTileArrOpt[SqStep]

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, Arr2(SPlayer(p, sc1), SqCen(y, c)), ArrHead(sc2 : SqCen)) =>
    { val newM: OptRef[SqStep] = sc1.findStep(sc2)
      newM.foldDo{ if (sc1 == sc2) moves = moves.setNone(sc1) }(m => moves = moves.setSome(sc1, m))
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn, zoomIn, zoomOut))
  thisTop()
  def moveGraphics2: GraphicElems = moveGraphics.slate(-focus).scale(cPScale).flatMap(_.arrow)
  def frame: GraphicElems = (lunits +% sidesDraw ++ css).gridScale(cPScale) ++ moveGraphics2
  repaint()
}
