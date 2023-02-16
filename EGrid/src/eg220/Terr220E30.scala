/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import pEarth._, prid._, phex._, WTile._

object Terr220E30 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.e30(132)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(182, hillTundra * 2, sea * 3)
    wr(180, hillTundra, taiga, tundra, hillTundra, sea)
    wr(178, taiga * 4, tundra)
    wr(176, taiga, taiga * 3, taiga * 2)
    wr(174, taiga, taiga * 5)
    wr(172, taiga, taiga * 6)
    wr(170, sea, taiga * 6)
    wr(168, plain, plain, plain * 5)
    wr(166, sea, plain * 7)
    wr(164, plain * 8)
    wr(162, plain * 9)
    wr(160, plain * 9)
    wr(158, plain * 9)
    wr(156, plain * 10)
    wr(154, plain * 7, sea, plain * 2)
    wr(152, plain * 4, sea * 3, plain * 3)
    wr(150, plain * 4, sea * 5, plain * 2)
    wr(148, hills, hills * 10)
    wr(146, hills, hills * 4, plain * 4, plain * 2)
    wr(144, hills, sea, hills, sea, plain * 7)
    wr(142, sea * 8, plain * 4)
    wr(140, sea * 3, hills, sea * 4, plain * 4)
    wr(138, sea * 2, desert, sea * 5, desert * 4)
    wr(136, desert, sea, desert * 4, sea, desert * 6)
    wr(134, desert * 7, desert, desert * 5)
    wr(132, desert * 8, sea, desert * 4)

    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]

    res.setSomeInts(Sea,  175,1543, 176,1542,  177,1541,  177,1543,  178,1544)//White Sea
    res.setSomeInts(Lake, 171,1537,  172,1542,  173,1541)//Lakes near St Petersburg
    res.setSomeInts(Sea, 167,1525,  168,1526,  167,1527,  167,1529,  168,1530,  169,1529,  169,1531,  169,1533,  169,1535)//Baltic
    res.setSomeInts(Sea,  172,1526,  173, 1527,  174,1528,  175,1529,  176,1530)//Gulf of Bothnia
    res.setSomeInts(Sea, 144,1514,  146,1520,  147,1519,  148,1518,  149,1517)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth5(174, 1546)//White Sea south
    res.setVert4In(176, 1544)//White Sea
    res.setTJunction(177, 1542)//White Sea
    res.setMouth2(178, 1538)//White Sea north west
    res.setVert2In(178, 1542)//White Sea
    res.setMouth3(180, 1544)//White Sea north

    res.setMouth1(170, 1534)//Lake Lagoda West
    res.setMouth4(172, 1540)//Lake Lagoda north east
    res.setMouth2(174, 1538)//Lake Onega north
    res.setVert1In(172, 1540)//Lake Onega
    res.setMouth0(170, 1542)//Lake Onega south

    res.setMouth3(178, 1530)//Gulf of Bothnia north
    res.setVert2In(176, 1528)//Gulf of Bothnia
    res.setVert5In(174, 1530)//Gulf of Bothnia
    res.setVert2In(174, 1526)//Gulf of Bothnia
    res.setVert5In(172, 1528)//Gulf of Bothnia
    res.setMouth0(170, 1526)//Gulf of Bothnia south

    res.setMouth1(166, 1522)//Lativa - Baltic Sea
    res.setTJunction(167, 1526)//Baltic Sea - Gulf of Riga
    res.setMouth3(170, 1526)//Baltic
    res.setVert3In(168, 1528)//Riga
    res.setVert2In(168, 1528)//Parnu
    res.setMouth2(170, 1526)//Baltic Sea - Gulf of Finland
    res.setTJunction(169, 1530)//Baltic Sea - Gulf of Finland
    res.setVert0In(168, 1532)//Gulf of Finland
    res.setVert3In(170, 1534)//Gulf of Finland
    res.setMouth4(170, 1538)//St Petersburg Gulf of Finland

    res.setCorner(148, 1516, 0, HVDn)//Adriatic
    res.setCorner(150, 1518, 4, HVUR)//Adriatic
    res.setVert1In(148, 1516)//Adriatic
    res.setVert4In(148, 1520)//Adriatic
    res.setVert1In(146, 1518)//Adriatic
    res.setMouth0(144, 1520)//Adriatic south

    res.setCorner(144, 1516, 5, HVDR)//Italy - Sicily north
    res.setCorner(144, 1516, 4, HVUR)//Italy - Sicily
    res.setMouth0(142, 1514)

    res
  }
}