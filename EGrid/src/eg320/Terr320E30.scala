/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale of 320km. */
object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(118)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, Isle(Level, IceCap, LandFree, SeaIceWinter)),
      TRow(162, Land(Mountains, IceCap, LandFree)),//Check
      TRow(156, Cape(5, 2, hillyTundra), Cape(0, 2, hillyTundra), sea),
      VRow(155, Mouth(1538, HVDn)),
      TRow(154, hillyTaiga, taiga * 2, Cape(0, 4, tundra)),
      VRow(153, Mouth(1534, HVUp)),
      TRow(152, Cape(2,1, taiga), taiga, Cape(1, 2, taiga), taiga),
      VRow(151, BendAll(1532, HVDR), Mouth(1540, HVDL)),
      TRow(150, Cape(2, 1, taiga), taiga * 3),
      VRow(149, Mouth(1536, HVUL, Lake), Mouth(1538, HVDR, Lake)),
      VRow(147, Mouth(1534, HVUR)),
      TRow(148, Cape(2, 1, taiga), Cape(3, 3, taiga), taiga * 3),
      TRow(146, Cape(5, 2), taiga * 4),
      VRow(145, SetSep(1527)),
      TRow(144, land * 5),
      TRow(142, land * 6),
      TRow(140, land * 6),
      TRow(138, mtain * 2, hilly, land * 3, desert),
      VRow(137, Mouth(1522, HVUp), MouthLtRt(1538, HVUp, 3, 7), BendAll(1542, HVDR), Mouth(1544, HVUR)),
      TRow(136, hilly, land * 2, land, land, land, land),

      VRow(135, SetSep(1523), BendAll(1522, HVUR), BendOut(1536, HVDR, 7), ThreeUp(1538, 7, 0, 13), BendIn(1540, HVUp, 10), ThreeUp(1542, 7, 0, 7),
        MouthRt(1544, HVDR, 7)),

      TRow(134, Cape(4, 1, hilly), hilly, hilly, sea * 3, mtain),
      VRow(133, Mouth(1530, HVUp), MouthLt(1536, HVDn, 7)),
      TRow(132, Cape(1, 2, hilly), hilly, Cape(4, 1, hilly), hilly * 4),
      VRow(131, VertLeftsRight(1522), Mouth(1528, HVDR)),
      TRow(130, Cape(2, 4, hilly), Cape(3, 4, hilly), Cape(1, 3, hilly), Cape(3, 2, hilly), hilly * 4),
      VRow(129, BendAll(1528, HVDn), Mouth(1536, HVUR)),
      TRow(128, sea * 2, Isle(hillySavannah), sea, Isle(hilly), hilly, desert * 2),
      VRow(127, BendAll(1528, HVUp)),
      TRow(126, sea, Cape(5, 2, sahel), Cape(0, 2, sahel), sea * 2, Cape(5, 1, hilly), desert * 2),
      VRow(125, Mouth(1524, HVDn), Mouth(1532, HVDn), Mouth(1540, HVDn)),
      TRow(124, sahel * 4, savannah, desert * 4),
      VRow(123, Mouth(1538, HVUL), Mouth(1540, HVDR)),
      TRow(122, desert * 4, savannah, sea, desert * 3),
      TRow(120, desert * 6, sea, desert * 2),
      TRow(118, desert * 5, hillyDesert, sea, hillyDesert, desert),
    )
  }
  help.run
}