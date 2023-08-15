/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75 east to 105 east, centred on 90° east. */
object Terr13E90 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e90(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, taiga),
      TRow(110, mtain),
      TRow(108, hillyDesert),
      TRow(106, mtain, mtain),
      TRow(104, Hland(1, 3, Level(Jungle)), hillyJungle),
      VRow(103, Mouth(3582, HVUL), Mouth(3586, HVUR)),
      TRow(102, Hland(4, 1, Hilly(Jungle)), Hland(4, 2, Level(Jungle))),
      TRow(100, sea, Isle(Level(Jungle))),
      TRow(86, Hland(1, 0, Level(IceCap)))
    )
  }
  help.run
}