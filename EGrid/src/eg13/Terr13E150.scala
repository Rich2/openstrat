/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300Km [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile area of 1.463582932 million km².
 * [[Isle8]] 321588.046km² => 413061.979km². Japan combined 377973 km². */
object Terr13E150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(115, BendOut(5632, HVUp, 7, siceWin), BendIn(5634, HVDn, 13, siceWin), BendIn(5636, HVDL, 13, siceWin)),
    TRow(114, hillyTundra),
    VRow(113, BendIn(5636, HVUR, 13, siceWin)),
    TRow(112, hillyTundra),
    VRow(111, BendOut(5636, HVDR, 7, siceWin)),
    TRow(110, hillyTaiga),
    VRow(109, BendIn(5630, HVDR, 8), ThreeUp(5632, 13, 8, 0), ThreeDown(5634, 13, 0, 8), BendIn(5634, HVDL, 13), BendIn(5636, HVUL, 13, siceWin, sea)),
    TRow(108, hillyOceForest),
    VRow(107, BendOut(5630, HVDR, 7), BendOut(5632, HVDR, 7), BendIn(5634, HVUL, 13)),
    TRow(106, hillySubForest),
    VRow(105, BendOut(5628, HVDn, 7), BendIn(5630, HVUp, 13), BendIn(5632, HVUL, 13)),
    VRow(103, BendIn(5628, HVDL, 13)),
    TRow(102, SepB()),
    VRow(101, SetSep(5628)),
    TRow(100, SepB(), sea * 2),
    VRow(99, Bend(5628, HVDL, 12, 2)),
    TRow(98, hillyJungle, sea),
    VRow(97, ThreeUp(5628, 13, 0, 12),  BendIn(5630, HVUp, 13), Bend(5632, HVDn, 13, 7), BendIn(5634, HVDL, 13)),
    TRow(96, hillySavannah, sea),
    VRow(95, SourceLt(5634, HVUp, 7)),
    TRow(94, savannah, sea),
    VRow(93, BendIn(5634, HVDL, 13)),
    TRow(92, hillyOce),
    VRow(91, SourceLt(5630, HVDR, 7), BendIn(5632, HVUp, 13), BendIn(5634, HVUL, 13)),
    VRow(87, Bend(5630, HVDn, 6, 7, siceWin), BendIn(5632, HVUp, 13, siceWin), BendMax(5634, HVDn, siceWin), BendMax(5636, HVUp, siceWin)),
    TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "Japan north")
    str(106, "Japan central")
    str(98,"New Guinee west")
  }
}