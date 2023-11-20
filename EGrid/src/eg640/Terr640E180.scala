/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E180 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e180(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, Hland(2, 0, tundra)),
      VRow(127, BendOut(6658, HVUR)),
      TRow(126, hillyTundra, Hland(3, 1, tundra)),
      VRow(125, Mouth(6656, HVUL)),
      TRow(124, sea, Hland(3, 3, hillyTundra)),
      TRow(122, SideB()),
    )
  }
  help.run
}