/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1300Km [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile area of 1.463582932 million km².
 * [[Isle8]] 321588.046km² => 413061.979km². Japan combined 377973 km².
 * [[Isle3]] 35732.005km² => 70034.730km². Bismark Archipelago 49700km².
 * Below 35732.005km². Solomon Islands 28896km². */
object Terr13E150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendOut(5632, HVUp, 7, siceWin), BendIn(5634, HVDn, 13, siceWin), BendIn(5636, HVDL, 13, siceWin)),
    TileRow(114, hillyTundra),
    VertRow(113, BendIn(5636, HVUR, 13, siceWin)),
    TileRow(112, hillyTundra),
    VertRow(111, Orig(5632, HVDn, 7, 3), BendOut(5636, HVDR, 7, siceWin)),
    TileRow(110, hillyTaiga),
    VertRow(109, BendIn(5630, HVDR, 8), ThreeUp(5632, 13, 8, 0), ThreeDown(5634, 13, 0, 8), BendIn(5634, HVDL, 13), BendIn(5636, HVUL, 13, siceWin, sea)),
    VertRow(101, ThreeUp(5628, 0, 7, 9), OrigLt(5630, HVUL, 7)),
    TileRow(108, hillyContForest),
    VertRow(107, BendOut(5630, HVDR, 7), BendOut(5632, HVDR, 7), BendIn(5634, HVUL, 13)),
    TileRow(106, hillySubForest),
    VertRow(105, BendOut(5628, HVDn, 7), BendIn(5630, HVUp, 13), BendIn(5632, HVUL, 13)),
    VertRow(103, BendIn(5628, HVDL, 13)),
    VertRow(101, BendIn(5622, HVDL, 9)),
    TileRow(100, Isle3(hillyJungle)),
    VertRow(99, Bend(5628, HVDL, 12, 2)),
    TileRow(98, hillyJungle),
    VertRow(97, ThreeUp(5628, 13, 0, 12),  BendIn(5630, HVUp, 13), Bend(5632, HVDn, 13, 7), BendIn(5634, HVDL, 13)),
    TileRow(96, hillySavannah, sea),
    VertRow(95, BendOut(5634, HVUR, 7), BendIn(5636, HVDL, 13)),
    TileRow(94, sahel, hillySavannah),
    VertRow(93, BendExtra(5634, HVDR, 9, 7), BendIn(5636, HVUL, 13)),
    TileRow(92, hillyOce),
    VertRow(91, OrigLt(5630, HVDR, 7), BendIn(5632, HVUp, 13), BendIn(5634, HVUL, 13)),
    VertRow(87, Bend(5630, HVDn, 6, 7, siceWin), BendIn(5632, HVUp, 13, siceWin), BendMax(5634, HVDn, siceWin), BendMax(5636, HVUp, siceWin)),
    TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "Japan north")
    str(106, "Japan central")
    str(100, "New Britain")
    str(98,"New Guinee east")
    str(96, "Australia north east")
    str(94, "Australia midst east")
    str(92, "Australia south east")
  }
}