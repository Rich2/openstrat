/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 640km.  */
object Terr640E60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e60(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, taiga * 2),
      TRow(122, plain, plain),
      TRow(120, savannah * 3),
      VRow(119, Mouth(2556, HVUp, Lake)),
      TRow(118, sahel, sahel * 2),
      VRow(117, BendAll(2556, HVUR, Lake)),
      TRow(116, Hland(2, 1, sahel, lake), sahel * 2),
      VRow(115, Mouth(2556, HVDL, Lake)),
      TRow(114, hillySahel, hillySahel, sea, sea),
//      TRow(112, desert, plain, desert * 2),
    )
  }
  help.run
}