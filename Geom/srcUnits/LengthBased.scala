/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LengthBased extends Any
{ /** The factor to convert the [[Length]] units of this class to picometres. */
  def unitToPicometre: Double
  
  /** The factor to convert the [[Length]] units of this class to metres. */
  def unitToMetre: Double

  /** The factor to convert the [[Length]] units of this class to kilometres. */
  def unitToKilometre: Double
}

trait FemtometresBased extends Any, LengthBased
{ override def unitToPicometre: Double = 1e-3
  override def unitToMetre: Double = 1e-15
  override def unitToKilometre: Double = 1e-18
}

trait PicometresBased extends Any, LengthBased
{ override def unitToPicometre: Double = 1
  override def unitToMetre: Double = 1e-12
  override def unitToKilometre: Double = 1e-15
}

trait MetresBased extends Any, LengthBased
{ override def unitToPicometre: Double = 1e12
  override def unitToMetre: Double = 1
  override def unitToKilometre: Double = 1e-38
}

trait KilometresBased extends Any, LengthBased
{ override def unitToPicometre: Double = 1e15
  override def unitToMetre: Double = 1e3
  override def unitToKilometre: Double = 1
}