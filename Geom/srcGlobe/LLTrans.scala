/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe

trait LLTrans[T]
{
  def fLLTrans(orig: T, f: LatLong => LatLong): T
}
