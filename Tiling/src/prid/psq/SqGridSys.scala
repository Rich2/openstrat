/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** A system of Square tile grids. Could be a single or multiple grids. */
trait SqGridSys extends Any with TGridSys
{ /** C coordinates match 1 to 1 to x coordinates for square grids. */
  final override def yRatio: Double = 1

  /** New Square tile  immutable Tile Arr of Opt data values. */
  final def newSCenOptDGrider[A <: AnyRef](implicit ct: ClassTag[A]): SqCenOptDGrid[A] = new SqCenOptDGrid(new Array[A](numTiles))

  final def newSCenBuffDGrider[A <: AnyRef](implicit ct: ClassTag[A]): SqCenBuffDGrid[A] = SqCenBuffDGrid(numTiles)

  def defaultView(pxScale: Double = 50): SqGridView
}

/** A system of Square tile grids that is flat within a 2D plane. includes all single [[SqGrid]]s. */
trait SqGridSysFlat extends Any with SqGridSys with TGriderFlat