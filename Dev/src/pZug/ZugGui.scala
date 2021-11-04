/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pgui._, prid._, geom._, Colour._, pStrat._

/** Graphical User Interface for ZugFuhrer game. */
case class ZugGui(canv: CanvasPlatform, scenIn: ZugScen) extends HexMapGui("ZugFuhrer Gui")
{
  var scen = scenIn
  implicit def grid: HGrid = scen.grid
  var yScale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val active = grid.map{ hc =>hc.polygonReg.active(hc) }
  val text = terrs.hcMap((hc, t) => hc.decText(14, t.contrastBW))
  val rows = terrs.rowCombine.map{ hv => hv.polygonReg.fill(hv.value.colour) }
  val lines: Arr[LineSegDraw] = terrs.sideFlatMap((hs, _) => Arr(hs.draw()), (hs, t1, t2 ) => ife(t1 == t2, Arr(hs.draw(t1.contrastBW)), Arr()))

  def lunits: GraphicElems = scen.lunits.gridHeadsFlatMap{ (hc, squad) =>
    val uc = UnitCounters.infantry(0.6, squad, squad.colour, terrs(hc).colour).slate(hc.toPt2)

    val actions: GraphicElems = squad.action match
    { case mv: HSteps => mv.segsMap(hc)(_.draw())
      case Fire(target) => Arr(LineSegHC(hc, target).lineSeg.draw(Red, 2).dashed(20, 20))
      case _ => Arr()
    }
    actions +- uc
  }

  mainMouseUp = (but: MouseButton, clickList, _) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList
      statusText = selected.headFoldToString("Nothing Clicked")
      thisTop()
    }

    case (RightButton, ArrHead(squad: Squad), ArrHead(newTile: HCen)) => { deb("Move")}
      /*grid.findPath(squad.roord, newTile)(moveFunc).fold[Unit]{
        statusText = "Squad can not move to " + newTile.rcStr
        thisTop()
      } { l =>
        squad.action = Move(l: _*)
        mainRepaint(frame)
        statusText = Squad.toString()
        thisTop()
      }*/

    case (MiddleButton, ArrHead(squad : Squad), ArrHead(newTile: HCen)) =>
    { squad.action = Fire(newTile)
      deb("Fire")
      mainRepaint(frame)
    }

    //case (RightButton, List(squad : Squad), List(newTile: HexTile)) => deb("No Move" -- squad.toString -- newTile.roord.toString)//unreachable
    case (RightButton, ll, _) => debvar(ll)
    case _ => deb("Other" -- clickList.toString)
  }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    scen = scen.endTurn()
    repaint()
    thisTop()
  }
  statusText = "Welcome to ZugFuher"
  def thisTop(): Unit = reTop(Arr(bTurn, zoomIn, zoomOut))
  thisTop()
  def frame: GraphicElems = (rows ++ lines ++ active ++ text ++ lunits).gridScale(yScale)
  mainRepaint(frame)
}