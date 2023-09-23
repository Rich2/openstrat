/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package peri
import Colour._

class Nation(val str: String, val colour: Colour) extends Coloured with ShowSimple
{ override def typeStr: String = "Nation"
}

object Nation{
  implicit val persistEv: PersistSingletons[Nation] = new PersistSingletons[Nation]("Nation") {
    override def singletonList: List[Nation] = ???
  }
}

object NoNation extends Nation("NoNation", White)
object NRed extends Nation("NRed", Red)
object NViolet extends Nation("NViolet", Violet)
object NBlue extends Nation("NBlue", Blue)
object NOrange extends Nation("NOrange", Orange)

case class Army(nation: Nation, num: Int) extends Coloured
{  override def colour: Colour = nation.colour
}