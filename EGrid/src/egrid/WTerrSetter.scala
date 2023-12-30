/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][WTile], [[HSideLayer]][WSide] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[WTile], val sTerrs: LayerHSOptSys[WSide, WSideSome], val corners: HCornerLayer) extends
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

  /** Sets the side terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  case class Isle(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with IsleBase

  object Isle
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle = Isle(Land(elev, biome, landUse), sTerr)
  }
  /** Sets the side terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  case class Isle8(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle8Base

  object Isle8
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle8 = Isle8(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the side terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  case class Isle6(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle6
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle6 = Isle6(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the side terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  case class Isle5(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle5
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle5 = Isle5(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the side terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  case class Isle4(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle4
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle4 = Isle4(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the side terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  case class Isle3(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle3
  { /** Factory apply method for Isle. Sets the side terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle3 = Isle3(Land(elev, biome, landUse), sTerr)
  }

  /** Cape / headland / peninsula for [[WTile]]s. */
  class Cape private(val indentStartIndex: Int, val numIndentedVerts: Int, val terr: Land = Land(Level, Temperate, CivMix), val sideTerrs: Water = Sea) extends
    TRunner with CapeBase

  object Cape
  {
    def apply(indentStartIndex: Int, numIndentedVerts: Int = 1, terr: Land = Land(), sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, terr, sideTerrs)

    def apply(indentStartIndex: Int, elev: Lelev, biome: Climate, landUse: LandUse, sideTerrs: Water): Cape =
      new Cape(indentStartIndex, 1, Land(elev, biome, landUse), sideTerrs)
  }

  /** Isthmus for [[WTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
  class Isthmus private(val indentIndex: Int, val terr: Land = Land(), val sideTerrs1: Water = Sea, val sideTerrs2: Water = Sea) extends TRunner with
    IsthmusBase

  object Isthmus
  { /** Factory apply method for Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
    def apply(indentIndex: Int, terr: Land = Land(), sideTerrs1: Water = Sea, sideTerrs2: Water = Sea): Isthmus =
      new Isthmus(indentIndex, terr, sideTerrs1, sideTerrs2)
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
  class BendAll(val c: Int, val dirn: HVDirn, val terr: WSideSome, val magIn: Int, val magOut: Int) extends VRowElem with BendInOutBase

  object BendAll
  {
    def apply(c: Int, dirn: HVDirn, terr: WSideSome = Sea, magnitude: Int = 3): BendAll = new BendAll(c, dirn, terr, 3, 3)

    def apply(c: Int, dirn: HVDirn, terr: WSideSome, magIn: Int, magOut: Int): BendAll = new BendAll(c, dirn, terr, magIn, magOut)
  }

  /** Sets the 2 outer corners of the bend for side terrain with a default offset of 6, max 7, Also sets the left most of the sides of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendOut(val c: Int, val dirn: HVDirn, val magnitude: Int, val terr: WSideSome = Sea) extends VRowElem with BendOutBase

  object BendOut
  {
    def apply(c: Int, dirn: HVDirn, magnitude: Int = 6, terr: WSideSome = Sea): BendOut = {
      ifExcep(magnitude < 0, magnitude.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magnitude > 7, magnitude.toString -- "magnitude, magnitude values > 7 not allowed.")
      new BendOut(c, dirn, magnitude, terr)
    }
  }


  /** Sets the 2 outer corners of the bend for side terrain with a default offset of 6, Also sets the left most of the sides of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendIn(val c: Int, val dirn: HVDirn, val magnitude: Int, val terr: WSideSome = Sea) extends VRowElem with BendInBase

  object BendIn
  {
    def apply(c: Int, dirn: HVDirn, magnitude: Int = 6, terr: WSideSome = Sea): BendIn =
    { ifExcep(magnitude < 0, magnitude.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magnitude > 13, magnitude.toString -- "magnitude, magnitude values > 13 not allowed.")
      new BendIn(c, dirn, magnitude, terr)
    }
  }

  /** Used for setting the a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  case class VertRightsLeft(c: Int, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with VertRightsLeftBase

  /** Used for setting a vertex on the left edge of a grid. Sets the vertex to the right on both hex tiles. */
  case class VertLeftsRight(c: Int, terr: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with VertLeftsRightBase

  /** Sets a vertex where 3 side terrains meet. Also sets the left most side terrain, the default is [[Sea]]. */
  case class ThreeWay(c: Int, st: WSideSome = Sea, magnitude: Int = 3) extends VRowElem with ThreeWayBase

  /** Sets a vertex where 3 side terrains meet. Also sets the left most side terrain, the default is [[Sea]]. */
  case class ThreeDown(c: Int, magUp: Int = 3, magDR: Int = 3, magDL: Int = 3, st: WSideSome = Sea) extends VRowElem with ThreeDownBase

}