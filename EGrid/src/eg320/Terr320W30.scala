/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° wast, centred on 30° west. Hex tile scale of 320km. */
object Terr320W30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w30(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, ice),
      TRow(164, ice),
      TRow(162, ice * 2),
      TRow(160, ice, Hilly(IceCap)),
      TRow(158, ice * 2, SeaIceWinter),
      TRow(156, ice * 2, SeaIceWinter),
      TRow(154, ice, tundra, sea * 2),
      VRow(153, BendOut(11774, HVDR, 7), BendIn(11776, HVUL, 10)),
      TRow(152, Land(Hilly, IceCap, LandFree), sea, hillyTundra, hillyTundra),
      VRow(151, BendOut(11772, HVDR, 7), BendIn(11774, HVUL, 13), BendIn(11786, HVUL)),
      TRow(150, Land(Hilly, IceCap, LandFree)),
      VRow(149, BendOut(11770, HVDR, 7), BendIn(11772, HVUL, 13)),
      TRow(148, CapeOld(2, 2, tundra)),
      TRow(122, sea * 8, Isle10(hillyTemp)),
      TRow(118, sea * 8, desert),
    )
  }
  help.run
}