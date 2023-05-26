/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, pgui._, pParse._

/** Settings for the sole GUI player. */
case class G3HGuiSettings(view: HGView, counterSet: RArr[Team])
object G3HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g3Hex"

  override def default: (CanvasPlatform => Any, String) =
    (G3HGui(_, G3HGame(G3HScen1, G3HScen1.teamSet), G3HGuiSettings(G3HScen1.defaultView(), G3HScen1.teamSet)), "JavaFx Game Three")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: G3HScen = num match
    { case 1 => G3HScen1
      case 2 => G3HScen2
      case _ => G3HScen1
    }

    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("counters")
    val plAll = scen.teamSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: HGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings = G3HGuiSettings(view, pls3)
    val game = G3HGame(scen, pls3)
    (G3HGui(_, game, settings), "JavaFx Game Three Hexs")
  }
}