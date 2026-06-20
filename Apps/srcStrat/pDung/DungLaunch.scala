/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import geom.pgui.*, pParse.*, prid.psq.*

object DungLaunch extends GuiLaunchMore
{  override def settingStr: String = "gTwo"
  override def default: (CanvasPlatform => Any, String) = (DungeonGui(_, Dungeon1/*, Dungeon1.grid.defaultView()*/), "JavaFx DungeonGame")
  
  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: ExcMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)

    val scen: DungeonScen = num match
    { case 1 => Dungeon1
      case 2 => Dungeon1
      case _ => Dungeon1
    }
    val oview: ExcMon[SGView] = sts.findKeySetting[Int, SGView](num)
    (DungeonGui(_, scen/*, oview.getElse(scen.grid.defaultView())*/), "JavaFx Dungeon Game")
  }
}