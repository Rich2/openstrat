/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale 1300km or 1.3 Megametres. */
object Terr13E30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e30(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(112, taiga),
      TRow(110, land),
      VRow(109, Mouth(1538, HVUp)),
      TRow(108, hilly),
      VRow(107, Mouth(1536, HVDR), Mouth(1538, HVDn)),
      TRow(106, Hland(1, 0, sahel), sahel),
      VRow(105, Mouth(1536, HVUL), BendAll(1538, HVDL), Mouth(1540, HVUL), BendAll(1542, HVUp)),
      TRow(104, desert * 2),
      VRow(103, BendAll(1538, HVUR), BendAll(1540, HVUp)),
      TRow(102, savannah, Hilly(Savannah)),
      VRow(101, BendOut(1542, HVDR)),
      TRow(100, jungle, Hland(1, 2, land)),
      VRow(99, BendOut(1540, HVDR)),
      TRow(98, jungle, Hland(1, 2, jungle)),
      TRow(96, hilly, Isle(forest)),
      TRow(94, Hland(2, 2), sea),
      VRow(87, SetSide(1534)),
      TRow(86, Hland(1, 0, ice)),
    )
  }
  help.run
}