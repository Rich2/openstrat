/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 45° west to 15° west centred on 30° west. 130km per hex tile. */
object Terr13W30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w30(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(112, SideB(), sea),
      VRow(111, SetSide(11774)),
      TRow(110, SideB(), sea),
      VRow(109, SetSide(11774)),
      TRow(108, SideB(), sea),
      VRow(105, BendAll(11780, HVUL)),
      TRow(104, sea, Hland(2, 4, desert)),
      VRow(103, Mouth(11780, HVDR)),
      VRow(101, SetSide(11772)),
      TRow(100, SideB(), sea * 2),
      TRow(98, Hland(2, 1, jungle), sea),
      TRow(96, SideB(), sea * 2),
      VRow(95, SetSide(11772)),
      TRow(94, SideB(), sea * 2),
      VRow(93, SetSide(11772)),
      TRow(92, SideB(), sea),
    )
  }
  help.run
}