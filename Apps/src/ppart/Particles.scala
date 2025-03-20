/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.*, Colour.*

trait ParticlePrimary extends Coloured
{ /** The maximum radius of the particle. */
  def radius: Length
  
  /** The electric charge. */
  def charge: Int
}

/** [[Proton]] or [[Neutron]], particles of the nucleus. */
trait Nucleon extends ParticlePrimary

/** the Neutron */
class Neutron extends Nucleon
{ override def radius: Length = 0.8.femtometres
  override def colour: Colour = DarkRed
  override val charge = 0
}

/** the Proton */
class Proton extends Nucleon
{ override def radius: Length = 0.8e-3.picometres
  override def colour: Colour = LightGreen
  override def charge: Int = 1
}