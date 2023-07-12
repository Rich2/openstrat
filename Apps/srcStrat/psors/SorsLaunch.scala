/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package psors
import pgui._, prid._, phex._, pParse._

/** Scenario selector and launcher for Sors Imperiorum. */
object SorsLaunch extends GuiLaunchMore
{
  override def settingStr: String = "sors"

  override def default: (CanvasPlatform => Any, String) = (SorsGui(_, SorsScen1, SorsScen1.defaultView()), "Sors Imperiorum")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val num: Int = sts.findSettingElse("scen", 1)
    val isFlat: Boolean = sts.findSettingElse("flat", false)

    val oview: EMon[HGView] = sts.findKeySetting[Int, HGView](num)

    val scen: SorsScen = num match
    { case 1 => SorsScen1
      //case 2 => DiscScen2
      case _ => SorsScen1
    }

    (SorsGui(_, scen, oview.getElse(scen.gridSys.coordCen.view()), isFlat), scen.title +  " Sors Imperiorum " + ife(isFlat, "Flat", "Globe") + " JavaFx")
  }
}