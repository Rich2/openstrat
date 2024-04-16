/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 135° east to 165° east, centred on 150° east. */
object Terr220E150 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e150(138, 152)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(152, sea, hillyContForest * 2),
    TRow(150, sea, hillyContForest * 2, continental),
    VRow(149, OrigRt(5616, HVUp, 7), OrigRt(5618, HVUR), BendIn(5620, HVDn, 13), BendIn(5622, HVDL, 13)),
    TRow(148, sea * 2, hillyContForest),
    VRow(147, OrigLt(5622, HVUp, 7)),
    TRow(146, sea, mtainContForest),
    TRow(144, mtainSubForest, mtainContForest),
    TRow(142, mtainSubForest, hillySubForest, hillySub),
    TRow(140, mtainSubForest),
    )
  }
  help.run
}
