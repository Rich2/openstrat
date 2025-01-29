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

  trait TRowElem extends WTileHelper with TRowElemBase

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex. */
  trait Isle13 extends TRowElem with Isle13Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle13Homo(terr: Land, sepTerrs: Water) extends Isle13, IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle13Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle13

  object Isle13
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle13 = Isle13Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 13/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle13 = Isle13(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex. */
  trait Isle12 extends TRowElem with Isle10Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle12Homo(terr: Land, sepTerrs: Water = Sea) extends Isle12, IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle12Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle12
  
  object Isle12
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle12 = Isle12Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 12/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle12 = Isle12Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  trait Isle11 extends TRowElem, Isle11Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 11/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle11Homo(terr: Land, sepTerrs: Water = Sea) extends Isle11, IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 11/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle11Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle11
  
  object Isle11
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 11/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle11 = Isle11Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 11/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle11 = Isle11(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  trait Isle10 extends TRowElem with Isle10Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle10Homo(terr: Land, sepTerrs: Water) extends Isle10 with IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle10Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle10

  object Isle10
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle10 = Isle10Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle10 = Isle10Homo(Land(elev, biome, landUse), sTerr)
  }
  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
  trait Isle9 extends TRowElem with Isle9Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle9Homo(terr: Land, sepTerrs: Water) extends Isle9, IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle9Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle9

  object Isle9
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle9 = Isle9Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle9 = Isle9Homo(Land(elev, biome, landUse), sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(land: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water): Isle9 =
      Isle9Het(land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  trait Isle8 extends TRowElem with Isle8Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle8Homo(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends Isle8, IsleNBaseHomo

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex, where the 6 [[HSep]]s can have different terrain
   * types. */
  case class Isle8Het(terr: Land, sepTerr0: Water, sepTerr1: Water, sepTerr2: Water, sepTerr3: Water, sepTerr4: Water, sepTerr5: Water) extends Isle8

  object Isle8
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle8 = Isle8Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 9/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle8 = Isle8Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex. */
  trait Isle7 extends TRowElem, Isle7Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle7Homo(terr: Land = Land(Plain, Oceanic, CivMix), sepTerrs: Water = Sea) extends Isle7, IsleNBaseHomo

  object Isle7
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle7 = Isle7Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 7/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle7 = Isle7Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  trait Isle6 extends TRowElem with Isle6Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle6Homo(terr: Land, sepTerrs: Water = Sea) extends Isle6, IsleNBaseHomo
  
  object Isle6
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle6 = Isle6Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle6 = Isle6Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  trait Isle5 extends TRowElem, Isle5Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle5Homo(terr: Land, sepTerrs: Water = Sea) extends Isle5, IsleNBaseHomo
  
  object Isle5
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle5 = Isle5Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle5 = Isle5Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  trait Isle4 extends TRowElem with Isle4Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  case class Isle4Homo(terr: Land, sepTerrs: Water) extends Isle4, IsleNBaseHomo

  object Isle4
  { /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
    def apply(land: Land, sTerr: Water = Sea): Isle4 = Isle4Homo(land, sTerr)

    /** Factory apply method for Isle. Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle4 = Isle4Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  trait Isle3 extends TRowElem with Isle3Base

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex, where all 6 [[HSep]]s have the same terrain. */
  class Isle3Homo(val terr: Land, val sepTerrs: Water) extends Isle3, IsleNBaseHomo

  object Isle3
  {
    /** Factory apply method for [[Isle3]]. Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an island, with a radius of 3/16 of the
     *  radius of the hex. */
    def apply(terr: Land, sTerrs: Water = Sea): Isle3 = new Isle3Homo(terr, sTerrs)

    /** Factory apply method for [[Isle3]]. Sets the [[HCen]] terrain, the [[HSep]] terrains and the [[HCorner]]s for an island, with a radius of 3/16 of the
     *  radius of the hex. */
    def apply(elev: Lelev, biome: Climate, landUse: LandUse, sTerr: Water): Isle3 = Isle3Homo(Land(elev, biome, landUse), sTerr)
  }

  /** Make sure you do not add 4 to the column coordinate after applying this. Creates an [[HSepB]], an [[HSep]] of the vertical alignment. The only place this
   * should be needed is on the left or west edge of an [[EGrid]]. Otherwise, the [[HSep]]s should be set in the [[VRow]]s along with [[HCorner]]s using bends
   * and sources and threeways. */
  case class SepB(sTerr: Water = Sea) extends TRowElem with SepBBase

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
        case Multiple(value : SepB, _) => value.run(row, c)
        case multi => multi.foreach { help =>
          if (c > grid.rowRightCenC(row)) excep("Too many tiles for row.")
          help match {
            case wt: WTile => terrs.set(row, c, wt)
            case il: TRowElem => il.run(row, c)
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
  { /** Factory apply method to construct a [[BendOut]]. Note the direction of the bend is always given inwards. */
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

  /** Bend at junction of 2 [[HSep]]s, with extra separator vertex. Sets the [[HSep]] terrains and the 3 [[HCorner]]s of the [[HVert]]. */
  class BendExtra(val c: Int, val dirn: HVDirn, val magIn: Int, val magOut: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with
    BendInOutExtraBase

  object BendExtra
  {
    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, terr: WSepSome = Sea): BendExtra = apply(c, dirn, magIn, magOut, terr, terr)

    def apply(c: Int, dirn: HVDirn, magIn: Int, magOut: Int, leftTerr: WSepSome, rightTerr: WSepSome): BendExtra =
    { ifExcep(magIn < 0, magIn.str -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magOut < 0, magOut.toString -- "magnitude, negative magnitude values not allowed.")
      ifExcep(magIn > 13, magIn.str -- "magnitude, magnitude values > 13 not allowed.")
      ifExcep(magOut > 7, magOut.str -- "magnitude, outer bend magnitude values > 7 not allowed.")
      new BendExtra(c, dirn, magIn, magOut, leftTerr, rightTerr)
    }
  }

  /** Bend at junction of 2 [[HSep]]s. Sets the [[HSep]] terrains and the 3 [[HCorner]]s of the [[HVert]]. */
  class BendInLt(val c: Int, val dirn: HVDirn, val magIn: Int, val OrigMag: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with
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
  class BendInRt(val c: Int, val dirn: HVDirn, val magIn: Int, val origMag: Int, val leftTerr: WSepSome, val rightTerr: WSepSome) extends VRowElem with
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

  /** Used for setting a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  case class BendLtOut(c: Int, magnitude: Int, leftTerr: WSepSome, rightTerr: WSepSome) extends VRowElem with BendLtOutBase

  object BendLtOut
  { /** Factory apply method to sets a bend out in an [[HVLt]] direction. Normally wanted on the right edge of the grid where 4 hexs share a vertex. */
    def apply(c: Int, magnitude: Int, terr: WSepSome = Sea): BendLtOut = new BendLtOut(c, magnitude, terr, terr)
  }

  /** Sets a vertex where 3 [[HSep]] terrains meet. Sets the three [[HCorner]]s and the 3 [[HSep]]'s terrain. */
  class ThreeDown(val c: Int, val magUp: Int, val magDR: Int, val magDL: Int, val upRightTerr: WSepSome, val downTerr: WSepSome,
    val upLeftTerr: WSepSome) extends VRowElem with ThreeDownBase

  object ThreeDown
  {
    /** Factory apply method to set a vertex where 3 [[HSep]] terrains meet. Sets the three [[HCorner]]s and sets 3 [[HSep]]'s terrain to the same value, the
     * default being [[Sea]]. There is a name overload to set the three [[HSep]]s to different values. */
    def apply(c: Int, magUp: Int, magDR: Int, magDL: Int, sTerr: WSepSome = Sea): ThreeDown = new ThreeDown(c, magUp, magDR, magDL, sTerr, sTerr, sTerr)

    /** Factory apply method to sets a vertex where 3 [[HSep]] terrains meet. Sets the three [[HCorner]]s and the 3 [[HSep]]'s terrain. There isa name overload
     * to set the 3 [[HSep]]s to the same value the default being [[Sea]].. */
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