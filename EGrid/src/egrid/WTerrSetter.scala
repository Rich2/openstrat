/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][WTile], [[HSepLayer]][WSep] and [[HCornerLayer]] at the same time." */
abstract class WTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[WTile], val sTerrs: LayerHSOptSys[WSep, WSepSome], val corners: HCornerLayer)
  extends HSetter[WTile, WSep, WSepSome]
{
  implicit val grid: HGrid = gridIn

  sealed trait RowBase
  case class TRow(row: Int, mutlis: Multiple[WTileHelper]*) extends RowBase
  trait TRowElem extends WTileHelper

  trait TRunner extends TRowElem
  { def run (row: Int, c: Int): Unit
  }

  trait TRunnerExtra extends TRunner

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  case class Isle(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with IsleBase

  object Isle
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle = Isle(Land(elev, biome, landUse), sTerr)
  }
  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  case class Isle8(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle8Base

  object Isle8
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle8 = Isle8(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  case class Isle6(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle6
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle6 = Isle6(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  case class Isle5(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle6Base

  object Isle5
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle5 = Isle5(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  case class Isle4(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle4Base

  object Isle4
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle4 = Isle4(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  case class Isle3(terr: Land = Land(Level, Temperate, CivMix), sTerr: Water = Sea) extends TRunner with Isle3Base

  object Isle3
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle3 = Isle3(Land(elev, biome, landUse), sTerr)
  }

  /** Cape / headland / peninsula for [[WTile]]s. */
  class Cape (val indentStartIndex: Int, val numIndentedVerts: Int, val magnitude: Int, val terr: Land, val sepTerrs: Water) extends TRunner with CapeBase

  object Cape
  {
    def apply(indentStartIndex: Int, numIndentedVerts: Int, terr: Land = Land(), sideTerrs: Water = Sea): Cape =
      new Cape(indentStartIndex, numIndentedVerts, 7, terr, sideTerrs)

    def apply(indentStartIndex: Int, numIndentedVerts: Int, elev: Lelev, biome: Climate, landUse: LandUse, sideTerrs: Water): Cape =
      new Cape(indentStartIndex, numIndentedVerts, 7, Land(elev, biome, landUse), sideTerrs)
  }

  case class SepB(sTerr: Water = Sea) extends TRunnerExtra with SepBBase
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

  case class SetSep(c: Int, terr: WSepSome = Sea) extends  VRowElem with SetSepBase

  /** Creates an [[HSep]] separator terrain mouth. Magnitude should be between 3 and 7. Sets the 3 [[HCorner]]s and the [[HSep]]. The magnitude
   *  parameter comes before the terrain type parameter as this is designed primarily for modeling existing terrain rather than pure creation.  */
  case class Mouth(c: Int, dirn: HVDirnPrimary, magnitude: Int = 3, sTerr: WSepSome = Sea) extends VRowElem with MouthBase

  case class MouthLt(c: Int, dirn: HVDirnPrimary, magnitude: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with MouthLtBase
  case class MouthRt(c: Int, dirn: HVDirnPrimary, magnitude: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with MouthRtBase
  case class MouthLtRt(c: Int, dirn: HVDirnPrimary, magLeft: Int, magRight: Int, sTerr: WSepSome = Sea) extends VRowElem with MouthLtRtBase

  case class MouthSpec(c: Int, mouthDirn: HVDirnPrimary, dirn1: HVDirn, dirn2: HVDirn, sTerr: WSepSome = Sea, magnitude1: Int = 3,
    magnitude2: Int = 3) extends VRowElem with MouthSpecBase

  /** Sets all the corners of Vertex for a bend [[HSep]] terrain with a default offset of 3. Also sets the left most of the [[HSep]]s of this vertex with a
   *  default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendAll(val c: Int, val dirn: HVDirn, val leftTerr: WSepSome, val rightTerr: WSepSome, val magnitude: Int) extends VRowElem with BendAllBase

  object BendAll
  {
    def apply(c: Int, dirn: HVDirn, terr: WSepSome = Sea, magnitude: Int = 3): BendAll = apply(c, dirn, terr, terr, magnitude)

    def apply(c: Int, dirn: HVDirn, leftTerr: WSepSome, rightTerr: WSepSome, magnitude: Int): BendAll =
    { ifExcep(magnitude < 0, magnitude.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magnitude > 7, magnitude.toString -- "magnitude, magnitude values > 7 not allowed.")
      new BendAll(c, dirn, leftTerr, rightTerr, magnitude)
    }
  }

  /** Sets the 2 outer corners of the bend for [[HSep]] terrain with a default offset of 6, max 7, Also sets the left most of the [[HSep]]s of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendOut(val c: Int, val dirn: HVDirn, val magnitude: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendOutBase

  object BendOut
  {
    def apply(c: Int, dirn: HVDirn, magnitude: Int = 6, terr: WSepSome = Sea): BendOut = apply(c, dirn, magnitude, terr, terr)

    def apply(c: Int, dirn: HVDirn, magnitude: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendOut =
    { ifExcep(magnitude < 0, magnitude.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magnitude > 7, magnitude.toString -- "magnitude, magnitude values > 7 not allowed.")
      new BendOut(c, dirn, magnitude, leftTerr, rightTerr)
    }
  }

  /** Sets the 2 outer corners of the bend for [[HSep]] terrain with a default offset of 6, Also sets the left most of the [[HSep]]s of the bend vertex, with
   * a default terrain of [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendIn(val c: Int, val dirn: HVDirn, val magnitude: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendInBase

  object BendIn
  {
    def apply(c: Int, dirn: HVDirn, magnitude: Int = 6, terr: WSepSome = Sea): BendIn = apply(c, dirn, magnitude, terr, terr)

    def apply(c: Int, dirn: HVDirn, magnitude: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendIn =
    { ifExcep(magnitude < 0, magnitude.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magnitude > 13, magnitude.toString -- "magnitude, magnitude values > 13 not allowed.")
      new BendIn(c, dirn, magnitude, leftTerr, rightTerr)
    }
  }

  class BendInOut(val c: Int, val dirn: HVDirn, val magIn: Int, val magOut: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendInOutBase

  object BendInOut
  {
    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, terr: WSepSome = Sea): BendInOut = apply(c, dirn, magIn, magOut, terr, terr)

    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendInOut =
    { ifExcep(magIn < 0, magIn.str -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magOut < 0, magOut.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magIn > 13, magIn.str -- "magnitude, magnitude values > 13 not allowed.")
      ifExcep(magOut > 7, magOut.str -- "magnitude, outer bend magnitude values > 7 not allowed.")
      new BendInOut(c, dirn, magIn, magOut, leftTerr, rightTerr)
    }
  }

  /** Used for setting the a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  case class VertRightsLeft(c: Int, terr: WSepSome = Sea, magnitude: Int = 3) extends VRowElem with VertRightsLeftBase

  /** Used for setting a vertex on the left edge of a grid. Sets the vertex to the right on both hex tiles. */
  case class VertLeftsRight(c: Int, terr: WSepSome = Sea, magnitude: Int = 3) extends VRowElem with VertLeftsRightBase

  /** Sets a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]] terrain, the default is [[Sea]]. */
  class ThreeDown(val c: Int, val magUp: Int, val magDR: Int, val magDL: Int, val upRightTerr: WSepSome, val downTerr: WSepSome,
    val upLeftTerr: WSepSome) extends VRowElem with ThreeDownBase

  object ThreeDown
  {
    def apply(c: Int, magUp: Int, magDR: Int, magDL: Int, sTerr: WSepSome = Sea): ThreeDown =
      new ThreeDown(c, magUp, magDR, magDL, sTerr, sTerr, sTerr)

    def apply(c: Int, magUp: Int, magDR: Int, magDL: Int, upRightTerr: WSepSome, downTerr: WSepSome, upLeftTerr: WSepSome): ThreeDown =
      new ThreeDown(c, magUp, magDR, magDL, upRightTerr, downTerr, upLeftTerr)
  }

  /** Sets a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]] terrain, the default is [[Sea]]. */
  class ThreeUp(val c: Int, val magUR: Int, val magDn: Int, val magUL: Int, val upTerr: WSepSome, val downRightTerr: WSepSome,
    val downLeftTerr: WSepSome) extends VRowElem with ThreeUpBase

  object ThreeUp
  {
    def apply(c: Int, magUR: Int, magDn: Int, magUL: Int, sTerr: WSepSome = Sea): ThreeUp = new ThreeUp(c, magUR, magDn, magUL, sTerr, sTerr, sTerr)

    def apply(c: Int, magUR: Int, magDn: Int, magUL: Int, upTerr: WSepSome, downRightTerr: WSepSome, downLeftTerr: WSepSome): ThreeUp =
      new ThreeUp(c, magUR, magDn, magUL, upTerr, downRightTerr, downLeftTerr)
  }
}