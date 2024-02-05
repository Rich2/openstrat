/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][WTile], [[HSepLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class VTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[VTile], val sTerrs: LayerHSOptSys[VSide, VSideSome], val corners: HCornerLayer) extends
HSetter[VTile, VSide, VSideSome]
{ implicit val grid: HGrid = gridIn

  sealed trait RowBase

  trait TRowElem extends VTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }
  trait TRunnerExtra extends TRunner

  case class Isle(terr: Land = Plain, sTerr: Water = Sea) extends TRunner with IsleBase

  class Cape private(val indentStartIndex: Int, val numIndentedVerts: Int, val terr: Land = Plain, val sepTerrs: Water = Sea) extends TRunner with
    CapeBase

  object Cape
  {
    def apply(indentStartIndex: Int, numIndentedVerts: Int, terr: Land = Plain, sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, terr, sideTerrs)
  }

  /** Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
  class Isthmus private(val indentIndex: Int, val terr: Land = Plain, val sideTerrs1: Water = Sea, val sideTerrs2: Water = Sea) extends TRunner with
    IsthmusBase

  object Isthmus
  { /** Factory apply method for Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
    def apply(indentIndex: Int, terr: Land = Plain, sideTerrs1: Water = Sea, sideTerrs2: Water = Sea): Isthmus =
      new Isthmus(indentIndex, terr, sideTerrs1, sideTerrs2)
  }

  case class SepB(sTerr: VSideSome = Sea) extends TRunnerExtra with SepBBase
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
          help match {
            case wt: VTile => tileRun(row, c, wt)
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

  case class SetSide(c: Int, terr: VSideSome = Sea) extends VRowElem with SetSideBase
  case class ThreeWay(c: Int, sTerr: VSideSome = Sea, magnitude: Int = 3) extends VRowElem with ThreeWayBase
  case class Mouth(c: Int, dirn: HVDirnPrimary, sTerr: VSideSome = Sea, magnitude: Int = 3) extends VRowElem with MouthBase

  case class BendAll(c: Int, dirn: HVDirn, sTerr: VSideSome = Sea) extends VRowElem with BendAllBase
  { override def magnitude: Int = 3
  }
}