/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import pgui._, pParse._, prid._, psq._, gPlay._

/** Settings for the sole GUI player. */
case class G2SGuiSettings(view: SGView, counterSet: RArr[Counter])

object G2SLaunch extends GuiLaunchMore
{
  override def settingStr: String = "G2Sq"

  override def default: (CanvasPlatform => Any, String) =
    (G2SGui(_, G2SGame(G2SScen1, G2SScen1.counterSet), G2SGuiSettings(G2SScen1.defaultView(), G2SScen1.counterSet)), "JavaFx Game Two Square")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    val scen: G2SqScen = num match
    { case 1 => G2SScen1
      case 2 => G2SScen2
      case 3 => G2SScen3
      //case 4 => G2SScen4
     // case 5 => G2SScen5
     // case 6 => G2SScen6
    //  case 7 => G2SScen7
      case _ => G2SScen1
    }

    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("counters")
    val plAll = scen.counterSet
    val pls2 = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3 = pls2.getElse(plAll)
    val view: SGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings = G2SGuiSettings(view, pls3)
    val game = G2SGame(scen, pls3)
    (G2SGui(_, game, settings), "JavaFx Game Two Hexs")
  }
}
