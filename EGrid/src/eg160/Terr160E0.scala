/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(310, sea * 6, CapeOld(4, 3, mtainOld)),
    TRow(308, sea * 7, mtainOld),
    TRow(306, sea * 7, CapeOld(4, 2, mtainOld)),
    TRow(304, sea * 7, mtainOld),
    TRow(302, sea * 6, CapeOld(5, 2, hillyTaiga), hillyTaiga, taiga),
    TRow(300, sea * 6, CapeOld(5, 1,  mtainOld), hillyTaiga * 2),
    TRow(298, sea * 6, CapeOld(4, 2, mtainOld), mtainOld, hillyTaiga * 2),
    TRow(296, sea * 6, hillyTaiga * 2, taiga * 2),
    VRow(295, MouthOld(528, HVUp)),
    TRow(294, sea * 2, hillyOce, hillyOce, sea * 3, hillyOce, CapeOld(2, 2, hillyTaiga), taiga),
    TRow(292, sea * 3, hillyOce, CapeOld(1, 2, hillyOce), sea * 4, CapeOld(5, 4), oceanic),
    TRow(290, sea * 2, CapeOld(4, 2, hillyOce), hillyOce, sea * 4, oceanic, Isle10(), oceanic),
    VRow(289, BendOut(502, HVDL)),
    TRow(288, sea * 2, oceanic, CapeOld(3, 2, hillyOce), oceanic, sea * 4, oceanic, sea),
    VRow(287, BendIn(520, HVDR, 13),  BendIn(522, HVDn, 13), MouthLt(524, HVDR, 7)),
    TRow(286, sea * 2, oceanic, CapeOld(1, 2), oceanic * 2, sea * 2, oceanic * 4),
    VRow(285, ThreeDown(518, 0, 10, 13)),
    TRow(284, sea, oceanic * 2, hillyOce, oceanic * 3, oceanic * 5),
    VRow(283, Bend(516, HVDR, 8, 7), Bend(518, HVUL, 13, 4)),
    TRow(282, sea * 4, hillyOce, oceanic * 2, oceanic * 2, hillyOce * 2, oceanic * 2),
    VRow(281, Source(510, HVUR, 6, 7), BendIn(512, HVDn, 13), BendIn(514, HVUp, 10), BendIn(516, HVUL, 13)),
    TRow(280, sea * 4, hillyOce, sea, oceanic * 2, hillyOce * 5),
    VRow(279, SourceLt(502, HVDR, 7), BendInLt(504, HVUp, 13, 7), BendInLt(506, HVDn, 13, 7), SourceLt(508, HVUL, 7)),
    TRow(278, sea * 4, oceanic * 5, hillyOce, oceanic * 2, hillyOce),
    TRow(276, sea * 5, oceanic * 4, hillyOce * 2, mtainOld * 3),
    TRow(274, sea * 6, oceanic * 3, hillyOce, mtainOld * 4),
    VRow(273, Source(538, HVDn, 2, 7)),
    TRow(272, sea * 6, oceanic, hillyOce * 2, mtainOld, hillyOce, mtainOld, oceanic, hillyOce),
    VRow(271, Bend(538, HVUR, 11, 7), MouthOld(540, HVDR)),
    TRow(270, sea * 2, CapeOld(4, 3), CapeOld(0, 1, hillyOce) * 3, oceanic * 2, hillyOce * 2, mtainOld, sea, hillyOce * 2, sea),
    TRow(268, sea * 3, hillyOce, oceanic, hillyDeshot * 2, mtainOld * 2, sea * 3, Isle10(hillyOce), hillyOce * 2),
    TRow(266, sea * 2, hillyOce * 2, deshot, hillyDeshot * 2, hillyOce, sea * 3, hillyOce, sea * 2, hillyOce),
    VRow(265, MouthOld(514, HVUp)),
    TRow(264, sea * 3, hillyOce * 2, hillyDeshot, hillyOce, CapeOld(2, 1, hillyOce), sea, Isle10(hillyOce), sea * 2, hillyOce, sea * 2),
    TRow(262, sea * 3, oceanic * 2, hillyOce * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: LayerHcRefSys[WTile] = Terr160E0.terrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] =Terr160E0.sTerrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britCorners: HCornerLayer = Terr160E0.corners.spawn(Terr160E0.grid, EGrid160.britGrid)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}