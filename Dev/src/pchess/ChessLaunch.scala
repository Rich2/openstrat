/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import pgui._, pParse._

object ChessLaunch extends GuiLaunchMore
{
  override def settingStr: String = "chess"

  override def default: (CanvasPlatform => Any, String) = (ChessGui(_, ChessStart), "JavaFx Chess")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    num match
    { case 1 => (ChessGui(_, ChessStart), "JavaFx Chess")
      case 2 => (pdraughts.DraughtsGui(_, pdraughts.DraughtsStart), "JavaFx Draughts")
      case _ => (ChessGui(_, ChessStart), "JavaFx Chess")
    }

    //(GOneGui(_, scen, oview.getElse(scen.grid.coordCen.view())), "JavaFx Game One")
  }
}