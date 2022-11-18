/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pgui._, pStrat._

/** Object for selecting various JavaFx apps / examples at run time. */
object Apps
{
  val idMap: Map[String, GuiLaunch] = Map(
    ("L", learn.Lessons),
    ("W2", pWW2.WW2Launch),
    ("EA", pEarth.EarthBasicLaunch),
    ("EG", egrid.EGridLaunch),
    ("G1h", gOne.hp1.G1HLaunch),
    ("G1s", gOne.sp1.G1SLaunch),
    ("G2h", gTwo.h2p.G2HLaunch),
    ("G4", gFour.FourLaunch),
    ("Go", pgo.GoLaunch),
    ("Z", pzug.ZugLaunch),
    ("DG", pDung.DungLaunch),
    ("CV", pCiv.CivLaunch),
    ("CH", pchess.ChessLaunch),
  )

  val strMap: Map[String, (CanvasPlatform => Any, String)] = Map(
    ("Y2", (p1783.Y1783GuiOld(_, p1783.Nap1), "1783")),
    ("Y3", (pFlags.FlagsGui(_), "JavaFx Flags")),
    ("Y4", (pspace.PlanetsGui(_), "JavaFx Planets")),
    ("Y8", (ColourGen(_), "JavaFx Some Colours")),
    ("Y9", (p305.BC305Gui(_, p305.Bc1Old), "BC 305")),
    ("Y10", (pCard.BlackJackGui(_), "JavaFx BlackJack")),
    ("Y11", (pchess.pdraughts.DraughtsGui(_, pchess.pdraughts.DraughtsStart), "Draughts")),
    ("Y13", (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "JavaFx Formation")),
    ("Y14", (pReactor.ReactorGUI(_), "reactor")),
    ("Y16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),
    ("Y18", (pAltReact.AltReacGui(_, 8, 8), "Alternate Reactor")),
    ("AT", (learn.CArcExs(_), "Temporary - Testing Arcs")), //this is a temporary app
    ("HW", (learn.HelloWorld(_), "JavaFx Demonstration Canvas Hello World")), //Static Graphics
  )

  def default: (CanvasPlatform => Any, String) = pWW2.WW2Launch.default

  /** Change appNum to change the default loaded application. */
  def curr(str: String): (CanvasPlatform => Any, String) = strMap.getOrElse(str, strMap("1"))
}