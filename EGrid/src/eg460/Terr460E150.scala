/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E150 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e150(94)
//  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
//  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
//  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(140, Cape(0, 2, tundra)),
      TRow(138, tundra, tundraLakes),
      TRow(136, mtain, hillyTaiga, mtain),
      VRow(135, MouthOld(5636, HVUp)),
      TRow(134, mtain, Cape(2, 1, mtain), mtain),
      VRow(133, BendOut(5638, HVDR), BendAllOld(5640, HVDn)),
      TRow(132, Cape(2, 1, mtain), sea, Cape(5, 1, hillyTaiga)),
      VRow(131, MouthOld(5634, HVDn), VertRightsLeft(5638, sea, 7)),
      TRow(130, mtain, sea * 2, Cape(1, 2, mtain)),
      VRow(129, MouthOld(5638, HVDL)),
      TRow(128, Cape(1, 2, mtain)),
      VRow(127, BendOut(5628, HVDR)),
      TRow(126, Cape(2, 2, mtain)),
      VRow(125, MouthOld(5624, HVUL)),
      TRow(124, sea, Cape(1, 3, mtain)),
      VRow(123, MouthOld(5622, HVUR) ),
      TRow(122, Cape(5, 4, hilly)),
      VRow(121, BendOut(5622, HVUp), BendOut(5624, HVUL), BendOut(5626, HVDR)),
      TRow(120, Cape(2, 2, hilly), sea * 4),
      VRow(117, MouthOld(5620, HVUR)),
      VRow(101, BendIn(5618, HVDL, 13)),
      VRow(99, MouthOld(5618, HVDn)),
      TRow(98, hillyJungle * 2),
      VRow(97, BendOut(5618, HVDL, 7)),
      TRow(96, jungle * 2, mtain),
      VRow(95, BendIn(5618, HVUR, 13), MouthRt(5620, HVDR)),
      TRow(94, sea * 2, mtain),
    )
  }
  help.run
}