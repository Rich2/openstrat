/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.*, Colour.*

abstract class Element(val name: String, val abrev: String)
{ val atomicNum: Int
  def cpkColour: Colour
  def radius: Angstroms
  override def toString: String = name + "Atom"

  class Isotope(val numNeutrons: Int)
  { def numNucleons: Int = atomicNum + numNeutrons
  }
}

class Atom(val element: Element) extends ParticlePrimary
{ override def radius: Length = element.radius
  override def toString: String = element.abrev + "-" + "atom"
  override def colour: Colour = element.cpkColour
  override def charge: Int = 0
}

object Hydrogen extends Element("Hydrogen", "H")
{ override val atomicNum: Int = 1
  override val cpkColour: Colour = White
  override def radius: Angstroms = 0.55.angstroms
  object Protium extends Isotope(0)
  object Deuterium extends Isotope(1)
  object Tritium extends Isotope(2)
}

class HAtom extends Atom(Hydrogen)

object Carbon extends Element("Carbon", "C")
{ override val atomicNum: Int = 6
  override val cpkColour: Colour = Black
  override def radius: Angstroms = 0.70.angstroms
  object Carbon12 extends Isotope(6)
  object Carbon13 extends Isotope(7)
  object Carbon14 extends Isotope(8)
}

class CAtom extends Atom(Carbon)

object Oxygen extends Element("Oxygen", "O")
{ override val atomicNum: Int = 8
  override val cpkColour: Colour = Red
  override def radius: Angstroms = 0.73.angstroms
  object Oxygen16 extends Isotope(8)
}

class OAtom extends Atom(Oxygen)