/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 640km. A hex tile area of 354724.005km².
 *  A minimum island area of 59120.667km², which
 *  [[Isle5]] 28059.223km² => 41915.629km². (Sardinia 24090km²) + (Corsica.8722km²) = 32812. */
object Terr640E0 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e0(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(131, OrigRt(512, HVDn)),
    VertRow(129, BendOut(510, HVDR), BendIn(512, HVUL, 13), OrigRt(514, HVUR, 7)),
    VertRow(127, SetSep(509), OrigLt(512, HVDn, 7)),
    TileRow(126, sea, hillyTaiga),
    VertRow(125, OrigRt(508, HVDL), BendIn(510, HVDR, 13), ThreeUp(512, 13, 13, 0), BendIn(514, HVDL, 13), OrigRt(518, HVDn, 7)),
    TileRow(124, hillyOce, taiga),
    VertRow(123, BendIn(508, HVDR, 10), BendOut(510, HVUL), BendIn(512, HVDR, 13), ThreeUp(514, 0, 6, 13), BendOut(516, HVUp), Bend(518, HVUL, 2, 4)),
    TileRow(122, oceanic * 2),
    VertRow(121, OrigRt(508, HVUp), OrigRt(510, HVUR, 7), BendOut(512, HVUL, 7)),
    TileRow(120, sea, oceanic * 2),
    VertRow(119, OrigLt(508, HVDn, 7)),
    TileRow(118, hillyOce, oceanic, hillyOce),
    VertRow(117, BendIn(506, HVDR, 13), BendOut(508, HVUL), Bend(514, HVDR, 11, 3), Bend(516, HVDn, 11, 2), BendIn(518, HVDL, 11)),
    TileRow(116, hillyOce, hillyOce, mtainSubForest),

    VertRow(115, OrigRt(506, HVUp, 7), OrigRt(508, HVUR), BendIn(510, HVDn, 10), BendMin(512, HVUp), ThreeUp(514, 11, 3, 10), BendIn(516, HVUp, 11),
      ThreeUp(518, 0, 8, 11), BendIn(520, HVDL, 13)),

    TileRow(114, sea, hillyOce, hillyOce, hillyOce),
    VertRow(113, BendMin(520, HVUR, 1), OrigMin(522, HVUL, 4)),
    TileRow(112, hillyDeshot, deshot * 3),
    TileRow(110, deshot * 2, hillyDeshot, deshot),
    TileRow(108, deshot * 4),
    TileRow(106, sahel * 5),
    TileRow(104, Land(Hilly, Savannah, Forest), Land(Plain, Savannah, Forest) * 3, Land(Hilly, Savannah, Forest)),
    TileRow(102, jungle, jungle * 2, jungle, jungle),
    VertRow(101, OrigLt(504, HVDR, 7), BendIn(506, HVUp, 11), BendOut(508, HVDn), BendIn(510, HVUp, 13), BendOut(512, HVDn, 7), BendIn(514, HVUp, 13),
      BendOut(516, HVDn), OrigRt(518, HVUL)),
    TileRow(100, sea * 4, hillyJungle),
    TileRow(98, sea * 4, hillyJungle),
    VertRow(97, BendIn(518, HVDR, 13), OrigLt(520, HVDL)),
    TileRow(96, sea * 4, hillySavannah),
    VertRow(95, BendIn(518, HVUR, 13), OrigRt(520, HVUL)),
    TileRow(94, sea * 4, savannah),
    VertRow(93, OrigLt(518, HVDn, 7)),
    TileRow(92, sea * 3, hillySahel),
    VertRow(91, OrigRt(518, HVUp, 7)),
    VertRow(89, BendIn(518, HVDR, 13), OrigMin(520, HVDL, 1)),
    TileRow(88, sea * 3, hillySahel),
    VertRow(87, BendIn(518, HVUR, 13), BendOut(520, HVDL, 7)),
    TileRow(72, ice),
    TileRow(70, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(124, "Scotland", "Scandanavia south")
    str(122, "British Isles", "Denmark")
    str(116, "Portugal", "Spain east", "Sardinia-Corsica")
  }
}