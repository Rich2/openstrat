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
  { def run(row: Int): Unit
  }

  /** Creates the head of a strait / river / etc with the head up and the straits going down. */
  case class MouthUp(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth3 (row + 1, c)
      sTerrs.set (row - 1, c, WSideMid (st) )
    }
  }

  /** Creates the head of a strait / river with the head up right and the straits going down left. */
  case class MouthUR(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth4(row + 1, c + 2)
      sTerrs.set(row, c - 1, WSideMid(st))
    }
  }

  /** Creates the head of a strait / river with the head down right and the straits going up left. */
  case class MouthDR(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth5(row - 1, c + 2)
      sTerrs.set(row, c - 1, WSideMid(st))
    }
  }

  /** Creates the head of a strait / river / etc with the head down and the straits going up. */
  case class MouthDn(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth0(row - 1, c)
      sTerrs.set(row + 1, c, WSideMid(st))
    }
  }

  /** Creates the head of a strait / river with the head down left and the straits going up right. */
  case class MouthDL(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth1(row - 1, c - 2)
      sTerrs.set(row, c + 1, WSideMid(st))
    }
  }

  /** Creates the head of a strait / river with the head up left and the straits going down right. */
  case class MouthUL(c: Int, st: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setMouth2(row + 1, c - 2)
      sTerrs.set(row, c + 2, WSideMid(st))
    }
  }

  case class VertInUR(c: Int, upSide: WSTerr = Sea, rightSide: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setVert4In(row + 1, c + 2)
      sTerrs.setIf(row + 1, c, WSideMid(upSide))
      sTerrs.setIf(row, c + 1, WSideMid(rightSide))
    }
  }

  case class VertInDR(c: Int, downSide: WSTerr = Sea, rtSide: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setVert5In(row - 1, c + 2)
      sTerrs.set(row - 1, c, WSideMid(downSide))
      sTerrs.set(row, c + 1, WSideMid(rtSide))
    }
  }
  case class VertInDn(c: Int, leftSide: WSTerr = Sea, rightSide: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setVert0In(row - 1, c, 3)
      sTerrs.setIf(row, c - 1, WSideMid(leftSide))
      sTerrs.setIf(row, c + 1, WSideMid(rightSide))
    }
  }

  case class VertInDL(c: Int, leftSide: WSTerr = Sea, downSide: WSTerr = Sea) extends VRowElem
  {
    override def run(row: Int): Unit =
    { corners.setVert1In(row - 1, c - 2)
      sTerrs.set(row, c - 1, WSideMid(leftSide))
      sTerrs.set(row - 1, c, WSideMid(downSide))
    }
  }

  case class VertInUL(c: Int, st1: WSTerr = Sea, st2: WSTerr = Sea) extends VRowElem
  {
    def run(row: Int): Unit =
    { corners.setVert2In(row + 1, c - 2)
      sTerrs.setIf(row + 1, c, WSideMid(st1))
      sTerrs.setIf(row, c - 1, WSideMid(st2))
    }
  }

  case class VertInUp(c: Int, leftSide: WSTerr = Sea, rightSide: WSTerr = Sea) extends VRowElem
  {
    def run(row: Int): Unit =
    { corners.setVert3In(row + 1, c, 3)
      sTerrs.setIf(row, c - 1, WSideMid(leftSide))
      sTerrs.setIf(row, c + 1, WSideMid(rightSide))
    }
  }

  val rowDatas: RArr[RowBase]

  def run: Unit = rowDatas.foreach{
    case data: TRow =>
    { val row = data.row
      var c = grid.rowLeftCenC(row)
      data.mutlis.foreach { multi =>
        multi.foreach { tile =>
          if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
          terrs.set(row, c, tile)
          tile match
          { case ct: Coastal =>
            { corners.setNCornersIn(row, c, ct.numIndentedVerts, ct.indentStartIndex, 7)
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

    case data: VRow => data.edits.foreach(_.run(data.row))
  }
}