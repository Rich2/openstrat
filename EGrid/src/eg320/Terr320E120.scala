/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale of 320km. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, SeaIcePerm),
    TRow(164, SeaIceWinter),
    TRow(162, SeaIceWinter * 2),
    TRow(160, SeaIceWinter * 2),
    TRow(158, tundra, hillyTundra, SeaIceWinter),
    TRow(156, taiga * 2, mtainDepr),
    TRow(154, taiga * 3, hillyOce),
    TRow(152, taiga * 2, mtainDepr, hillyTaiga),
    TRow(150, taiga * 4),
    TRow(148, taiga * 5),
    TRow(146, hillyTaiga, mtainDepr, taiga, hillyTaiga * 2),
    TRow(144, mtainDepr, hillyTaiga * 4),
    TRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
    TRow(140, hillyTaiga, oceanic, hillyTaiga * 2, taiga, hillyTaiga),
    TRow(138, deshot * 2, continental, hillyCont, continental, contForest * 2),
    TRow(136, continental * 7),
    VRow(135, BendInRt(4624, HVDR, 13, 7)),
    TRow(134, continental * 6, sea),
    VRow(133, SourceRt(4610, HVDn)),
    TRow(132, continental * 3, hillyCont, hillyCont * 2),
    VRow(131, Source(4608, HVUR, 7, 6), ThreeUp(4610, 0, 13, 13), Bend(4612, HVDL, 12, 7)),
    TRow(130, deshot, continental * 4, hillyCont * 2),
    VRow(129, BendOut(4610, HVDR), ThreeUp(4612, 13, 0, 8), BendMin(4614, HVDL, 4), BendOut(4624, HVUL, 7)),
    TRow(128, continental * 3, subtrop, sea, hillySub, sea, hillySub),
    VRow(127, BendOut(4610, HVUR, 7),BendIn(4612, HVDL, 13), BendIn(4614, HVUR, 7), BendIn(4616, HVUp, 13), SourceRt(4618, HVDL, 7)),
    TRow(126, mtainDepr, hillyOce, subtrop * 3, sea * 2, hillySub),
    VRow(125, BendOut(4612, HVUR, 7), BendIn(4614, HVDL, 13)),
    TRow(124, hillyOce, mtainDepr, oceanic, hillySub * 3),
    )
  }
  help.run
}