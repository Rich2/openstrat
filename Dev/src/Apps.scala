/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pgui._, pStrat._

/** Object for selecting various JavaFx apps / examples at run time. */
object Apps
{ /** Matches the [[String]] for the identifier in DevSettings.rson to a list of Apps. */
  val launchs: StrPairArr[GuiLaunch] = StrPairArr[GuiLaunch](
    ("L", learn.Lessons),
    ("DL", dless.DLessLaunch),
    ("W1", pww1.WW1Launch),
    ("BC", p305.BcLaunch),
    ("EA", pEarth.EarthBasicLaunch),
    ("UL", puloc.ULocLaunch),
    ("EG", egrid.EGridLaunch),
    ("G1h", gOne.h1p.G1HLaunch),
    ("G1s", gOne.s1p.G1SLaunch),
    ("G2h", gTwo.h2p.G2HLaunch),
    ("G2s", gTwo.s2p.G2SLaunch),
    ("G3h", gThree.h3p.G3HLaunch),
    ("Z", pzug.ZugLaunch),
    ("DG", pDung.DungLaunch),
    ("NA", pnap.NapLaunch),
    ("W2", pww2.WW2Launch),
    ("DI", pdisc.DiscLaunch),
    ("SO", psors.SorsLaunch),
    ("CV", pCiv.CivLaunch),
    ("NM", pnorm.NormLaunch),
    ("Go", pgo.GoLaunch),
    ("CH", pchess.ChessLaunch),
    ("DR", pchess.pdraughts.DraughtsLaunch),
  )

  val ids: StrPairArr[(CanvasPlatform => Any, String)] = StrPairArr(
    ("PL", (pspace.PlanetsGui(_), "JavaFx Planets")),
    ("FL", (pFlags.FlagsGui(_), "JavaFx Flags")),
    ("Y1", (pZero.TessGui(_), "Tess")),
    ("Y8", (ColourGen(_), "JavaFx Some Colours")),
    ("Y10", (pCard.BlackJackGui(_), "JavaFx BlackJack")),
    ("Y11", (pchess.pdraughts.DraughtsGui(_, pchess.pdraughts.DraughtsStart), "Draughts")),
    ("Y13", (pCloseOrder.BattleGui(_, pCloseOrder.Nap1), "JavaFx Formation")),
    ("Y14", (pReactor.ReactorGUI(_), "reactor")),
    ("Y16", (pFlags.FlagSelectorGUI(_), "Flag Fun")),
    ("Y18", (pAltReact.AltReacGui(_, 8, 8), "Alternate Reactor")),
    ("AT", (learn.CArcExs(_), "Temporary - Testing Arcs")), //this is a temporary app
    ("HW", (learn.HelloWorld(_), "JavaFx Demonstration Canvas Hello World")), //Static Graphics
  )

  def default: (CanvasPlatform => Any, String) = dless.DLessLaunch.default
}