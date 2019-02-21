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
  val theMap: Map[Int, (CanvasPlatform => Unit, String)] = Map(
      (0, (new pGames.pCiv.CivGui(_), "ScalaFx Rise of Civs")),
      (1, (pGames.pWW2.WWIIGui(_, pGames.pWW2.WW1940), "World War II") ),
      (2, (pGames.p1783.Y1783Gui(_, pGames.p1783.Nap1), "1783")),
      (3, (FlagsGui(_), "ScalaFx Flags")),
      (4, (pGames.pSpace.Planets(_), "ScalaFx Planets")),
      (5, (pEarth.pFlat.FlatEarthGui(_), "Flat Earth")),
      (6, (new pGames.pDung.DungeonGui(_), "ScalaFx Dungeon")),
      (7, (pGames.pCloseOrder.BattleGui(_, pGames.pCloseOrder.Nap1), "ScalaFx Formation")),         
      (8, (ColourGen(_), "ScalaFx Some Colours")),
      (9, (pGames.p305.BC305Gui(_, pGames.p305.Bc1), "BC 305")),
      (10, (pGames.pCard.BlackJackGui(_), "ScalaFx BlackJack")),
      (11, (new pGames.pChess.DraughtsGui(_), "Draughts")),
      
      (20, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayBritain), "ScalaFx Zugfuhrer Z1 Britain")),
      (21, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermany), "ScalaFx Zugfuhrer Z1 Germany")),
      (22, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame1, pGames.pZug.PlayGermanyBritain), "ScalaFx Zugfuhrer Z1 Play both")),
      (23, (new pGames.pZug.ZugGui(_, pGames.pZug.ZGame2, pGames.pZug.PlayGermanyFrance), "ScalaFx Zugfuhrer Z2 Play both")),
      (26, (new pGames.pReactor.ReactorGUI(_), "reactor")),
      
      (31, (learn.LessonA1(_), "ScalaFx Demonstration Canvas 1")), //Static Graphics
      (32, (learn.LessonA2(_), "ScalaFx Demonstration Canvas 2")),
      (33, (learn.LessonA3(_), "ScalaFx Demonstration Canvas 3")),
      (34, (learn.LessonA4(_), "ScalaFx Demonstration Canvas 4")),
      (35, (learn.LessonA5(_), "ScalaFx Demonstration Canvas 5")),
      (36, (learn.LessonA6(_), "ScalaFx Demonstration Canvas 6")),
      (37, (learn.LessonA7(_), "ScalaFx Demonstration Canvas 7")),
      (38, (learn.LessonA8(_), "ScalaFx Demonstration Canvas 8")),
      (39, (learn.LessonA9(_), "ScalaFx Demonstration Canvas 9")),
      
      (41, (learn.LessonB1(_), "ScalaFx Demonstration Animated Canvas 1")), //Moving Graphics
      (42, (learn.LessonB2(_), "ScalaFx Demonstration Animated Canvas 2")),
      (43, (learn.LessonB3(_), "ScalaFx Demonstration Animated Canvas 3")),
      
      (51, (learn.LessonC1(_), "ScalaFx Demonstration Interactive Canvas 1")), //User interactive graphics
      (52, (learn.LessonC2(_), "ScalaFx Demonstration Interactive Canvas 2")),
      (53, (learn.LessonC3(_), "ScalaFx Demonstration Interactive Canvas 3")),
      (54, (learn.LessonC4(_), "ScalaFx Demonstration Interactive Canvas 4")),
      (55, (learn.LessonC5(_), "ScalaFx Demonstration Interactive Canvas 5")),
      (56, (learn.LessonC6(_), "ScalaFx Demonstration Interactive Canvas 6")),
      
      (61, (learn.LessonD1(_), "ScalaFx Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
      (62, (learn.LessonD2(_), "ScalaFx Demonstration Persistence 2")),
      (63, (learn.LessonD3(_), "ScalaFx Demonstration Persistence 3")),
      (64, (learn.LessonD4(_), "ScalaFx Demonstration Persistence 4")),
      (65, (learn.LessonD5(_), "ScalaFx Demonstration Persistence 5")),
      
      (71, (learn.LessonE1(_), "ScalaFx Demonstration Games 1")), //Building turn based games.
      (72, (learn.LessonE2(_), "ScalaFx Demonstration Games 2")),
  )
  
  /** Change appNum to change the loaded application. */
  val appNum: Int = 1

  def curr: (CanvasPlatform => Unit, String) = theMap(appNum)
}
