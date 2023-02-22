/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for Game One example game. Each player can move one hex tile step. Any move to a tile already containing a player or that
 *  one more than one player is attempting to move to fails. */
case class G1HGui(canv: CanvasPlatform, scenStart: G1HScen, viewIn: HGView) extends HGridSysGui("Game One Gui")
{
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  var history: RArr[G1HScen] = RArr(scen)
  implicit def gridSys: HGridSys = scen.gridSys
  def players: HCenOptLayer[Player] = scen.oPlayers

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  /** There are no moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: HCenStepPairArr[Player] = HCenStepPairArr[Player]()

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   *  moves. This data is state for the Gui. */
  var moves: HCenStepPairArr[Player] = NoMoves

  val urect = Rect(1.4, 1)

  def units: GraphicElems = players.projSomeHcPtMap { (p, hc, pt) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + hc.strComma, 150, p.charStr + "\n" + hc.strComma, 60, p.charStr)
    urect.scale(80).slate(pt).fillDrawTextActive(p.colour, HPlayer(hc, p), str, 24, 2.0)
  }

  /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
  def hexStrs: RArr[TextGraphic] = players.projNoneHcPtMap{ (hc, pt) => pt.textAt(hc.strComma, 20) }

  def hexStrs2: GraphicElems = proj.ifTileScale(60, hexStrs)

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  val actives: RArr[PolygonActive] = proj.tileActives

  /** Draws the tiles sides (or edges). */
  def innerSidesDraw: LinesDraw = proj.innerSidesDraw()

  /** Draws the tiles sides (or edges). */
  def outerSidesDraw: LinesDraw = proj.outerSidesDraw(2, Colour.Gold)

  def moveSegPairs: LineSegPairArr[Player] = moves.optMapOnA1(_.projLineSeg)

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap{ (seg, pl) => seg.draw(pl.colour).arrow }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    scen = scen.endTurn(moves)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  {
    case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, pl)), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HStep] = gridSys.findStep(hc1, hc2)
      newM.foreach{ d => moves = moves.replaceA1byA2OrAppend(pl, hc1.andStep(d)) }
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  def frame: GraphicElems = (actives ++ units +% innerSidesDraw +% outerSidesDraw ++ moveGraphics ++ hexStrs2)
  proj.getFrame = () => frame
  proj.setStatusText = {str =>
    statusText = str
    thisTop()
  }
  repaint()
}