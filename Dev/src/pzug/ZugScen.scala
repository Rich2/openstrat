/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import prid._, phex._

/** ZugFuhrer scenario turn state. */
trait ZugScen extends HSysTurnScen
{ origSelf =>

  /** tile terrain. */
  def terrs: HCenDGrid[ZugTerr]

  def sTerrs: HSideBoolDGrid
  val lunits: HCenArrDGrid[Squad]
  def setSquadMove(r: Int, c: Int, polity: Polity, steps: HDirn*): Unit = lunits.set(r, c, Squad(polity, Move(HDirnArr(steps:_*))))

  def endTurn(): ZugScen = new ZugScen
  {
    /** tile terrain. */
    override def terrs: HCenDGrid[ZugTerr] = origSelf.terrs

    override def sTerrs: HSideBoolDGrid = origSelf.sTerrs

    override val lunits: HCenArrDGrid[Squad] = origSelf.lunits

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
{ override implicit val gridSys: HGrid = HGridRegOrig(2, 14, 2, 48)
  val terrs: HCenDGrid[ZugTerr] = gridSys.newHCenDGrid[ZugTerr](Plain)
  def cr(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.completeRow(yRow, cStart, tileValues :_*)(gridSys)
  cr(yRow = 12, cStart = 4, WheatField * 2, Plain * 10)
  cr(10, 2, WheatField * 2, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding, Plain * 2)
  cr(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake, Plain * 5)
  cr(6, 2, WheatField * 2, Plain * 2, StoneBuilding, Plain, Lake, Plain * 5)
  cr(4, 4, WheatField * 2, Plain * 10)
  cr(2, 2, WheatField * 2, Plain * 10)

  val sTerrs: HSideBoolDGrid = gridSys.newSideBooleans
  val wall1: HSideArr = HSideArr(14 hs 36, 13 hs 35, 12 hs 34, 11 hs 35, 10 hs 36)
  //sTerrs.setTrues(wall1)

  val lunits: HCenArrDGrid[Squad] = gridSys.newHCenArrDGrid[Squad]
  setSquadMove(2, 30, Britain, HStepLt, HStepLt)
  lunits.set(10, 38, Squad(Britain, Fire(6 hc 18)))
  setSquadMove(4, 32, Britain, HStepLt, HStepLt)
  setSquadMove(6, 46, Britain, HStepLt)
  setSquadMove(14, 46, Britain, HStepLt, HStepLt, HexDL)
  lunits.set(10, 46, Squad(Britain))
  lunits.set(6, 18, Squad(Germany, Fire(4 hc 32)))
  lunits.set(10, 18, Squad(Germany, Fire(4 hc 32)))
  setSquadMove(6, 10, Germany, HStepUL, HexRt, HexRt)
}

/** ZugFuhrer scenario 2. */
object Zug2 extends ZugScenStart
{
  override implicit val gridSys: HGrid = HGridRegOrig(2, 10, 4, 38)
  val terrs = gridSys.newHCenDGrid[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.completeRow(yRow, cStart, tileValues :_*)(gridSys)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain * 4)
  val sTerrs: HSideBoolDGrid = gridSys.newSideBooleans
  val lunits: HCenArrDGrid[Squad] = gridSys.newHCenArrDGrid[Squad]
}

/** ZugFuhrer scenario 3. */
object Zug3 extends ZugScenStart
{
  override implicit val gridSys: HGrid = HGridRegOrig(2, 10, 4, 38)
  val terrs = gridSys.newHCenDGrid[ZugTerr](Plain)
  val sTerrs: HSideBoolDGrid = gridSys.newSideBooleans
  //sTerrs.gridSetTrues(grid.SidesHorr(7, 5, 37))

  val lunits = gridSys.newHCenArrDGrid[Squad]
  lunits.setSame(Squad( Germany), 6 hc 18, 6 hc 30)
  lunits.setSame(Squad(France), 10 hc 14, 10 hc 22, 10 hc 30)
}