/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object TerrMegaE30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e30(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendOut(1536, HVUp), OrigLt(1538, HVDL, 7)),
    TileRow(118, tundra),
    TileRow(116, taiga),
    TileRow(114, continental),
    TileRow(112, hillyOce, oceanic),
    VertRow(111, BendIn(1532, HVDL, 13)),
    TileRow(110, hillyOce, hillyOce),
    VertRow(109, BendOut(1532, HVUR), Bend(1534, HVUp, 8, 3), BendIn(1536, HVDn, 13), OrigLt(1538, HVUL)),
    TileRow(108, savannah, deshot),
    VertRow(107, Orig(1538, HVDR, 1, 5), BendIn(1540, HVDL, 13)),
    TileRow(106, deshot * 3),
    VertRow(105, BendOut(1540, HVUR, 7), BendIn(1542, HVDL, 13)),
    TileRow(104, deshot * 3),
    VertRow(103, BendOut(1542, HVUR), BendIn(1544, HVUp, 13)),
    TileRow(102, jungle * 2, hillyOce),
    TileRow(100, jungle * 2, sahel),
    TileRow(98, jungle * 2, sea),
    VertRow(97, BendInLt(1542, HVDR, 6, 7)),
    TileRow(96, savannah, hillySavannah * 2),
    VertRow(95, Bend(1540, HVDR, 13, 7), BendIn(1542, HVUL, 13)),
    TileRow(94, sahel, hillySavannah, hillySavannah),
    VertRow(93, BendOut(1538, HVDR, 7), ThreeUp(1540, 13, 0, 13), BendIn(1542, HVUp, 10), BendIn(1544, HVUL, 7)),
    TileRow(92, hillySavannah, sea),
    VertRow(91, OrigRt(1532, HVUL, 7), OrigLt(1536, HVUR, 7), BendIn(1538, HVUL, 13)),
    TileRow(82, ice),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(110, "Greece", "Turkey")
    str(108, "Cairo")
    str(94, "" * 2, "Madagascar south")
    str(92, "Durban")
  }
}