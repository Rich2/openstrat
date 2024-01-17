/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale of 320km. So one of the principles of these terrain grids is that
 *  tiles and tile sides should be specified according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should
 *  not be a sea hex as the majority of the hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex.
 *  Given that it is a land hex by geographical area it must be assigned to France. */
object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(118)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, SeaIceWinter),
      TRow(162, SeaIceWinter * 2),
      TRow(160, SeaIceWinter),
      TRow(156, sea * 3),
      TRow(154, sea * 4),
      TRow(152, sea * 3, hillyTaiga),
      TRow(150, sea * 3, hillyTaiga),
      VRow(149, Mouth(514, HVUp)),
      TRow(148, sea * 3, Cape(4, 1, hillyTaiga), taiga),
      VRow(147, ThreeWay(516)),
      TRow(146, sea, hilly, sea, Cape(4, 4), Cape(2, 3, forest)),
      VRow(145, Mouth(508, HVUL), Mouth(518, HVDR)),
      TRow(144, sea, Cape(1, 2), land, sea, land),
      TRow(142, sea, Cape(2, 2), Cape(5), Cape(1, 3), land * 2),
      VRow(141, Mouth(504, HVUL)),
      TRow(140, sea, Cape(2, 4, hilly), Cape(5, 2), land * 3),

      TRow(138, sea * 2, land * 2, hilly, mtain * 2),
      VRow(137, Mouth(526, HVUp)),
      TRow(136, sea * 3, land, hilly, mtain, land),
      VRow(135, Mouth(516, HVUp), BendAll(526, HVUR)),
      TRow(134, sea, hilly * 2, Cape(2, 1, hilly), sea, Isle(hilly), Cape(1, 1, hilly)),
      VRow(133, BendOut(514, HVDR)),
      TRow(132, sea, hilly, land, Cape(2, 1), sea, Isle(hilly), sea),
      VRow(131, BendAll(512, HVDR), MouthSpec(526, HVUL, HVRt, HVLt)),
      TRow(130, sea, Cape(3, 3), Cape(3, 1, hilly), Cape(2, 2, hilly), sea, Cape(5, 2, mtain), Cape(0, 2, hilly), Cape(3, 2, hilly)),
      VRow(129, Mouth(516, HVDn)),
      TRow(128, sea, Cape(5, 2, hilly), Cape(0, 1, hilly), hilly * 3, Cape(1, 1, hillySavannah), sea),
      VRow(127, BendOut(502, HVUL), Mouth(526, HVDn)),
      TRow(126, sea, Cape(5), mtain, hillyDesert, sahel * 4),
      VRow(125, BendAll(500, HVUL)),
      TRow(124, sea, Cape(5, 1, hillyDesert), hilly, desert * 6),
      VRow(123, SetSide(495), BendOut(498, HVUL)),
      TRow(122, SideB(), Cape(5, 1, desert), desert * 8),
      VRow(121, SetSide(495)),
      TRow(120, desert * 5, hillyDesert * 2, desert * 2),
      TRow(118, desert * 9),
    )
  }
  help.run
}

object BritReg320
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr320E0.terrs.spawn(Terr320E0.grid, britGrid)
  def britSTerrs: LayerHSOptSys[WSide, WSideSome] =Terr320E0.sTerrs.spawn(Terr320E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr320E0.corners.spawn(Terr320E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}