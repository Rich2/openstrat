/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e30(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    gs(156, 1532, taiga * 2, sea)
    gs(154, 1530, taiga * 4)
    gs(152, 1536, taiga, sea, taiga)
    gs(150, 1534, taiga * 3)
    gs(148, 1528, taiga, sea, taiga * 3)
    gs(146, 1534, taiga * 4)
    gs(144, 1532, plain * 4)
    gs(142, 1526, plain * 6)
    gs(140, 1528, plain * 6)
    gs(138, 1526, mtain * 2, hills, plain * 3, desert)
    gs(136, 1524, hills, plain * 2, sea, plain * 3)
    gs(134, 1526, hills * 3, sea * 3, mtain)
    gs(132, 1524, hills * 7)
    wr(130, hills * 2, sea, hills * 5)
    gs(128, 1544, hills, desert * 2)
    wr(126, sea, desert, sea * 3, hills, desert * 2)
    wr(124, desert * 4, plain, desert * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea, 153,1537,  153,1543,  154,1544,  155,1543)
    res.setSomeInts(Lake, 149, 1537)
    res.setSomeInts(Sea, 133,1525,  133,1535,  134,1524,  135,1523,  136,1522,  136,1542,  137,1541,  137,1543)
    res.setSomeInts(Sea, 130,1520,  130,1524,  131,1525,  131,1533,  132,1534,  132,1526,  132,1530)
    res.setSomeInts(Sea, 131,1521)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth2(154, 1534)//White Sea north West
    res.setMouth5(152, 1540)//White Sea
    res.setMouth1(152, 1540)//White Sea
    res.setCorner(152, 1544, 0, HVDR)//White Sea
    res.setCorner(154, 1542, 2, HVUL)//White Sea
    res.setCorner(154, 1542, 1, HVDL)//White Sea
    res.setCorner(154, 1542, 0, HVDL)//White Sea

    res.setMouth2(150, 1534)//Lake Ladoga north west
    res.setMouth5(148, 1540)//Leke Ladoga south east

    res.setMouth5Corner(144, 1528)

    res.setMouth0(134, 1542)//Kerch straits
    res.setSideCorner2(136, 1540, 1, HVDL, HVUp)
    res.setCornerIn(138, 1542, 3)
    res.setCornerIn(136, 1544, 5)
    res.setMouth4(138, 1546)//Rostov north west Azov sea
    res.setMouth2(138, 1538)

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

    res.setMouth3(134, 1530)//Aegean north head
    res.setMouth0(130, 1530)//Aegean north

    res.setMouth1(130, 1530)//Gallipoli
    res.setVert2In(132, 1532)//Dardanelles
    res.setVert5In(132, 1536)//Sea of Marmara
    res.setMouth4(134, 1538)//Constantinople

    res.setCorner(132, 1524, 4, HVUR)//Sicily - Italy north, has to be upright rather than down right because the end rows are 132,524 and 130,526
    res.setCorner(130, 1522, 0, HVUR)//Sicily - Italy north, has to be upright rather than down right because the end rows are 132,524 and 130,526
    res.setCornerIn(130, 1522, 5)//Sicily Italy
    res.setCornerIn(130, 1522, 4)//Sicily Italy south

    res
  }
}