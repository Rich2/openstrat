/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import pgui._, pParse._, prid._, psq._

object DungLaunch extends GuiLaunchMore
{
  override def settingStr: String = "gTwo"

  override def default: (CanvasPlatform => Any, String) = (DungeonGui(_, Dungeon1/*, Dungeon1.grid.defaultView()*/), "JavaFx Game Two")
  override def fromStatments(sts: RArr[Statement]): (CanvasPlatform => Any, String) = {
    val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: DungeonScen = num match {
      case 1 => Dungeon1
      case 2 => Dungeon1
      case _ => Dungeon1
    }
    val oview: EMon[SqGridView] = sts.findKeySetting[Int, SqGridView](num)
    (DungeonGui(_, scen/*, oview.getElse(scen.grid.defaultView())*/), "JavaFx Game Two")
  }
}