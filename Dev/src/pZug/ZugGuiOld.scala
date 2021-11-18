/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pgui._, pGrid._, geom._, Colour._, pStrat._

/** Uses the old Roards from pGrid, but with the new simpler Gui. */
case class ZugGuiOld(canv: CanvasPlatform, scen: ZugScenOld) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid: HexGridRegOld = scen.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val terrs = scen.terrs
  val tiles = grid.map{ r => r.tilePoly.fillTextActive(terrs(r).colour, r.toHexTile, r.ycStr, 16) }
  val sides = scen.sTerrs.gridMap{ (r, b) =>
    val sl = grid.sideRoordToLine2(r)
    ife(b, sl.draw(Colour.Gray, 8), sl.draw(lineWidth = 2))
  }

  def lunits = scen.lunits.gridHeadsFlatMap{ (roord, squad) =>
    val uc = UnitCounters.infantry(0.6, squad, squad.colour, terrs(roord).colour).slate(roord.gridPt2)
    val action: GraphicElems = squad.action match
    {
      case MoveOld(rs) =>
      { rs.foldWithPrevious[GraphicElems](roord, Arr()){ (acc, prevCood, nextCood) =>
          val sideCood = (prevCood + nextCood) / 2
          val l1 = RoordLine(prevCood, sideCood).gridLine2.draw(Black, 2)
          val l2 = RoordLine(sideCood, nextCood).gridLine2.draw(Black, 2)
          acc +% l1 +% l2
        }
      }
      case FireOld(target) => Arr(RoordLine(roord, target).gridLine2.draw(Red, 2).dashed(20, 20))
      case _ => Arr()
    }
    action +% uc
  }

  def moveFunc: (Roord, Roord) => OptInt = (r1, r2) =>  SquadOld.terrCost(terrs(r1)) |+| SquadOld.terrCost(terrs(r2))

  mainMouseUp = (but: MouseButton, clickList, _) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList
      statusText = selected.headFoldToString("Nothing Clicked")
      thisTop()
    }

    case (RightButton, Arr1(squad: SquadOld), Arr1(newTile: HexTile)) =>
      grid.findPath(squad.roord, newTile.roord)(moveFunc).fold[Unit]{
        statusText = "Squad can not move to " + newTile.roord.ycStr
        thisTop()
      } { l =>
        squad.action = MoveOld(l: _*)
        mainRepaint(frame)
        statusText = SquadOld.toString()
        thisTop()
      }

    case (MiddleButton, Arr1(squad : SquadOld), Arr1(newTile: Roord)) =>
    { squad.action = FireOld(newTile)
      mainRepaint(frame)
    }

    //case (RightButton, List(squad : Squad), List(newTile: HexTile)) => deb("No Move" -- squad.toString -- newTile.roord.toString)//unreachable
    case (RightButton, ll, _) => debvar(ll)
    case _ => deb("Other" -- clickList.toString)
  }

  statusText = "Welcome to the old ZugFuhrer"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame = (tiles ++ sides ++ lunits).gridScale(scale)
  mainRepaint(frame)
}