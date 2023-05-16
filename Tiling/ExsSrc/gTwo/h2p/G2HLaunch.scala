/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import pgui._, pParse._, prid._, phex._, gPlay._

case class G2HGuiSettings(view: HGView, playerSet: RArr[Counter])

object G2HLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g2Hex"

  override def default: (CanvasPlatform => Any, String) =
    (G2HGui(_, G2HGame(G2HScen1, G2HScen1.counterSet), G2HGuiSettings(G2HScen1.defaultView(), G2HScen1.counterSet)), "JavaFx Game Two Hex")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: G2HScen = num match
    { case 1 => G2HScen1
      case 2 => G2HScen2
      case 3 => G2HScen3
      case 4 => G2HScen4
     // case 5 => G2HScen5
     // case 6 => G2HScen6
    //  case 7 => G2HScen7
      case _ => G2HScen1
    }

    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("counters")
    val plAll = scen.counterSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: HGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings = G2HGuiSettings(view, pls3)
    val game = G2HGame(scen, pls3)
    (G2HGui(_, game, settings), "JavaFx Game Two Hexs")
  }
}