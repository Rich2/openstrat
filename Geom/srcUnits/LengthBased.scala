/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait LengthBased extends Any
{ /** The factor to convert the [[Length]] units of this class to picometres. */
  def toPicometreNum: Double
  
  /** The factor to convert the [[Length]] units of this class to metres. */
  def toMetresNum: Double

  /** The factor to convert the [[Length]] units of this class to kilometres. */
  def toKilometresNum: Double
}

trait FemtometresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e-3
  inline override def toMetresNum: Double = 1e-15
  inline override def toKilometresNum: Double = 1e-18
}

trait PicometresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1
  inline override def toMetresNum: Double = 1e-12
  inline override def toKilometresNum: Double = 1e-15
}

trait NanometresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e3
  inline override def toMetresNum: Double = 1e-9
  inline override def toKilometresNum: Double = 1e-12
}

trait AngstromsBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 100
  inline override def toMetresNum: Double = 1e-10
  inline override def toKilometresNum: Double = 1e-13
}

trait MicrometresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e6
  inline override def toMetresNum: Double = 1e-6
  inline override def toKilometresNum: Double = 1e-9
}

trait MillimetresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e9
  inline override def toMetresNum: Double = 1e-3
  inline override def toKilometresNum: Double = 1e-6
}

trait MetresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e12
  inline override def toMetresNum: Double = 1
  inline override def toKilometresNum: Double = 1e-3
}

trait KilometresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e15
  inline override def toMetresNum: Double = 1e3
  inline override def toKilometresNum: Double = 1
}

trait MegametresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e18
  inline override def toMetresNum: Double = 1e6
  inline override def toKilometresNum: Double = 1e3
}

trait GigametresBased extends Any, LengthBased
{ inline override def toPicometreNum: Double = 1e21
  inline override def toMetresNum: Double = 1e9
  inline override def toKilometresNum: Double = 1e6
}