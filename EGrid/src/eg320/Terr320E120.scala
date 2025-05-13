/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale of 320km. */
object Terr320E120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e120(124)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, SeaIceWinter),
    TileRow(162, SeaIceWinter * 2),
    TileRow(160, SeaIceWinter * 2),
    TileRow(158, tundra, hillyTundra, SeaIceWinter),
    TileRow(156, taiga * 2, mtainDepr),
    TileRow(154, taiga * 3, hillyOce),
    TileRow(152, taiga * 2, mtainDepr, hillyTaiga),
    TileRow(150, taiga * 4),
    TileRow(148, taiga * 5),
    TileRow(146, hillyTaiga, mtainDepr, taiga, hillyTaiga * 2),
    TileRow(144, mtainDepr, hillyTaiga * 4),
    TileRow(142, hillyTaiga, taiga, hillyTaiga * 2, taiga, hillyTaiga),
    TileRow(140, hillyTaiga, oceanic, hillyTaiga * 2, taiga, hillyTaiga),
    TileRow(138, deshot * 2, continental, hillyCont, continental, contForest * 2),
    TileRow(136, continental * 7),
    VertRow(135, BendInRt(4624, HVDR, 13, 7)),
    TileRow(134, continental * 6, sea),
    VertRow(133, OrigRt(4610, HVDn)),
    TileRow(132, continental * 3, hillyCont, hillyCont * 2),
    VertRow(131, Orig(4608, HVUR, 7, 6), ThreeUp(4610, 0, 13, 13), Bend(4612, HVDL, 12, 7), OrigRt(4618, HVDR, 7), BendIn(4620, HVDL, 13)),
    TileRow(130, deshot, continental * 4, hillyCont * 2),
    VertRow(129, BendOut(4610, HVDR), ThreeUp(4612, 13, 0, 8), BendMin(4614, HVDL, 4), OrigLt(4620, HVUp, 7), BendOut(4624, HVUL, 7)),
    TileRow(128, continental * 3, subtrop, sea, hillySub, sea, hillySub),
    VertRow(127, BendOut(4610, HVUR, 7),BendIn(4612, HVDL, 13), BendIn(4614, HVUR, 7), BendIn(4616, HVUp, 13), OrigRt(4618, HVDL, 7)),
    TileRow(126, mtainDepr, hillyOce, subtrop * 3, sea * 2, hillySub),
    VertRow(125, BendOut(4612, HVUR, 7), BendIn(4614, HVDL, 13)),
    TileRow(124, hillyOce, mtainDepr, oceanic, hillySub * 3),
    )
  }
  help.run
}