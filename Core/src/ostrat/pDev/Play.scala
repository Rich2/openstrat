/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDev
import pDisp._
import pStrat._
import pGames._

object Play
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
         (7, (pFormation.BattleGui(_, pFormation.Nap1), "ScalaFx Formation")),
         (8, (TestCanv(_), "ScalaFx Simple Test Canvas")),
         (9, (ColourGen(_), "ScalaFx Some Colours")),
         (10, (p305.BC305Gui(_, p305.Bc1), "BC 305")),
         (11, (pCard.BlackJack(_), "ScalaFx BlackJack")),
         (12, (new pChess.DraughtsGui(_), "Draughts"))         
         )
      
        def curr(appNum: Int): (CanvasPlatform => Unit, String) = theMap(appNum)
    
}