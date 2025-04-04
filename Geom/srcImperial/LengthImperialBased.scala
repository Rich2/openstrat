/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

trait LengthImperialBased extends Any, LengthBased
{ override def toPicometreNum: Double = toMetresNum * 1e12
  override def toKilometresNum: Double = toMetresNum * 1e-3
}

trait YardBased extends Any, LengthImperialBased
{ override def toMetresNum: Double = 0.9144
}

trait MileBased extends Any, LengthImperialBased
{ override def toMetresNum: Double = 1609.344
  override def toKilometresNum: Double = 1.60934
}

trait MegamileBased extends Any, LengthImperialBased
{ override def toMetresNum: Double = 1609.344e6
  override def toKilometresNum: Double = 1.60934e3
}