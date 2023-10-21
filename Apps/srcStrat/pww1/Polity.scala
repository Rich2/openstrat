/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1

trait Polity extends TellSimple
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Polity
{ implicit val showEv: ShowTellSimple[Nothing] = ShowTellSimple("Polity")
}

object Germany extends Polity
{ def str: String = "Germany"
  def colour = Colour.fromInts(128, 177, 179)//CadetBlue 60% shade
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(125, 255, 255)
}