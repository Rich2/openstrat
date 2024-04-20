/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

implicit class LengthImperialImplicit(val thisLength: Length)
{
  def yardsNum: Double = thisLength match
  { case iunit: ImperialLength => iunit.yardsNum
    case l => l.metresNum * ImperialLength.yardsToMetres
  }

  def milesNum: Double = thisLength match
  { case iunit: ImperialLength => iunit.milesNum
    case l => l.metresNum * ImperialLength.milesToMetres
  }

  def megaMilesNum: Double = thisLength match
  { case iunit: ImperialLength => iunit.megaMilesNum
    case l => l.metresNum * ImperialLength.milesToMetres / 1000000
  }
}