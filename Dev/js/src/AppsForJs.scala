package ostrat
package pSJs
import pCanv._, pStrat._
/** This file is normally set by "git update-index --skip-worktree Core/src/ostrat/pDev/Apps.scala" this is to allow the developer to make local
 *  changes to appNum without modifying the master project. If permanent changes are required run:
 *  "git update-index --no-skip-worktree openstrat/Dev/js/src/AppsForJs.scala". Stage and commit the changes and then run:
 *  "git update-index --skip-worktree openstrat/Dev/js/src/AppsForJs.scala" before pushing or pull-requesting changes." */
object AppsForJs
{
  val theMap: Map[String, (CanvasPlatform => Any, String)] = Map(
    ("1", (pWW2.WWIIGui(_, pWW2.WW1940), "World War II") ),
    ("2", (p1783.Y1783Gui(_, p1783.Nap1), "1783")),
    ("3", (pFlags.FlagsGui(_), "Js Flags")),
    ("4", (pSpace.Planets(_), "Js Planets")),
    ("5", (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),
    ("6", (pDung.DungeonGui(_, pDung.Dungeon1), "Js Dungeon")),
    ("7", (gOne.GOneGui(_, gOne.OneScen1$), "Js Game One")),
    ("8", (ColourGen(_), "Js Some Colours")),
    ("9", (p305.BC305Gui(_, p305.Bc1), "Js BC 305")),
    ("10", (pCard.BlackJackGui(_), "Js BlackJack")),
    ("11", (new pChess.DraughtsGui(_), "Draughts")),
    ("12", (new pGames.pSimp.UnusGui(_, pGames.pSimp.Simp1()), "Simplest Game")),
    ("13", (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "JavaFx Formation")),
    ("14", (new pReactor.ReactorGUI(_), "reactor")),
    ("15", (new pChess.ChessGui(_), "Chess")),
    ("16", (new pFlags.FlagSelectorGUI(_), "Flag Fun")),
    ("17", (gOne.IrrGui(_), "Js irregular Grid Game One")),
    ("18", (gOne.SqOneGui(_), "Js Square Grid Game One")),
    ("19", (new pDung.DungeonGuiOld(_), "Js Dungeon")),

    ("c1", (new pCiv.CivGui(_, pCiv.Civ1), "Js Rise of Civs")),


    ("Z1", (new pZug.ZugGui(_, pZug.Zug1), "Js Zugfuhrer Z1 Britain")),
    ("Z2", (new pZug.ZugGui(_, pZug.Zug2), "Js Zugfuhrer Z2 Britain")),

    ("Z11", (new pZug.ZugGuiOld(_, pZug.ZGameOld1, pZug.PlayBritain), "Js Zugfuhrer Z1 Britain")),
    ("Z12", (new pZug.ZugGuiOld(_, pZug.ZGameOld1, pZug.PlayGermany), "Js Zugfuhrer Z1 Germany")),
    ("Z13", (new pZug.ZugGuiOld(_, pZug.ZGameOld1, pZug.PlayGermanyBritain), "Js Zugfuhrer Z1 Play both")),
    ("Z14", (new pZug.ZugGuiOld(_, pZug.ZGameOld2, pZug.PlayGermanyFrance), "Js Zugfuhrer Z2 Play both")),

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
  def curr: (CanvasPlatform => Any, String) = theMap("17")
}
