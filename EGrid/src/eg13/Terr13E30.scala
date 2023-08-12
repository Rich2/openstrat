/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

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
//      TRow(118, sea),
//      TRow(116, taiga),
//      TRow(114, plain),
      TRow(112, taiga),
      TRow(110, plain),
      VRow(109, Mouth(1538, HVUp)),
      TRow(108, hills),
      VRow(107, Mouth(1536, HVDR), Mouth(1538, HVDn)),
      TRow(106, Hland(1, 0, Level(Desert)), desert),
      VRow(105, Mouth(1536, HVUL), VertIn(1538, HVDL), Mouth(1540, HVUL)),
      TRow(104, desert * 2),
      VRow(103, VertIn(1538, HVUR), VertIn(1540, HVUp)),
      TRow(102, jungle * 2),
      TRow(100, jungle, Hland(1, 2, Level())),
      TRow(98, jungle, Hland(1, 2, Level(Jungle))),
      TRow(96, hills, Isle(Level(Forest))),
      TRow(94, Hland(2, 2), sea),
      VRow(87, SetSide(1534)),
      TRow(86, Hland(1, 0, Level(IceCap))),
    )
  }
  help.run
}