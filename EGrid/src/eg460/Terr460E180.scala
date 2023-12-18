/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E180 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e180(116, 122)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(128, Cape(0, 2, tundra)),
//      VRow(127, BendOut(6658, HVUR)),
//      TRow(126, hillyTundra, Cape(1, 3, tundra)),
//      VRow(125, Mouth(6656, HVUL)),
//      TRow(124, sea, Cape(3, 3, hillyTundra)),
//      TRow(122, SideB()),
    )
  }
  help.run
}