/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west centred on 30° west. 130km per hex tile. */
object Terr13W30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w30(86)
  override val terrs: LayerHcSys[WTile] = LayerHcSys[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(113, BendOut(11780, HVUL)),
      TRow(112, SideB(), sea),
      VRow(111, SetSide(11774), Mouth(11778, HVDn)),
      TRow(110, SideB(), sea),
      VRow(109, SetSide(11774), Mouth(11780, HVUR)),
      TRow(108, sea),
      VRow(105, BendAll(11780, HVUL)),
      TRow(104, sea, Cape(4, 2, desert)),
      VRow(103, Mouth(11780, HVDR)),
      VRow(101, SetSide(11772)),
      TRow(100, SideB(), sea * 2),
      VRow(99, BendAll(11774, HVUR)),
      TRow(98, Cape(1, 2, jungle), sea),
      VRow(97, BendAll(11774, HVDR)),
      TRow(96, SideB(), sea * 2),
      VRow(95, SetSide(11772)),
      VRow(95, BendAll(11772, HVDR)),
      TRow(94, SideB(), sea * 2),
      VRow(93, SetSide(11772)),
      TRow(92, SideB(), sea),
      VRow(87, Mouth(11776, HVDL, wice), BendOut(11780, HVUp, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}