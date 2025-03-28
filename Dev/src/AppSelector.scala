/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pgui.*, pStrat.*

/** Object for selecting various JavaFx apps / examples at run time. */
object AppSelector
{ /** Matches the [[String]] for the identifier in DevSettings.rson to a list of Apps. */
  val launchs: ArrPairStr[GuiLaunch] = ArrPairStr[GuiLaunch](
    ("L", learn.LessonsLaunch),
    ("PF", peri.PeriLaunch),
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
    ("IR", pind.IndRevLaunch),
    ("W2", pww2.WW2Launch),
    ("DI", pdiscov.DiscovLaunch),
    ("SO", psors.SorsLaunch),
    ("CV", pCiv.CivLaunch),
    ("NM", pnorm.NormLaunch),
    ("Go", pgo.GoLaunch),
    ("CH", pchess.ChessLaunch),
    ("DR", pchess.pdraughts.DraughtsLaunch),
    ("PL", pspace.PlanetLaunch),
    ("PA", ppart.PhysChemLaunch),
    ("FL", pFlags.FlagsLaunch),
  )

  val ids: ArrPairStr[(CanvasPlatform => Any, String)] = ArrPairStr(
    ("GZ", (pgiza.GizaGui(_), "Giza Pyramids")),
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

  def defaultApp: GuiLaunch = dless.DLessLaunch
  def defaultFunc: (CanvasPlatform => Any, String) = defaultApp.default

  def findChars(key: String, minChars: Int = 2): Option[GuiLaunch] = launchs.findChars(key, minChars)

  def findCharsOrDefault(key: String, minChars: Int = 2): GuiLaunch = launchs.findChars(key, minChars).getOrElse(defaultApp)

  def findErrBiCharsOrDefault(eKey: ErrBi[?, String], minChars: Int = 2): GuiLaunch = eKey.flatOptMap(str => findChars(str, minChars)).getElse(defaultApp)

  def eFindEither(eKey: ErrBi[?, String], minChars: Int = 2): Either[(CanvasPlatform => Any, String), GuiLaunch] =
    eKey.fld(_ => Right(defaultApp), findEither(_, minChars))
  
  def findEither(key: String, minChars: Int = 2): Either[(CanvasPlatform => Any, String), GuiLaunch] = findChars(key, minChars) match
    { case Some(gl) => Right(gl)
      case None => ids.a1FindA2(key).fld(Right(defaultApp), Left(_))
    }
}