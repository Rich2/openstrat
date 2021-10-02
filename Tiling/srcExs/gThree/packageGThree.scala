/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import prid._

/** This is the package for the 3rd example of a simple simultaneous turn tile based game. This example introduces terrain. */
package object gThree
{
  /**  This is just a dummy method as empty package objects can cause problems. */
  def aboutStr: String = """This is the package for the 3rd example of a simple simultaneous turn tile based game. This example introduces terrain."""

  type Command = HStep | Hold
}
