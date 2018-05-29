/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pFx
import scalafx.scene.canvas.{Canvas => CanvasFx} 
import geom._
import pEarth._
import pStrat._

object PlayArgs
{
   val a0: (CanvasFx => Unit, String) = (cf => new pCiv.CivGui(CanvDispFx(cf)), "ScalaFx Rise of Civs")
   val a1: (CanvasFx => Unit, String) = (cf => pWW2.WWIIGui(CanvDispFx(cf), pWW2.WW1940), "World War II")
   val a2: (CanvasFx => Unit, String) = (cf => p1783.Y1783Gui(CanvDispFx(cf), p1783.Nap1), "1783")
   val a3: (CanvasFx => Unit, String) = (cf => FlagsGui(CanvDispFx(cf)), "ScalaFx Browser Test")
   val a4: (CanvasFx => Unit, String) = (cf => geom.pSpace.Planets(CanvDispFx(cf)), "ScalaFx Planets")
   val a5: (CanvasFx => Unit, String) = (cf => new pZug.ZugGui(CanvDispFx(cf)), "ScalaFx Zugfuhrer")
   val a6: (CanvasFx => Unit, String) = (cf => new pDung.DungGui(CanvDispFx(cf)), "ScalaFx Dungeon")
   val a7: (CanvasFx => Unit, String) = (cf => new EGridGui(CanvDispFx(cf), EuropeWestGrid[TerrOnly](TerrOnly.tileMaker)),
         "ScalaFx Europe Grid")
   val a8: (CanvasFx => Unit, String) = (cf => TestCanv(CanvDispFx(cf)), "ScalaFx Simple Test Canvas")
   val a9: (CanvasFx => Unit, String) = (cf => ColourGen(CanvDispFx(cf)), "ScalaFx Some Colours")
   val a10: (CanvasFx => Unit, String) = (cf => p305.BC305Gui(CanvDispFx(cf), p305.BC1), "BC 305")
   val a11: (CanvasFx => Unit, String) = (cf => pCard.BlackJack(CanvDispFx(cf)), "ScalaFx BlackJack")
   val a12: (CanvasFx => Unit, String) = (cf => new Checkers(CanvDispFx(cf)), "Checkers")
}

import PlayArgs._
object PlayApp extends RSApp(a1)