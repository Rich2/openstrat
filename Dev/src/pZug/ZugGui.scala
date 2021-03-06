/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pCanv._, prid._, geom._, Colour._, pStrat._

/** Graphical User Interface for ZugFuhrer game. */
case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid: HGrid = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val active = grid.map{ hc =>hc.polygonReg.active(hc) }
  val text = terrs.mapHC((t, hc) => hc.rcText(14, t.contrastBW))
  val rows = terrs.rowCombine.map{ hv => hv.polygonReg.fill(hv.value.colour) }
  val lines: Arr[LineSegDraw] = terrs.sideFlatMap((hs, _) => Arr(hs.draw()), (hs, t1, t2 ) => ife(t1 == t2, Arr(hs.draw(t1.contrastBW)), Arr()))

  def lunits = scen.lunits.gridHeadsFlatMap{ (roord, squad) =>
    val uc = UnitCounters.infantry(0.6, squad, squad.colour, terrs(roord).colour).slate(roord.toPt2)// gridPt2)
    val action: GraphicElems = squad.action match
    {
      /*case Move(rs) =>
      { rs.foldWithPrevious[GraphicElems](roord, Arr()){ (acc, prevCood, nextCood) =>
        val sideCood = (prevCood + nextCood) / 2
        val l1 = RoordLine(prevCood, sideCood).gridLine2.draw(Black, 2)
        val l2 = RoordLine(sideCood, nextCood).gridLine2.draw(Black, 2)
        acc +- l1 +- l2
      }
      }*/
      //case FireOld(target) => Arr(RoordLine(roord, target).gridLine2.draw(Red, 2).dashed(20, 20))
      case _ => Arr()
    }
    action +- uc
  }

  mainMouseUp = (but: MouseButton, clickList, _) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList.headOption.toList
      statusText = selected.headToStringElse("Nothing Clicked")
      thisTop()
    }

    /*case (RightButton, List(squad: Squad), List(newTile: HexTile)) =>
      grid.findPath(squad.roord, newTile.roord)(moveFunc).fold[Unit]{
        statusText = "Squad can not move to " + newTile.roord.ycStr
        thisTop()
      } { l =>
        squad.action = Move(l: _*)
        mainRepaint(frame)
        statusText = Squad.toString()
        thisTop()
      }

    case (MiddleButton, List(squad : Squad), List(newTile: Roord)) =>
    { squad.action = Fire(newTile)
      mainRepaint(frame)
    }*/

    //case (RightButton, List(squad : Squad), List(newTile: HexTile)) => deb("No Move" -- squad.toString -- newTile.roord.toString)//unreachable
    case (RightButton, ll, _) => debvar(ll)
    case _ => deb("Other" -- clickList.toString)
  }


  statusText = "Welcome to ZugFuher"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame: GraphicElems = (rows ++ lines ++ active ++ text ++ lunits).gridScale(scale)
  mainRepaint(frame)
}