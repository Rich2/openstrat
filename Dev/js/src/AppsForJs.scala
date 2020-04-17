package ostrat
package pSJs
import pCanv._, pStrat._, pGrid._
/** This file is normally set by "git update-index --skip-worktree Core/src/ostrat/pDev/Apps.scala" this is to allow the developer to make local
 *  changes to appNum without modifying the master project. If permanent changes are required run:
 *  "git update-index --no-skip-worktree openstrat/Dev/js/src/AppsForJs.scala". Stage and commit the changes and then run:
 *  "git update-index --skip-worktree openstrat/Dev/js/src/AppsForJs.scala" before pushing or pull-requesting changes." */
object AppsForJs
{
  val theMap: Map[String, (CanvasPlatform => Any, String)] = Map(
    ("1", (pWW2.WWIIGuiOld(_, pWW2.WW1940), "World War II") ),
    ("2", (p1783.Y1783GuiOld(_, p1783.Nap1), "1783")),
    ("3", (pFlags.FlagsGui(_), "Js Flags")),
    ("4", (pSpace.Planets(_), "Js Planets")),

    ("6", (pDung.DungeonGui(_, pDung.Dungeon1), "Js Dungeon")),
    ("7", (pCiv.CivGui(_, pCiv.Civ1), "Js Rise of Civs")),
    ("8", (ColourGen(_), "Js Some Colours")),
    ("9", (p305.BC305Gui(_, p305.Bc1), "Js BC 305")),
    ("10", (pCard.BlackJackGui(_), "Js BlackJack")),
    ("11", (pChess.DraughtsGui(_, pChess.DraughtsStart), "Draughts")),

    ("13", (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "JavaFx Formation")),
    ("14", (pReactor.ReactorGUI(_), "reactor")),
    ("15", (pChess.ChessGui(_, pChess.ChessStart), "Chess")),
    ("16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),
    ("17", (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),

    ("EG1", (pEarth.E80GridGui(_, pEarth.pEurope.EuropeNWTerr, 472 rr 206), "Js NW Wurope Grid")),
    ("EG2", (pEarth.E80GridGui(_, pEarth.pEurope.EuropeNWTerr, 466 rr 402), "Js NE Wurope Grid")),

    ("G1", (gOne.GOneGui(_, gOne.OneScen1), "Js Game One")),
    ("G2", (gOne.IrrGui(_), "Js irregular Grid Game One")),
    ("G3", (gOne.SqOneGui(_), "Js Square Grid Game One")),

    ("Z1", (pZug.ZugGui(_, pZug.Zug1), "Js Zugfuhrer Z1 Britain")),
    ("Z2", (pZug.ZugGui(_, pZug.Zug2), "Js Zugfuhrer Z2 Britain")),
    ("Z3", (pZug.ZugGui(_, pZug.Zug3), "Js Zugfuhrer Z3 France")),

    ("A1", (learn.LessonA1(_), "Js Demonstration Canvas 1")), //Static Graphics
    ("A2", (learn.LessonA2(_), "Js Demonstration Canvas 2")),
    ("A3", (learn.LessonA3(_), "Js Demonstration Canvas 3")),
    ("A4", (learn.LessonA4(_), "Js Demonstration Canvas 4")),
    ("A5", (learn.LessonA5(_), "Js Demonstration Canvas 5")),
    ("A6", (learn.LessonA6(_), "Js Demonstration Canvas 6")),
    ("A7", (learn.LessonA7(_), "Js Demonstration Canvas 7")),
    ("A8", (learn.LessonA8(_), "Js Demonstration Canvas 8")),
    ("A9", (learn.LessonA9(_), "Js Demonstration Canvas 9")),
    ("A10", (learn.LessonA10(_), "Js Demonstration Canvas 10")),

    ("B1", (learn.LessonB1(_), "Js Demonstration Animated Canvas 1")), //Moving Graphics
    ("B2", (learn.LessonB2(_), "Js Demonstration Animated Canvas 2")),
    ("B3", (learn.LessonB3(_), "Js Demonstration Animated Canvas 3")),

    ("C1", (learn.LessonC1(_), "Js Demonstration Interactive Canvas 1")), //User interactive graphics
    ("C2", (learn.LessonC2(_), "Js Demonstration Interactive Canvas 2")),
    ("C3", (learn.LessonC3(_), "Js Demonstration Interactive Canvas 3")),
    ("C4", (learn.LessonC4(_), "Js Demonstration Interactive Canvas 4")),
    ("C5", (learn.LessonC5(_), "Js Demonstration Interactive Canvas 5")),
    ("C6", (learn.LessonC6(_), "Js Demonstration Interactive Canvas 6")),

    ("D1", (learn.LessonD1(_), "Js Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
    ("D2", (learn.LessonD2(_), "Js Demonstration Persistence 2")),
    ("D3", (learn.LessonD3(_), "Js Demonstration Persistence 3")),
    ("D4", (learn.LessonD4(_), "Js Demonstration Persistence 4")),
    ("D5", (learn.LessonD5(_), "Js Demonstration Persistence 5")),

    ("E1", (learn.LessonE1(_), "Js Demonstration Games 1")), //Building turn based games.
    ("E2", (learn.LessonE2(_), "Js Demonstration Games 2")),
  )

  /** Change appNum to change the default loaded application. */
  def curr: (CanvasPlatform => Any, String) = theMap("EG1")
}
