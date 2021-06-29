/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import pCanv._, pStrat._, pGrid._

/** This file was meant to allow the developer to make local changes to appNum without modifying the master project. */
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
    ("11", (pDraughts.DraughtsGui(_, pDraughts.DraughtsStart), "Draughts")),

    ("13", (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "JavaFx Formation")),
    ("14", (pReactor.ReactorGUI(_), "reactor")),
    ("15", (pChess.ChessGui(_, pChess.ChessStart), "Chess")),
    ("16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),

    ("18", (pAltReact.AltReacGui(_, 8, 8), "Alternate Reactor")),

    ("EG1", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNWTerrOld, 472 rr 204), "Js NW Wurope Grid")),
    ("EG2", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNETerrOld, 456 rr 388), "Js NE Wurope Grid")),

    ("G1", (gOne.GOneGui(_, gOne.OneScen1), "Js Game One")),
    ("G1i", (gOne.GOneGui(_, gOne.OneScen2), "Js irregular Grid Game One")),
    ("G2", (gTwo.GTwoGui(_, gTwo.TwoScen1), "Js Square Grid Game Two")),

    ("Z1", (pZug.ZugGuiOld(_, pZug.Zug1Old), "Js Zugfuhrer Z1 Britain")),
    ("Z2", (pZug.ZugGuiOld(_, pZug.Zug2Old), "Js Zugfuhrer Z2 Britain")),
    ("Z3", (pZug.ZugGuiOld(_, pZug.Zug3Old), "Js Zugfuhrer Z3 France")),

    ("AT", (learn.CArcExs(_), "Temporary - Testing Arcs")), //this is a temporary app
    ("A1", (learn.LsA1a(_), "Js Demonstration Canvas 1")), //Static Graphics
    ("A2", (learn.LsA2(_), "Js Demonstration Canvas 2")),
    ("A3", (learn.LsA3(_), "Js Demonstration Canvas 3")),
    ("A4", (learn.LsA4(_), "Js Demonstration Canvas 4")),
    ("A5", (learn.LsA5(_), "Js Demonstration Canvas 5")),
    ("A6", (learn.LsA6(_), "Js Demonstration Canvas 6")),
    ("A7", (learn.LsA7(_), "Js Demonstration Canvas 7")),
    ("A8", (learn.LsA8(_), "Js Demonstration Canvas 8")),
    ("A9", (learn.LsA9(_), "Js Demonstration Canvas 9")),
    ("A10", (learn.LsA10(_), "Js Demonstration Canvas 10")),
    ("A11", (learn.LsA11(_), "Reflecting a point across a Line")),
    
    ("B1", (learn.LsB1(_), "Js Demonstration Animated Canvas 1")), //Moving Graphics
    ("B2", (learn.LsB2(_), "Js Demonstration Animated Canvas 2")),
    ("B3", (learn.LsB3(_), "Js Demonstration Animated Canvas 3")),

    ("C1", (learn.LsC1(_), "Js Demonstration Interactive Canvas 1")), //User interactive graphics
    ("C2", (learn.LsC2(_), "Js Demonstration Interactive Canvas 2")),
    ("C3", (learn.LsC3(_), "Js Demonstration Interactive Canvas 3")),
    ("C4", (learn.LsC4(_), "Js Demonstration Interactive Canvas 4")),
    ("C5", (learn.LsC5(_), "Js Demonstration Interactive Canvas 5")),
    ("C6", (learn.LsC6(_), "Js Demonstration Interactive Canvas 6")),
    ("C7", (learn.LsC7(_), "Js Demonstration Interactive Canvas 7: Exploring Beziers")), //
    
    ("D1", (learn.LsD1(_), "Js Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
    ("D2", (learn.LsD2(_), "Js Demonstration Persistence 2")),
    ("D3", (learn.LsD3(_), "Js Demonstration Persistence 3")),
    ("D4", (learn.LsD4(_), "Js Demonstration Persistence 4")),
    ("D5", (learn.LsD5(_), "Js Demonstration Persistence 5")),

    ("E1", (learn.LsE1(_), "Js Demonstration Games 1")), //Building turn based games.
    ("E2", (learn.LsE2(_), "Js Demonstration Games 2")),
  )

  /** Change appNum to change the default loaded application. */
  def curr: (CanvasPlatform => Any, String) = theMap("1")
}
