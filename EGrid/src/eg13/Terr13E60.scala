/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° East to 75°, centred on 60° east. Hex tile scale 1300km. */
object Terr13E60 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e60(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendOut(2560, HVUp, 6, SeaIceWinter), OrigLt(2562, HVDL, 7, SeaIceWinter)),
    TileRow(114, tundra),
    TileRow(112, taiga),
    TileRow(110, sahel),
    VertRow(109, OrigLt(2558, HVDn, 7, lake)),
    TileRow(108, sahel),
    VertRow(107, OrigRt(2558, HVUp, 7, lake)),
    TileRow(106, sahel, hillySahel),
    VertRow(105, OrigLt(2556, HVDR), BendIn(2558, HVUp), BendIn(2560, HVDn), BendIn(2562, HVDL, 13)),
    TileRow(104, hillyDeshot, savannah),
    VertRow(103, BendOut(2556, HVUp), BendIn(2558, HVDn, 12), ThreeDown(2560, 13, 0, 13), ThreeUp(2562, 13, 0, 13), OrigLt(2564, HVUL)),
    TileRow(102, deshot),
    VertRow(101, BendOut(2558, HVDR), BendIn(2560, HVUL, 13), OrigLt(2564, HVDR), BendIn(2566, HVUp)),
    VertRow(99, BendOut(2556, HVDR, 7), BendIn(2558, HVUL, 13)),
    VertRow(97, ThreeUp(2556, 0, 6, 6), BendIn(2558, HVDL)),
    TileRow(96, sea * 2),
    VertRow(95, BendIn(2556, HVUp), BendIn(2558, HVUL)),
    TileRow(88, SeaIceWinter),
    VertRow(87, OrigLt(2558, HVDL, 7, siceWin), OrigRt(2562, HVDR, 7, siceWin), BendIn(2564, HVUp, 7, siceWin)),
    TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(112, "Urals")
    str(110, "Kazakstan")
    str(108, "Uzbekistan")
    str(106, "Persia", "Mughalstan")
    str(104, "Oman", "India west")
    str(102, "Africa Horn")
  }
}