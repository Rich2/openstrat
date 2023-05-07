/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

/** Scenario trait for Game Two. */
trait G1SqScen extends SqGridScen
{  override def title: String = "Game 1 Squares scen."
  
  /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def players: SqCenOptLayer[Player]

  def endTurn(orders: SqCenStepPairArr[Player]): G1SqScen =
  { val res1 = SqCenOptStepLayer[Player]()
    orders.pairForeach { (hcst, pl) => res1.setSome(hcst.startSC, hcst.step, pl) }
    val playersNew = resolve(res1)
    G1SqScen(turn + 1, gridSys, playersNew)
  }

  /** Contains the resolution logic. The actions are presumed to be correct. Combining and checking of actions should be done before calling this
   * method. */
  def resolve(actions: SqCenOptStepLayer[Player]): SqCenOptLayer[Player] =
  { val playersNew: SqCenOptLayer[Player] = players.copy
    val acc: SqCenAccLayer[Player] = actions.mapAcc
    acc.foreach { (target, arr) =>
      if (arr.length == 1 & players(target).isEmpty) playersNew.moveUnsafe(arr.headSqCen, target)
      else{
        //arr.filterOnA1(a1 =>  )
      }
    }
    playersNew
  }
}

/** Companion object for TwoScen trait, contains factory apply method. */
object G1SqScen
{ /** Apply factory method for TwoScen game. */
  def apply(turnIn: Int, gSysIn: SqGridSys, opIn: SqCenOptLayer[Player]): G1SqScen = new G1SqScen
  { override val turn = turnIn
    override implicit val gridSys: SqGridSys = gSysIn
    override def players: SqCenOptLayer[Player] = opIn
  }
}

/** This trait just puts the value 0 in for the turn. */
trait G1SqScenStart extends G1SqScen
{ override val turn: Int = 0
}