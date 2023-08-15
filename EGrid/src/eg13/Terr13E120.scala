/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1300km or 1.3 MegaMetres. */
object Terr13E120 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e120(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, hillyTundra),
      TRow(112, taiga),
      TRow(110, hillyTaiga),
      TRow(108, Hland(1, 2, Hilly(Forest))),
      TRow(106, hills),
      TRow(104, sea * 2),
      TRow(102, SideB(), sea * 2),
      VRow(101, SetSide(4604)),
      TRow(100, Isle(Hilly(Jungle)), Hland(4, 4, Hilly(Jungle))),
      VRow(99, SetSide(4604)),
      TRow(98, Isle(Hilly(Jungle)), sea),
      TRow(96, Hland(2, 5, Level(Desert)), desert),
      TRow(94, Hland(3, 3), desert),
      TRow(86, Hland(1, 0, Level(IceCap)))
    )
  }
  help.run
}