/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSep extends Coloured with TellSimple
{ def nonEmpty: Boolean
}

object WSep
{ /** [[DefaultValue]] type class instance / evidence for [[WSep]]. */
  implicit val defaultValueEv: DefaultValue[WSep] = new DefaultValue[WSep]
  { override def default: WSep = WSepNone
  }

  /** [[Show]] type class instance / evidence for [[WSep]]. */
  implicit val showEv: ShowTell[WSep] = ShowTell[WSep]("WSide")

  /** [[Unshow]] type class instance / evidence for [[WSep]]. */
  implicit val unshowEv: UnshowSingletons[WSep] = UnshowSingletons[WSep]("WSide", Sea, Lake, WSepNone)
}

case object WSepNone extends WSep
{ /** The type of the object to be persisted. */
  override def typeStr: String = "NoSide"

  override val colour: Colour = Black
  override def nonEmpty: Boolean = false

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = "None"
}

trait WSepSome extends WSep with HSepSome
{ override def nonEmpty: Boolean = true
}

/** Escarpment / cliff. Sudden change or changes elevation, heavily restiracting movement between the tiles. */
case object Scarp extends WSepSome
{ override def typeStr: String = "Scarp"
  override def str: String = "Scarp"
  override def colour: Colour = Maroon
}