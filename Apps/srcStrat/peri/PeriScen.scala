/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, util.Random

trait PeriScenBase extends HSysScen
{
  def title: String = "PeriScen"

  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
}

trait PeriScenStart extends PeriScenBase
{
  val nations: RArr[Nation]
}

trait PeriScen extends PeriScenBase
{ val armies: HCenOptLayer[Army]
  val rand: Random = new Random

  def attack(src: HCen, target: HCen): PeriScen = {
    val att1: Option[Army] = armies(src) match {
      case Some(a1) if a1.num > 1 => Some(a1)
      case _ => None
    }
    val def1: Option[Army] = armies(target)
    val res: Option[Boolean] = att1.flatMap{at => def1.map{ df =>
        val ar = rand.nextInt(10)
        val dr = rand.nextInt(10)
        ar > dr
      }
    }
    ???
  }
}

object PeriScen
{
  def apply(gSysIn: EGridSys, terrsIn: HCenLayer[WTile], sTerrsIn: HSideOptLayer[WSide, WSideSome], cornersIn: HCornerLayer,
    armiesIn: HCenOptLayer[Army]): PeriScen = new PeriScen
    { override implicit val gridSys: EGridSys = gSysIn
      override val terrs: HCenLayer[WTile] = terrsIn
      override val sTerrs: HSideOptLayer[WSide, WSideSome] = sTerrsIn
      override val corners: HCornerLayer = cornersIn
      override val armies: HCenOptLayer[Army] = armiesIn
    }

  def init(inp: PeriScenStart): PeriScen =
  {
    implicit val gSys: EGridSys = inp.gridSys
    val terrs = inp.terrs
    val nats = inp.nations
    val lands: HCenPairArr[Land] = gSys.optMapPair{ hc => terrs(hc) match
      { case ld: Land => Some(ld)
        case _ => None
      }
    }
    val res: HCenOptLayer[Army] = HCenOptLayer[Army]()
    val len = lands.length
    val rand: Random = new Random
    iToForeach(len - 1, 0, -1){i =>
      val j = rand.nextInt(i + 1)
      val jLands = lands(j)
      val armyNum = jLands.a2.climate match{
        case Desert | Tundra => 1
        case Taiga | Tropical => 2
        case _ => 3
      }
      val army = Army(nats.indexCycle(i), armyNum)

      res.setSomeMut(jLands.a1, army)
      lands.setElemUnsafe(j, lands(i))
      lands.setElemUnsafe(i, jLands)
    }
//    val army = Army(nats.indexCycle(0), 2)
//    res.setSomeMut(lands(0).a1, army)
    PeriScen(gSys, terrs, inp.sTerrs, inp.corners, res)
  }
}