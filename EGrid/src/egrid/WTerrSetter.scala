/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[HCenLayer]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: HCenLayer[WTile], val sTerrs: HSideLayer[WSide], val corners: HCornerLayer)
{
  implicit val grid: HGrid = gridIn
  sealed trait RowBase
  case class TRow(row: Int, mutlis: Multiple[WTile]*) extends RowBase
  case class VRow(row: Int, edits: VRowElem*) extends RowBase

  sealed trait VRowElem
  case class MouthDn(c: Int) extends VRowElem
  case class VertInDR(c: Int, WSTerr: WSTerr = Sea) extends VRowElem

  val rowDatas: RArr[RowBase]

  def run: Unit = rowDatas.foreach{
    case data: TRow =>
    { val row = data.row
      var c = grid.rowLeftCenC(row)
      data.mutlis.foreach { multi =>
        multi.foreach { tile =>
          if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
          terrs.set(row, c, tile)
          tile match {
            case ct: Coastal => {
              corners.setNCornersIn(row, c, ct.numIndentedVerts, ct.indentStartIndex, 7)
              ct.indentedSideIndexForeach { i =>
                val side = HCen(row, c).side(i)
                sTerrs(side) match {
                  case _: WSideMid =>
                  case _: WSideLt if i >= 3 => sTerrs.set(side, WSideBoth(ct.sideTerrs))
                  case _ if i >= 3 => sTerrs.set(side, WSideRt(ct.sideTerrs))
                  case _: WSideRt => sTerrs.set(side, WSideBoth(ct.sideTerrs))
                  case _ => sTerrs.set(side, WSideLt(ct.sideTerrs))
                }
              }
            }
            case _ =>
          }
          c += 4
        }
      }
    }
    case data: VRow =>
    { val row = data.row
      data.edits.foreach{
        case MouthDn (c) =>
        { corners.setMouth3 (row + 1, c)
          sTerrs.set(row - 1, c, WSideMid())
        }
        case VertInDR(c, st) =>{
          corners.setVert5In(row - 1, c + 2)
          sTerrs.set(row, c + 1, WSideMid(st))
          sTerrs.set(row - 1, c, WSideMid(st))
        }
      }
    }
  }
}