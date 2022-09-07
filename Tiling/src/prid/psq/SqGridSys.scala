/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import reflect.ClassTag

/** A system of Square tile grids. Could be a single or multiple grids. */
trait SqGridSys extends Any with TGridSys
{ def foreach(f: SqCen => Unit): Unit

  /** C coordinates match 1 to 1 to x coordinates for square grids. */
  final override def yRatio: Double = 1

  /** New Square tile data grid for this Square grid system. */
  final def newSCenOptDGrider[A <: AnyRef](implicit ct: ClassTag[A]): SqCenOptLayer[A] = new SqCenOptLayer(new Array[A](numTiles))

  /** Gives the default view in terms of [[SqCoord]] focus and scaling of this square grid system. */
  def defaultView(pxScale: Double = 50): SqGridView

  override final lazy val numTiles: Int =
  { var i = 0
    foreach(_ => i += 1)
    i
  }
}