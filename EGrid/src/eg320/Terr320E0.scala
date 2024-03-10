/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** 320km [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale of 320km.
 * [[Tile5]] 7014.805km² => 10478.907km². Corsica 8722 km² */
object Terr320E0 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e0(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter),
      VRow(157, MouthRt(518, HVDL, 7), MouthLt(520, HVUR, 7)),
      TRow(156, sea * 3),
      TRow(154, sea * 4),
      TRow(152, sea * 3, hillyTaiga),
      VRow(151, BendIn(506, HVUL)),
      TRow(150, sea * 3, hillyTaiga),
      VRow(149, MouthLt(514, HVUp, 7)),
      TRow(148, sea * 3, hillyTaiga, taiga),

      VRow(147, BendIn(504, HVDR, 13), MouthLt(506, HVUR, 7), MouthRt(512, HVUp, 7), MouthRt(514, HVDn, 7), BendIn(516, HVDR, 13), BendIn(518, HVDn, 13),
        Bend(520, HVDL, 5, 1), Bend(524, HVDR, 13, 3)),

      TRow(146, mtainOld, hillyOce, sea, oceanic, oceForest),

      VRow(145, BendIn(502, HVDR, 13), ThreeUp(504, 13, 13, 0), BendOut(506, HVDL, 7), BendOut(512, HVUR, 7), BendIn(514, HVDL, 13), BendIn(516, HVUR, 13),
        MouthMin(518, HVDR), BendIn(520, HVUR), BendOut(522, HVUp, 7), Bend(524, HVUL, 2, 7)),

      TRow(144, hillyOce, oceanic, oceanic, sea, oceanic),
      VRow(143, MouthRt(502, HVDn, 7), BendIn(506, HVUR, 13), BendIn(508, HVDL, 7), BendOut(514, HVUR, 7), BendIn(516, HVDL, 13)),
      TRow(142, sea, oceanic, oceanic, oceanic, oceanic * 2),
      VRow(141, Mouth(506, HVDL, 7, 7), BendIn(508, HVUL, 11), BendIn(510, HVDR, 13), BendIn(512, HVDn), BendIn(514, HVUp), BendIn(516, HVUL, 13)),
      TRow(140, sea, hillyOce, oceanic, oceanic * 3),
      VRow(139, BendIn(506, HVUR, 13), BendIn(508, HVUp, 13), BendIn(510, HVUL, 13)),
      TRow(138, sea * 2, oceanic * 2, hillyOce, mtainOld * 2),
      VRow(137, MouthOld(526, HVUp)),
      TRow(136, sea * 3, oceanic, hillyOce, mtainOld, oceanic),
      VRow(135, MouthOld(516, HVUp), BendAllOld(526, HVUR)),
      TRow(134, sea, hillyOce * 2, CapeOld(2, 1, hillyOce), sea, Isle10(hillyOce), CapeOld(1, 1, hillyOce)),
      VRow(133, BendOut(514, HVDR)),
      TRow(132, sea, hillyOce, oceanic, CapeOld(2, 1), sea, Isle10(hillyOce), sea),
      VRow(131, BendAllOld(512, HVDR), MouthSpec(526, HVUL, HVRt, HVLt)),
      TRow(130, sea, CapeOld(3, 3), CapeOld(3, 1, hillyOce), CapeOld(2, 2, hillyOce), sea, CapeOld(5, 2, mtainOld), CapeOld(0, 2, hillyOce), CapeOld(3, 2, hillyOce)),
      VRow(129, MouthOld(516, HVDn)),
      TRow(128, sea, CapeOld(5, 2, hillyOce), CapeOld(0, 1, hillyOce), hillyOce * 3, CapeOld(1, 1, hillySavannah), sea),
      VRow(127, BendOut(502, HVUL), MouthOld(526, HVDn)),
      TRow(126, sea, sahel, mtainOld, hillyDesert, sahel * 4),
      VRow(125, BendAllOld(500, HVUL)),
      TRow(124, sea, CapeOld(5, 1, hillyDesert), hillyOce, desert * 6),
      VRow(123, SetSep(495), BendOut(498, HVUL)),
      TRow(122, SepB(), CapeOld(5, 1, desert), desert * 8),
      VRow(121, SetSep(495)),
      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
      TRow(118, desert * 9),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(140, "", "English West Country", "" * 4)
    str(134, "" * 5, "Corsica")
    str(132, "" * 5, "Sardinia")
  }
}

object BritReg320
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr320E0.terrs.spawn(Terr320E0.grid, britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] =Terr320E0.sTerrs.spawn(Terr320E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr320E0.corners.spawn(Terr320E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}