/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import Colour._

class Nation(val str: String, val colour: Colour) extends Coloured with TellSimple
{ override def typeStr: String = "Nation"
}

object Nation
{
  implicit val showTEv: ShowTellSimple[Nation] = ShowTellSimple[Nation]("Nation")
  def unshowEv(arr: RArr[Nation]): UnshowSingletons[Nation] = UnshowSingletons[Nation]("Nation", arr)
  def unshowEv(nations: Nation*): UnshowSingletons[Nation] = UnshowSingletons[Nation]("Nation", nations.toArr)
}

object NoNation extends Nation("NoNation", White)
object NRed extends Nation("NRed", Red)
object NViolet extends Nation("NViolet", Violet)
object NBlue extends Nation("NBlue", Blue)
object NOrange extends Nation("NOrange", Orange)

case class Army(nation: Nation, num: Int) extends Coloured with Tell2[Nation, Int]
{ override def typeStr: String = "Army"
  override def colour: Colour = nation.colour
  override def name1: String = "nation"
  override def name2: String = "num"
  override def tell1: Nation = nation
  override def tell2: Int = num
  override def show1: Show[Nation] = Nation.showTEv
  override def show2: Show[Int] = Show.intEv
}

object Army
{
  implicit val showEv: ShowTell[Army] = ShowTell2[Nation, Int, Army]("Army")

  def unshowEv(arr: RArr[Nation]): Unshow2[Nation, Int, Army] =
    Unshow2.explicit[Nation, Int, Army]("Army", "nation", "num", Army.apply, Nation.unshowEv(arr), Unshow.intEv)

  def unshowEv(nations: Nation*): Unshow2[Nation, Int, Army] =
    Unshow2.explicit[Nation, Int, Army]("Army", "nation", "num", Army.apply, Nation.unshowEv(nations.toArr), Unshow.intEv)
}