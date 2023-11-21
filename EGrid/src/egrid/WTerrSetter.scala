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

  case class Isle(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with IsleBase

  object Isle
  {
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle = Isle(Land(elev, biome, landUse), sTerr)
  }

  /** Cape / headland / peninsula for [[WTile]]s. */
  class Cape private(val indentStartIndex: Int, val numIndentedVerts: Int, val terr: Land = Land(Level, Temperate, CivMix), val sideTerrs: Water = Sea) extends
    TRunner with CapeBase

  object Cape
  {
    def apply(indentStartIndex: Int, numIndentedVerts: Int = 1, terr: Land = Land(), sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, terr, sideTerrs)

    def a(indentStartIndex: Int, numIndentedVerts: Int = 1, terr: Land = Land(), sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, terr, sideTerrs)

    def apply(indentStartIndex: Int, numIndentedVerts: Int, elev: Lelev, biome: Climate, landUse: LandUse, sideTerrs: Water): Cape =
      new Cape(indentStartIndex, numIndentedVerts, Land(elev, biome, landUse), sideTerrs)
  }

  case class SideB(sTerr: Water = Sea) extends TRunnerExtra with SideBBase
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

  /** Sets all the corners of Vertex for a bend side terrain with a default offset of 3. Also sets the left most of the sides of this vertex with a
   *  default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  case class BendAll(c: Int, dirn: HVDirn, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with BendAllBase

  /** Sets the 2 outer corners of the bend for side terrain with a default offset of 6, Also sets the left most of the sides of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  case class BendOut(c: Int, dirn: HVDirn, terr: WSideSome = Sea, magnitude: Int = 6) extends VRowElem with BendOutBase

  /** Sets the 2 outer corners of the bend for side terrain with a default offset of 6, Also sets the left most of the sides of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  case class BendIn(c: Int, dirn: HVDirn, terr: WSideSome = Sea, magnitude: Int = 6) extends VRowElem with BendInBase

  case class VertRightsRight(c: Int, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with VertRightsRightBase

  /** Sets a vertex where 3 side terrains meet. Also sets the left most side terrain, the default is [[Sea]]. */
  case class ThreeWay(c: Int, st: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with ThreeWayBase
}