/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import pgui._, prid._, phex._, geom._, Colour._, pStrat._

/** Graphical User Interface for ZugFuhrer game. */
case class ZugGui(canv: CanvasPlatform, scenIn: ZugScen) extends HGridSysGui("ZugFuhrer Gui") {
  var scen = scenIn
  implicit def gridSys: HGridSys = scen.gridSys
  val terrs: HCenLayer[ZugTerr] = scen.terrs
  val sTerrs: HSideBoolLayer = scen.sTerrs
  val corners = scen.corners
  def squads: HCenArrLayer[Squad] = scen.lunits

  focus = gridSys.cenVec
  cPScale = gridSys.fullDisplayScale(mainWidth, mainHeight)
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  //proj.setView(viewIn)

  def frame: GraphicElems =
  {
    //def tiles: RArr[PolygonFill] = terrs.projRowsCombinePolygons.map { pp => pp.a1.fill(pp.a2.colour) }

    def tiles2: RArr[PolygonFill] = gridSys.map { hc =>
      corners.tilePoly(hc).map { hvo => hvo.toPt2(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }

    def tileActives: RArr[PolygonActive] = proj.tileActives

    def walls2: GraphicElems = proj.sidesOptMap { (hs: HSide) =>
      val sTerr: Boolean = sTerrs(hs)
      if (sTerr) Some(corners.sideVerts(hs).project(proj).fill(Colour.Gray)) else None
    }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      val hc1 = hs.tileLt
      val t1 = terrs(hc1)

      def t2 = terrs(hs.tileRt)

      if (sTerrs(hs) | t1 != t2) None
      else {
        val cs: (HCen, Int, Int) = hs.corners
        val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
        val ls2 = ls1.map(hva => hva.toPt2(proj.transCoord(_)))
        Some(ls2.draw(t1.contrastBW))
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    def texts2 = squads.projEmptyHcPtMap{ (hc, pt) => pt.textAt(hc.rcStr, 14, terrs(hc).contrastBW) }

    /*def lunits: GraphicElems = squads.gridHeadsFlatMap{ (hc, squad) =>
      val uc = UnitCounters.infantry(1.2, HSquad(hc, squad), squad.colour).slate(hc.toPt2Reg)

      val actions: GraphicElems = squad.action match
      { case mv: Move => mv.dirns.segHCsMap(hc)(_.lineSeg.draw())
        case Fire(target) => RArr(LineSegHC(hc, target).lineSeg.draw(Red, 2).dashed(20, 20))
        case _ => RArr()
      }
      actions +% uc
    }*/

    def lunits2: GraphicElems = squads.projSomeHcPtMap { (army, hc, pt) =>
      val str = ptScale.scaledStr(170, army.toString + "\n" + hc.strComma, 150, "A" + "\n" + hc.strComma, 60, army.toString)
      pStrat.UnitCounters.infantry(proj.pixelsPerTile * 0.6, army, army.colour).slate(pt) //.fillDrawTextActive(p.colour, p.polity, str, 24, 2.0)
    }

    tiles2 ++ tileActives ++ walls2 ++ lines2 ++ lunits2 ++ texts2
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

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  mainRepaint(frame)
}