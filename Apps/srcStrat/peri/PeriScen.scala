/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri;
import prid.phex._, egrid._, util.Random

/** A scenario state for Periculo Fundatusa a sequential turn game at a 1300km scale. */
trait PeriScen extends PeriScenBase
{ val armies: LayerHcOptSys[Army]
  val rand: Random = new Random

  /** Invade and move. */
  def invMove(src: HCen, target: HCen): PeriScen =
  { val att1: Option[Army] = armies(src) match
    { case Some(a1) if a1.num > 1 => Some(a1)
      case _ => None
    }
    val def1: Option[Army] = armies(target)
    val optArmies: Option[LayerHcOptSys[Army]] = att1.flatMap{at => def1.map{ df =>
        val ar = rand.nextInt(10)
        val dr = rand.nextInt(10)
        val res = armies.copy
        (ar > dr) match
        { case true if df.num == 1 =>
          { res.setSomeMut(target, Army(at.nation, 1))
            res.setSomeMut(src, Army(at.nation, at.num - 1))
          }
          case true => res.setSomeMut(target, Army(df.nation, df.num - 1))
          case _ => res.setSomeMut(src, Army(at.nation, at.num - 1))
        }
        res
      }
    }
    val newArmies = optArmies.getOrElse(armies)
    PeriScen(gridSys, terrs, sTerrs, corners, newArmies)
  }
}

object PeriScen
{
  def apply(gSysIn: EGridSys, terrsIn: LayerHcRefSys[WTile], sTerrsIn: LayerHSOptSys[WSep, WSepSome], cornersIn: HCornerLayer,
    armiesIn: LayerHcOptSys[Army]): PeriScen = new PeriScen
    { override implicit val gridSys: EGridSys = gSysIn
      override val terrs: LayerHcRefSys[WTile] = terrsIn
      override val sTerrs: LayerHSOptSys[WSep, WSepSome] = sTerrsIn
      override val corners: HCornerLayer = cornersIn
      override val armies: LayerHcOptSys[Army] = armiesIn
    }

  def init(inp: PeriScenStart): PeriScen =
  {
    implicit val gSys: EGridSys = inp.gridSys
    val terrs = inp.terrs
    val nats = inp.nations
    val lands: HCenPairArr[Land] = gSys.optMapPair{ hc => terrs(hc) match
      { case ld: Land if ld.climate == IceCap => None
        case ld: Land => Some(ld)
        case _ => None
      }
    }
    val res: LayerHcOptSys[Army] = LayerHcOptSys[Army]()
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