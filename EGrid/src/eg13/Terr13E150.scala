/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1300km or 1.3Megametre. */
object Terr13E150 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(114, hillyTundra),
      TRow(112, hillyTundra),
//      TRow(110, hillyTaiga),
//      TRow(108, hillyForest),
//      TRow(106, hills),
//      TRow(104, sea * 2),
//      TRow(102, SideB(), sea * 2),
      VRow(101, SetSide(5628)),
      TRow(100, SideB(), sea * 2),
//      VRow(99, SetSide(4604)),
      TRow(98, Hland(3, 2, Hilly(Jungle)), sea),
      TRow(96, Hland(3, 0, Hilly()), sea),
      TRow(94, plain, sea),
//      TRow(86, Hland(1, 0, Level(IceCap)))
    )
  }
  help.run
}