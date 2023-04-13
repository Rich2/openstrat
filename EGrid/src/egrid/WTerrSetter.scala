/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[HCenLayer]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: HCenLayer[WTile], val sTerrs: HSideOptLayer[WSide, WSideSome], val corners: HCornerLayer) extends
  HSetter[WTile, WSide, WSideSome]
{
  implicit val grid: HGrid = gridIn

  sealed trait RowBase
  case class TRow(row: Int, mutlis: Multiple[WTileHelper]*) extends RowBase
  trait TRowElem extends WTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }

  trait TRunnerExtra extends TRunner

  case class Isle(terr: Land = Level(), sTerr: Water = Sea) extends TRunner with IsleBase
  case class LeftSide(sTerr: Water = Sea) extends TRunnerExtra with LeftSideBase
  case class Hland(numIndentedVerts: Int, indentStartIndex: Int, terr: Land = Level(), sideTerrs: Water = Sea) extends TRunner with HlandBase
  case class VRow(row: Int, edits: VRowElem*) extends RowBase

  sealed trait VRowElem
  { def run(row: Int): Unit
  }

  val rowDatas: RArr[RowBase]

  def run: Unit = rowDatas.foreach{
    case data: TRow => tRowRun(data)
    case data: VRow => data.edits.foreach(_.run(data.row))
  }

  def tRowRun(inp: TRow): Unit =
  { val row = inp.row
    var c = grid.rowLeftCenC(row)
    inp.mutlis.foreach { multi =>
      multi match {
        case Multiple(value : TRunnerExtra, _) => value.run(row, c)
        case multi => multi.foreach { help =>
          if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
          help match {
            case wt: WTile => terrs.set(row, c, wt)
            case il: TRunner => il.run(row, c)
            case _ =>
          }
          c += 4
        }
      }
    }
  }

  case class SetSide(c: Int, terr: WSideSome = Sea) extends  VRowElem with SetSideBase
  case class Mouth(c: Int, dirn: HVDirnPrimary, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with MouthBase

  case class MouthSpec(c: Int, mouthDirn: HVDirnPrimary, dirn1: HVDirn, dirn2: HVDirn, terr: WSideSome = Sea, magnitude1: Int = 3,
    magnitude2: Int = 3) extends VRowElem with MouthSpecBase

  case class VertIn(c: Int, dirn: HVDirn, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with VertInBase
  case class VertRightsRight(c: Int, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with VertRightsRightBase
}