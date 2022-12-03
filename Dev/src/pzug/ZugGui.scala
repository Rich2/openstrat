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
  val active: RArr[PolygonActive] = proj.tileActives
  val text: RArr[TextGraphic] = terrs.hcOptMap((t, hc) => proj.transOptCoord(hc).map(_.textAt(hc.rcStr, 14, t.contrastBW)))

  val polyFills: RArr[PolygonFill] =
    terrs.rowsCombine.map{ (hcrv: HCenRowPair[ZugTerr]) => hcrv.a1.hVertPolygon.toPolygon(_.toPt2Reg).fill(hcrv.a2.colour) }

  val lines: RArr[LineSegDraw] = terrs.sideFlatMap((hs, _) => RArr(hs.drawDepr()), (hs, t1, t2 ) => ife(t1 == t2, RArr(hs.drawDepr(t1.contrastBW)), RArr()))

  def lunits: GraphicElems = scen.lunits.gridHeadsFlatMap{ (hc, squad) =>
    val uc = UnitCounters.infantry(1.2, HSquad(hc, squad), squad.colour, terrs(hc).colour).slate(hc.toPt2Reg)

    val actions: GraphicElems = squad.action match
    { case mv: Move => mv.dirns.segHCsMap(hc)(_.oldLineSeg.draw())
      case Fire(target) => RArr(LineSegHC(hc, target).oldLineSeg.draw(Red, 2).dashed(20, 20))
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
  def thisTop(): Unit = reTop(RArr(bTurn, zoomIn, zoomOut) ++ proj.buttons)
  thisTop()
  def frame: GraphicElems = (polyFills ++ lines ++ lunits).slate(-focus).scale(cPScale) ++ active ++ text
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}