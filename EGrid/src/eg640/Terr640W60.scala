/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 640km.  */
object Terr640W60 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w60(96)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, tundra),
      TRow(128, tundra),
      TRow(126, tundra, hillyTundra),
      VRow(125, BendOut(10758, HVDR)),
      TRow(124, Mountains(Tundra), mtain),
      VRow(123, MouthOld(10748, HVDR), BendIn(10756, HVUp, 13), BendIn(10758, HVUL, 9)),
      TRow(122, taiga, sea),
      TRow(120, taiga, taiga),
      VRow(119, MouthOld(10750, HVUL), MouthOld(10752, HVDR)),
      TRow(118, Cape(2, 2, taiga), sea),
      VRow(117, MouthOld(10748, HVUL)),
      VRow(109, BendIn(10746, HVDL, 6), ThreeUp(10744, 0, 13, 13)),
      VRow(107, BendIn(10746, HVUL, 7)),
      VRow(105, BendOut(10742, HVUp), BendOut(10746, HVUp), BendAll(10750, HVUp)),
      TRow(104, Cape(0, 1, hillyJungle), Cape(0, 1, jungle), Cape(0, 2, jungle)),
      VRow(103, BendOut(10754, HVUR), BendOut(10756, HVUp)),
      TRow(102, savannah, hillyJungle, jungle, Cape(0, 2, hillyJungle)),
      VRow(101, BendOut(10760, HVUR), MouthOld(10762, HVDR)),
      TRow(100, jungle * 3, hillyJungle, jungle),
      TRow(98, jungle * 4, hillyJungle),
      TRow(96, jungle * 4, hillyJungle),
    )
  }
  help.run
}