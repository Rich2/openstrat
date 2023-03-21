/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[HCenLayer]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: HCenLayer[WTile], val sTerrs: HSideLayer[WSide], val corners: HCornerLayer)
{
  implicit val grid: HGrid = gridIn
  case class TRow(row: Int, mutlis: Multiple[WTile]*)

  val rowDatas: RArr[TRow]
  //var c: Int = 0
  def run: Unit = rowDatas.foreach{data =>
    val row = data.row
    var c = grid.rowLeftCenC(row)
    data.mutlis.foreach { multi =>
      multi.foreach { tile =>
        if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
        terrs.set(row, c, tile)
        tile match
        { case ct: Coastal =>
          { corners.setNCornersIn(row, c, ct.numIndentedVerts, ct.indentStartIndex, 7)
            ct.indentedSideIndexForeach{ i =>
              val side = HCen(row, c).side(i)
              side match {
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
}