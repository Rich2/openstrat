/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for GThree example game. */
case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen, viewIn: HGView) extends HGridSysGui("Game Three Gui")
{
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  var history: Arr[ThreeScen] = Arr(scen)
  implicit def gridSys: HGridSys = scen.gridSys
  def players: HCenOptDGrid[Player] = scen.oPlayers
  cPScale = viewIn.cPScale
  focus = viewIn.vec
  val proj = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   *  moves. This data is state for the Gui. */
  var moves: Map[Player, HDirnArr] = scen.playersData

  val urect = Rect(1.4, 1)

  def units: Arr[PolygonCompound] = players.someHCOptMap { (p, hc) =>
    proj.transCoord(hc).map { pt =>
      val str = ptScale.scaledStr(170, p.toString + "\n" + hc.strComma, 150, p.charStr + "\n" + hc.strComma, 60, p.charStr)
      urect.scale(80).slate(pt).fillDrawTextActive(p.colour, HPlayer(hc, p), str, 24, 2.0)
    }
  }

  /** We could of used the mapHCen method and produced the units and the hexstrs graphics at the same time, but its easier to keep them separate. */
  /*def units: Arr[PolygonCompound] = players.someHCMap { (pl, hc) =>
    val str = ptScale.scaledStr(170, pl.toString + "\n" + hc.strComma, 150, pl.charStr + "\n" + hc.strComma, 60, pl.charStr)
    urect.scale(1.5).slate(hc.toPt2).fillDrawTextActive(pl.colour, HPlayer(hc, pl), str, 24, 2.0)
  }*/

  /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
  def hexStrs: Arr[TextGraphic] = players.noneHCOptMap{ hc => proj.transCoord(hc).map(TextGraphic(hc.strComma, 20, _)) }//players.noneHCMap(hc => TextGraphic(hc.strComma, 20, hc.toPt2))

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def tiles: Arr[PolygonActive] = proj.tileActives//gridSys.activeTiles

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: Arr[LineSegDraw] = players.someHCFlatMap { (p, hc) =>
    val hss: HDirnArr = moves.withDefault(_ => HDirnArr())(p)
    hss.oldSegsMap(hc) { ls => ls.draw(players.unSafeApply(hc).colour)
    }
  }

  /** This is the graphical display of the planned move orders. */
  /*def moveGraphics: GraphicElems = moves.someHCOptFlatMap { (step, hc) =>
    proj.transLineSeg(LineSegHC(hc, hc.unsafeStep(step))).map(_.draw(players.unSafeApply(hc).colour).arrow)
  }*/

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    scen = scen.endTurn(moves)
    moves = scen.playersData
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr(bTurn) ++ proj.buttons)//reTop(Arr(bTurn) ++ navButtons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, p)), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      newM.fold[Unit]{ if (hc1 == hc2) moves = moves.replaceValue(p, HDirnArr()) } { m => moves = moves.replaceValue(p, HDirnArr(m)) }
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  def moveGraphics2: GraphicElems = moveGraphics.slate(-focus).scale(cPScale).flatMap(_.arrow)
  def frame: GraphicElems = tiles ++ units ++ hexStrs +% sidesDraw ++ moveGraphics2
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}