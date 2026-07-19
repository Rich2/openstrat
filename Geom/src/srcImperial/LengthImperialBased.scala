/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

trait LengthImperialBased extends Any, LengthBased
{ override def toPicometreFactor: Double = toMetresFactor * 1e12
  override def toKilometresFactor: Double = toMetresFactor * 1e-3
}

trait YardBased extends Any, LengthImperialBased
{ override def toMetresFactor: Double = 0.9144
}

trait MileBased extends Any, LengthImperialBased
{ override def toMetresFactor: Double = 1609.344
  override def toKilometresFactor: Double = 1.60934
}

trait MegamileBased extends Any, LengthImperialBased
{ override def toMetresFactor: Double = 1609.344e6
  override def toKilometresFactor: Double = 1.60934e3
}