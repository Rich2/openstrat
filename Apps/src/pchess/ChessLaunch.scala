/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pchess
import pgui._, pParse._

object ChessLaunch extends GuiLaunchMore
{
  override def settingStr: String = "chess"

  override def default: (CanvasPlatform => Any, String) = (ChessGui(_, ChessStart), "JavaFx Chess")

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val oScen: EMon[Int] = sts.findSetting[Int]("scen")
    val num: Int = oScen.getElse(1)
    num match
    { case 1 => (ChessGui(_, ChessStart), "JavaFx Chess")
      case _ => (ChessGui(_, ChessStart), "JavaFx Chess")
    }
  }
}