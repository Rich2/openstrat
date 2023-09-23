/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import Colour._

class Nation(val str: String, val colour: Colour) extends Coloured with ShowSimple
{ override def typeStr: String = "Nation"
}

object Nation
{
  implicit val showTEv: ShowShowSimpleT[Nation] = ShowShowSimpleT[Nation]("Nation")
  def persistEv(arr: RArr[Nation]): PersistSingletons[Nation] = PersistSingletons[Nation]("Nation", arr)
  def persistEv(nations: Nation*): PersistSingletons[Nation] = PersistSingletons[Nation]("Nation", nations.toArr)
}

object NoNation extends Nation("NoNation", White)
object NRed extends Nation("NRed", Red)
object NViolet extends Nation("NViolet", Violet)
object NBlue extends Nation("NBlue", Blue)
object NOrange extends Nation("NOrange", Orange)

case class Army(nation: Nation, num: Int) extends Coloured with Show2[Nation, Int]
{ override def typeStr: String = "Army"
  override def colour: Colour = nation.colour
  override def name1: String = "nation"
  override def name2: String = "num"
  override def show1: Nation = nation
  override def show2: Int = num
  override def showT1: ShowT[Nation] = Nation.showTEv
  override def showT2: ShowT[Int] = ShowT.intPersistEv
}

object Army
{
  implicit val showTEv: ShowShowT[Army] = ShowShow2T[Nation, Int, Army]("Army")
  //def persistEv(arr: RArr[Nation]): PersistShow2[Nation, Int, Army] = PersistShow2[Nation, Int Army]("Army", arr)
  //def persistEv(nations: Nation*): PersistSingletons[Nation] = PersistSingletons[Nation]("Nation", nations.toArr)
}