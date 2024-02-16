/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 1300km or 1.3 Megametres.
 * Isle 463086.787km² => 966193.420km². Madagascar 587,041km².
 *  */
object Terr13E30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e30(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      //VRow(115, BendIn(512, HVDR, 13)),
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, land),
      VRow(109, MouthLt(1534, HVUp), Mouth(1538, HVUp)),
      TRow(108, hilly),
      VRow(107, ThreeUp(1534, 6, 6, 0), Mouth(1536, HVDR), Mouth(1538, HVDn)),
      TRow(106, sahel, sahel),
      VRow(105, Mouth(1536, HVUL), BendAll(1538, HVDL), Mouth(1540, HVUL), BendAll(1542, HVUp)),
      TRow(104, desert * 2),
      VRow(103, BendAll(1538, HVUR), BendAll(1540, HVUp)),
      TRow(102, savannah, Hilly(Savannah)),
      VRow(101, BendOut(1542, HVDR)),
      TRow(100, jungle, hillySavannah),
      VRow(99, BendOut(1540, HVDR, 7)),
      TRow(98, jungle, jungle),
      TRow(96, hilly, Isle(forest)),
      TRow(94, Cape(2, 2), sea),
      VRow(87, BendOut(1536, HVUp, 6, wice), Mouth(1538, HVUR, 3, wice), Mouth(1540, HVDL, 3, wice)),
      TRow(86, ice)
    )
  }
  help.run
}