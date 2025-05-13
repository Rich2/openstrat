/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 45° west to 15° wast, centred on 30° west. Hex tile scale of 320km.
 * [[Tile5]] 7014.805km² => 10478.907km². Canaries 7492km². */
object Terr320W30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w30(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(166, ice),
      TileRow(164, ice),
      TileRow(162, ice * 2),
      TileRow(160, ice, Hilly(IceCap)),
      TileRow(158, ice * 2, SeaIceWinter),
      TileRow(156, ice * 2, SeaIceWinter),
      TileRow(154, ice, tundra, sea * 2),
      VertRow(153, BendOut(11774, HVDR, 7), BendIn(11776, HVUL, 10), OrigLt(11778, HVDn, 7), OrigRt(11784, HVDR), BendIn(11786, HVDL)),
      TileRow(152, Land(Hilly, IceCap, LandFree), sea, hillyTundra, hillyTundra),
      VertRow(151, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13), BendIn(11786, HVUL), BendIn(11778, HVUR, 13), OrigRt(11780, HVUL), OrigLt(11784, HVUR, 7)),
      TileRow(150, Land(Hilly, IceCap, LandFree)),
      VertRow(149, BendOut(11770, HVDR, 7), BendIn(11772, HVUL, 13)),
      TileRow(148, tundra),
      VertRow(147, BendIn(11768, HVUp, 13), BendIn(11770, HVUL, 13)),
      VertRow(123, BendIn(11792, HVDR, 11), BendIn(11794, HVDn, 11), ThreeDown(11796, 0, 7, 11)),
      TileRow(122, sea * 8, mtainSavannah),
      VertRow(121, BendIn(11792, HVUR, 11), BendIn(11794, HVUp, 11), BendIn(11796, HVUL, 11)),
      VertRow(119, OrigLt(11792, HVDn, 7)),
      TileRow(118, sea * 8, deshot),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(152, "" * 2, "Iceland west", "Iceland east")
    str(122, "" * 8, "Canaries")
  }
}