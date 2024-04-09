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
  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex. */
  case class Isle13(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle13Base

  object Isle13
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle13 = Isle13(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex. */
  case class Isle12(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle10Base

  object Isle12
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle12 = Isle12(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  case class Isle11(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle11Base

  object Isle11
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 11/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle11 = Isle11(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  case class Isle10(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle10Base

  object Isle10
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle10 = Isle10(Land(elev, biome, landUse), sTerr)
  }
  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
  case class Isle9(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle9Base

  object Isle9
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle9 = Isle9(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  case class Isle8(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle8Base

  object Isle8
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle8 = Isle8(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex. */
  case class Isle7(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle7Base

  object Isle7
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle7 = Isle7(Land(elev, biome, landUse), sTerr)
  }
  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  case class Isle6(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle6Base

  object Isle6
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle6 = Isle6(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  case class Isle5(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle5Base

  object Isle5
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle5 = Isle5(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  case class Isle4(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends TRunner with Isle4Base

  object Isle4
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle4 = Isle4(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  class Isle3(val terr: Land, val sepTerrs: Water) extends TRunner with Isle3Base

  object Isle3
  {
    /** Factory apply method for [[Isle3]]. Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an island, with a radius of 3/16 of the
     *  radius of the hex. */
    def apply(terr: Land = Land(Plain, Oceanic, CivMix), sTerrs: Water = Sea): Isle3 = new Isle3(terr, sTerrs)

    /** Factory apply method for [[Isle3]]. Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an island, with a radius of 3/16 of the
     *  radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle3 = Isle3(Land(elev, biome, landUse), sTerr)
  }

  /** Creates an [[HSepB]], an [[HSep]] of the vertical alignment. The only place this should be needed is on the left or west edge of an [[EGrid]]. Otherwise
   * the [[HSep]]s should be set in the [[VRow]]s along with [[HCorner]]s using bends and sources and threeways. */
  case class SepB(sTerr: Water = Sea) extends TRunnerExtra with SepBBase

  /** Sets terrain along a row of [[HVert]]s and in the [[HSepB]]s in the rows immediately below and above. */
  case class VRow(row: Int, edits: VRowElem*) extends RowBase

  sealed trait VRowElem
  { def run(row: Int): Unit
  }

  /** The [[TRow]] tile rows and [[VRow]] vertex rows data layer setter values. */
  val rows: RArr[RowBase]

  def run: Unit =
  { rows.foreach{
      case data: TRow => tRowRun(data)
      case _: VRow =>
    }
    rows.foreach{
      case _: TRow =>
      case data: VRow => data.edits.foreach(_.run(data.row))
    }
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

  /** Sets the [[HSep]] separator at the given position. This should only be needed for setting the [[HSep]] on the left hand side of an [[EGrid]] where the
   * join with the grid to the left is not regular. */
  case class SetSep(c: Int, terr: WSepSome = Sea) extends  VRowElem with SetSepBase

  /** origin / end point of [[HSep]] hex tile separator. */
  case class Orig(c: Int, dirn: HVDirnPrimary, magLt: Int, magRt: Int, sTerr: WSepSome = Sea) extends VRowElem with OrigLtRtBase

  /** Origin / end point of an [[HSep]] hex tile separator, offset to the left as viewed from the [[HVert]] looking down the [[HSep]]. */
  case class OrigLt(c: Int, dirn: HVDirnPrimary, magLt: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with OrigLtBase

  /** Deprecated. Replace with [[OrigLt]] reversing the dirn. */
  case class OrigLtRevDepr(c: Int, dirn: HVDirnPrimary, magnitude: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with MouthLtBase

  /** Origin / end point of an [[HSep]] hex tile separator, offset to the right as viewed from the [[HVert]] looking down the [[HSep]]. */
  case class OrigRt(c: Int, dirn: HVDirnPrimary, magRt: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with OrigRtBase

  /** Origin / end point of an [[HSep]] hex tile separator, with a minimum combined offset of 6. */
  class OrigMin(val c: Int, val dirn: HVDirnPrimary, val magLt: Int, val sTerr: WSepSome) extends VRowElem with OrigLtRtBase
  { override def magRt: Int = 6 - magLt
  }

  object OrigMin
  {
    /** Factory apply method for origin / end point of an [[HSep]] hex tile separator, with a minimum combined offset of 6. The default offsets are 3 for lett
     * and right. */
    def apply(c: Int, dirn: HVDirnPrimary, magLt: Int = 3, sTerr: WSepSome = Sea): OrigMin =
    { ifExcep(magLt < 0 || magLt > 6, s"magLt = $magLt. Value must be tween 9 and 6")
      new OrigMin(c, dirn, magLt, sTerr)
    }
  }

  /** Origin / end point of an [[HSep]] separator with a left and right magnitude of 7. */
  case class OrigMax(c: Int, dirn: HVDirnPrimary, sTerr: WSepSome = Sea) extends VRowElem with OrigLtRtBase
  { override def magLt: Int = 7
    override def magRt: Int = 7
  }

  /** Deprecated. Reverse the direction and replace with [[OrigMin]] [[OrigLi]], [[OrigRt]] or[[Orig]]. */
  case class OrigMinRevDepr(c: Int, dirn: HVDirnPrimary, magnitude: Int = 3, sTerr: WSepSome = Sea) extends VRowElem with MouthBase

  /** Deprecated. Reverse the direction and replace with [[OrigRt]]. */
  case class OrigRtRevDepr(c: Int, dirn: HVDirnPrimary, magnitude: Int = 6, sTerr: WSepSome = Sea) extends VRowElem with MouthRtBase

  /** Deprecated. Replace with [[Orig]] reversing the dirn. */
  case class OrigRevDepr(c: Int, dirn: HVDirnPrimary, magLeft: Int, magRight: Int, sTerr: WSepSome = Sea) extends VRowElem with MouthLtRtBase

  /** Deprecated. Replacement unclear at moment. */
  case class OrigSpecRevDepr(c: Int, mouthDirn: HVDirnPrimary, dirn1: HVDirn, dirn2: HVDirn, sTerr: WSepSome = Sea, magnitude1: Int = 3, magnitude2: Int = 3)
    extends VRowElem with MouthSpecBase

  /** Bend connecting 2 [[HSeps]], with an inner and outer offset of 3/16. [[BendMin]] just exists for a convenient way of setting values. */
  class BendMin(val c: Int, val dirn: HVDirn, val magIn: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendInOutBase
  { override def magOut: Int = 6 - magIn
  }

  /** Companion object for [[BendMin]] class, contains 2 factory apply method name overloads. */
  object BendMin
  { /** Factory apply method for creating bend connecting 2 [[HSeps]], with an inner and outer offset of 3/16, where both [[HSep]]s have the same value. There
     * is a name overload where both [[HSep]] layer values are specified. */
    def apply(c: Int, dirn: HVDirn, magIn: Int = 3, terr: WSepSome = Sea): BendMin = BendMin(c, dirn, magIn, terr, terr)

    /** Factory apply method for creating bend, connecting 2 [[HSeps]], with an inner and outer offset of 3/16, where the 2 [[HSep]] layer values are specified.
     * There is a name overload where 1 [[HSep]] layer value is given for both. That will be the most common use case. */
    def apply(c: Int, dirn: HVDirn, magIn: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendMin =
    { ifExcep(magIn > 6 || magIn < 0, s"magIn = $magIn. value must lie within 0 and 6")
      new BendMin(c, dirn, magIn, leftTerr, rightTerr)
    }
  }

  /** Bend connecting 2 [[HSeps]], with an inner and outer offset of 7/16. */
  class BendMax(val c: Int, val dirn: HVDirn, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendInOutBase
  { override def magIn: Int = 13
    override def magOut: Int = 7
  }

  object BendMax
  { /** Factory apply method ofr creating bend connecting 2 [[HSeps]], with an inner and outer offset of 7/16, where both [[HSep]]s have the same value. There
     * is a name overload where both [[HSep]] layer values are specified. */
    def apply(c: Int, dirn: HVDirn, terr: WSepSome = Sea): BendMax = new BendMax(c, dirn, terr, terr)

    /** Factory apply method ofr creating bend connecting 2 [[HSeps]], with an inner and outer offset of 7/16, where the 2 [[HSep]] layer values are specified.
     * There is a name overload where 1 [[HSep]] layer value is given for both. That will be the most common use case. */
    def apply(c: Int, dirn: HVDirn, leftTerr: WSepSome, rightTerr: WSepSome): BendMax = new BendMax(c, dirn, leftTerr, rightTerr)
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

  /** Bend at junction of 2 [[HSep]]s. Sets the [[HSep]] terrains and the 3 [[HCorner]]s of the [[HVert]]. */
  class Bend(val c: Int, val dirn: HVDirn, val magIn: Int, val magOut: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with BendInOutBase

  object Bend
  {
    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, terr: WSepSome = Sea): Bend = apply(c, dirn, magIn, magOut, terr, terr)

    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, leftTerr: WSepSome, rightTerr: WSepSome): Bend =
    { ifExcep(magIn < 0, magIn.str -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magOut < 0, magOut.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magIn > 13, magIn.str -- "magnitude, magnitude values > 13 not allowed.")
      ifExcep(magOut > 7, magOut.str -- "magnitude, outer bend magnitude values > 7 not allowed.")
      new Bend(c, dirn, magIn, magOut, leftTerr, rightTerr)
    }
  }

  /** Bend at junction of 2 [[HSep]]s. Sets the [[HSep]] terrains and the 3 [[HCorner]]s of the [[HVert]]. */
  class BendInLt(val c: Int, val dirn: HVDirn, val magIn: Int, val magSource: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with
    BendInLtBase

  object BendInLt
  {
    def apply(c: Int, dirn: HVDirn, magIn: Int, magMouth: Int, terr: WSepSome = Sea): BendInLt = apply(c, dirn, magIn, magMouth, terr, terr)

    def apply(c: Int, dirn: HVDirn, magIn: Int, magMouth: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendInLt =
    { ifExcep(magIn < 0, magIn.str -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magMouth < 0, magMouth.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magIn > 13, magIn.str -- "magnitude, magnitude values > 13 not allowed.")
      ifExcep(magMouth > 7, magMouth.str -- "magnitude, outer bend magnitude values > 7 not allowed.")
      new BendInLt(c, dirn, magIn, magMouth, leftTerr, rightTerr)
    }
  }

  /** Bend at junction of 2 [[HSep]]s. Sets the [[HSep]] terrains and the 3 [[HCorner]]s of the [[HVert]]. */
  class BendInRt(val c: Int, val dirn: HVDirn, val magIn: Int, val magSource: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with
    BendInRtBase

  object BendInRt
  {
    def apply(c: Int, dirn: HVDirn, magIn: Int, magMouth: Int, terr: WSepSome = Sea): BendInRt = apply(c, dirn, magIn, magMouth, terr, terr)

    def apply(c: Int, dirn: HVDirn, magIn: Int, magMouth: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendInRt =
    { ifExcep(magIn < 0, magIn.str -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magMouth < 0, magMouth.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magIn > 13, magIn.str -- "magnitude, magnitude values > 13 not allowed.")
      ifExcep(magMouth > 7, magMouth.str -- "magnitude, outer bend magnitude values > 7 not allowed.")
      new BendInRt(c, dirn, magIn, magMouth, leftTerr, rightTerr)
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
  class ThreeUp(val c: Int, val magUR: Int, val magDn: Int, val magUL: Int, val upTerr: WSepSome, val downRightTerr: WSepSome, val downLeftTerr: WSepSome)
    extends VRowElem with ThreeUpBase

  object ThreeUp
  {
    def apply(c: Int, magUR: Int, magDn: Int, magUL: Int, sTerr: WSepSome = Sea): ThreeUp = new ThreeUp(c, magUR, magDn, magUL, sTerr, sTerr, sTerr)

    def apply(c: Int, magUR: Int, magDn: Int, magUL: Int, upTerr: WSepSome, downRightTerr: WSepSome, downLeftTerr: WSepSome): ThreeUp =
      new ThreeUp(c, magUR, magDn, magUL, upTerr, downRightTerr, downLeftTerr)
  }
}