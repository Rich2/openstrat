/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      TRow(140, tundra, SeaIceWinter),
      TRow(138, tundra, lakesTundra),
      TRow(136, mtainTundra, hillyTaiga, mtainTundra),
      VRow(135, SourceLt(5636, HVDn)),
      TRow(134, mtainTundra * 3),
      VRow(133, SourceRt(5630, HVDn, 7), BendIn(5634, HVDR, 13), Bend(5636, HVUL, 11, 5), BendOut(5638, HVDR)),
      TRow(132, mtainTaiga, sea, hillyTaiga),
      VRow(131, SourceLt(5628, HVUR, 7), BendIn(5630, HVUL, 13), SourceRt(5634, HVUp), VertRightsLeft(5638, sea, 7)),
      TRow(130, mtainTaiga, sea * 2, hillyTaiga),
      VRow(129, SourceRt(5630, HVDn, 7)),
      TRow(128, mtainTaiga),
      VRow(127, BendMin(5628, HVDR), BendIn(5630, HVUL, 7)),
      TRow(126, mtainTaiga),
      VRow(125, SourceLt(5626, HVUR, 7), ThreeUp(5628, 0, 6, 9), BendIn(5630, HVDL)),
      TRow(124, sea, mtainTaiga),
      VRow(123, SourceRt(5622, HVDL, 7), BendIn(5624, HVDR), BendIn(5626, HVDn), ThreeDown(5628, 6, 0, 13), BendIn(5630, HVUL, 12)),
      TRow(122, hillyOce),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR), BendIn(5628, HVUL, 13)),
      TRow(120, hillyOce),
      VRow(119, BendIn(5626, HVUL, 10)),
      VRow(117, MouthOld(5620, HVUR), SourceRt(5620, HVDL, 7)),
      VRow(101, BendIn(5618, HVDL, 13)),
      VRow(99, MouthOld(5618, HVDn)),
      TRow(98, hillyJungle * 2),
      VRow(97, BendOut(5618, HVDL, 7)),
      TRow(96, jungle * 2, mtainOld),
      VRow(95, BendIn(5618, HVUR, 13), MouthRt(5620, HVDR)),
      TRow(94, sea * 2, mtainOld),
    )
  }
  help.run
}