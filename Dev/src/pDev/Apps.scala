/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDev
import pCanv._, pStrat._, pGrid._

object Apps
{
  val theMap: Map[String, (CanvasPlatform => Any, String)] = Map(
      ("1", (pWW2.WWIIGuiOld(_, pWW2.WW1940), "World War II") ),
      ("2", (p1783.Y1783GuiOld(_, p1783.Nap1), "1783")),
      ("3", (pFlags.FlagsGui(_), "JavaFx Flags")),
      ("4", (pSpace.Planets(_), "JavaFx Planets")),
      ("5", (new pEarth.pEurope.NWGui(_, pEarth.pEurope.EuropeNWTerr, 466 rr 202), "JavaFx NW Wurope Grid")),
      ("6", (pDung.DungeonGui(_, pDung.Dungeon1), "JavaFx Dungeon")),
      ("7", (new pCiv.CivGui(_, pCiv.Civ1), "JavaFx Rise of Civs")),
      ("8", (ColourGen(_), "JavaFx Some Colours")),
      ("9", (p305.BC305Gui(_, p305.Bc1), "BC 305")),
      ("10", (pCard.BlackJackGui(_), "JavaFx BlackJack")),
      ("11", (new pChess.DraughtsGui(_, pChess.DraughtsStart), "Draughts")),

      ("13", (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "JavaFx Formation")),
      ("14", (new pReactor.ReactorGUI(_), "reactor")),
      ("15", (new pChess.ChessGui(_, pChess.ChessStart), "Chess")),
      ("16", (new pFlags.FlagSelectorGUI(_), "Flag Fun")),
      ("17", (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),

      ("G1", (gOne.GOneGui(_, gOne.OneScen1), "JavaFx Game One")),
      ("G2", (gOne.IrrGui(_), "JavaFx irregular Grid Game One")),
      ("G3", (gOne.SqOneGui(_), "JavaFx Square Grid Game One")),

      ("Z1", (new pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")),
      ("Z2", (new pZug.ZugGui(_, pZug.Zug2), "JavaFx Zugfuhrer Z2 Britain")),
      ("Z3", (new pZug.ZugGui(_, pZug.Zug3), "JavaFx Zugfuhrer Z3 France")),
      
      ("A1", (learn.LessonA1(_), "JavaFx Demonstration Canvas 1")), //Static Graphics
      ("A2", (learn.LessonA2(_), "JavaFx Demonstration Canvas 2")),
      ("A3", (learn.LessonA3(_), "JavaFx Demonstration Canvas 3")),
      ("A4", (learn.LessonA4(_), "JavaFx Demonstration Canvas 4")),
      ("A5", (learn.LessonA5(_), "JavaFx Demonstration Canvas 5")),
      ("A6", (learn.LessonA6(_), "JavaFx Demonstration Canvas 6")),
      ("A7", (learn.LessonA7(_), "JavaFx Demonstration Canvas 7")),
      ("A8", (learn.LessonA8(_), "JavaFx Demonstration Canvas 8")),
      ("A9", (learn.LessonA9(_), "JavaFx Demonstration Canvas 9")),
      ("A10", (learn.LessonA10(_), "JavaFx Demonstration Canvas 10")),
      
      ("B1", (learn.LessonB1(_), "JavaFx Demonstration Animated Canvas 1")), //Moving Graphics
      ("B2", (learn.LessonB2(_), "JavaFx Demonstration Animated Canvas 2")),
      ("B3", (learn.LessonB3(_), "JavaFx Demonstration Animated Canvas 3")),
      
      ("C1", (learn.LessonC1(_), "JavaFx Demonstration Interactive Canvas 1")), //User interactive graphics
      ("C2", (learn.LessonC2(_), "JavaFx Demonstration Interactive Canvas 2")),
      ("C3", (learn.LessonC3(_), "JavaFx Demonstration Interactive Canvas 3")),
      ("C4", (learn.LessonC4(_), "JavaFx Demonstration Interactive Canvas 4")),
      ("C5", (learn.LessonC5(_), "JavaFx Demonstration Interactive Canvas 5")),
      ("C6", (learn.LessonC6(_), "JavaFx Demonstration Interactive Canvas 6")),
      
      ("D1", (learn.LessonD1(_), "JavaFx Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
      ("D2", (learn.LessonD2(_), "JavaFx Demonstration Persistence 2")),
      ("D3", (learn.LessonD3(_), "JavaFx Demonstration Persistence 3")),
      ("D4", (learn.LessonD4(_), "JavaFx Demonstration Persistence 4")),
      ("D5", (learn.LessonD5(_), "JavaFx Demonstration Persistence 5")),

      ("E1", (learn.LessonE1(_), "JavaFx Demonstration Games 1")), //Building turn based games.
      ("E2", (learn.LessonE2(_), "JavaFx Demonstration Games 2")),
  )
  
  /** Change appNum to change the default loaded application. */
  def curr(str: String): (CanvasPlatform => Any, String) = theMap.getOrElse(str, theMap("7"))
}
