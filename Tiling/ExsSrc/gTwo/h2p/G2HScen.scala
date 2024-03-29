/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** A scenario turn or state for Game Three. Adds in multiple turn orders which are now part of the game state. */
trait G2HScen extends HSysTurnScen
{ override def title: String = "Game 2 hex scenario"

  /** An optional player can occupy each tile. This is the only tile data in the game. */
  def counterStates: LayerHcOptSys[CounterState]

  /** The [[Counter]]s in this scenario. */
  def counterSet: RArr[Counter] = counterStates.somesMap(cs => cs.counter)

  /** Resolves the turn returning a new [[LayerHcOptSys]] of [[CounterState]]s. */
  def resolve(oldStates: LayerHcOptSys[CounterState]): LayerHcOptSys[CounterState] =
  { val acc: HCenAccPairLayer[CounterState] = HCenAccPairLayer()
    oldStates.somesHcForeach{ (ps, origin) =>
      val steps: HStepArr = ps.steps
      if(steps.length > 0)
      { gridSys.stepEndFind(origin, steps.head) match
        { case Some(target) if counterStates.emptyTile(target) => acc.append (target, origin, ps)
          case _ =>
        }
      }
    }

    val newStates = oldStates.copy
    acc.foreach{ (target, pairArr) => pairArr match
      { case HCenPairArr1(origin, ps) if origin != target =>
        { newStates.setSomeMut(target, ps.takeStep)
          newStates.setNoneMut(origin)
        }
        case _ =>
      }
    }
    newStates
  }
}

/** Companion object for [[G2HScen]] trait, contains factory apply method. */
object G2HScen
{ /** Factory apply method for [[G2HScen]] trait. */
  def apply(turnIn: Int, gridIn: HGridSys, opIn: LayerHcOptSys[CounterState]): G2HScen = new G2HScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def counterStates: LayerHcOptSys[CounterState] = opIn
  }
}