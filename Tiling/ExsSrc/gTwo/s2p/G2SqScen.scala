/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package s2p
import prid._, psq._, gPlay._

case class CounterState(counter: Counter, steps: SqStepArr)
{ /** Returns the PlayerState with the tail of the steps. */
  def takeStep: CounterState = CounterState(counter, steps.tail)
}

object CounterState
{
  def apply(player: Counter, steps: SqStep*): CounterState = new CounterState(player, SqStepArr(steps: _*))
}

/** A scenario turn or state for Game Three. Adds in multiple turn orders which are now part of the game state. */
trait G2SqScen extends SqGridScen
{
  /** An optional player can occupy each tile. This is the only tile data in the game. */
  def counterStates: SqCenOptLayer[CounterState]

  def counterSet: RArr[Counter] = counterStates.somesMap(cs => cs.counter)

  def resolve(oldStates: SqCenOptLayer[CounterState]): SqCenOptLayer[CounterState] =
  { val acc: SqCenAccLayer[CounterState] = SqCenAccLayer()
    oldStates.somesScForeach { (ps, origin) =>
      val steps: SqStepArr = ps.steps
      if (steps.length > 0) {
        gridSys.stepEndFind(origin, steps.head) match {
          case Some(target) if counterStates.emptyTile(target) => acc.append(target, origin, ps)
          case _ =>
        }
      }
    }

    val newStates = oldStates.copy
    acc.foreach { (target, pairArr) =>
      pairArr match
      { case SqCenPairArr1(origin, ps) if origin != target =>
        { newStates.setSomeMut(target, ps.takeStep)
          newStates.setNoneMut(origin)
        }
        case _ =>
      }
    }
    newStates
  }
}

/** Companion object for [[G2SqScen]] trait, contains factory apply method. */
object G2SqScen
{ /** Factory apply method for [[G2SqScen]] trait. */
  /*def apply(turnIn: Int, gridIn: SqGridSys, opIn: SqCenOptLayer[Player], newData: HDirnPathPairArr[Player]): G2SqScen = new G2SqScen
  { override val turn = turnIn
    override implicit val gridSys: HGridSys = gridIn
    override def oPlayers: HCenOptLayer[Player] = opIn
    override def playerOrders: HDirnPathPairArr[Player] = newData
  }*/
}
