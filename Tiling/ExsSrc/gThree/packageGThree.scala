/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** This is the package for the 3rd example of a simple simultaneous turn tile based game. This example introduces multiple units per hex. Multiple
 *  units can enter an empty tile as long as all units attempting to enter the target as long as they all belong to the same team. Units can enter an
 *  occupied tile if they belong to the same team as the pre existing occupiers. */
package object gThree
{
  /**  This is just a dummy method as empty package objects can cause problems. */
  def aboutStr: String =
    """This is the package for the 3rd example of a simple simultaneous turn tile based game. This example introduces multiple
      | units per hex.""".stripMargin
}