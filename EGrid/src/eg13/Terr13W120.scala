/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

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
      VRow(109, Mouth(8704, HVUR)),
      TRow(108, Cape(2, 4, hillyForest)),
      VRow(107, BendAll(8704, HVDL)),
      TRow(106, sea, Cape(1, 4, hillyDesert)),
      VRow(105, BendOut(8706, HVDL)),
      TRow(104, sea, Cape(2, 3, hillySahel)),
    )
  }
  help.run
}