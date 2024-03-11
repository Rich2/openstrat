/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][VTile], [[HSepLayer]][VSep] and [[HCornerLayer]] at the same time." */
abstract class VTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[VTile], val sTerrs: LayerHSOptSys[VSep, VSepSome], val corners: HCornerLayer) extends
  HSetter[VTile, VSep, VSepSome]
{ implicit val grid: HGrid = gridIn

  sealed trait RowBase

  trait TRowElem extends VTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }

  trait TRunnerExtra extends TRunner

  case class Isle10(terr: Land = Plain, sepTerrs: Water = Sea) extends TRunner with Isle10Base

  /** Needs removing. */
  class Cape private(val indentStartIndex: Int, val numIndentedVerts: Int, val terr: Land, val sepTerrs: Water) extends TRunner with CapeBase
  { override def magnitude: Int = 7
  }

  object Cape
  {
    def apply(indentStartIndex: Int, numIndentedVerts: Int, terr: Land = Plain, sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, terr, sideTerrs)
  }

  /** Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
  class Isthmus private(val indentIndex: Int, val terr: Land = Plain, val sepTerrs1: Water = Sea, val sepTerrs2: Water = Sea) extends TRunner with
    IsthmusBase

  object Isthmus
  { /** Factory apply method for Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
    def apply(indentIndex: Int, terr: Land = Plain, sideTerrs1: Water = Sea, sideTerrs2: Water = Sea): Isthmus =
      new Isthmus(indentIndex, terr, sideTerrs1, sideTerrs2)
  }

  case class SepB(sTerr: VSepSome = Sea) extends TRunnerExtra with SepBBase
  case class VRow(row: Int, edits: VRowElem*) extends RowBase

  sealed trait VRowElem
  { def run(row: Int): Unit
  }

  case class TRow(row: Int, mutlis: Multiple[VTileHelper]*) extends RowBase

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
        case Multiple(value: TRunnerExtra, _) => value.run(row, c)
        case multi => multi.foreach { help =>
          if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
          help match
          { case wt: VTile => tileRun(row, c, wt)
            case il: TRunner => il.run(row, c)
            case _ =>
          }
          c += 4
        }
      }
    }
  }

  def tileRun(row: Int, c: Int, tile: VTile): Unit =
  {  terrs.set(row, c, tile)
  }

  case class SetSep(c: Int, terr: VSepSome = Sea) extends VRowElem with SetSepBase

  class ThreeUp(val c: Int, val upTerr: VSepSome, val downRightTerr: VSepSome, val downLeftTerr: VSepSome, val magUR: Int, val magDn: Int, val magUL: Int)
    extends VRowElem with ThreeUpBase

  object ThreeUp
  { def apply(c: Int, sTerr: VSepSome = Sea): ThreeUp = new ThreeUp(c, sTerr, sTerr, sTerr, 3, 3, 3)
    def apply(c: Int, upTerr: VSepSome, downRightTerr: VSepSome, downLeftTerr: VSepSome): ThreeUp = new ThreeUp(c, upTerr, downRightTerr, downLeftTerr, 3, 3, 3)
  }

  case class ThreeDown(val c: Int, val upRightTerr: VSepSome, val downTerr: VSepSome, val upLeftTerr: VSepSome, val magUp: Int, val magDR: Int, val magDL: Int)
    extends VRowElem with ThreeDownBase

  object ThreeDown
  { def apply(c: Int, sTerr: VSepSome = Sea): ThreeDown = new ThreeDown(c, sTerr, sTerr, sTerr, 3, 3, 3)

    def apply(c: Int, upRightTerr: VSepSome, downTerr: VSepSome, upLeftTerr: VSepSome): ThreeDown =
      new ThreeDown(c, upRightTerr, downTerr, upLeftTerr, 3, 3, 3)
  }

  /** Source or end point for a river or straits. */
  case class Source(c: Int, dirn: HVDirnPrimary, sTerr: VSepSome = Sea, magLt: Int = 3, magRt: Int = 3) extends VRowElem with SourceBase

  class BendAll(val c: Int, val dirn: HVDirn, val leftTerr: VSepSome, val rightTerr: VSepSome) extends VRowElem with BendAllBase
  { override def magnitude: Int = 3
  }

  object BendAll
  {
    def apply(c: Int, dirn: HVDirn, sTerr: VSepSome = Sea): BendAll = new BendAll(c, dirn, sTerr, sTerr)

    def apply(c: Int, dirn: HVDirn, leftTerr: VSepSome, rightTerr: VSepSome): BendAll = new BendAll(c, dirn, leftTerr, rightTerr)
  }
}