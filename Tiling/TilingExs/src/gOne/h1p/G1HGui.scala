/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package h1p
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for Game One example game. Each player can move one hex tile step. Any move to a tile already containing a player or that
 *  one more than one player is attempting to move to fails. */
case class G1HGui(canv: CanvasPlatform, game: G1HGame, settings: G1HGuiSettings) extends HGridSysGui("Game One Gui")
{ def controlStr: String = settings.counterSet.map(_.charStr).mkStr(", ")
  statusText = "You control players" -- controlStr -- ". Left click on Counter to select. Right click on adjacent Hex to set move."
  var scen: G1HScen = game.scen

  implicit def gridSys: HGridSys = scen.gridSys

  def counters: LayerHcOptSys[Counter] = scen.counters
  def counterSet: RArr[Counter] = settings.counterSet

  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(settings.view)

  /** There are no moves set. The Gui is reset to this state at the start of every turn. */
  def NoMoves: HCenStepPairArr[Counter] = HCenStepPairArr[Counter]()

  val noMoves2 = LayerHcRefSys[HStepLike](HStepStay)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   * moves. This data is state for the Gui. */
  var moves: HCenStepPairArr[Counter] = NoMoves

  var moves2: LayerHcRefSys[HStepLike] = noMoves2

  def frame: GraphicElems =
  {
    def units: GraphicElems = counters.projSomesHcPtMap { (counter, hc, pt) =>
      val str = pixPerTile.scaledStr(170, counter.toString + "\n" + hc.strComma, 150, counter.charStr + "\n" + hc.strComma, 60, counter.charStr)
      Rect(1.4).scale(pixPerTile * 0.4).slate(pt).fillActiveDrawText(counter.colour, HCenPair(hc, counter), str, 24, 2.0, Colour.Black, counter.contrastBW)
    }

    /** [[TextFixed]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
    def hexStrs: RArr[TextFixed] = counters.projNoneHcPtMap { (hc, pt) => pt.textAt(hc.strComma, 20) }

    def hexStrs2: GraphicElems = proj.ifTileScale(60, hexStrs)

    /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
    val actives: RArr[PolygonActive] = proj.tileActives

    /** Draws the tiles sides (or edges). */
    def innerSidesDraw: LSeg2ArrDraw = proj.innerSidesDraw()

    /** Draws the tiles sides (or edges). */
    def outerSidesDraw: LSeg2ArrDraw = proj.outerSidesDraw(2, Colour.Gold)

    def moveSegPairs: LineSegPairArr[Counter] = moves.optMapOnA1(_.projLineSeg)

    /** This is the graphical display of the planned move orders. */
    def moveGraphics: GraphicElems = moveSegPairs.pairFlatMap { (seg, pl) => seg.draw(lineColour = pl.colour).arrow }

    actives ++ units +% innerSidesDraw +% outerSidesDraw ++ moveGraphics ++ hexStrs2
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    //scen = game.endTurn(moves.mapOnA1(_.step))
    val dirs = moves2.hcOptMap { (sl, hc) => sl.mapOpt { st => HCenStep(hc, st) } }
    scen = game.endTurn(dirs)
    moves = NoMoves
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }

    case (RightButton, HCenPair(hc1, selectedCounter: Counter), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HStep] = gridSys.stepFind(hc1, hc2)
      newM.foreach{ st => if(counterSet.contains(selectedCounter))
        { moves = moves.replaceA1byA2OrAppend(selectedCounter, hc1.andStep(st))
          moves2.set(hc1, st)
        }
        else { statusText = s"Illegal move You don't have control of $selectedCounter"; thisTop()} }
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  proj.getFrame = () => frame
  proj.setStatusText = {str =>
    statusText = str
    thisTop()
  }
  repaint()
}