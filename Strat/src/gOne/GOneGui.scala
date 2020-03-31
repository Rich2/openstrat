package ostrat
package gOne
import pCanv._, geom._, pGrid._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform, scen: OneGrid) extends CmdBarGui("Game One Gui")
{ var statusText = "Stuff"
  implicit val grid = scen.grid
  val players = scen.players
  var moves: OptRefs[HTStep] = grid.newOptRefs[HTStep]
  players.setOtherOptRefs(moves)(_ => HTStepNone)


  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val units = grid.mapArrOptRefVec(OneGrid1.players, scale){ (r, v, p) => Rectangle(120, 80, v).fillDrawTextActive(p.colour, RPlayer(p, r), p.toString, 24, 2.0) }
  val tiles = grid.activeTiles(scale)
  val sides = cenSideVertCoodText(grid, scale)
  //val mMoves = moves.

  def thisTop(): Unit = reTop(Refs(status))

  mainMouseUp = (b, cl, _) => (b, cl, selected) match
    { case (LeftButton, cl, _) =>
      { selected = cl
        statusText = selected.headToStringElse("Nothing Selected")
        thisTop()
      }

      case (RightButton, (t : HexTile) :: _, RPlayer(p, r) :: l) => t.adjOf(r).foreach(ht => moves.setSome(grid.index(r), ht))// ??? //moves.setSome() t.adjOf(r)
       case (_, h, _) => deb("Other; " + h.toString)
    }
  thisTop()
  mainRepaint(tiles ++ sides ++ units)
}