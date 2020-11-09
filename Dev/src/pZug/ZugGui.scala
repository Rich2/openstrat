/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pZug
import pCanv._, pGrid._, geom._, Colour._, pStrat._

/** Uses the new simpler Gui for Grids. */
case class ZugGui(canv: CanvasPlatform, scen: ZugScen) extends CmdBarGui("ZugFuhrer Gui")
{
  implicit val grid = scen.grid
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
      case Move(rs) =>
      { rs.foldWithPrevious[GraphicElems](roord, Arr()){ (acc, prevCood, nextCood) =>
          val sideCood = (prevCood + nextCood) / 2
          val l1 = RoordLine(prevCood, sideCood).gridLine2.draw(Black, 2)
          val l2 = RoordLine(sideCood, nextCood).gridLine2.draw(Black, 2)
          acc +- l1 +- l2
        }
      }
      case Fire(target) => Arr(RoordLine(roord, target).gridLine2.draw(Red, 2).dashed(20, 20))
      case _ => Arr()
    }
    action +- uc
  }

  def moveFunc: (Roord, Roord) => OptInt = (r1, r2) =>  Squad.terrCost(terrs(r1)) |+| Squad.terrCost(terrs(r2))

  mainMouseUp = (but: MouseButton, clickList, _) => (but, selected, clickList) match
  {
    case (LeftButton, _, cl) =>
    { selected = clickList.headOption.toList
      statusText = selected.headToStringElse("Nothing Clicked")
      thisTop()
    }

    case (RightButton, List(squad: Squad), List(newTile: HexTile)) =>
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
    }

    case (RightButton, List(squad : Squad), List(newTile: HexTile)) => deb("No Move" -- squad.toString -- newTile.roord.toString)
    case (RightButton, ll, _) => debvar(ll)
    case _ => deb("Other" -- clickList.toString)
  }

  var statusText = "Welcome to ZugFuhrer"
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  def frame = (tiles ++ sides ++ lunits).gridScale(scale)
  mainRepaint(frame)
}