/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import pgui._, pParse._, prid._, psq._, gPlay._

case class G1SGuiSettings(view: SGView, playerSet: RArr[Player])

object G1SLaunch extends GuiLaunchMore
{
  override def settingStr: String = "g1Sq"

  override def default: (CanvasPlatform => Any, String) =
    (G1SGui(_, G1SGame(G1SScen1, G1SScen1.playerSet), G1SGuiSettings(G1SScen1.defaultView(), G1SScen1.playerSet)), "JavaFx Game One Squares")
  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: G1SqScen = num match
    { case 1 => G1SScen1
      case 2 => G1SScen2
      case 3 => G1SScen3
      case _ => G1SScen1
    }
    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("players")
    val plAll = scen.playerSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: SGView = sts2.findTypeElse(scen.gridSys.defaultView())
    debvar(pls3)
    val settings = G1SGuiSettings(view, pls3)
    val game = G1SGame(scen, pls3)
    (G1SGui(_, game, settings), "JavaFx Game One")

  //  val oview: EMon[SGView] = sts.findKeySetting[Int, SGView](num)
   // (G1SGui(_, scen, oview.getElse(scen.gridSys.defaultView())), "JavaFx One Squares")
  }
}