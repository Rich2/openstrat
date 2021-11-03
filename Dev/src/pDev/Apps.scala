/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pgui._, pStrat._, pGrid._, pParse._

/** Object for selecting various JavaFx apps /examples at run time. */
object Apps
{
  val idMap: Map[String, GuiLaunch] = Map(
    ("L", learn.Lessons),
    ("W2", pWW2.WW2Launch),
    ("WM", pEarth.EarthBasicLaunch),
    ("G1", gOne.OneLaunch),
    ("G2", gTwo.TwoLaunch),
    ("G3", gThree.ThreeLaunch),
    ("Z", pZug.ZugLaunch),
    ("CV", pCiv.CivLaunch),
  )

  val strMap: Map[String, (CanvasPlatform => Any, String)] = Map(
    ("Y2", (p1783.Y1783GuiOld(_, p1783.Nap1), "1783")),
    ("Y3", (pFlags.FlagsGui(_), "JavaFx Flags")),
    ("Y4", (pSpace.Planets(_), "JavaFx Planets")),
    ("Y6", (pDung.DungeonGui(_, pDung.Dungeon1), "JavaFx Dungeon")),
    ("Y8", (ColourGen(_), "JavaFx Some Colours")),
    ("Y9", (p305.BC305Gui(_, p305.Bc1), "BC 305")),
    ("Y10", (pCard.BlackJackGui(_), "JavaFx BlackJack")),
    ("Y11", (pDraughts.DraughtsGui(_, pDraughts.DraughtsStart), "Draughts")),
    ("Y13", (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "JavaFx Formation")),
    ("Y14", (pReactor.ReactorGUI(_), "reactor")),
    ("Y15", (pChess.ChessGui(_, pChess.ChessStart), "Chess")),
    ("Y16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),

    ("Y18", (pAltReact.AltReacGui(_, 8, 8), "Alternate Reactor")),

    ("EG1", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNWTerrOld, 472 rr 204), "JavaFx NW Wurope Grid")),
    ("EG2", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNETerrOld, 456 rr 388), "JavaFx NE Wurope Grid")),

    ("Z1", (pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")),
    ("X1", (pZug.ZugGuiOld(_, pZug.Zug1Old), "Old JavaFx Zugfuhrer Z1 Britain")),
    ("Z2", (pZug.ZugGui(_, pZug.Zug2), "JavaFx Zugfuhrer Z2 Britain")),
    ("X2", (pZug.ZugGuiOld(_, pZug.Zug2Old), "Old JavaFx Zugfuhrer Z2 Britain")),
    ("Z3", (pZug.ZugGui(_, pZug.Zug3), "JavaFx Zugfuhrer Z3 France")),
    ("X3", (pZug.ZugGuiOld(_, pZug.Zug3Old), "Old JavaFx Zugfuhrer Z3 France")),

    ("AT", (learn.CArcExs(_), "Temporary - Testing Arcs")), //this is a temporary app
    ("HW", (learn.HelloWorld(_), "JavaFx Demonstration Canvas Hello World")), //Static Graphics
  )

  def default: (CanvasPlatform => Any, String) = pWW2.WW2Launch.default

  /** Change appNum to change the default loaded application. */
  def curr(str: String): (CanvasPlatform => Any, String) = strMap.getOrElse(str, strMap("1"))
}
