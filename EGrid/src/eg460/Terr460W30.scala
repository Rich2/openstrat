/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 460km. Maximum Isle area is 120974.276km², which would
 *  include Iceland if it was centred.
 *  Isle3 from 8768.845km² down to 4473.900km² includes the Canaries. */
object Terr460W30 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w30(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val terrSet: WTerrSetter = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(146, ice),
      VertRow(145, BendOut(11780, HVDn, 6, SeaIceWinter, sea)),
      TileRow(144, ice),
      VertRow(143, BendOut(11778, HVUR, 7, SeaIceWinter), BendIn(11780, HVDL, 11, SeaIceWinter)),
      TileRow(142, ice),
      VertRow(141, BendIn(11778, HVDR, 6, SeaIceWinter), BendIn(11780, HVUL, 11, SeaIceWinter)),
      TileRow(140, ice),
      VertRow(139, BendIn(11776, HVDR, 6, SeaIceWinter), BendOut(11778, HVUL, 6, SeaIceWinter)),
      TileRow(138, ice),
      VertRow(137, Bend(11774, HVDR, 4, 2, SeaIceWinter), BendIn(11776, HVUL, 6, SeaIceWinter)),
      TileRow(136, ice, sea, mtainTundra),
      VertRow(135, BendMin(11772, HVDR, 3, SeaIceWinter), BendIn(11774, HVUL, 13, SeaIceWinter), OrigMin(11778, HVDR), BendIn(11780, HVUp), OrigRt(11782, HVDL)),
      VertRow(133, BendIn(11770, HVUp, 6, SeaIceWinter), BendIn(11772, HVUL, 6, SeaIceWinter)),
      VertRow(117, BendIn(11786, HVDR, 13), BendIn(11788, HVDn, 13), ThreeDown(11790, 0, 10, 13)),
      TileRow(116, sea * 5, mtainSavannah),
      VertRow(115, BendIn(11786, HVUR, 13), BendIn(11788, HVUp, 13), BendIn(11790, HVUL, 13)),
      VertRow(113, OrigLt(11786, HVDn)),
      TileRow(112, sea * 5, deshot),
      VertRow(111, BendIn(11784, HVDR, 13), BendOut(11786, HVUL, 7)),
      TileRow(110, sea * 5, sahel),
      VertRow(109, BendIn(11784, HVUR, 13), BendOut(11786, HVDL, 7)),
      TileRow(108, sea * 6, savannah),
      VertRow(107, BendIn(11786, HVUR, 13), BendOut(11788, HVDL, 7)),
      TileRow(106, sea * 6, hillySavannah),
      VertRow(105, BendIn(11788, HVUR, 13), BendOut(11790, HVDL, 7)),
      VertRow(103, BendIn(11790, HVUR, 13), BendIn(11792, HVUp, 13)),
      VertRow(101, BendIn(11762, HVDL, 13)),
      VertRow(99, OrigMin(11762, HVUp), OrigRt(11766, HVDR, 7), BendOut(11768, HVUp, 7), BendIn(11770, HVDn, 13), BendIn(11772, HVDL, 13)),
      TileRow(98, hillySavannah, savannah),
      VertRow(97, BendOut(11772, HVUR, 7), BendIn(11774, HVDL, 13)),
      TileRow(96, hillyJungle * 2, hillySavannah),
      VertRow(95, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13)),
      TileRow(94, hillySavannah * 2),
      VertRow(93, BendOut(11770, HVDR, 7), BendIn(11772, HVUL, 13)),
      TileRow(92, hillySavannah, mtainJungle),
      VertRow(91, BendMin(11768, HVDR), BendIn(11770, HVUL, 13)),
      TileRow(90, hillyJungle),
      VertRow(89, BendOut(11768, HVUR), BendIn(11770, HVDL, 13)),
      TileRow(88, hillySub),
      VertRow(87, OrigLt(11768, HVUR, 7), BendIn(11770, HVUL, 13)),
    )
  }
  terrSet.run
}