/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pCanv._, pStrat._, pGrid._

/** Object for selecting various JavaFx apps /examples at run time. */
object Apps
{
  val idMap: Map[String, GuiLaunch] = Map(
    ("G1", gOne.OneLaunch),
    ("Z", pZug.ZugLaunch),
    ("LA", learn.LessonsA),
  )

  val theMap: Map[String, (CanvasPlatform => Any, String)] = Map(
    ("Y1", (pWW2.WWIIGuiOld(_, pWW2.WW1940), "World War II") ),
    ("Y2", (p1783.Y1783GuiOld(_, p1783.Nap1), "1783")),
    ("Y3", (pFlags.FlagsGui(_), "JavaFx Flags")),
    ("Y4", (pSpace.Planets(_), "JavaFx Planets")),

    ("Y6", (pDung.DungeonGui(_, pDung.Dungeon1), "JavaFx Dungeon")),
    ("Y7", (pCiv.CivGui(_, pCiv.Civ1), "JavaFx Rise of Civs")),
    ("Y8", (ColourGen(_), "JavaFx Some Colours")),
    ("Y9", (p305.BC305Gui(_, p305.Bc1), "BC 305")),
    ("Y10", (pCard.BlackJackGui(_), "JavaFx BlackJack")),
    ("Y11", (pDraughts.DraughtsGui(_, pDraughts.DraughtsStart), "Draughts")),

    ("Y13", (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "JavaFx Formation")),
    ("Y14", (pReactor.ReactorGUI(_), "reactor")),
    ("Y15", (pChess.ChessGui(_, pChess.ChessStart), "Chess")),
    ("Y16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),
    
    ("Y18", (pAltReact.AltReacGui(_, 8, 8), "Alternate Reactor")),

    ("EG1", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNWTerrOld, 472 rr 204), "JavaFx NW Wurope Grid")),
    ("EG2", (pEarth.E80GridGuiOld(_, pEarth.pEurope.EuropeNETerrOld, 456 rr 388), "JavaFx NE Wurope Grid")),

    ("G2", (gTwo.GTwoGui(_, gTwo.TwoScen1), "JavaFx Game Two")),
    ("G3", (gThree.GThreeGui(_, gThree.ThreeScen2), "JavaFx Game Three")),
    ("G31", (gThree.GThreeGui(_, gThree.ThreeScen1), "JavaFx Game Three")),
    ("G33", (gThree.GThreeGui(_, gThree.ThreeScen3), "JavaFx Game Three")),

    ("Z1", (pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")),
    ("X1", (pZug.ZugGuiOld(_, pZug.Zug1Old), "Old JavaFx Zugfuhrer Z1 Britain")),
    ("Z2", (pZug.ZugGui(_, pZug.Zug2), "JavaFx Zugfuhrer Z2 Britain")),
    ("X2", (pZug.ZugGuiOld(_, pZug.Zug2Old), "Old JavaFx Zugfuhrer Z2 Britain")),
    ("Z3", (pZug.ZugGui(_, pZug.Zug3), "JavaFx Zugfuhrer Z3 France")),
    ("X3", (pZug.ZugGuiOld(_, pZug.Zug3Old), "Old JavaFx Zugfuhrer Z3 France")),

    ("AT", (learn.CArcExs(_), "Temporary - Testing Arcs")), //this is a temporary app
    ("HW", (learn.HelloWorld(_), "JavaFx Demonstration Canvas Hello World")), //Static Graphics

    ("B1", (learn.LsB1(_), "JavaFx Demonstration Animated Canvas 1")), //Moving Graphics
    ("B2", (learn.LsB2(_), "JavaFx Demonstration Animated Canvas 2")),
    ("B3", (learn.LsB3(_), "JavaFx Demonstration Animated Canvas 3")),

    ("C1", (learn.LsC1(_), "JavaFx Demonstration Interactive Canvas 1")), //User interactive graphics
    ("C2", (learn.LsC2(_), "JavaFx Demonstration Interactive Canvas 2")),
    ("C3", (learn.LsC3(_), "JavaFx Demonstration Interactive Canvas 3")),
    ("C4", (learn.LsC4(_), "JavaFx Demonstration Interactive Canvas 4")),
    ("C5", (learn.LsC5(_), "JavaFx Demonstration Interactive Canvas 5")),
    ("C6", (learn.LsC6(_), "JavaFx Demonstration Interactive Canvas 6")),
    ("C7", (learn.LsC7(_), "JavaFx Demonstration Interactive Canvas 7: Exploring Beziers")),
    ("C8", (learn.LsC8(_), "JavaFx Demonstration Interactive Canvas 8: More Dragging")),
    
    ("D1", (learn.LsD1(_), "JavaFx Demonstration Persistence 1")), //Persistence, saving and retrieving data outside of code
    ("D2", (learn.LsD2(_), "JavaFx Demonstration Persistence 2")),
    ("D3", (learn.LsD3(_), "JavaFx Demonstration Persistence 3")),
    ("D4", (learn.LsD4(_), "JavaFx Demonstration Persistence 4")),
    ("D5", (learn.LsD5(_), "JavaFx Demonstration Persistence 5")),

    ("E1", (learn.LsE1(_), "JavaFx Demonstration Games 1")), //Building turn based games.
    ("E2", (learn.LsE2(_), "JavaFx Demonstration Games 2")),
  )

  /** Change appNum to change the default loaded application. */
  def curr(str: String): (CanvasPlatform => Any, String) = theMap.getOrElse(str, theMap("1"))
  import pParse._
  def gen(expr: Expr): (CanvasPlatform => Any, String) = expr match {
    case StringToken(_, str) => theMap.getOrElse(str, theMap("1"))
    case it: IdentifierToken if it.srcStr == "G" => (pZug.ZugGui(_, pZug.Zug1), "JavaFx Zugfuhrer Z1 Britain")
    case _ => {debvar(expr); theMap("1") }
  }
  def eGen(eExpr: EMon[Expr]): (CanvasPlatform => Any, String) = eExpr.fold{ debvar(eExpr); theMap("1") }(gen(_))
  /*match {
    case Good(StringToken(_, str)) => theMap.getOrElse(str, theMap("1"))
    case Good(expr) => {debvar(expr); theMap("1") }
    case _ => { debvar(eExpr); theMap("1") }
  }*/
}
