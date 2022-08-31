/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A reduced hex grid system that uses the data layers of its parent [[HGridSys]] grid system. */
trait HGridChildSys
{
  def parent: HGridSys
}
