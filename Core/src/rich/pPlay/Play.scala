/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pPlay
import geom._
import pDisp._
import pEarth._
import pStrat._

object Play
{   
   val theMap: Map[Int, (CanvasLike => Unit, String)] = Map(
         (0, (new pCiv.CivGui(_), "ScalaFx Rise of Civs")),
         (1, (pWW2.WWIIGui(_, pWW2.WW1940), "World War II")),
         (2, (p1783.Y1783Gui(_, p1783.Nap1), "1783")),
         (3, (FlagsGui(_), "ScalaFx Browser Test")),
         (4, (pSpace.Planets(_), "ScalaFx Planets")),
         (5, (new pZug.ZugGui(_), "ScalaFx Zugfuhrer")),
         (6, (new pDung.DungGui(_), "ScalaFx Dungeon")),
         (7, (new EGridGui(_, EuropeWestGrid[TerrOnly](TerrOnly.tileMaker)), "ScalaFx Europe Grid")),
         (8, (TestCanv(_), "ScalaFx Simple Test Canvas")),
         (9, (ColourGen(_), "ScalaFx Some Colours")),
         (10, (p305.BC305Gui(_, p305.BC1), "BC 305")),
         (11, (pCard.BlackJack(_), "ScalaFx BlackJack")),
         (12, (new pChess.DraughtsGui(_), "Draughts")),         
         )
    /** Change the number below to select a different application */     
    val curr: (CanvasLike => Unit, String) = theMap(12)
}