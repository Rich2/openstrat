/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDev
import pCanv._, pStrat._, pGames._, pLearn._

/** This file is normally set by "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" this is to allow the developer to make local
 *  changes to appNum without modifying the master project. If permanent changes are required run:
 *  "git update-index --no-assume-unchanged Core/src/ostrat/pDev/Apps.scala". Stage and commit the changes and then run:
 *  "git update-index --assume-unchanged Core/src/ostrat/pDev/Apps.scala" before pushing or pull-requesting changes." */
object Apps
{
   val ww2Pair: (CanvasPlatform => Unit, String) =  (pWW2.WWIIGui(_, pWW2.WW1940), "World War II") 
   val theMap: Map[Int, (CanvasPlatform => Unit, String)] = Map(
         (0, (new pCiv.CivGui(_), "ScalaFx Rise of Civs")),
         (1, ww2Pair),
         (2, (p1783.Y1783Gui(_, p1783.Nap1), "1783")),
         (3, (FlagsGui(_), "ScalaFx Browser Test")),
         (4, (pSpace.Planets(_), "ScalaFx Planets")),
         (5, (new pZug.ZugGui(_), "ScalaFx Zugfuhrer")),
         (6, (new pDung.DungGui(_), "ScalaFx Dungeon")),
         (7, (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "ScalaFx Formation")),         
         (8, (ColourGen(_), "ScalaFx Some Colours")),
         (9, (p305.BC305Gui(_, p305.Bc1), "BC 305")),
         (10, (pCard.BlackJack(_), "ScalaFx BlackJack")),
         (11, (new pChess.DraughtsGui(_), "Draughts")),
         (21, (Lesson1(_), "ScalaFx Demonstration Canvas 1")),
         (22, (Lesson2(_), "ScalaFx Demonstration Canvas 2")),
         (23, (Lesson3(_), "ScalaFx Demonstration Canvas 3")),
         )
      val appNum: Int = 11
      def curr: (CanvasPlatform => Unit, String) = theMap(appNum)
    
}