/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package impunits

implicit class LengthImperialImplicit(val thisLength: Length)
{
  def toYards: Yards = thisLength match
  { case li: LengthImperial => Yards(li.yardsNum)
    case l => Yards(l.metresNum * Yards.fromMetres)
  }

  /*def milesNum: Double = thisLength match
  { case iunit: LengthImperial => iunit.milesNum
    case l => l.metresNum * LengthImperial.milesToMetres
  }

  def megaMilesNum: Double = thisLength match
  { case iunit: LengthImperial => iunit.megaMilesNum
    case l => l.metresNum * LengthImperial.milesToMetres / 1000000
  }*/
}