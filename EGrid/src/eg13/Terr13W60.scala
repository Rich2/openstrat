/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr13W60 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.w60(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, Hland(1, 4, Level, IceCap)),
      TRow(112, Hland(3, 5, Hilly, Taiga)),
      TRow(110, Hland(3, 1, Hilly, Forest)),
      VRow(109, Mouth(10752, HVUL)),
      TRow(108, Hland(3, 1, Hilly, Forest)),
//      VRow(107, Mouth(2556, HVDR)),
      TRow(106, SideB(), sea * 2),
      VRow(105, SetSide(10748)),
      TRow(104, SideB(), sea * 2),
      TRow(102, Hland(2, 0, Hilly), sea),
      TRow(100, jungle, Hland(1, 1, Level, Jungle)),
      TRow(98, hills, plain),
      TRow(96, hills, Hland(1, 2)),
      TRow(94, Hland(1, 5, Mountains), Hland(2, 2)),
      VRow(93, Mouth(10752, HVUL)),
      TRow(92, Hland(2, 1)),
      VRow(89, SetSide(10750)),
      VRow(87, SetSide(10750)),
      TRow(86, SideB(), sea),
    )
  }
  help.run
}

/*
object BritReg
{ def britGrid: EGrid14Long = EGrid14Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = Terr14E0.terrs.spawn(Terr14E0.grid, britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr14E0.sTerrs.spawn(Terr14E0.grid, britGrid)
  def britCorners: HCornerLayer =Terr14E0.corners.spawn(Terr14E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid14Long = britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}*/
