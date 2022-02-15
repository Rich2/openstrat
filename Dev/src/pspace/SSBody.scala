/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace

sealed trait SSBody

/** The Sun or a body that directly orbits the Sun. */
trait SSPrimaryBody extends SSBody
{
  val avSunDist: Length
  val name: String

  override def toString = name
}

trait Planet extends SSPrimaryBody
{
  trait Moon extends SSBody
  def moons: Arr[Moon] = Arr()
}



object Sun extends SSPrimaryBody
{ override val avSunDist: Length = 0.mMiles
  override val name: String = "Sun"
}
