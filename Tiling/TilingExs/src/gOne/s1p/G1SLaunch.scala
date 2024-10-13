/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package s1p
import pgui._, pParse._, prid._, psq._, gPlay._

case class G1SGuiSettings(view: SGView, counterSet: RArr[Counter])

object G1SLaunch extends GuiLaunchMore
{ override def settingStr: String = "g1Sq"

  override def default: (CanvasPlatform => Any, String) =
    (G1SGui(_, G1SGame(G1SScen1, G1SScen1.counterSet), G1SGuiSettings(G1SScen1.defaultView(), G1SScen1.counterSet)), "JavaFx Game One Squares")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMonOld[Int] = sts.findSettingOld[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: G1SScen = num match
    { case 1 => G1SScen1
      case 2 => G1SScen2
      case 3 => G1SScen3
      case _ => G1SScen1
    }
    val oSetts: ErrBi[Exception, AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: ErrBi[Exception, RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1: ErrBi[Throwable, StrArr] = sts2.findSettingIdentifierArr("counters")
    val plAll: RArr[Counter] = scen.counterSet
    val pls2: ErrBi[Throwable, RArr[Counter]] = pls1.map { arrA => arrA.optMap(st => plAll.find(_.charStr == st)) }
    val pls3: RArr[Counter] = pls2.getElse(plAll)
    val ov: ErrBi[Throwable, SGView] = sts2.findType[SGView]
    debvar(ov)
    val view: SGView = sts2.findTypeElse(scen.gridSys.defaultView())
    debvar(pls3)
    val settings: G1SGuiSettings = G1SGuiSettings(view, pls3)
    val game: G1SGame = G1SGame(scen, pls3)
    (G1SGui(_, game, settings), "JavaFx Game One Squares")
  }
}