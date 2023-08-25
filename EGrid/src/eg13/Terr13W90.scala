/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75 East to 105 East. */
object Terr13W90 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w90(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Isle(tundra)),
      TRow(112, Hland(2, 1, taiga)),
      VRow(111, Mouth(9728, HVDL)),
      TRow(110, forest),
      TRow(108, plain),
      TRow(106, plain, Hland(1, 2)),
      VRow(105, Mouth(9726, HVDL)),
      TRow(104, Hland(2, 0, hillyJungle), Isle(hillyJungle)),
      VRow(103, SetSide(9725)),
      TRow(102, sea, Hland(2, 3, hillyJungle)),
      TRow(100, sea, Hland(2, 4, hillyJungle)),
      VRow(91, Mouth(9730, HVUR)),
      TRow(90, Hland(3, 3, mtain)),
      TRow(86, Hland(4, 5, ice))
    )
  }
  help.run
}