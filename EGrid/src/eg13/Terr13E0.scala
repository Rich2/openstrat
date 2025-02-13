/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 15° west to 15° east, centred on 0° east. A hex tile area of 1.463582932 million km²
 *  [[Isle7]] 241548.355km² => 321588.046km², British Isles combined 315159km²
 *  [[Isle4]] 70034.730km² => 115771.696km², Iceland 101826km², Ireland 84421km². */
object Terr13E0 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e0(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp, 7)),
    TileRow(114, hillyTundra),
    VertRow(113, ThreeDown(510, 0, 13, 12), BendOut(512, HVUL)),
    TileRow(112, hillyTaiga),
    VertRow(111, ThreeUp(510, 5, 12, 12), BendIn(512, HVDL, 12)),
    TileRow(110, oceanic),
    VertRow(109, ThreeDown(510, 12, 6, 0), OrigLt(514, HVDn)),
    TileRow(108, hillyOce),
    VertRow(107, BendIn(508, HVDR, 8), ThreeUp(510, 6, 10, 0), BendIn(512, HVUp, 10), BendIn(514, HVUL)),
    TileRow(106, hillySahel, sahel),
    VertRow(105, Bend(508, HVUL, 4, 2)),
    TileRow(104, deshot * 2),
    TileRow(102, savannah * 2),
    VertRow(101, OrigLt(514, HVDn)),
    TileRow(100, sea, jungle),
    VertRow(99, BendIn(512, HVDR, 13), BendOut(514, HVUL, 7)),
    TileRow(98, sea, hillyJungle),
    VertRow(97, BendIn(512, HVUR, 13), BendOut(514, HVDL, 7)),
    TileRow(96, sea, sahel),
    VertRow(95, BendIn(514, HVUR, 13), BendIn(516, HVDL)),
    TileRow(88, siceWin),
    VertRow(87, BendMax(510, HVDn, siceWin), BendOut(512, HVUp, 6, siceWin), BendIn(514, HVDn, 12, siceWin), BendMin(516, HVUp, 3, siceWin)),
    TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "Scandanavia north")
    str(112, "Europe north west")
    str(110, "Europe middle west")
    str(108, "Europe south west")
    str(106, "Morocco", "Algeria")
    str(104, "Mauritania", "Songhai")
    str(102,"Côte d'Ivoire", "Nigeria")
  }
}