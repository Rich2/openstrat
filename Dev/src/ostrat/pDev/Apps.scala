/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDev
import pCanv._, pStrat._

/** This file is normally set by "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" this is to allow the developer to make local
 *  changes to appNum without modifying the master project. If permanent changes are required run:
 *  "git update-index --no-assume-unchanged Core/src/ostrat/pDev/Apps.scala". Stage and commit the changes and then run:
 *  "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" before pushing or pull-requesting changes." */
object Apps
{
  val theMap: Map[String, (CanvasPlatform => Any, String)] = Map(
      ("0", (new pGames.pCiv.CivGui(_), "ScalaFx Rise of Civs")),
      ("1", (pGames.pWW2.WWIIGui(_, pGames.pWW2.WW1940), "World War II") ),
      ("2", (pGames.p1783.Y1783Gui(_, pGames.p1783.Nap1), "1783")),
      ("3", (FlagsGui(_), "ScalaFx Flags")),
      ("4", (pGames.pSpace.Planets(_), "ScalaFx Planets")),
      ("5", (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),
      ("6", (new pGames.pDung.DungeonGui(_), "ScalaFx Dungeon")),
      ("7", (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "ScalaFx Formation")),
      ("8", (ColourGen(_), "ScalaFx Some Colours")),
      ("9", (pGames.p305.BC305Gui(_, pGames.p305.Bc1), "BC 305")),
      ("10", (pGames.pCard.BlackJackGui(_), "ScalaFx BlackJack")),
      ("11", (new pGames.pChess.DraughtsGui(_), "Draughts")),
      ("12", (new pGames.pSimp.UnusGui(_, pGames.pSimp.Simp1()), "Simplest Game")),
      ("13", (pGames.pNew.TGui(_), "New Grid")),
      ("14", (new pGames.pReactor.ReactorGUI(_), "reactor")),
      
      ("Z0", (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayBritain), "ScalaFx Zugfuhrer Z1 Britain")),
      ("Z1", (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermany), "ScalaFx Zugfuhrer Z1 Germany")),
      ("Z2", (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermanyBritain), "ScalaFx Zugfuhrer Z1 Play both")),
      ("23", (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame2, pGames.pZug.PlayGermanyFrance), "ScalaFx Zugfuhrer Z2 Play both")),

      
      ("A1", (learn.LessonA1(_), "ScalaFx Demonstration Canvas 1")), //Static Graphics
      ("A2", (learn.LessonA2(_), "ScalaFx Demonstration Canvas 2")),
      ("A3", (learn.LessonA3(_), "ScalaFx Demonstration Canvas 3")),
      ("A4", (learn.LessonA4(_), "ScalaFx Demonstration Canvas 4")),
      ("A5", (learn.LessonA5(_), "ScalaFx Demonstration Canvas 5")),
      ("A6", (learn.LessonA6(_), "ScalaFx Demonstration Canvas 6")),
      ("A7", (learn.LessonA7(_), "ScalaFx Demonstration Canvas 7")),
      ("A8", (learn.LessonA8(_), "ScalaFx Demonstration Canvas 8")),
      ("A9", (learn.LessonA9(_), "ScalaFx Demonstration Canvas 9")),
      ("A10", (learn.LessonA10(_), "ScalaFx Demonstration Canvas 10")),
      
      ("B1", (learn.LessonB1(_), "ScalaFx Demonstration Animated Canvas 1")), //Moving Graphics
      ("B2", (learn.LessonB2(_), "ScalaFx Demonstration Animated Canvas 2")),
      ("B3", (learn.LessonB3(_), "ScalaFx Demonstration Animated Canvas 3")),
      
      ("C1", (learn.LessonC1(_), "ScalaFx Demonstration Interactive Canvas 1")), //User interactive graphics
      ("C2", (learn.LessonC2(_), "ScalaFx Demonstration Interactive Canvas 2")),
      ("C3", (learn.LessonC3(_), "ScalaFx Demonstration Interactive Canvas 3")),
      ("C4", (learn.LessonC4(_), "ScalaFx Demonstration Interactive Canvas 4")),
      ("C5", (learn.LessonC5(_), "ScalaFx Demonstration Interactive Canvas 5")),
      ("C6", (learn.LessonC6(_), "ScalaFx Demonstration Interactive Canvas 6")),
      
      ("D1", (learn.LessonD1(_), "ScalaFx Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
      ("D2", (learn.LessonD2(_), "ScalaFx Demonstration Persistence 2")),
      ("D3", (learn.LessonD3(_), "ScalaFx Demonstration Persistence 3")),
      ("D4", (learn.LessonD4(_), "ScalaFx Demonstration Persistence 4")),
      ("D5", (learn.LessonD5(_), "ScalaFx Demonstration Persistence 5")),
      
      ("E1", (learn.LessonE1(_), "ScalaFx Demonstration Games 1")), //Building turn based games.
      ("E2", (learn.LessonE2(_), "ScalaFx Demonstration Games 2")),

      ("G1", (learn.LessonG1(_), "ScalaFx Demonstration Grid Canvas 1")),
  )
  
  /** Change appNum to change the loaded application. */
  val appStr: String = "1"
  def curr(str: String): (CanvasPlatform => Any, String) = theMap.getOrElse(str, theMap("A1"))
}