/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, mtain),
      VRow(111, Mouth(8708, HVDL)),
      TRow(110, hillyTaiga),
      TRow(108, hillyDesert),
      TRow(106, sea, Hland(1, 4, hillyDesert)),
      TRow(104, sea, Hland(2, 3, hillyDesert)),
    )
  }
  help.run
}