/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package hp4
import prid._, phex._, Colour._

/** Example Game three scenario trait. */
abstract class FourScen(val turn: Int) extends HGridScen
{ /** tile terrain. */
  def terrs: HCenLayer[Terr]
  
 // def lunits: HCenArrLayer[LunitState]
  //def playerOrders: HStepPathPairArr[LunitState] = HStepPathPairArr()

  /** Resolves turn. Takes a list [[RArr]] of commands consisting in this simple case of (Player, HStep) pairs. The command is passed in as a relative
   * move. This is in accordance with the principle in more complex games that the entity issuing the command may not know its real location. */
 // def endTurn(orderList: HStepPathPairArr[LunitState]): ThreeScen =
  //{
    //val playersKey = units.SomesKeyMapDepr

    /** A mutable grid of data. The tile data is an Array buffer of [[HStep]]s, the HStep pointing back to the origin [[HCen]] of the player. */
  //  val targets: HCenBuffLayer[HStep] = gridSys.newHCenArrOfBuff

    /*orderList.foreach { case (player, steps) =>  steps.ifHead { step =>
      val hc1 = playersKey(player)
      val optTarget: Option[HCen] = hc1.stepOpt(step)
      optTarget.foreach { target => targets.appendAt(target, step.reverse) }
      }
    }*/

    /** A new Players grid is created by cloning the old one and then mutating it to the new state. This preserves the old turn state objects and
     * isolates mutation to within the method. */
    //val oPlayersNew: HCenArrLayer[LunitState] = lunits.clone
  //  targets.foreach{ (hc2, buff) => buff.foreachLen1(backStep => if (lunits.emptyTile(hc2)) oPlayersNew.moveMut(hc2.unsafeStepDepr(backStep), hc2)) }

    //ThreeScen(turn + 1, gridSys, terrs, oPlayersNew)
    ???
 // }
}

object FourScen
{
  /*def apply(turnNum: Int, gridIn: HGrid, terrsIn: HCenLayer[Terr], unitsIn: HCenArrLayer[LunitState]): ThreeScen = new ThreeScen(turnNum)
  {
    /** tile terrain. */
    //override def terrs: HCenLayer[Terr] = terrsIn

    override def lunits: HCenArrLayer[LunitState] = unitsIn

    /** This gives the structure of the hex grid. It contains no data about the elements of the grid. But it allows the scenario to create and operate
     * on flat arrays of data. */
    override implicit val gridSys: HGrid = gridIn
  }*/
}