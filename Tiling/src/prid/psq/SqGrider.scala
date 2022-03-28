/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

trait SqGrider  extends Any with TGrider
{ /** C coordinates match 1 to 1 to x coordinates for square grids. */
  final override def yRatio: Double = 1
}
