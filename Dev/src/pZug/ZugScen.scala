/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._, pCanv._

/** ZugFuhrer scenario turn state. */
trait ZugScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[ZugTerr]

  def sTerrs: HSideBooleans
  val lunits: HCenArrArr[Squad]
}

/** ZugFuhrer scenario turn 0 state. */
trait ZugScenStart extends ZugScen
{ override def turn: Int = 0
}

object ZugLaunch extends GuiLaunchStd
{
  override def settingStr: String = "zugFuhrer"

  override def default: (CanvasPlatform => Any, String) = (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match
  { case 1 => (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")
    case 2 => (ZugGui(_, Zug2), "JavaFx Zugfuhrer Z2 Britain")
    case 3 => (ZugGui(_, Zug3), "JavaFx Zugfuhrer Z3 Britain")
    case _ => (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")
  }
}

/** ZugFuhrer scenario 1. */
object Zug1 extends ZugScenStart
{ override implicit val grid: HGrid = HGridReg(2, 14, 4, 48)
  val terrs: HCenArr[ZugTerr] = grid.newTileArr[ZugTerr](Plain)
  def tr(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  tr(yRow = 12, cStart = 4, WheatField * 2)
  tr(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  tr(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  tr(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  tr(4, 4, WheatField * 2)
  tr(2, 6, WheatField)

  val sTerrs: HSideBooleans = grid.newSideBooleans
  val wall1: HSides = HSides(14 hs 36, 13 hs 35, 12 hs 34, 11 hs 35, 10 hs 36)
  //sTerrs.setTrues(wall1)

  val lunits: HCenArrArr[Squad] = grid.newTileArrArr[Squad]
  lunits.set(2, 30, Squad(Britain, Move(2 hc 26, 2 hc 22)))
  lunits.set(10, 38, Squad(Britain, Fire(6 hc 18)))
  lunits.set(4, 32, Squad(Britain, Move(4 hc 28, 4 hc 24, 4 hc 20)))
  lunits.set(6, 46, Squad(Britain, Move(6 hc 42)))
  lunits.set(14, 46, Squad(Britain, Move(14 hc 42, 14 hc 38, 12 hc 36)))
  lunits.set(10, 46, Squad(Britain))
  lunits.set(6, 18, Squad(Germany, Fire(4 hc 32)))
  lunits.set(10, 18, Squad(Germany, Fire(4 hc 32)))
  lunits.set(6, 10, Squad(Germany, Move(8 hc 8, 8 hc 12, 8 hc 16)))
}

/** ZugFuhrer scenario 2. */
object Zug2 extends ZugScenStart
{
  override implicit val grid: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain)
  val sTerrs: HSideBooleans = grid.newSideBooleans
  val lunits: HCenArrArr[Squad] = grid.newTileArrArr[Squad]
}

/** ZugFuhrer scenario 3. */
object Zug3 extends ZugScenStart
{
  override implicit val grid: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Plain)
  val sTerrs: HSideBooleans = grid.newSideBooleans
  //sTerrs.gridSetTrues(grid.SidesHorr(7, 5, 37))

  val lunits = grid.newTileArrArr[Squad]
  lunits.setSame(Squad( Germany), 6 hc 18, 6 hc 30)
  lunits.setSame(Squad(France), 10 hc 14, 10 hc 22, 10 hc 30)
}