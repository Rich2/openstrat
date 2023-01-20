/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import pgui._, prid._, phex._, geom._, Colour._, pStrat._

/** Graphical User Interface for ZugFuhrer game. */
case class ZugGui(canv: CanvasPlatform, scenIn: ZugScen) extends HGridSysGui("ZugFuhrer Gui")
{
  var scen = scenIn
  implicit def gridSys: HGridSys = scen.gridSys
  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  //proj.setView(viewIn)

  val terrs: HCenLayer[ZugTerr] = scen.terrs
  val sTerrs: HSideBoolLayer = scen.sTerrs
  val active: RArr[PolygonActive] = proj.tileActives
  val text: RArr[TextGraphic] = terrs.hcOptMap((t, hc) => proj.transOptCoord(hc).map(_.textAt(hc.rcStr, 14, t.contrastBW)))

  def polyFills: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }

  def walls: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Colour.Gray) }
  val lines: RArr[LineSegDraw] = terrs.projLinksHsLineOptMap((hs, line, t1, t2) => ife(t1 == t2 & !sTerrs(hs), Some(line.draw(t1.contrastBW)), None))
  def lunits: GraphicElems = scen.lunits.gridHeadsFlatMap{ (hc, squad) =>
    val uc = UnitCounters.infantry(1.2, HSquad(hc, squad), squad.colour).slate(hc.toPt2Reg)

    val actions: GraphicElems = squad.action match
    { case mv: Move => mv.dirns.segHCsMap(hc)(_.lineSeg.draw())
      case Fire(target) => RArr(LineSegHC(hc, target).lineSeg.draw(Red, 2).dashed(20, 20))
      case _ => RArr()
    }
    actions +% uc
  }

  mainMouseUp = (but: MouseButton, clickList, _) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList
      statusText = selected.headFoldToString("Nothing Clicked")
      thisTop()
    }

    case (RightButton, AnyArrHead(HSquad(hc2, squad)), AnyArrHead(newTile: HCen)) =>
    {
      deb("Move")
      gridSys.findPath(hc2, newTile)((_, _) => SomeInt(1)).fold[Unit] {
        statusText = "Squad can not move to " + newTile.rcStr
        thisTop()
      } { (hcs: HCenArr) =>
          deb("Valid Move " + hcs.toString)
          squad.action = ???//Move(hcs)
          mainRepaint(frame)
          statusText = Squad.toString()
          thisTop()
        }
    }

    case (MiddleButton, AnyArrHead(HSquad(_, squad)), hits) => hits.findHCenForEach{ hc2 =>
      squad.action = Fire(hc2)
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
  def thisTop(): Unit = reTop(bTurn %: proj.buttons)
  thisTop()
  def frame: GraphicElems = polyFills ++ walls ++ lines ++ (lunits).slate(-focus).scale(cPScale) ++ active ++ text
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}