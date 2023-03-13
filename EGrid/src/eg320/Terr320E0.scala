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
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(156, sea * 3)
    wr(154, sea * 4)
    wr(152,  sea * 3, taigaHills)
    wr(150, sea * 3, taigaHills)
    wr(148, sea * 3, taigaHills, taiga)
    wr(146, sea, hills, sea, plain, forest)
    wr(144, sea, plain, sea * 2, plain)
    wr(142, sea, plain, plain * 2, plain * 2)
    wr(140, sea, hills, plain * 4)
    wr(138, sea * 2, plain * 2, hills, mtain * 2)
    wr(136, sea * 3, plain, hills, mtain, plain)
    wr(134, sea, hills * 3, sea, hills * 2)
    wr(132, sea, hills, plain * 2, sea, hills, sea)
    wr(130, sea, plain, hills * 2, sea * 3, hills)
    wr(128, sea * 2, hills * 5, sea)
    wr(126, sea * 2, mtain, desertHills, desert * 4)
    wr(124, sea * 2, hills, desert * 6)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(SCSea,  145,521,  145,523,  146,520,  146,524,  147,517,  147,519)//Scandinavia
    res.setSomeInts(SCSea, 139,509,  140,510,  141,507,  141,511,  141,513,  142,516,  141,515,  142,508, 143,507)//British Isles
    res.setSomeInts(SCSea, 133,521,  134,524,  135,521,  135,523)//Corsica
    res.setSomeInts(SCSea, 129,507,  129,509,  129,511,  129,525, 130,528,  131,527,  134,528,  135,527,  136,526)//Mediterranean
    res
  }

  override val corners: HCornerLayer =
  { val res: HCornerLayer = grid.newHVertOffsetLayer

    res.setMouth1(146, 514)//Skagerrack west
    res.setVert0In(146, 518)//Oslo
    res.setVert1In(146, 518)//Gothenberg
    res.setVert4In(146, 522)//Copenhagen
    res.setCorner(146, 522, 3, HVUp)//Stralsund - Ystad
    res.setCorner(144, 520, 1, HVDn)//Stralsund - Ystad
    res.setCorner(146, 522, 2, HVUL)
    res.setCorner(146, 522, 1, HVUL)

    res.setMouth1(140, 504)// St Georges Chanel
    res.setVert2In(142, 506)//Bristol
    res.setVert1In(142, 506)//Anglesey
    res.setMouth2(144, 504)//Scotland Northern Ireland

    res.setMouth1(138, 506)// English Channel Atlantic end
    res.setVert2In(140, 508)//Le Mont St Michelle
    res.setVert5In(140, 512)//Exmouth - Cherbourg
    res.setVert0In(140, 512)//Le Havre - Portsmouth
    res.setVert3In(142, 514)//Dover - Calais
    res.setVert2In(142, 514)//Margate - Antwerp
    res.setMouth3(144, 516)//English Channel north east

    res.setMouth1(134, 518)//Sardinia
    res.setVert0In(134, 522)
    res.setVert1In(134, 522)
    res.setMouth0(132, 524)
    res.setMouth2(134, 518)//Corsica
   res.setMouth5(132, 524)//Corsica

    res.setMouth3Corner(138, 526)//Adriatic head
    res.setCornerIn(136, 524, 1)//Adriatic head
    res.setCorner(136, 524, 2, HVDL)//Adriatic San Marino
    res.setCorner(134, 526, 0, HVDL)//Adriatic San Marino
    res.setCornerIn(134, 526, 1)//Adriatic
    res.setCorner(134, 526, 2, HVDL)//Adriatic

    res.setCornerPair(132, 524, 2, HVUR, HVUL)//Sicily Italy
    res.setCorner(130, 526, 0, HVUL)//Sicily Italy
    res.setCornerIn(130, 526, 1)//Sicily Italy
    res.setCornerIn(130, 526, 2)//Sicily Italy
    res.setCornerPair(128, 528, 0, HVUL, HVUR)//Sicily Italy south mouth


    res.setMouth1(128, 504)//Gibraltar
    res.setVert0In(128, 508)//South Spain
    res.setVert3In(130, 510)//South Spain
    res.setMouth4(130, 514)//East end of Gibraltar Straits

    res.setMouth2(130, 522) //Tunis Sicily west
    res.setMouth5(128, 528) //Tunis Sicily east

    res
  }
}

object BritReg
{ def britGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)
  def britTerrs: HCenLayer[WTile] = britGrid.hCenLayerSpawn(Terr320E0.grid, Terr320E0.terrs)
  def britSTerrs: HSideOptLayer[WSide] = britGrid.sideOptLayerSpawn(Terr320E0.grid, Terr320E0.sTerrs)
  def britCorners: HCornerLayer = britGrid.cornerLayerSpawn(Terr320E0.grid, Terr320E0.corners)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = britGrid

    override val terrs: HCenLayer[WTile] = britTerrs

    override val sTerrs: HSideOptLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = britCorners
  }
}