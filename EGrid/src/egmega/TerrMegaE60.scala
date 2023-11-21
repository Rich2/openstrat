/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 1 MegaMetre or 1000km. */
object TerrMegaE60 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e60(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, tundra),
      TRow(116, taiga),
      TRow(114, land),
      TRow(112, desert * 2),
      TRow(110, desert * 2),
      TRow(108, hillyDesert * 2),
      TRow(106, desert, sea, land),
      TRow(104, SideB(), Cape(2, 2, hillyDesert), sea * 2),
      TRow(96, SideB(), Cape(1, 2, hilly), sea * 2),
    )
  }
  help.run
}