/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess; package pdraughts
import pgui._, pParse._

object DraughtsLaunch extends GuiLaunchMore
{
  override def settingStr: String = "draughts"

  override def default: (CanvasPlatform => Any, String) = (DraughtsGui(_, DraughtsStart), "JavaFx Draughts")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    num match
    { //case 1 => (ChessGui(_, ChessStart), "JavaFx Chess")
      case 1 => (DraughtsGui(_, DraughtsStart), "JavaFx Draughts")
      case _ => (DraughtsGui(_, DraughtsStart), "JavaFx Chess")
    }

    //(GOneGui(_, scen, oview.getElse(scen.grid.coordCen.view())), "JavaFx Game One")
  }
}