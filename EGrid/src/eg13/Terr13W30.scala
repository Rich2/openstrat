/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west centred on 30° west. 130km per hex tile. */
object Terr13W30 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w30(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(113, BendOut(11780, HVUL)),
      TRow(112, SepB(), sea),
      VRow(111, SetSep(11774), Mouth(11778, HVDn)),
      TRow(110, SepB(), sea),
      VRow(109, SetSep(11774), Mouth(11780, HVUR)),
      TRow(108, sea),
      VRow(105, BendAll(11780, HVUL)),
      TRow(104, sea, Cape(4, 2, desert)),
      VRow(103, Mouth(11780, HVDR)),
      VRow(101, BendIn(11774, HVDL, 13)),
      TRow(100, SepB(), sea * 2),
      VRow(99, BendIn(11774, HVUR), BendIn(11776, HVDL, 8)),
      TRow(98, jungle, SepB(), sea),
      VRow(97, BendAll(11774, HVDR), BendIn(11776, HVUL, 13)),
      TRow(96, SepB(), sea * 2),
      VRow(95, SetSep(11772)),
      VRow(95, BendAll(11772, HVDR)),
      TRow(94, SepB(), sea * 2),
      VRow(93, SetSep(11772)),
      TRow(92, SepB(), sea),
      VRow(87, Mouth(11776, HVDL, wice), BendOut(11780, HVUp, 6, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}