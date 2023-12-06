/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Not sure about the usefulness of this trait yet. */
trait HCenBaseLayer[AA] extends Any
{
  def outt(implicit gridSys: HGridSys, evAA: Show[AA]): String = gridSys match {
    case hg: HGrid => "Not implemented"
    case hgm: HGridMulti => "Not implemented"
  }
}
