/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import prid._, phex._

/** ZugFuhrer scenario turn state. */
trait ZugScen extends HSysTurnScen
{ origSelf =>

  override def title: String = "Zugfuhrer scenario"

  /** tile terrain. */
  val terrs: LayerHcRefSys[ZugTerr]

  val sTerrs: HSideBoolLayer
  val corners: HCornerLayer
  val lunits: HCenRArrLayer[Squad]
  def setSquadMove(r: Int, c: Int, polity: Polity, steps: HStep*): Unit = lunits.set1(r, c, Squad(polity, Move(HStepArr(steps:_*))))

  def endTurn(): ZugScen = new ZugScen
  {
    override val terrs: LayerHcRefSys[ZugTerr] = origSelf.terrs
    override val sTerrs: HSideBoolLayer = origSelf.sTerrs
    override val corners: HCornerLayer = origSelf.corners
    override val lunits: HCenRArrLayer[Squad] = origSelf.lunits

    override implicit val gridSys: HGridSys = origSelf.gridSys

    /** The turn number. This will normally start at 0. The player will then give their instructions for turn 1. The scenario will take these orders /
     * instructions and return the new game state at turn 1. */
    override def turn: Int = origSelf.turn + 1
  }
}

/** ZugFuhrer scenario turn 0 state. */
trait ZugScenStart extends ZugScen
{ override def turn: Int = 0
}

/** ZugFuhrer scenario 1. */
object Zug1 extends ZugScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 14, 2, 48)
  val terrs: LayerHcRefSys[ZugTerr] = LayerHcRefSys[ZugTerr](Plain)
  def cr(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRowEnd(yRow, cStart, tileValues :_*)(gridSys)
  cr(yRow = 12, cStart = 4, WheatField * 2, Plain * 10)
  cr(10, 2, WheatField * 2, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding, Plain * 2)
  cr(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake, Plain * 5)
  cr(6, 2, WheatField * 2, Plain * 2, StoneBuilding, Plain, Lake, Plain * 5)
  cr(4, 4, WheatField * 2, Plain * 10)
  cr(2, 2, WheatField * 2, Plain * 10)

  val sTerrs: HSideBoolLayer = gridSys.newSideBooleans
  override val corners: HCornerLayer = HCornerLayer()

  sTerrs.setTruesInts(14,36,  13,35,  12,34,  11,35,  10,36,  9,37,  9,39,  9,41,  9,43,  9,45,  9,47)

  corners.setMouth3OffGrid(16, 36, 1)
  corners.setBend2All(14, 34, 1)
  corners.setBend5All(12, 36, 1)
  corners.setBend4All(12, 36, 1)
  corners.setBend1All(10, 34, 1)
  corners.setBend4All(10, 38, 1)
  corners.setBend3All(10, 38, 1)
  corners.setBend0All(8, 40, 1)
  corners.setBend3All(10, 42, 1)
  corners.setBend0All(8, 44, 1)
  corners.setBend3All(10, 46, 1)
  corners.setMouth4OffGrid(10, 50, 1)

  val lunits: HCenRArrLayer[Squad] = HCenRArrLayer[Squad]()
  setSquadMove(2, 30, Britain, HexLt, HexLt)
  lunits.set1(10, 38, Squad(Britain, Fire(6 hc 18)))
  setSquadMove(4, 32, Britain, HexLt, HexLt)
  setSquadMove(6, 46, Britain, HexLt)
  setSquadMove(14, 46, Britain, HexLt, HexLt, HexDL)
  lunits.set1(10, 46, Squad(Britain))
  lunits.set1(6, 18, Squad(Germany, Fire(4 hc 32)))
  lunits.set1(10, 18, Squad(Germany, Fire(4 hc 32)))
  setSquadMove(6, 10, Germany, HexUL, HexRt, HexRt)
}

/** ZugFuhrer scenario 2. */
object Zug2 extends ZugScenStart
{
  override implicit val gridSys: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = LayerHcRefSys[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRowEnd(yRow, cStart, tileValues :_*)(gridSys)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain * 4)

  val sTerrs: HSideBoolLayer = gridSys.newSideBooleans
  override val corners: HCornerLayer = HCornerLayer()
  val lunits: HCenRArrLayer[Squad] = HCenRArrLayer[Squad]()
}

/** ZugFuhrer scenario 3. */
object Zug3 extends ZugScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = LayerHcRefSys[ZugTerr](Plain)
  val sTerrs: HSideBoolLayer = gridSys.newSideBooleans
  override val corners: HCornerLayer = HCornerLayer()

  override val lunits = HCenRArrLayer[Squad]()
  lunits.setSame(Squad( Germany), 6 hc 18, 6 hc 30)
  lunits.setSame(Squad(France), 10 hc 14, 10 hc 22, 10 hc 30)
}