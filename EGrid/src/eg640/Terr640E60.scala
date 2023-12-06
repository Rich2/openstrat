/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 640km.  */
object Terr640E60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e60(102)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(128, tundra),
      TRow(126, taiga * 2),
      TRow(124, taiga * 2),
      TRow(122, land, land),
      TRow(120, savannah * 3),
      VRow(119, Mouth(2556, HVUp, Lake)),
      TRow(118, sahel, sahel * 2),
      VRow(117, BendAll(2556, HVUR, Lake)),
      TRow(116, Cape(1, 2, sahel, lake), sahel * 2),
      VRow(115, Mouth(2556, HVDL, Lake)),
      TRow(114, hillySahel, hillySahel, Land(Mountains, Desert), Land(Mountains, Sahel)),
      TRow(112, hillyDesert, hillyDesert, Land(Mountains, Desert), savannah),
      VRow(111, Mouth(2554, HVUL), BendOut(2556, HVUp)),
      TRow(110, sahel, Cape(0, 2, sahel), Cape(3, 2, sahel), savannah),
      VRow(109, Mouth(2564, HVUR)),
      TRow(108, sahel, sea * 2, savannah),
      TRow(106, Cape(2, 2, desert)),
      TRow(104, Cape(0, 1, desert)),

    )
  }
  help.run
}