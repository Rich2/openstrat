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
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, SeaIceWinter),
      TRow(140, tundra, SeaIceWinter),
      TRow(138, tundra, lakesTundra),
      TRow(136, mtainOld, hillyTaiga, mtainOld),
      VRow(135, MouthOld(5636, HVUp)),
      TRow(134, mtainOld, CapeOld(2, 1, mtainOld), mtainOld),
      VRow(133, BendOut(5638, HVDR), BendAllOld(5640, HVDn)),
      TRow(132, CapeOld(2, 1, mtainOld), sea, CapeOld(5, 1, hillyTaiga)),
      VRow(131, MouthOld(5634, HVDn), VertRightsLeft(5638, sea, 7)),
      TRow(130, mtainOld, sea * 2, CapeOld(1, 2, mtainOld)),
      VRow(129, MouthOld(5638, HVDL)),
      TRow(128, CapeOld(1, 2, mtainOld)),
      VRow(127, BendOut(5628, HVDR)),
      TRow(126, CapeOld(2, 2, mtainOld)),
      VRow(125, MouthOld(5624, HVUL)),
      TRow(124, sea, CapeOld(1, 3, mtainOld)),
      VRow(123, MouthOld(5622, HVUR) ),
      TRow(122, CapeOld(5, 4, hillyTemp)),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR)),
      TRow(120, CapeOld(2, 2, hillyTemp), sea * 4),
      VRow(117, MouthOld(5620, HVUR)),
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