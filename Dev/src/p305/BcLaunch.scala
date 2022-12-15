/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import pgui._, prid._, phex._, egrid._, pParse._

/** Scenario selector and launcher for Bc305. */
object BcLaunch extends GuiLaunchMore
{
  override def settingStr: String = "bc305"

  override def default: (CanvasPlatform => Any, String) = (Bc305Gui(_, BcScen2, BcScen2.defaultView()), "JavaFx BC305")

  override def fromStatments(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val num: Int = sts.findSettingElse("scen", 1)
    val flat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    def egg(scen: EScenFlat): (CanvasPlatform => Any, String) =
      (EGridFlatGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), scen.title + " Flat JavaFx")

    def gwg(scen: EScenBasic): (CanvasPlatform => Any, String) =
      (GridWorldGui(_, scen, oview.getElse(scen.gridSys.coordCen.view())), scen.title + " Globe JavaFx")

    val scen: EScenFlat = num match
    { case 2 => BcScen2
      case _ => BcScen2
    }
    scen match {
      case s: EScenBasic if !flat => gwg(s)
      case s => egg(s)
    }
}
}