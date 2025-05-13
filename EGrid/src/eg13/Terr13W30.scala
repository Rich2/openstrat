/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1300km [[WTile]] terrain for 45° west to 15° west centred on 30° west. A hex tile area of 1.463582932 million km²
 * Isle4 70034.730km² => 115771.696km², Iceland 103000km², Ireland 84421km². */
object Terr13W30 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w30(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      VertRow(115, BendIn(11776, HVDR, 12), BendIn(11778, HVDn, 12), BendIn(11780, HVDL, 12)),
      TileRow(114, hillyTundra),
      VertRow(113, BendIn(11776, HVUR, 12), ThreeDown(11778, 12, 0, 13), BendIn(11780, HVUL, 12)),
      TileRow(112, mtainTundra),
      VertRow(111, BendIn(11774, HVUR, 6, siceWin), ThreeDown(11776, 12, 12, 13), ThreeUp(11778, 5, 12, 12), BendIn(11780, HVDL, 12)),
      TileRow(110, oceanic),
      VertRow(109, ThreeUp(11776, 12, 0, 13), ThreeDown(11778, 12, 6, 0), BendIn(11780, HVUL, 12)),
      TileRow(108, SepB()),
      VertRow(105, BendIn(11778, HVDR, 13), Bend(11780, HVUL, 4, 2)),
      TileRow(104, sea, deshot),
      VertRow(103, OrigRt(11778, HVUp, 7)),
      VertRow(101, BendIn(11774, HVDL, 13)),
      TileRow(100, SepB(), sea * 2),
      VertRow(99, BendIn(11774, HVUR), BendIn(11776, HVDL, 8)),
      TileRow(98, jungle, SepB(), sea),
      VertRow(97, OrigLt(11776, HVUp, 7)),
      TileRow(96, SepB(), sea * 2),
      VertRow(95, SetSep(11772)),
      TileRow(94, SepB(), sea * 2),
      VertRow(93, BendIn(11772, HVUL, 12), SetSep(11773)),
      VertRow(91, BendIn(11774, HVUL, 13)),
      VertRow(87, BendOut(11774, HVDn, 7, siceWin), BendMax(11776, HVUp, siceWin), BendMax(11778, HVDn, siceWin), BendOut(11780, HVUp, 6, siceWin)),
      TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(114, "Iceland")
    str(112, "Greenland south")
    str(110, "Ireland")
  }
}