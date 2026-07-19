/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait VertSeqSpec[+VT] extends Any, SeqSpec[VT]
{
  /** The number of vertices. */
  def numVerts: Int
}