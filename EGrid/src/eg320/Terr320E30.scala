/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320E30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e30(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }
    gs(156, 1532, tundraHills * 2, sea)
    gs(154, 1530, taigaHills, taiga * 2, tundra)
    wr(152, taiga, taiga, taiga, taiga)
    wr(150, taiga, taiga * 3)
    wr(148, taiga * 2, taiga * 3)
    wr(146, plain, taiga * 4)
    wr(144, plain * 5)
    gs(142, 1526, plain * 6)
    gs(140, 1528, plain * 6)
    gs(138, 1526, mtain * 2, hills, plain * 3, desert)
    gs(136, 1524, hills, plain * 2, sea, plain * 3)
    gs(134, 1526, hills * 3, sea * 3, mtain)
    gs(132, 1524, hills * 7)
    wr(130, hills * 2, sea, hills * 5)
    wr(128, sea * 2, SeaIsland(Hilly, OpenTerrain), sea, SeaIsland(Hilly, OpenTerrain), hills, desert * 2)
    wr(126, sea, desert, sea * 3, hills, desert * 2)
    wr(124, desert * 4, plain, desert * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res.setSomeInts(SCSea, 151,1539,  152,1538,  153,1537,  153,1539,  153,1541,  153,1543,  154,1544,  155,1543)
    res.setSomeInts(SCLake, 149, 1537)
    res.setSomeInts(SCSea, 145,1527,  146,1528,  147,1529,  147,1531, 147,1533,  148,1530,  149,1531,  150,1532,  151,1533,  152,1534)//Baltic
    res.setSomeInts(SCSea, 133,1525,  134,1524,  135,1523,  136,1522,  136,1542,  137,1541,  137,1543)
    res.setSomeInts(SCSea, 130,1524,  131,1525,  132,1526,  132,1530)
    res.setSomeInts(SCSea, 133,1531,  133,1533,  133,1535)//Boshphorus
    res.setSomeInts(SLSea, 130,1520, 131,1521)//Sicily - Italy
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth2(154, 1534)//White Sea north West
    res.setVert3In(154, 1542)//White Sea
    res.setVert0In(152, 1540)//White Sea
    res.setCorner(152, 1544, 0, HVDR)//White Sea
    res.setCorner(154, 1542, 2, HVUL)//White Sea
    res.setCorner(154, 1542, 1, HVDL)//White Sea
    res.setCorner(154, 1542, 0, HVDL)//White Sea
    res.setTJunction(153, 1538)//White Sea
    res.setVert4In(152, 1540)//White Sea
    res.setMouth5(150, 1542)//White Sea

    res.setMouth2(150, 1534)//Lake Ladoga north west
    res.setMouth5(148, 1540)//Leke Ladoga south east

    //res.setMouth5Corner(144, 1528)
    res.setCorner(144, 1528, 5, HVDn)//Baltic west
    res.setCorner(144, 1528, 0, HVDR)//Baltic west
    res.setCorner(146, 1530, 4, HVDR)//Baltic
    res.setCorner(146, 1530, 5, HVDR)//Baltic
    res.setCorner(148, 1528, 3, HVUL)//Baltic
    res.setTJunction(147, 1530)//Baltic - Gulf of Finland - Gulf of Bothnia
    res.setVert3In(148, 1532)//Helsinki - Tallinn
    res.setMouth4(148, 1536)//St Petersburg
    res.setVert5In(148, 1532)//Gulf of Bothnia
    res.setVert2In(150, 1532)//Gulf of Bothnia
    res.setVert5In(150, 1534)//Gulf of Bothnia
    res.setVert2In(152, 1532)//Gulf of Bothnia
    res.setMouth3(154, 1534)//Gulf of Bothnia north

    res.setMouth0(134, 1542)//Kerch straits

    res.setTJunction(137, 1542)//Azov Sea
    res.setMouth4(138, 1546)//Rostov north west Azov sea
    res.setMouth2(138, 1538)//Azov Sea north east

    res.setCornerIn(136, 1524, 5)//Adriatic head
    res.setCornerIn(136, 1524, 4)//Adriatic San Marino
    res.setCorner(136, 1524, 3, HVUR)//Adriatic
    res.setCorner(134, 1526, 5, HVUR)//Adriatic
    res.setCornerIn(134, 1526, 4)//Adriatic
    res.setCorner(132, 1524, 0, HVDL)//Adriatic
    res.setVert1In(132, 1524)//Adriatic
    res.setVert2In(132, 1524)//Adriatic
    res.setVert5In(130, 1526)//Adriatic
    res.setMouth0(128, 1524)//Greece Italy


    res.setMouth0(130, 1530)//Aegean
    res.setVert5In(132, 1532)//Aegean north
    res.setVert0In(132, 1532)//Gallipoli
    res.setVert3In(134, 1534)//Sea of Marmara
    res.setMouth4(134, 1538)//Constantinople

    res
  }
}