/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  val terrs: HCenLayer[VTile]
  val sTerrs: HSideOptLayer[VSide]
  val corners: HCornerLayer
  val lunits: HCenArrLayer[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Land())
  terrs.setRowEnd(12, 20, Land(Hill), Land(Mountain) * 2, Land() * 3)
  terrs.setRowEnd(4, 4, Land(Hill) * 3, Land(Plain) * 7)
  override val sTerrs: HSideOptLayer[VSide] = gridSys.newSideOpts[VSide]
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridReg(2, 14, 4, 42)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Sea)

  terrs.setRow(10, Land(), Head3Land(5), Sea * 4, Island() * 2, Sea * 2)
  terrs.setRow(8, Land(Plain) * 4, Head5Land(5), Sea * 3, Land(), Sea)
  terrs.setRow(6, Land(Plain) * 3, Sea * 2, Head4Land(4, Mountain), Sea, Head2Land(4), Land() * 2)
  terrs.setRowEndUnchecked(4, Land(Mountain) * 3, Land(Plain) * 2)
  terrs.setRowSame(2, Land())

  override val sTerrs: HSideOptLayer[VSide] =
  { val res = gridSys.newSideOpts[VSide]
    res.setSomeInts(VSideCen(River), 3, 39, 4, 38, 5, 39, 6, 40)
    res.setSomeInts(VSideCen(), 4, 30)
    res.setSomeInts(VSideLt(), 7,27,  6,28,  9,21,  8,22,  7,21)
    res
  }

  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer =
  { val res = gridSys.newHVertOffsetLayer
    res.setMouth3(8, 40)
    res.setVert2In(6, 38)
    res.setVert5In(4, 40)
    res.setVert4In(4, 40)
    res.setMouth5(2, 42)

    res.setMouth3(6, 30)
    res.setMouth0(2, 30)
    res
  }
}