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

  /** Creates the head of a strait / river with the head up and the river going down. */
  case class MouthUp(c: Int, st: WSTerr = Sea) extends VRowElem


  case class VertInUR(c: Int, st1: WSTerr = Sea, st2: WSTerr = Sea) extends VRowElem
  case class VertInDR(c: Int, st1: WSTerr = Sea, st2: WSTerr = Sea) extends VRowElem
  case class VertInDL(c: Int, st1: WSTerr = Sea, st2: WSTerr = Sea) extends VRowElem
  case class VertInUp(c: Int, st1: WSTerr = Sea, st2: WSTerr = Sea) extends VRowElem

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

        case MouthUp (c, st) =>
        { corners.setMouth3(row + 1, c)
          sTerrs.set(row - 1, c, WSideMid(st))
        }

        case VertInUR(c, st1, st2) =>
        { corners.setVert4In(row + 1, c + 2)
          sTerrs.setIf(row + 1, c, WSideMid(st1))
          sTerrs.setIf(row, c + 1, WSideMid(st2))
        }

        case VertInDR(c, st1, st2) => {
          corners.setVert5In(row - 1, c + 2)
          sTerrs.set(row, c + 1, WSideMid(st1))
          sTerrs.set(row - 1, c, WSideMid(st2))
        }

        case VertInDL(c, st1, st2) => {
          corners.setVert1In(row - 1, c - 2)
          sTerrs.set(row, c - 1, WSideMid(st1))
          sTerrs.set(row - 1, c, WSideMid(st2))
        }

        case VertInUp(c, st1, st2) =>
        { corners.setVert3In(row + 1, c, 3)
          sTerrs.setIf(row, c - 1, WSideMid(st1))
          sTerrs.setIf(row, c + 1, WSideMid(st2))
        }
      }
    }
  }
}