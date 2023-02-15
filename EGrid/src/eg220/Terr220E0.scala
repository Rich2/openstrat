/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import pEarth._, prid._, phex._, WTile._, egrid._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. So hex 4CG0 140, 512 should not be a sea hex as the majority of the
 *  hex is covered by land and we do not want the narrowest gap from England to France to be a whole hex. Given that it is a land hex by geoprhical
 *  area it must be assigned to France  */
object Terr220E0 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.e0(148)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(170, sea * 4, hillTaiga, taiga * 2)
    wr(168, sea * 2, hills, sea * 2, hillTaiga, taiga)
    wr(166, sea * 2, hills, sea * 3, plain, plain)
    wr(164, sea, plain, hills, sea * 3, plain, sea)
    wr(162, sea, plain, sea, plain, sea, plain * 4)
    wr(160, sea, plain, sea, plain *2, plain * 4)
    wr(158, sea * 2, hills, sea, plain * 5)
    wr(156, sea * 3, plain * 7)
    wr(154, sea * 4, plain * 6)
    wr(152, sea * 4, plain * 6)
    wr(150, sea, hills * 5, sea * 3, hills, sea)

    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
//    res.setSomeInts(Sea,  145,521,  145,523,  146,520,  146,524,  147,517,  147,519)//Scandinavia

    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

//    res.setMouth1(146, 514)//Skagerrack west
//    res.setVert0In(146, 518)//Oslo
//    res.setVert1In(146, 518)//Gothenberg
//    res.setVert4In(146, 522)//Copenhagen
//    res.setCorner(146, 522, 3, HVUp)//Stralsund - Ystad
//    res.setCorner(144, 520, 1, HVDn)//Stralsund - Ystad
//    res.setCorner(146, 522, 2, HVUL)
//    res.setCorner(146, 522, 1, HVUL)


    res
  }
}

object BritReg220
{ def britGrid: EGrid220Long = EGrid220Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr220E0.grid, Terr220E0.terrs)
  def britSTerrs: HSideOptLayer[WSide] = britGrid.sideOptLayerSpawn(Terr220E0.grid, Terr220E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr220E0.grid, Terr220E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideOptLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}