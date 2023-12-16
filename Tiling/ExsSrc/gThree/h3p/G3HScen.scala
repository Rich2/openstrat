/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._

/** Example Game three scenario trait. */
abstract class G3HScen(val turn: Int) extends HSysScen
{ def lunitStates: LayerHcRArr[LunitState]
  def teamSet: RArr[Team]

  def resolve(oldStates: LayerHcRArr[LunitState]): LayerHcRArr[LunitState] =
  { val acc: HCenAccPairLayer[LunitState] = HCenAccPairLayer()
    oldStates.foreachHcForeach{(origin, ls) =>
      val steps: HStepArr = ls.intentions
      if (steps.length > 0) gridSys.stepEndFind(origin, steps.head) match
        { case Some(target) if lunitStates.emptyTile(target) => acc.append(target, origin, ls)
          case Some(target) if lunitStates(target).head.team == ls.team => acc.append(target, origin, ls)
          case _ =>
        }
    }
    val newStates: LayerHcRArr[LunitState] = oldStates.copy
    acc.foreach { (target, pairArr) => pairArr match
      { case pa if pa.empty =>
        case HCenPairArr1(origin, ls) if origin != target => newStates.mutateMoveUnsafe(origin, target, _ == ls)(_.intensionsTail)
        case pa if pa.forAll(_.a2.team == pa.head.a2.team) =>
          pa.pairForeach{ (origin, ls) => newStates.mutateMoveUnsafe(origin, target, _ == ls)(_.intensionsTail) }
        case _ =>
      }
    }
    newStates
  }
}

object G3HScen
{
  def apply(turnNum: Int, gridIn: HGridSys, unitsIn: LayerHcRArr[LunitState], teamSetIn: RArr[Team]): G3HScen = new G3HScen(turnNum)
  { override implicit val gridSys: HGridSys = gridIn
    override def lunitStates: LayerHcRArr[LunitState] = unitsIn
    override val teamSet: RArr[Team] = teamSetIn
  }
}