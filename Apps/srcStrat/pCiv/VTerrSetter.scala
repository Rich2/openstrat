/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** Helper class for setting  [[LayerHcRefSys]][VTile], [[HSepLayer]][VSep] and [[HCornerLayer]] at the same time. */
abstract class VTerrSetter(gridIn: HGrid, val terrs: LayerHcRefSys[VTile], val sTerrs: LayerHSOptSys[VSep, VSepSome], val corners: HCornerLayer) extends
  HSetter[VTile, VSep, VSepSome]
{ implicit val grid: HGrid = gridIn

  sealed trait DataRow extends DataRowBase

  trait TRowElem extends VTileHelper, TileRowElemBase

  class VertRow(val row: Int, val edits: RArr[VertRowElem]) extends VertRowBase, DataRow

  object VertRow
  { def apply(row: Int, edits: VertRowElem*): VertRow = new VertRow(row, edits.toRArr)
  }

  sealed trait VertRowElem extends VertRowElemBase

  class TileRow(val row: Int, val mutlis: RArr[Multiple[VTileHelper]]) extends TileRowBase, DataRow

  object TileRow
  { def apply(row: Int, mutlis: Multiple[VTileHelper]*): TileRow = new TileRow(row, mutlis.toRArr)
  }

  override val rows: RArr[DataRow]

  def tileRun(row: Int, c: Int, tile: VTile): Unit = terrs.set(row, c, tile)

  trait Isle10 extends TRowElem with Isle10Base
  case class Isle10Homo(terr: Land, sepTerrs: Water) extends Isle10, IsleNBaseHomo

  object Isle10
  { def apply(terr: Land = Plain, sepTerrs: Water = Sea): Isle10 = Isle10Homo(terr, sepTerrs)
  }

  /** Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
  class Isthmus private(val indentIndex: Int, val terr: Land = Plain, val sepTerrs1: Water = Sea, val sepTerrs2: Water = Sea) extends TRowElem, IsthmusBase

  object Isthmus
  { /** Factory apply method for Isthmus for [[VTile]]s. Sets the [[HCen]] terrain Pulls in opposite vertices and sets 4 side terrains. */
    def apply(indentIndex: Int, terr: Land = Plain, sideTerrs1: Water = Sea, sideTerrs2: Water = Sea): Isthmus =
      new Isthmus(indentIndex, terr, sideTerrs1, sideTerrs2)
  }

  case class SepB(sTerr: VSepSome = Sea) extends TRowElem, SepBBase
  case class SetSep(c: Int, terr: VSepSome = Sea) extends VertRowElem with SetSepBase

  class ThreeUp(val c: Int, val upTerr: VSepSome, val downRightTerr: VSepSome, val downLeftTerr: VSepSome, val magUR: Int, val magDn: Int, val magUL: Int)
    extends VertRowElem, ThreeUpBase

  object ThreeUp
  { def apply(c: Int, sTerr: VSepSome = Sea): ThreeUp = new ThreeUp(c, sTerr, sTerr, sTerr, 3, 3, 3)
    def apply(c: Int, upTerr: VSepSome, downRightTerr: VSepSome, downLeftTerr: VSepSome): ThreeUp = new ThreeUp(c, upTerr, downRightTerr, downLeftTerr, 3, 3, 3)
  }

  case class ThreeDown(val c: Int, val upRightTerr: VSepSome, val downTerr: VSepSome, val upLeftTerr: VSepSome, val magUp: Int, val magDR: Int, val magDL: Int)
    extends VertRowElem with ThreeDownBase

  object ThreeDown
  { def apply(c: Int, sTerr: VSepSome = Sea): ThreeDown = new ThreeDown(c, sTerr, sTerr, sTerr, 3, 3, 3)

    def apply(c: Int, upRightTerr: VSepSome, downTerr: VSepSome, upLeftTerr: VSepSome): ThreeDown =
      new ThreeDown(c, upRightTerr, downTerr, upLeftTerr, 3, 3, 3)
  }

  /** Sets the origin / end point of an [[HSep]] tile separator terrain for a river or straits. */
  case class Orig(c: Int, dirn: HVDirnPrimary, sTerr: VSepSome = Sea, magLt: Int = 3, magRt: Int = 3) extends VertRowElem with OrigLtRtBase

  /** Origin / end point of an [[HSep]] hex tile separator, offset to the left as viewed from the [[HVert]] looking down the [[HSep]]. */
  case class OrigLt(c: Int, dirn: HVDirnPrimary, sTerr: VSepSome = Sea) extends VertRowElem with OrigLtBase
  { override def magLt: Int = 6
  }

  /** Origin / end point of an [[HSep]] hex tile separator, offset to the right as viewed from the [[HVert]] looking down the [[HSep]]. */
  case class OrigRt(c: Int, dirn: HVDirnPrimary, sTerr: VSepSome = Sea) extends VertRowElem with OrigRtBase
  { override def magRt: Int = 6
  }

  class BendAll(val c: Int, val dirn: HVDirn, val leftTerr: VSepSome, val rightTerr: VSepSome) extends VertRowElem with BendInOutBase
  { override def magIn: Int = 3
    override def magOut: Int = 3
  }

  object BendAll
  {
    def apply(c: Int, dirn: HVDirn, sTerr: VSepSome = Sea): BendAll = new BendAll(c, dirn, sTerr, sTerr)

    def apply(c: Int, dirn: HVDirn, leftTerr: VSepSome, rightTerr: VSepSome): BendAll = new BendAll(c, dirn, leftTerr, rightTerr)
  }

  /** Sets the inner corner of the bend for [[HSep]] terrain with a magnitude of 6, Sets the 2 [[HSep]] terrains of the bend vertex, with a default terrain of
   * [[Sea]]. The orientation of the bend is specified by the direction of the inside of the bend. */
  class BendIn(val c: Int, val dirn: HVDirn, val leftTerr: VSepSome, val rightTerr: VSepSome) extends VertRowElem with BendInBase
  { override def magnitude: Int = 6
  }

  object BendIn
  { /** Factory apply method to create [[BendIn]], default [[HSep]] terrain is [[Sea]]. There is a name overload to apply different terrain types to the 2
     * [[HSep]]s. */
    def apply(c: Int, dirn: HVDirn, terr: VSepSome = Sea): BendIn = apply(c, dirn, terr, terr)

    /** Factory apply method to create [[BendIn]],with different [[HSep]] terrains. There is a name overload for when the [[HSep]]s have the same terrain type
     * which defaults to [[Sea]]. */
    def apply(c: Int, dirn: HVDirn, leftTerr: VSepSome, rightTerr: VSepSome): BendIn = new BendIn(c, dirn, leftTerr, rightTerr)
  }
}