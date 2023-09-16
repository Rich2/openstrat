/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import pgui._, prid._, phex._, pParse._

case class DLessSettings(view: HGView, counterSet: RArr[Nation], isFlat: Boolean = false)

/** Scenario selector and launcher for DLess. */
object DLessLaunch extends GuiLaunchMore
{
  override def settingStr: String = "dless"

  override def default: (CanvasPlatform => Any, String) =
    (DLessGui(_, DLessGame(DLessScen1, DLessScen1.nationSet), DLessSettings(DLessScen1.defaultView(), DLessScen1.nationSet)), "JavaFx Diceless")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: DLessScen = num match
    { case 1 => DLessScen1
      case 2 => DLessScen2
      case _ => DLessScen1
    }
    val oSetts: EMon[AssignMemExpr] = sts.findIntSettingExpr(num)
    val sts2: EMon[RArr[Statement]] = oSetts.map(_.toStatements)
    val pls1 = sts2.findSettingIdentifierArr("nations")
    val plAll: RArr[Nation] = scen.nationSet
    val pls2: EMon[RArr[Nation]] = pls1.map { arrA => arrA.optMap(st => plAll.find(_.name.toLowerCase() == st.toLowerCase())) }
    val pls3: RArr[Nation] = pls2.getElse(scen.nationSet)
    val view: HGView = sts2.findTypeElse(scen.gridSys.defaultView())
    val settings: DLessSettings = DLessSettings(view, pls3)
    val game: DLessGame = DLessGame(scen, pls3)
    (DLessGui(_, game, settings), scen.title +  " Diceless " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}