/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package sp1
import prid._, psq._, gPlay._

/** Scenario trait for Game Two. */
trait G1SqScen extends SqGridScen
{  override def title: String = "Game 1 Squares scen."
  
  /** An optional player can occupy each tile. This is the only tile data in the game. this is the same as Game one. */
  def players: SqCenOptLayer[Player]

  def playerSet: RArr[Player] = players.somesMap(p => p)
  
  /** Contains the resolution logic. The actions are presumed to be correct. Combining and checking of actions should be done before calling this
   * method. */
  def resolve(actions: SqCenOptStepLayer[Player]): SqCenOptLayer[Player] =
  { val playersNew: SqCenOptLayer[Player] = players.copy
    val acc: SqCenAccLayer[SqStep] = actions.mapAcc
    acc.foreach { (target, arr) =>
      if (arr.length == 1 & players(target).isEmpty) playersNew.moveUnsafe(arr.headSqCen, target)
      else
      { val arr2: SqCenPairArr[SqStep] = arr.filterOnA2(a2 => a2.isPerp)
        if (arr2.length == 1 & players.emptyTile(target)) playersNew.moveUnsafe(arr2.headSqCen, target)
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