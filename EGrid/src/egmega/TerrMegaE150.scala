/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1Mm [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile ares of 866025.403 km².
 * [[Isle5]] 68503.962km² => 102333.079km². Salakhin 72,492 km².
 * [[Isle4]] 41440.668km² => 68503.962km². New Britain 35144km² + New Ireland 8990km² = 44134km².
 * [[Isle3]] 21143.198km² => 41440.668km².
 * Less than 21143.198km². New Caledonia. */
object TerrMegaE150 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e150(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendMin(5632, HVUp, 3, siceWin), BendIn(5634, HVDn, 13, siceWin), BendIn(5636, HVDL, 11, siceWin)),
    TileRow(118, hillyTundra),
    TileRow(116, hillyTundra),
    VertRow(115, OrigMax(5632, HVDn), BendOut(5636, HVDR, 7, siceWin)),
    TileRow(114, hillyTaiga),
    VertRow(113, BendIn(5630, HVDR, 11), ThreeUp(5632, 13, 11, 0), ThreeDown(5634, 6, 0, 11, siceWin, sea, sea), BendIn(5636, HVUL, 13, siceWin)),
    TileRow(112, hillyTaiga),
    VertRow(111, Bend(5628, HVDR, 7, 5), Bend(5630, HVUL, 4, 7), BendOut(5632, HVDR, 7), BendIn(5634, HVUL, 11)),//),
    TileRow(110, hillyContForest),
    VertRow(109, BendIn(5628, HVUL), BendOut(5630, HVDR), BendIn(5632, HVUL, 13)),
    VertRow(101, BendOut(5626, HVUp, 7), BendIn(5628, HVDn, 13), BendIn(5630, HVDL, 13)),
    TileRow(100, hillyJungle),
    VertRow(99, BendOut(5630, HVUR, 7), BendIn(5632, HVDL, 7)),
    TileRow(98, hillyJungle, Isle4(hillyJungle)),
    VertRow(97, BendIn(5626, HVUp, 13), Bend(5628, HVDn, 6, 6), OrigRt(5630, HVUL, 7)),
    TileRow(96, savannah),
    TileRow(94, savannah),
    VertRow(93, Orig(5634, HVDn, 1, 5)),
    TileRow(92, oceForest),
    VertRow(91, BendOut(5628, HVDL, 7), OrigLt(5634, HVUp, 7)),
    TileRow(90, hillySavannah),
    VertRow(89, BendIn(5628, HVUR, 13), OrigRt(5630, HVUL, 7)),
    TileRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(114, "Kamchatka")
    str(112, "Salakhin")
    str(110, "Japan north")
    str(98, "New Guinee east", "New Britain")
    str(96, "Australia north east")
  }
}