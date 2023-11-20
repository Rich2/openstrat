/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 640km. A hex tile area of 709448.010km² . A minimum island
 *  area of 118241.335km², which includes Britain but excludes Ireland and Sicily. */
object Terr640E0 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e0(108)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(124, sea, taiga),
      VRow(123, Mouth(512, HVUp)),
      TRow(122, Cape(3, 4), land),
      VRow(121, BendAll(512, HVUL)),
      TRow(120, sea, Cape(1, 5), land),
      TRow(118, sea, land, hilly),
      TRow(116, Cape(3, 4, hilly), Cape(1, 2, hilly), sea),
      VRow(115, BendAll(512, HVUp), Mouth(516, HVDL)),
      TRow(114, sea, Cape(2, 5, hilly), hilly, Cape(2, 0, hilly)),
      VRow(113, Mouth(520, HVDn)),
      TRow(112, desert * 4),
      TRow(110, desert * 4),
      TRow(108, desert * 4),
    )
  }
  help.run
}