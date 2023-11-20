/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 45° west to 15° west, centred on 30° wast. Hex tile scale 640km.  */
object Terr640W30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w30(112)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, ice),
      TRow(128, Cape(1, 2, ice)),
      TRow(126, ice, Cape(4, 3, hillyTundra)),

//      TRow(112, desert, plain, desert * 2),
//      TRow(150, sea * 3, hillyTaiga),
//      TRow(148, sea * 3, hillyTaiga, taiga),
//      TRow(146, sea, hills, sea, Hland(4, 4), Hland(3, 2, Level(Forest))),
//      VRow(145, Mouth(508, HVUL)),
//      TRow(144, sea, Hland(2, 1), plain, sea, plain),
//      TRow(142, sea, Hland(2, 2), Hland(1, 5), Hland(3, 1), plain * 2),
//      VRow(141, Mouth(504, HVUL)),
//      TRow(140, sea, Hland(4, 2, Hilly()), Hland(2, 5), plain * 3),
//      TRow(138, sea * 2, plain * 2, hills, mtain * 2),
//      VRow(137, Mouth(526, HVUp)),
//      TRow(136, sea * 3, plain, hills, mtain, plain),
//      VRow(135, VertIn(526, HVUR)),
//      TRow(134, sea, hills * 3, sea, Isle(Hilly()), Hland(1, 1, Hilly())),
//      TRow(132, sea, hills, plain * 2, sea, Isle(Hilly()), sea),
//      VRow(131, MouthSpec(526, HVUL, HVRt, HVLt)),
//      TRow(130, sea, Hland(3, 3), Hland(1, 3, Hilly()), Hland(2, 2, Hilly()), sea * 2, Hland(3, 5, Hilly()), Hland(2, 3, Hilly())),
//      TRow(128, sea, Hland(2, 5, Hilly()), Hland(1, 0, Hilly()), hills * 3, Hland(1, 1, Hilly()), sea),
//      TRow(126, sea, Hland(1, 5), mtain, hillyDesert, desert * 4),
//      TRow(124, sea, hillyDesert, hills, desert * 6),
//      VRow(123, SetSide(495)),
//      TRow(122, SideB(), desert * 9),
//      VRow(121, SetSide(495)),
//      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
//      TRow(118, desert * 9),
    )
  }
  help.run
}