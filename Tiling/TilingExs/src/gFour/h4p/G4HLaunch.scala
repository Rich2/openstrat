/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package h4p
import prid.*, phex.*, pgui.*, pParse.*

/** Settings for the sole GUI player. */
case class G4HGuiSettings(view: HGView, counterSet: RArr[Team])

object G4HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g3Hex"

  override def default: (CanvasPlatform => Any, String) =
    (G4HGui(_, G4HGame(G4HScen1, G4HScen1.teamSet), G4HGuiSettings(G4HScen1.defaultView(), G4HScen1.teamSet)), "JavaFx Game Three")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse[Int]("scen", 1)

    val scen: G4HScen = num match
    { case 1 => G4HScen1
      case 2 => G4HScen2
      case _ => G4HScen1
    }

    val oSetts: ErrBi[Exception, AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2 = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("counters")
    val plAll = scen.teamSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: HGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings = G4HGuiSettings(view, pls3)
    val game = G4HGame(scen, pls3)
    (G4HGui(_, game, settings), "JavaFx Game Three Hexs")
  }
}
