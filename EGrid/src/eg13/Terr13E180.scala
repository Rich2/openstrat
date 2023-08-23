/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1300km or 1.3 Megametre. */
object Terr13E180 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e180(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(112, hillyTundra),
      TRow(108, SideB(), sea),
      TRow(92, Isle(hills)),
    )
  }
  help.run
}