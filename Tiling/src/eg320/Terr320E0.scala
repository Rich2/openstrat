/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(124)

  override val terrs: HCenLayer[WTile] =
  {
    val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    wr(156, sea * 3)
    wr(154, sea * 4)
    wr(152,  sea * 3, taiga)
    wr(150, sea * 3, taiga)
    wr(148, sea * 3, hillTaiga, taiga)
    wr(146, sea, hillForest, sea * 2, plain)
    gs(144, 508, plain, sea * 2, plain)
    gs(142, 506, plain, plain, sea, plain * 2)
    wr(140, sea, hills, plain * 4)
    gs(138, 460 + 50, plain * 5)
    gs(136, 512, plain, hills, mtain, plain)
    gs(134, 506, hills * 3, sea * 2, hills)
    gs(132, 504, hills, plain * 2, sea, hills, sea)
    gs(130, 506, plain, hills, sea * 3, hills)
    wr(128, sea * 2, hills * 5, sea)
    wr(126, sea * 2, mtain, hillDesert, desert * 4)
    wr(124, sea * 2, hills, desert * 6)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setTruesInts(Sea, 143, 507) //,  144,522,  145,521)
    res.setTruesInts(Sea, 139, 509, 140, 510, 141, 511) //, 141, 507,//142,508,

    res.setTruesInts(Sea, 129, 507) //,
    res
  }

  override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools

    res.setTruesInts(  143,507)//,  144,522,  145,521)
    res.setTruesInts(139,509,  140,510, 141,507,  141,511,  142,508)

    res.setTruesInts(129,507,  129,509)//,  129,509,  129,511,  129,525,  130,528,  131,527,  134,528,  135,527,  136,526)
    res
  }

  override val corners: HCornerLayer =
  {
    val res: HCornerLayer = grid.newHVertOffsetLayer
    import res.{setVertSingle => svs }
   // svs(147, 512, HVDL, 7)
    //svs(145, 508, HVUL, 7); svs(145, 512, HVDL, 3)
    //svs(142, 50)
//    svs(143, 510, HVUR, 3)
//    svs(141, 510, HVDn, 2); svs(141, 512, HVDR, 3); svs(141, 514, HVUL, 3)
//    svs(139, 512, HVUp, 3); svs(139, 514, HVUL, 3)

    res.setCorner(144, 508, 4, HVUp, 3)
    res.setCorner(142, 506, 0, HVDL, 3)
    res.setDouble(144, 504, 2, HVUp, 3, HVDL, 3)

    res.setCorner(144, 508, 3, HVUR, 3)

    res.setCorner(142, 510, 5, HVUR, 3)
    res.setCorner(142, 506, 1, HVDL, 3)

    res.setCorner(142, 506, 2, HVUL, 3)
    res.setCorner(142, 510, 4, HVDR, 3)

    res.setCorner(140, 508, 0, HVDR, 3)

    /** St Georges Chanel */
    res.setDouble(140, 504, 1, HVUL, 3, HVDn, 3)
    res.setCorner(142, 506, 3, HVUL, 3)
    res.setCorner(140, 508, 5, HVDn, 3)

    /** English Channel Dover */
    res.setDouble(142, 514, 4, HVDn, 3, HVUL, 3)
    res.setCorner(142, 510, 2, HVUL, 3)
    res.setCorner(140, 512, 0, HVDn, 3)
    res.setCorner(142, 510, 3, HVUL, 3)
    /** Southampton - Le Mont St Michelle */
    res.setCorner(140, 508, 1, HVUL, 3)
    res.setCorner(140, 512, 5, HVDR, 3)
    res.setCorner(140, 508, 2, HVUL, 3)
    res.setCorner(140, 512, 4, HVDR, 3)

    res.setCorner(138, 510, 0, HVDR, 3)

    /** English Channel Atlantic end */
    res.setDouble(138, 506, 1, HVUL, 3, HVDn, 3)
    res.setCorner(140, 508, 3, HVUL, 3)
    res.setCorner(138, 510, 5, HVDn, 3)

    /** Gibraltar */
    res.setCorner(130, 506, 3, HVUp, 3)
    res.setCorner(128, 508, 5, HVDR, 3)
    res.setDouble(128, 504, 1, HVUp, 3, HVDR, 3)

    res.setCorner(130, 506, 2, HVUp, 3)
    res.setCorner(128, 508, 0, HVDn, 3)

    res.setCorner(130, 510, 4, HVUp, 3)

    res
  }
}

object BritReg
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr320E0.grid, Terr320E0.terrs)
  def britSTerrs: HSideOptLayer[WSide] = britGrid.sideOptLayerSpawn(Terr320E0.grid, Terr320E0.sTerrs)
  def britSTerrsDepr: HSideBoolLayer = britGrid.sideBoolLayerSpawn(Terr320E0.grid, Terr320E0.sTerrsDepr)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr320E0.grid, Terr320E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideOptLayer[WSide] = britSTerrs

    override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}