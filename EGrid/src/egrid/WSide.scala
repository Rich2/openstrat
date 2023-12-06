/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSide extends Coloured with TellSimple
{ def nonEmpty: Boolean
}

object WSide
{ /** [[DefaultValue]] type class instance / evidence for [[WSide]]. */
  implicit val defaultValueEv: DefaultValue[WSide] = new DefaultValue[WSide]
  { override def default: WSide = WSideNone
  }

  /** [[Show]] type class instance / evidence for [[WSide]]. */
  implicit val showEv: ShowTell[WSide] = ShowTell[WSide]("WSide")

  /** [[Unshow]] type class instance / evidence for [[WSide]]. */
  implicit val unshowEv: UnshowSingletons[WSide] = UnshowSingletons[WSide]("WSide", Sea, Lake, WSideNone)
}

case object WSideNone extends WSide
{ /** The type of the object to be persisted. */
  override def typeStr: String = "NoSide"

  override val colour: Colour = Black
  override def nonEmpty: Boolean = false

  /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  override def str: String = "None"
}

trait WSideSome extends WSide with HSideSome
{ override def nonEmpty: Boolean = true
}