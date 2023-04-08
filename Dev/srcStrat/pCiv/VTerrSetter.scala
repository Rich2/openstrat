/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** Helper class for setting  [[HCenLayer]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class VTerrSetter(gridIn: HGrid, val terrs: HCenLayer[VTile], val sTerrs: HSideOptLayer[VSide, VSideSome], val corners: HCornerLayer) extends
HSetter[VTile, VSide, VSideSome]
{ implicit val grid: HGrid = gridIn

  sealed trait RowBase

  trait TRowElem extends VTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }

  case class Isle(terr: Land = Plain, sTerr: Water = Sea) extends TRunner with IsleBase
  case class Hland(numIndentedVerts: Int, indentStartIndex: Int, terr: Land = Plain, sideTerrs: Water = Sea) extends TRunner with HlandBase

  /** This is for setting sides on the edge of grids that sit within the heex area of the tile on the neighbouring grid. */
  case class BSide(terr: VSideSome = Sea) extends TRowElem
  {
     def run(row: Int, c: Int): Unit = sTerrs.set(row, c, terr)
  }

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
      multi.foreach { help =>
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

  def tileRun(row: Int, c: Int, tile: VTile): Unit =
  {  terrs.set(row, c, tile)
  }

  /** This is for setting sides on the edge of grids that sit within the heex area of the tile on the neighbouring grid. */
  case class SetSide(c: Int, terr: VSideSome = Sea) extends /*TRowElem with*/ VRowElem {
    override def run(row: Int): Unit = sTerrs.set(row, c, terr)
  }

  case class ThreeWay(c: Int, st: VSideSome = Sea, magnitude: Int = 3) extends VRowElem
  {
    override def run(row: Int): Unit = {
      corners.setVertEqual(row, c, magnitude)
      sTerrs.set(row, c + 1, st)
    }
  }

  /** Creates the head of a strait / river / etc with the head in the given direction and the straits going in the opposite direction. */
  case class Mouth(c: Int, dirn: HVDirn, st: VSideSome = Sea) extends VRowElem with MouthBase

  case class VertIn(c: Int, dirn: HVDirn, side1: VSideSome = Sea, side2: VSideSome = Sea) extends VRowElem with VertInBase
}