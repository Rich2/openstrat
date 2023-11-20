/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W150 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w150(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      VRow(113, Mouth(7678, HVUp)),
      TRow(112, Cape(1, 3, hillyTundra)),
      VRow(111, BendAll(7678, HVUR)),
      VRow(109, Mouth(7684, HVUR)),
      TRow(92, SideB(), sea),
    )
  }
  help.run
}